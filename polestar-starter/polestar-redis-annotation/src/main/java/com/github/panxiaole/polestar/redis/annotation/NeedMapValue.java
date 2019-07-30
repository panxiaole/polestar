package com.github.panxiaole.polestar.redis.annotation;

import java.lang.annotation.*;

/**
 * 标识需要使用@MapValue为字段赋值的方法
 * 方法的返回值必须是Object或集合
 *
 * @author panxiaole
 * @date 2019-06-08
 */
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedMapValue {

}
