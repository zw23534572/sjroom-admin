package com.github.sjroom.common.exception;


import com.github.sjroom.common.CommonStatus;
import com.github.sjroom.common.IResult;


/**
 * 参数验证异常
 *
 * @author manson
 */
public class ArgumetException extends BaseApplicationException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ArgumetException(IResult resultStatus) {
        super(resultStatus.getCode(), resultStatus.getMsg());
    }

    public ArgumetException(int code, String message) {
        super(code, message);
    }

    public ArgumetException(String message) {
        super(CommonStatus.ILLEGAL_PARAM.getCode(), CommonStatus.ILLEGAL_PARAM.getMessage(message));
    }

}
