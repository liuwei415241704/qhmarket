package com.qh.app.resource.pay.config;

public class AlipayConfig {
	// 合作者身份ID
	private static String partner;
	// 卖家支付宝账号
	private static String seller;
	// 商户私钥
	private static String rsaPrivate;
	// 商户公钥
	private static String rsaPublic;
	// 支付交易回调URL
	private static String payNotifyUrl;
	// 字符编码格式 目前支持 gbk 或 utf-8
	private static  String inputCharset;
	// 签名方式 不需修改
	private static String signType;
	// 调试用，创建TXT日志文件夹路径
	private static String logPath;
	// 支付宝消息验证地址
	private static String httpsVerifyUrl;

	public String getHttpsVerifyUrl() {
		return httpsVerifyUrl;
	}

	public  String getPartner() {
		return partner;
	}

	public  void setPartner(String partner) {
		AlipayConfig.partner = partner;
	}

	public  String getSeller() {
		return seller;
	}

	public  void setSeller(String seller) {
		AlipayConfig.seller = seller;
	}

	public  String getRsaPrivate() {
		return rsaPrivate;
	}

	public  void setRsaPrivate(String rsaPrivate) {
		AlipayConfig.rsaPrivate = rsaPrivate;
	}

	public  String getRsaPublic() {
		return rsaPublic;
	}

	public  void setRsaPublic(String rsaPublic) {
		AlipayConfig.rsaPublic = rsaPublic;
	}

	public  String getPayNotifyUrl() {
		return payNotifyUrl;
	}

	public  void setPayNotifyUrl(String payNotifyUrl) {
		AlipayConfig.payNotifyUrl = payNotifyUrl;
	}

	public  String getInputCharset() {
		return inputCharset;
	}

	public  void setInputCharset(String inputCharset) {
		AlipayConfig.inputCharset = inputCharset;
	}

	public  String getSignType() {
		return signType;
	}

	public  void setSignType(String signType) {
		AlipayConfig.signType = signType;
	}

	public  String getLogPath() {
		return logPath;
	}

	public  void setLogPath(String logPath) {
		AlipayConfig.logPath = logPath;
	}

	public  void setHttpsVerifyUrl(String httpsVerifyUrl) {
		AlipayConfig.httpsVerifyUrl = httpsVerifyUrl;
	}



}
