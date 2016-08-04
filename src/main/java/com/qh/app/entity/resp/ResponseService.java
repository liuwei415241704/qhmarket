package com.qh.app.entity.resp;

import com.qh.app.constant.ServiceConstants;

public class ResponseService {
	private String msg;
	private String code;
	private Object data;

	public ResponseService() {
		this.msg = ServiceConstants.FALSE_MSG;
		this.code = ServiceConstants.FALSE_CODE;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object object) {
		this.data = object;
	}

	public void setSuccess() {
		this.msg = ServiceConstants.SUCCESS_MSG;
		this.code = ServiceConstants.SUCCESS_CODE;
	}

}
