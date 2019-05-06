package com.haier.polestar.starter.log.annotation;

import com.haier.polestar.starter.log.aspect.SystemLogAspect;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在启动类上添加该注解, 开启系统日志输出功能
 *
 * @author panxiaole
 * @date 2019-05-01
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(SystemLogAspect.class)
public @interface EnableOutputSystemLog {

}
