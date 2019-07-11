package com.github.panxiaole.polestar.redis.aspect;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.panxiaole.polestar.redis.service.MapValueService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * NeedMapValue切面
 *
 * @author panxiaole
 * @date 2019-06-08
 */
@Aspect
@Component
public class MapValueAspect {

	@Autowired
	private MapValueService mapValueService;

	@Around("@annotation(com.github.panxiaole.polestar.redis.annotation.NeedMapValue)")
	public Object setFieldValue(ProceedingJoinPoint pjp) throws Throwable {
		// 获取切面方法的返回值
		Object proceed = pjp.proceed();
		Collection collection;
		if (proceed instanceof Collection) {
			collection = (Collection) proceed;
		} else if (proceed instanceof Page) {
			// 适配spring data jpa分页结果集
			collection = ((Page) proceed).getRecords();
		} else {
			collection = Arrays.asList(proceed);
		}
		// 为返回值中指定的字段赋值
		mapValueService.mapValue(collection);
		return proceed;
	}

}
