package com.qh.app.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AliPayEntity;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.resource.pay.service.AlipayService;
import com.qh.app.service.AppPayRelationService;
import com.qh.app.util.JSONCommUtils;


/**
 * 支付宝支付
 * 
 * @author lw
 *
 */
@Controller
@RequestMapping({ "/pay" })
public class AppUserAliPayController {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppUserAliPayController.class);
	
	@Resource
	private AlipayService alipayService;
	
	@Resource
	private AppPayRelationService appPayRelationService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/alipay/sign" })
	@ResponseBody
	public ResponseService sign(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****支付宝交易签名开始****");
		ResponseService result = new ResponseService();
		Map<String, String> map = new HashMap<String, String>();
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AliPayEntity.class, requestData);
		AliPayEntity aliPayEntity = (AliPayEntity) requestData.getBody();
		// 拼凑订单签名数据
		String content = alipayService.getSignData(aliPayEntity);
		// 订单签名
		String sign = alipayService.sign(content);
		try {
			/**
			 * 仅需对sign 做URL编码
			 */
			sign = URLEncoder.encode(sign, "UTF-8");
			String url = content + "&sign=" + "\"" + sign + "\""
					+ "&sign_type=\"RSA\"";
			map.put("url", url);
			result.setData(map);
			result.setSuccess();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/**
	 * 支付宝
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/alipay/notify" })
	public String notify(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("****支付宝回调****");
		 return	appPayRelationService.addAliPayTradeInfoRelation(request,response);
	}
}
