package com.qh.app.entity;

/**
 * 配置订单支付签名信息
 * 
 * @orderId 系统唯一订单号
 * @subject 订单名称
 * @body 商品详情
 * @amount 订单金额
 */
public class AliPayEntity {

	private String orderid;
	
	private String subject;
	
	private String body;
	
	private String amount;


	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
