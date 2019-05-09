package com.haier.polestar.common.response;

import org.springframework.http.HttpStatus;

/**
 * 响应结果生成工具
 *
 * @author panxiaole
 * @date   2019-05-07
 */
public class ResultGenerator {

	/**
	 * 成功响应结果
	 *
	 * @param data 内容
	 * @return 响应结果
	 */
	public static <T> Result<T> genOkResult(String message, T data) {
		return new Result<T>().setSuccess(true).setCode(HttpStatus.OK.value()).setMessage(message).setData(data);
	}

	/**
	 * 成功响应结果
	 *
	 * @return 响应结果
	 */
	public static <T> Result<T> genOkResult(String message) {
		return genOkResult(message, null);
	}

	/**
	 * 失败响应结果
	 *
	 * @param code    状态码
	 * @param message 消息
	 * @param data    错误堆栈
	 * @return 响应结果
	 */
	public static <T> Result<T> genFailedResult(int code, String message, T data) {
		return new Result<T>().setSuccess(false).setCode(code).setMessage(message).setData(data);
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @return 响应结果
	 */
	public static <T> Result<T> genFailedResult(ResultCode resultCode) {
		return genFailedResult(resultCode.getValue(), resultCode.getReason(), null);
	}

	/**
	 * 失败响应结果
	 *
	 * @param resultCode 状态码枚举
	 * @param data       错误堆栈
	 * @return 响应结果
	 */
	public static <T> Result<T> genFailedResult(ResultCode resultCode, T data) {
		return genFailedResult(resultCode.getValue(), resultCode.getReason(), data);
	}

	/**
	 * 失败响应结果
	 *
	 * @param message 消息
	 * @return 响应结果
	 */
	public static <T> Result<T> genFailedResult(String message) {
		return genFailedResult(ResultCode.SUCCEED_REQUEST_FAILED_RESULT.getValue(), message, null);
	}

	/**
	 * 失败响应结果
	 *
	 * @return 响应结果
	 */
	public static <T> Result<T> genFailedResult() {
		return genFailedResult(ResultCode.SUCCEED_REQUEST_FAILED_RESULT);
	}
}
