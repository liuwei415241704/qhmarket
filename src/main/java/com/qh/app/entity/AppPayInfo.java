package com.qh.app.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付信息类
 * 
 * @author lw
 * 
 */
public class AppPayInfo {

	private String payid;

	private String ordersid;

	private String currency;

	private BigDecimal amount;

	private String channel;

	private String channelid;

	private String bank;

	private String codeurl;

	private BigDecimal tradestatus;

	private Date createdtime;

	public String getPayid() {
		return payid;
	}

	public void setPayid(String payid) {
		this.payid = payid == null ? null : payid.trim();
	}

	public String getOrdersid() {
		return ordersid;
	}

	public void setOrdersid(String ordersid) {
		this.ordersid = ordersid == null ? null : ordersid.trim();
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency == null ? null : currency.trim();
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel == null ? null : channel.trim();
	}

	public String getChannelid() {
		return channelid;
	}

	public void setChannelid(String channelid) {
		this.channelid = channelid == null ? null : channelid.trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.trim();
	}

	public String getCodeurl() {
		return codeurl;
	}

	public void setCodeurl(String codeurl) {
		this.codeurl = codeurl == null ? null : codeurl.trim();
	}

	public BigDecimal getTradestatus() {
		return tradestatus;
	}

	public void setTradestatus(BigDecimal tradestatus) {
		this.tradestatus = tradestatus;
	}

	public Date getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(Date createdtime) {
		this.createdtime = createdtime;
	}
}