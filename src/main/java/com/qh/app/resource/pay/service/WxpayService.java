package com.qh.app.resource.pay.service;

import com.qh.app.entity.WxpayEntity;
import com.qh.app.entity.resp.ResponseService;


public interface WxpayService {

	public String getPostParams(WxpayEntity aliPayEntity);

	public ResponseService parsePreOrderInfo(String responseStr, String nonceStr);

}
