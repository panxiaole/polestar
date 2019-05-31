package com.github.panxiaole.polestar.common.exception;

import com.github.panxiaole.polestar.common.response.Result;
import com.github.panxiaole.polestar.common.response.ResultCode;
import com.github.panxiaole.polestar.common.response.ResultGenerator;
import com.github.panxiaole.polestar.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.stream.Collectors;

/**
 * 全局异常处理
 *
 * @author panxiaole
 * @date 2019-04-20
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionResolver {
	/**
	 * IllegalArgumentException异常处理
	 * 返回状态码:400
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({IllegalArgumentException.class})
	public Result handleIllegalArgumentException(IllegalArgumentException e) {
		return defHandler(ResultCode.BAD_REQUEST, e);
	}

	/**
	 * ConstraintViolationException异常处理
	 * 返回状态吗:500
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ConstraintViolationException.class)
	public Result validatorException(ConstraintViolationException e) {
		String msg = e.getConstraintViolations()
				.stream()
				.map(ConstraintViolation::getMessage)
				.collect(Collectors.joining("、"));
		return defHandler(ResultCode.VIOLATION_EXCEPTION, msg, e);
	}

	/**
	 * AccessDeniedException异常处理
	 * 返回状态码:403
	 */
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler({AccessDeniedException.class})
	public Result handleAccessDeniedException(AccessDeniedException e) {
		return defHandler(ResultCode.UNAUTHORIZED, e);
	}

	/**
	 * HttpRequestMethodNotSupportedException异常处理
	 * 返回状态码:405
	 */
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		return defHandler(ResultCode.METHOD_NOT_ALLOWED, e);
	}

	/**
	 * HttpMediaTypeNotSupportedException异常处理
	 * 返回状态码:415
	 */
	@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
	@ExceptionHandler({HttpMediaTypeNotSupportedException.class})
	public Result handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		return defHandler(ResultCode.UNSUPPORTED_MEDIA_TYPE, e);
	}

	/**
	 * SQLException异常处理
	 * 返回状态码:500
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({SQLException.class, BadSqlGrammarException.class})
	public Result handleSQLException(SQLException e) {
		return defHandler(ResultCode.SQL_EXCEPTION, e);
	}

	/**
	 * DuplicateKeyException异常处理
	 * 返回状态码:500
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({DuplicateKeyException.class})
	public Result handleDuplicateKeyException(DuplicateKeyException e) {
		return defHandler(ResultCode.DUPLICATE_KEY_EXCEPTION, e);
	}

	/**
	 * BusinessException异常处理
	 * 返回状态码:500
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(BusinessException.class)
	public Result handleBusinessException(BusinessException e) {
		return defHandler(ResultCode.BUSINESS_EXCEPTION, ExceptionUtil.getExceptionCause(e), e);
	}

	/**
	 * 所有异常统一处理
	 * 返回状态码:500
	 */
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		return defHandler(ResultCode.INTERNAL_SERVER_ERROR, e);
	}

	/**
	 * 构造异常处理结果
	 *
	 * @param resultCode 返回码
	 * @param e          异常
	 * @return Result
	 */
	private Result defHandler(ResultCode resultCode, Exception e) {
		return defHandler(resultCode, null, e);
	}

	/**
	 * 构造异常处理结果
	 *
	 * @param resultCode 返回码
	 * @param message    自定义异常信息
	 * @param e          异常
	 * @return Result
	 */
	private Result defHandler(ResultCode resultCode, String message, Exception e) {
		log.error(resultCode.getReason(), e);
		return ResultGenerator.failed(resultCode, message, e.getMessage());
	}
}
