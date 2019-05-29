package com.haier.polestar.redis.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * Redis缓存配置
 *
 * @author panxiaole
 * @date 2019-04-03
 */
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Slf4j
@EnableCaching(proxyTargetClass = true)
@EnableConfigurationProperties(RedisProperties.class)
public class RedisCacheAutoConfiguration extends CachingConfigurerSupport {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Bean
	@Override
	public CacheManager cacheManager() {
		RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(this.redisConnectionFactory);
		RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				// 不缓存 null 值
				.disableCachingNullValues()
				// 使用注解时的序列化、反序列化对
				.serializeKeysWith(DefaultRedisCacheManager.STRING_PAIR)
				.serializeValuesWith(DefaultRedisCacheManager.JACKSON_PAIR);
		// 初始化RedisCacheManager
		return new DefaultRedisCacheManager(redisCacheWriter, defaultCacheConfig);
	}

	/**
	 * 如果 @Cacheable、@CachePut、@CacheEvict 等注解没有配置 key，则使用这个自定义 key 生成器
	 * <p>自定义缓存的 key 时，难以保证 key 的唯一性
	 * <p>此时最好指定方法名，比如：@Cacheable(value="", key="{#root.methodName, #id}")
	 */
	@Bean
	@Override
	public KeyGenerator keyGenerator() {
		// 比如 User 类 list(Integer page, Integer size) 方法
		// 用户 A 请求：list(1, 2)
		// redis 缓存的 key：User.list#1,2
		return (target, method, params) -> {
			final String dot = ".";
			final StringBuilder sb = new StringBuilder(32);
			// 类名
			sb.append(target.getClass().getSimpleName());
			sb.append(dot);
			// 方法名
			sb.append(method.getName());
			// 如果存在参数
			if (0 < params.length) {
				sb.append("#");
				// 带上参数
				String comma = "";
				for (final Object param : params) {
					sb.append(comma);
					if (param == null) {
						sb.append("NULL");
					} else {
						sb.append(param.toString());
					}
					comma = ",";
				}
			}
			return sb.toString();
		};
	}

	/**
	 * 操作缓存错误处理，主要是打印日志
	 */
	@Bean
	@Override
	public CacheErrorHandler errorHandler() {
		return new SimpleCacheErrorHandler() {
			@Override
			public void handleCacheGetError(
					final RuntimeException e, final Cache cache, final Object key) {
				log.error("==> cache: {}", cache);
				log.error("==>   key: {}", key);
				log.error("==> error: {}", e.getMessage());
				super.handleCacheGetError(e, cache, key);
			}

			@Override
			public void handleCachePutError(
					final RuntimeException e, final Cache cache, final Object key, final Object value) {
				log.error("==> cache: {}", cache);
				log.error("==>   key: {}", key);
				log.error("==> value: {}", value);
				log.error("==> error: {}", e.getMessage());
				super.handleCachePutError(e, cache, key, value);
			}

			@Override
			public void handleCacheEvictError(
					final RuntimeException e, final Cache cache, final Object key) {
				log.error("==> cache: {}", cache);
				log.error("==>   key: {}", key);
				log.error("==> error: {}", e.getMessage());
				super.handleCacheEvictError(e, cache, key);
			}

			@Override
			public void handleCacheClearError(final RuntimeException e, final Cache cache) {
				log.error("==> cache: {}", cache);
				log.error("==> error: {}", e.getMessage());
				super.handleCacheClearError(e, cache);
			}
		};
	}

	/**
	 * 配置@Cacheable的序列化器和过期时间
	 *
	 * @return redisCacheConfiguration
	 */
	@Bean
	public RedisCacheConfiguration redisCacheConfiguration() {
		return RedisCacheConfiguration.defaultCacheConfig()
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(DefaultRedisCacheManager.JACKSON_SERIALIZER))
				.entryTtl(Duration.ofDays(30));
	}

	/**
	 * 自定义redisTemplate
	 * 使用jackson序列化器
	 * 自定义redisTemplate,使用jackson序列化器
	 *
	 * @param redisConnectionFactory redisConnectionFactory
	 * @return RedisTemplate<Object, Object>
	 */
	@Bean("redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(DefaultRedisCacheManager.JACKSON_SERIALIZER);
		return template;
	}

	/**
	 * 自定义stringRedisTemplate
	 * 使用jackson序列化器
	 *
	 * @param redisConnectionFactory redisConnectionFactory
	 * @return StringRedisTemplate
	 */
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		template.setDefaultSerializer(DefaultRedisCacheManager.JACKSON_SERIALIZER);
		return template;
	}
}
