package com.github.panxiaole.polestar.common.singleton;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ObjectMapper单例,避免重复创建
 *
 * @author panxiaole
 */
public class JacksonMapper {

	private static final ObjectMapper mapper = new ObjectMapper();

	private JacksonMapper() {
	}

	public static ObjectMapper getInstance() {
		return mapper;
	}
}
