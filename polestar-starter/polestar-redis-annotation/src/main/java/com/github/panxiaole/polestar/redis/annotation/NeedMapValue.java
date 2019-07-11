package com.github.panxiaole.polestar.redis.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标识需要使用@MapValue为字段赋值的方法
 * 方法的返回值必须是Object或集合
 *
 * @author panxiaole
 * @date 2019-06-08
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedMapValue {

}
