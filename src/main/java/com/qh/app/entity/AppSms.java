package com.qh.app.entity;
/**
 * 短信请求模板
 * @author lw
 *
 */
public class AppSms {
	
	private String mobilephone;
	
	private String smstemplatetype;

	public String getSmstemplatetype() {
		return smstemplatetype;
	}

	public void setSmstemplatetype(String smstemplatetype) {
		this.smstemplatetype = smstemplatetype;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}

}
