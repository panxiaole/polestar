package com.haier.polestar.common.exception;

/**
 * 业务异常
 *
 * @author panxiaole
 * @date 2019-04-27
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

}
