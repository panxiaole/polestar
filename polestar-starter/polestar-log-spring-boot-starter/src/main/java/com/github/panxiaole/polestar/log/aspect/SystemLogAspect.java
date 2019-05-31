package com.github.panxiaole.polestar.log.aspect;

import com.github.panxiaole.polestar.common.singleton.JacksonMapper;
import com.github.panxiaole.polestar.common.util.IpUtil;
import com.github.panxiaole.polestar.log.annotation.SystemLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * 日志切面
 * 输出controller和@SystemLog注解标注方法的执行信息
 *
 * @author panxiaole
 * @date 2018-10-23
 * @see SystemLog
 */
@Aspect
@Component
@Profile({"dev", "test"})
public class SystemLogAspect {

	private static final Logger log = org.slf4j.LoggerFactory.getLogger(SystemLogAspect.class);
	/**
	 * 开始时间
	 */
	private LocalDateTime startTime;

	/**
	 * 申明需要打印日志的切点
	 */
	@Pointcut("execution(public * com.github.panxiaole.polestar..*.controller..*.*(..))")
	private void logPointcuts() {
	}

	@Pointcut("@annotation(com.github.panxiaole.polestar.log.annotation.SystemLog)")
	private void logAnnotations() {
	}

	/**
	 * 前置通知
	 *
	 * @param joinPoint
	 */
	@Before(value = "logPointcuts() || logAnnotations()")
	public void doBefore(JoinPoint joinPoint) {
		try {
			log.info("======================   Log Start [{}] =======================", Thread.currentThread());
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			this.startTime = LocalDateTime.now();
			log.info("请求方IP: {}", IpUtil.getIpAddress());
			log.info("请求地址: {}", request.getRequestURL().toString());
			log.info("请求方式: {}", request.getMethod());
			log.info("请求方法: {}", joinPoint.getSignature());
			log.info("请求参数: {}", Arrays.toString(joinPoint.getArgs()));
		} catch (Exception e) {
			log.error("异常信息: {}", e != null ? e.toString() : null);
			log.info("======================   Log End [{}] =========================", Thread.currentThread());
		}
	}

	/**
	 * 返回通知
	 *
	 * @param object
	 */
	@AfterReturning(returning = "object", pointcut = "logPointcuts() || logAnnotations()")
	public void doAfterReturning(Object object) throws Exception {
		final long difference = ChronoUnit.MILLIS.between(this.startTime, LocalDateTime.now());
		log.info("响应内容: {}", JacksonMapper.getInstance().writeValueAsString(object));
		log.info("耗时: {}s", difference / 1000.0);
		log.info("======================   Log End [{}] =========================", Thread.currentThread());
	}

	/**
	 * 异常通知
	 */
	@AfterThrowing(pointcut = "logPointcuts() || logAnnotations()", throwing = "e")
	public void doAfterThrowing(Throwable e) {
		log.error("异常信息: {}", e != null ? e.toString() : null);
		log.info("======================   Log End [{}] =========================", Thread.currentThread());
	}

}
