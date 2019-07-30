package com.github.panxiaole.polestar.common.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 上下文工具
 *
 * @author panxiaole
 * @date 2019-04-03
 */
public class ContextUtil {

	/**
	 * 获取 request
	 *
	 * @return request
	 */
	public static HttpServletRequest getRequest() {
		final ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		return attributes == null ? null : attributes.getRequest();
	}
}
