package com.haier.polestar.redis.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 设置缓存过期时间
 *
 * @author panxiaole
 * @date 2019-04-20
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface CacheExpire {

	/**
	 * 过期时间，默认 60s
	 */
	@AliasFor("expire")
	long value() default 60;

	/**
	 * 过期时间，默认 60s
	 */
	@AliasFor("value")
	long expire() default 60;

}
