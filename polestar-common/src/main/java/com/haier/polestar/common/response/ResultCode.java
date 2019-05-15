package com.haier.polestar.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应状态码枚举类
 *
 * @author panxiaole
 * @date 2019-05-07
 */
@Getter
@AllArgsConstructor
public enum ResultCode {

	/**
	 * 成功请求，但结果不是期望的成功结果
	 */
	SUCCEED_REQUEST_FAILED_RESULT(1000, "请求成功，但未获得期望的结果"),

	/**
	 * 查询失败
	 */
	FIND_FAILED(2000, "查询失败"),

	/**
	 * 保存失败
	 */
	SAVE_FAILED(2001, "保存失败"),

	/**
	 * 更新失败
	 */
	UPDATE_FAILED(2002, "更新失败"),

	/**
	 * 删除失败
	 */
	DELETE_FAILED(2003, "删除失败"),

	/**
	 * 账户名重复
	 */
	DUPLICATE_NAME(2004, "用户名重复"),

	/**
	 * 业务异常
	 */
	BUSINESS_EXCEPTION(3000, "业务异常"),

	/**
	 * 参数解析失败
	 */
	BAD_REQUEST(4000, "参数解析失败"),

	/**
	 * 未授权
	 */
	UNAUTHORIZED(4001, "未授权"),

	/**
	 * 数据库异常
	 */
	SQL_EXCEPTION(4002, "服务运行SQL异常"),

	/**
	 * 验证异常
	 */
	VIOLATION_EXCEPTION(4003, "实体校验异常"),

	/**
	 * 未找到请求资源
	 */
	NOT_FOUND(4004, "未找到请求资源"),

	/**
	 * 不支持当前请求方法
	 */
	METHOD_NOT_ALLOWED(4005, "不支持当前请求方法"),

	/**
	 * 违反唯一约束异常
	 */
	DUPLICATE_KEY_EXCEPTION(4006, "违反唯一约束"),

	/**
	 * 乐观锁异常
	 */
	OPTIMISTIC_LOCKER_EXCEPTION(4007, "该记录已被其他进程修改或删除,请刷新后重试"),

	/**
	 * 不支持当前请求方法
	 */
	UNSUPPORTED_MEDIA_TYPE(4015, "不支持的媒体类型"),

	/**
	 * 不支持当前请求方法
	 */
	INTERNAL_SERVER_ERROR(5000, "系统内部错误");


	private final int value;

	private final String reason;

}
