package com.github.panxiaole.polestar.log.annotation;

import java.lang.annotation.*;

/**
 * 标记需要输出日志的方法
 *
 * @author panxiaole
 * @date 2018-10-23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SystemLog {

}
