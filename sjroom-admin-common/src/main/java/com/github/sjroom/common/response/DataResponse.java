package com.github.sjroom.common.response;

import com.alibaba.fastjson.JSON;
import com.github.sjroom.common.CommonStatus;
import com.github.sjroom.common.IResult;
import lombok.Data;

/**
 * 
 * @author luobinwen
 *
 * @param <T>
 */
@Data
public class DataResponse<T> extends CommonResponse {

	private static final long serialVersionUID = -4197769202915890604L;

	protected T data;

	public DataResponse() {
	}

	public DataResponse(CommonResponse commonResponse) {
		this.setCode(commonResponse.getCode());
		this.setMsg(commonResponse.getMsg());
	}

	public DataResponse(T data) {
		super(CommonStatus.SUCCESS.joinSystemStatusCode());
		this.data = data;
	}

	public DataResponse(Integer retCode, String retMsg) {
		super(retCode, retMsg);
	}
	
	public DataResponse(IResult resultStatus){
		this(resultStatus.getCode(),resultStatus.getMsg());
	}

	public DataResponse(Integer retCode, String retMsg, T data) {
		super(retCode, retMsg);
		this.data = data;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}
