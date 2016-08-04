package com.qh.app.entity;

public class MessageResponse {
	private String respCode;
	private MessageTemplateSMS templateSMS;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public MessageTemplateSMS getTemplateSMS() {
		return templateSMS;
	}

	public void setTemplateSMS(MessageTemplateSMS templateSMS) {
		this.templateSMS = templateSMS;
	}
}
