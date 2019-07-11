package com.github.panxiaole.polestar.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识需要调用方法赋值的字段
 *
 * @author panxiaole
 * @date 2019-06-08
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MapValue {

	/**
	 * 调用赋值方法存放的类
	 */
	Class<?> bean();

	/**
	 * 用于赋值的方法
	 */
	String method();

	/**
	 * 入参字段名
	 */
	String source();

	/**
	 * 目标字段名
	 */
	String target() default "label";

}
