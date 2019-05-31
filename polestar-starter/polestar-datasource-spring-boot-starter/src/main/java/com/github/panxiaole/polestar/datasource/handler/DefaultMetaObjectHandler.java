package com.github.panxiaole.polestar.datasource.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充公共字段
 *
 * @author panxiaole
 * @date 2019-04-01
 */
@Component
public class DefaultMetaObjectHandler implements MetaObjectHandler {

	private final static String VERSION = "version";
	private final static String DELETED = "deleted";
	private final static String CREATE_TIME = "createTime";
	private final static String UPDATE_TIME = "updateTime";

	/**
	 * 插入自动填充
	 */
	@Override
	public void insertFill(MetaObject metaObject) {
		this.setInsertFieldValByName(DELETED, Boolean.FALSE, metaObject);
		this.setInsertFieldValByName(VERSION, 0, metaObject);
		this.setInsertFieldValByName(CREATE_TIME, new Date(), metaObject);
		this.setInsertFieldValByName(UPDATE_TIME, new Date(), metaObject);

	}

	/**
	 * 更新自动填充
	 *
	 * @param metaObject
	 */
	@Override
	public void updateFill(MetaObject metaObject) {
		this.setUpdateFieldValByName(UPDATE_TIME, new Date(), metaObject);
	}
}
