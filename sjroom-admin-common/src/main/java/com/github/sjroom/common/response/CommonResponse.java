package com.github.sjroom.common.response;

import com.github.sjroom.common.CommonStatus;
import com.github.sjroom.common.IResult;
import com.github.sjroom.common.exception.BaseApplicationException;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * @author luobinwen
 */
@Data
public class CommonResponse implements IResult, Serializable {

    private static final long serialVersionUID = 1L;

    protected int code;

    protected String msg;

    protected String exception;

    public CommonResponse() {

    }

    public CommonResponse(IResult resultStatus) {
        this(resultStatus.getCode(), resultStatus.getMsg());
    }

    public CommonResponse(BaseApplicationException e) {
        this(e.getCode(), e.getMsg());
    }

    public CommonResponse(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public boolean isSuccess() {
        return code % 1000 == CommonStatus.SUCCESS.getCode();
    }

    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("code", code);
        map.put("msg", msg);
        map.put("success", isSuccess());
        return map;
    }

}