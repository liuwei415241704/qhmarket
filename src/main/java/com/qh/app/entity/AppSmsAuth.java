package com.qh.app.entity;

import java.util.Date;
/**
 * 短信发送详情
 * @author lw
 *
 */
public class AppSmsAuth {
	
	private String smsid;

	private String smsphone;

	private String smscode;

	private String smstype;

	private String smsstatus;

	private String smscontent;

	private Date createdtime;

	public String getSmsid() {
		return smsid;
	}

	public void setSmsid(String smsid) {
		this.smsid = smsid == null ? null : smsid.trim();
	}

	public String getSmsphone() {
		return smsphone;
	}

	public void setSmsphone(String smsphone) {
		this.smsphone = smsphone == null ? null : smsphone.trim();
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode == null ? null : smscode.trim();
	}

	public String getSmstype() {
		return smstype;
	}

	public void setSmstype(String smstype) {
		this.smstype = smstype == null ? null : smstype.trim();
	}

	public String getSmsstatus() {
		return smsstatus;
	}

	public void setSmsstatus(String smsstatus) {
		this.smsstatus = smsstatus == null ? null : smsstatus.trim();
	}

	public String getSmscontent() {
		return smscontent;
	}

	public void setSmscontent(String smscontent) {
		this.smscontent = smscontent == null ? null : smscontent.trim();
	}

	public Date getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}
}