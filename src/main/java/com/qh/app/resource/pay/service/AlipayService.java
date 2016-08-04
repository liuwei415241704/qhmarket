package com.qh.app.resource.pay.service;

import com.qh.app.entity.AliPayEntity;

public interface AlipayService {
	public String getSignData(AliPayEntity aliPayEntity);

	public String getRechargeSignData(AliPayEntity aliPayEntity);

	public String sign(String content);
}
