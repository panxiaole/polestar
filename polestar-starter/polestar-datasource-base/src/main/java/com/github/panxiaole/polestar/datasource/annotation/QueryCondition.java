package com.github.panxiaole.polestar.datasource.annotation;

import java.lang.annotation.*;

/**
 * 查询条件
 *
 * @author panxiaole
 * @date 2019-05-23
 */
@Inherited
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QueryCondition {

	/**
	 * 字段名
	 */
	String field();

	/**
	 * 条件
	 */
	Contition condition();

	/**
	 * 条件分类
	 */
	enum Contition {
		/**
		 * 小于
		 */
		LT,
		/**
		 * 小于等于
		 */
		LE,
		/**
		 * 大于
		 */
		GT,
		/**
		 * 大于等于
		 */
		GE
	}
}
