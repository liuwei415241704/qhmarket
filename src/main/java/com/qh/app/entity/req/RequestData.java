package com.qh.app.entity.req;

public class RequestData<T> {
	private String reqData;
	private RequestHead requestHead;
	private T body;

	public String getReqData() {
		return this.reqData;
	}

	public void setReqData(String reqData) {
		this.reqData = reqData;
	}

	public RequestHead getRequestHead() {
		return this.requestHead;
	}

	public void setRequestHead(RequestHead requestHead) {
		this.requestHead = requestHead;
	}

	public T getBody() {
		return this.body;
	}

	public void setBody(T body) {
		this.body = body;
	}

}