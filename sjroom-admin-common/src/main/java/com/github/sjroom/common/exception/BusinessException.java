package com.github.sjroom.common.exception;


import com.github.sjroom.common.CommonStatus;
import com.github.sjroom.common.IResult;

/**
 * 公用业务异常
 *
 * @author luobw
 */
public class BusinessException extends BaseApplicationException {

    private static final long serialVersionUID = -8943298004576967279L;

    public BusinessException(String message) {
        super(message, null);
    }

    public BusinessException(CommonStatus commonStatus) {
        super(commonStatus.getCode(),commonStatus.getMsg());
    }

    public BusinessException(String message, Object[] args) {
        super(message, args);
    }

    public BusinessException(String message, Throwable cause, Object[] args) {
        super(message, cause, args);
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public BusinessException(int code, String message, Object[] args) {
        super(code, message, args);
    }

    /**
     * @param code    错误码
     * @param message 错误信息
     * @param cause   原始异常
     * @param args    额外参数
     */
    public BusinessException(int code, String message, Throwable cause, Object[] args) {
        super(code, message, cause, args);
    }

    public BusinessException(IResult status) {
        this(status.getCode(), status.getMsg());
    }

}
