package com.haier.polestar.common.response;

import org.springframework.http.HttpStatus;

/**
 * 响应结果生成工具
 *
 * @author panxiaole
 * @date 2019-05-07
 */
public class ResultGenerator {

	/**
	 * 成功响应结果
	 *
	 * @param message 响应信息
	 * @param data    响应内容
	 * @return 响应结果
	 */
	public static <T> Result<T> succeed(String message, T data) {
		return new Result<T>().setSuccess(true).setCode(HttpStatus.OK.value()).setMessage(message).setData(data);
	}

	/**
	 * 成功响应结果
	 *
	 * @param message 响应信息
	 * @return 响应结果
	 */
	public static <T> Result<T> succeed(String message) {
		return succeed(message, null);
	}

	/**
	 * 成功响应结果
	 *
	 * @return 响应结果
	 */
	public static <T> Result<T> succeed() {
		return succeed("操作成功");
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @param message    消息
	 * @param data       错误堆栈
	 * @return 响应结果
	 */
	public static <T> Result<T> failed(ResultCode resultCode, String message, T data) {
		message = message == null ? resultCode.getReason() : resultCode.getReason() + ": " + message;
		return new Result<T>().setSuccess(false).setCode(resultCode.getValue()).setMessage(message).setData(data);
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @param message    消息
	 * @return 响应结果
	 */
	public static <T> Result<T> failed(ResultCode resultCode, String message) {
		return failed(resultCode, message, null);
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @return 响应结果
	 */
	public static <T> Result<T> failed(ResultCode resultCode) {
		return failed(resultCode, null, null);
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @param data       错误堆栈
	 * @return 响应结果
	 */
	public static <T> Result<T> failed(ResultCode resultCode, T data) {
		return failed(resultCode, resultCode.getReason(), data);
	}

	/**
	 * 失败响应结果
	 *
	 * @param message 消息
	 * @return 响应结果
	 */
	public static <T> Result<T> failed(String message) {
		return failed(ResultCode.SUCCEED_REQUEST_FAILED_RESULT, message, null);
	}

	/**
	 * 失败响应结果
	 *
	 * @return 响应结果
	 */
	public static <T> Result<T> failed() {
		return failed(ResultCode.SUCCEED_REQUEST_FAILED_RESULT);
	}
}
