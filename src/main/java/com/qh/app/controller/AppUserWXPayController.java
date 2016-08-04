package com.qh.app.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.WxpayEntity;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.pay.CommonUtil;
import com.qh.app.resource.pay.HttpClientUtil;
import com.qh.app.resource.pay.config.WxpayConfig;
import com.qh.app.resource.pay.service.WxpayService;
import com.qh.app.service.AppPayRelationService;
import com.qh.app.util.JSONCommUtils;
import com.qh.app.util.StringUtil;

/**
 * 微信支付
 * 
 * @author lw
 * 
 */
@Controller
@RequestMapping({ "/pay" })
public class AppUserWXPayController {

	private Logger logger = Logger.getLogger(AppUserWXPayController.class);

	@Resource
	private WxpayService wxpayService;

	@Resource
	private WxpayConfig wxpayConfig;

	@Resource
	private AppPayRelationService appPayRelationService;

	/*
	 * 微信交易签名
	 * 
	 * @param reqData
	 * 
	 * @param userId
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/wxpay/sign" })
	@ResponseBody
	public ResponseService sign(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userid") String userid) {
		logger.info("****微信交易签名开始****");
		ResponseService result = new ResponseService();
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, WxpayEntity.class, requestData);
		WxpayEntity aliPayEntity = (WxpayEntity) requestData.getBody();
		String nonceStr = CommonUtil.generateRandomNumber(32); // 生成32位随机数,用于签名
		aliPayEntity.setNonce_str(nonceStr);
		String postParams = wxpayService.getPostParams(aliPayEntity);
		if (StringUtil.isNotEmpty(postParams)) {
			String responseStr = HttpClientUtil.postXml(wxpayConfig.getWxUrl(),
					postParams);
			result = wxpayService.parsePreOrderInfo(responseStr, nonceStr);
		}
		return result;
	}

	/**
	 * 微信支付回调
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/app/wxpayNotify")
	public String wxPayNotify(HttpServletRequest request,
			HttpServletResponse response) {
		return appPayRelationService.addWxPayTradeInfoRelation(request,
				response);
	}

}
