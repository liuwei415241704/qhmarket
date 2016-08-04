package com.qh.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.constant.RedisServiceConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppSms;
import com.qh.app.entity.AppSmsAuth;
import com.qh.app.entity.AppSmsResponse;
import com.qh.app.entity.MessageResult;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppSmsService;
import com.qh.app.service.sms.SmsService;
import com.qh.app.util.DateUtil;
import com.qh.app.util.JSONCommUtils;
import com.qh.app.util.RedisUtil;

/**
 * 短信控制层
 * 
 * @author lw
 *
 */

@Controller
@RequestMapping({ "/sms" })
public class AppSmsController {

	private static Logger logger = LoggerFactory
			.getLogger(AppSmsController.class);

	@Resource
	private SmsService smsService;

	@Resource
	private AppSmsService appSmsService;
	
	/**
	 * 短信发送
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/sendMsg" })
	@ResponseBody
	public ResponseService sendMsg(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****请求验证码****");
		ResponseService result = new ResponseService();
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppSms.class, requestData);
		AppSms sms = (AppSms) requestData.getBody();
		// 创建验证码
		int msgCode = smsService.addSmsCode();
		// 发送验证码
		MessageResult mr = smsService.smsSend(sms.getMobilephone(), msgCode,
				sms.getSmstemplatetype());
		if (mr == null) {
			return result;
		}
		String respCode = mr.getResp().getRespCode();
		// 根据返回的状态码查询短信发送详情
		AppSmsResponse asp = appSmsService.querySmsResponse(respCode);
		if (asp != null) {
			result.setCode(asp.getCode());
			result.setMsg(asp.getMsg());
		}
		if (mr.getResp().getTemplateSMS() != null) {
			// 记录发送的信息
			AppSmsAuth smsAuth = new AppSmsAuth();
			smsAuth.setCreatedtime(DateUtil.parseYMDHMS(mr.getResp()
					.getTemplateSMS().getCreateDate()));
			smsAuth.setSmsid(mr.getResp().getTemplateSMS().getSmsId());
			smsAuth.setSmscode(String.valueOf(msgCode));
			smsAuth.setSmsphone(sms.getMobilephone());
			// 发送成功
			if (ServiceConstants.APPSMS_SEND_SUCCESS_CODE.equals(respCode)) {
				Map<String, String> map = new HashMap<String, String>();
				// 将短信放到缓存里面
				RedisUtil.setex(sms.getMobilephone(), String.valueOf(msgCode),
						RedisServiceConstants.MSG_EXP);
				logger.info("短信验证码:" + msgCode);
				map.put("msgcode", String.valueOf(msgCode));
				// 将短信返回给前端做校验
				result.setSuccess();
				result.setData(map);
				smsAuth.setSmsstatus("Y");
			} else {
				// 发送失败
				smsAuth.setSmsstatus("N");
			}
			appSmsService.addSmsLog(smsAuth);
		}
		return result;
	}
}
