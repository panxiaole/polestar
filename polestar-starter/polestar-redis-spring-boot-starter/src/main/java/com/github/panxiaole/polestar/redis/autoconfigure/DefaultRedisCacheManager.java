package com.github.panxiaole.polestar.redis.autoconfigure;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.panxiaole.polestar.common.singleton.JacksonMapper;
import com.github.panxiaole.polestar.redis.annotation.CacheExpire;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Redis 容易出现缓存问题（超时、Redis 宕机等），当使用 spring cache 的注解 @Cacheable、@CachePut 等处理缓存问题时， 我们无法使用 try catch
 * 处理出现的异常，所以最后导致结果是整个服务报错无法正常工作。 通过自定义 DefaultRedisCacheManager 并继承 RedisCacheManager 来处理异常可以解决这个问题
 * <p>http://www.spring4all.com/article/937
 *
 * @author panxiaole
 * @date 2019-04-20
 */
@Slf4j
public class DefaultRedisCacheManager extends RedisCacheManager implements ApplicationContextAware, InitializingBean {

	private Map<String, RedisCacheConfiguration> initialCacheConfiguration = new LinkedHashMap<>();
	private ApplicationContext applicationContext;
	private static StringRedisSerializer STRING_SERIALIZER = new StringRedisSerializer();
	static Jackson2JsonRedisSerializer<Object> JACKSON_SERIALIZER = new Jackson2JsonRedisSerializer<>(Object.class);

	static {
		ObjectMapper objectMapper = JacksonMapper.getInstance();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		JACKSON_SERIALIZER.setObjectMapper(objectMapper);
	}

	/**
	 * key serializer pair
	 */
	static RedisSerializationContext.SerializationPair<String> STRING_PAIR =
			RedisSerializationContext.SerializationPair.fromSerializer(STRING_SERIALIZER);

	/**
	 * value serializer pair
	 */
	static RedisSerializationContext.SerializationPair<Object> JACKSON_PAIR =
			RedisSerializationContext.SerializationPair.fromSerializer(JACKSON_SERIALIZER);


	DefaultRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
		super(cacheWriter, defaultCacheConfiguration);
	}

	@Override
	public Cache getCache(@NonNull String name) {
		Cache cache = super.getCache(name);
		return new RedisCacheWrapper(cache);
	}

	@Override
	public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public void afterPropertiesSet() {
		String[] beanNames = this.applicationContext.getBeanNamesForType(Object.class);
		for (String beanName : beanNames) {
			Class clazz = this.applicationContext.getType(beanName);
			this.add(clazz);
		}
		super.afterPropertiesSet();
	}

	@NonNull
	@Override
	protected Collection<RedisCache> loadCaches() {
		List<RedisCache> caches = new LinkedList<>();
		for (Map.Entry<String, RedisCacheConfiguration> entry : this.initialCacheConfiguration.entrySet()) {
			caches.add(super.createRedisCache(entry.getKey(), entry.getValue()));
		}
		return caches;
	}

	private void add(Class clazz) {
		ReflectionUtils.doWithMethods(
				clazz,
				method -> {
					ReflectionUtils.makeAccessible(method);
					CacheExpire cacheExpire = AnnotationUtils.findAnnotation(method, CacheExpire.class);
					if (cacheExpire == null) {
						return;
					}
					Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
					if (cacheable != null) {
						this.add(cacheable.cacheNames(), cacheExpire);
						return;
					}
					Caching caching = AnnotationUtils.findAnnotation(method, Caching.class);
					if (caching != null) {
						Cacheable[] cs = caching.cacheable();
						if (cs.length > 0) {
							for (Cacheable c : cs) {
								if (c != null) {
									this.add(c.cacheNames(), cacheExpire);
								}
							}
						}
					} else {
						CacheConfig cacheConfig = AnnotationUtils.findAnnotation(clazz, CacheConfig.class);
						if (cacheConfig != null) {
							this.add(cacheConfig.cacheNames(), cacheExpire);
						}
					}
				},
				method -> null != AnnotationUtils.findAnnotation(method, CacheExpire.class));
	}

	private void add(String[] cacheNames, CacheExpire cacheExpire) {
		for (String cacheName : cacheNames) {
			if (cacheName == null || "".equals(cacheName.trim())) {
				continue;
			}
			long expire = cacheExpire.expire();
			log.debug("cache name<{}> expire: {}", cacheName, expire);
			if (expire >= 0) {
				// 缓存配置
				RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
						.entryTtl(Duration.ofSeconds(expire))
						.disableCachingNullValues()
						.serializeKeysWith(STRING_PAIR)
						.serializeValuesWith(JACKSON_PAIR);
				this.initialCacheConfiguration.put(cacheName, config);
			} else {
				log.warn("{} use default expiration.", cacheName);
			}
		}
	}

	@SuppressWarnings("NullableProblems")
	protected static class RedisCacheWrapper implements Cache {

		private Cache cache;

		RedisCacheWrapper(Cache cache) {
			this.cache = cache;
		}

		@Override
		public String getName() {
			log.debug("get cache name: {}", this.cache.getName());
			try {
				return this.cache.getName();
			} catch (Exception e) {
				log.error("get cache name => {}", e.getMessage());
				return null;
			}
		}

		@Override
		public Object getNativeCache() {
			log.debug("native cache: {}", this.cache.getNativeCache());
			try {
				return this.cache.getNativeCache();
			} catch (Exception e) {
				log.error("get native cache => {}", e.getMessage());
				return null;
			}
		}

		@Override
		public ValueWrapper get(@NonNull Object o) {
			log.debug("get cache => o: {}", o);
			try {
				return this.cache.get(o);
			} catch (Exception e) {
				log.error("get cache => o: {}, error: {}", o, e.getMessage());
				return null;
			}
		}

		@Override
		public <T> T get(@NonNull Object o, Class<T> aClass) {
			log.debug("get cache => o: {}, clazz: {}", o, aClass);
			try {
				return this.cache.get(o, aClass);
			} catch (Exception e) {
				log.error("get cache => o: {}, clazz: {}, error: {}", o, aClass, e.getMessage());
				return null;
			}
		}

		@Override
		public <T> T get(@NonNull Object o, @NonNull Callable<T> callable) {
			log.debug("get cache => o: {}", o);
			try {
				return this.cache.get(o, callable);
			} catch (Exception e) {
				log.error("get cache => o: {}, error: {}", o, e.getMessage());
				return null;
			}
		}

		@Override
		public void put(@NonNull Object o, Object o1) {
			try {
				this.cache.put(o, o1);
				log.debug("put cache => o: {}, o1: {}", o, o1);
			} catch (Exception e) {
				log.error("put cache => o: {}, o1: {}, error: {}", o, o1, e.getMessage());
			}
		}

		@Override
		public ValueWrapper putIfAbsent(@NonNull Object o, Object o1) {
			try {
				ValueWrapper valueWrapper = this.cache.putIfAbsent(o, o1);
				log.debug("put cache if absent => o: {}, o1: {}", o, o1);
				return valueWrapper;
			} catch (Exception e) {
				log.error("put cache if absent => o: {}, o1: {}, error: {}", o, o1, e.getMessage());
				return null;
			}
		}

		@Override
		public void evict(@NonNull Object o) {
			try {
				this.cache.evict(o);
				log.debug("evict cache => o: {}", o);
			} catch (Exception e) {
				log.error("evict cache => o: {}, error: {}", o, e.getMessage());
			}
		}

		@Override
		public void clear() {
			try {
				this.cache.clear();
				log.debug("clear cache");
			} catch (Exception e) {
				log.error("clear cache => error: {}", e.getMessage());
			}
		}
	}
}
