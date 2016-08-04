package com.qh.app.service.sms.impl;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qh.app.constant.ClientConstants;
import com.qh.app.entity.MessageResult;
import com.qh.app.resource.sms.JsonReqClient;
import com.qh.app.resource.sms.SysConfig;
import com.qh.app.service.sms.SmsService;

@Service("smsServiceImpl")
public class SmsServiceImpl implements SmsService {
	
	@Override
	public int addSmsCode() {
		double d = Math.random() * 10000;
		int validateCode = (int) d;
		if (String.valueOf(validateCode).length() < 4) {
			validateCode = (int) addSmsCode();
		}
		return validateCode;
	}

	
	@Override
	public MessageResult smsSend(String mobilePhone, int smsCode,
			String smsTemplateType) {
		String account_id = SysConfig.getInstance().getProperty("account_id");
		String token = SysConfig.getInstance().getProperty("token");
		String app_id = SysConfig.getInstance().getProperty("app_id");
		
		String sms_template = "";
		if (ClientConstants.SMS_REGISTER_TEMPLATE_TYPE.equals(smsTemplateType)) {
			sms_template = SysConfig.getInstance().getProperty(
					ClientConstants.SMS_REGISTER_TEMPLATE);
		}
		if (ClientConstants.SMS_MODIFYPWD_TEMPLATE_TYPE.equals(smsTemplateType)) {
			sms_template = SysConfig.getInstance().getProperty(
					ClientConstants.SMS_MODIFYPWD_TEMPLATE);
		}
		if (ClientConstants.SMS_FORGETPWD_TEMPLETE_TYPE.equals(smsTemplateType)) {
			sms_template = SysConfig.getInstance().getProperty(
					ClientConstants.SMS_MODIFYPWD_TEMPLATE);
		}
		
		
		System.out.println(account_id + "------" + token + "-----" + app_id
				+ "------" + sms_template);
		String msg = new JsonReqClient().templateSMS(account_id, token, app_id,
				sms_template, mobilePhone, smsCode + "");
		System.out.println("::::::::::::::::::::::::::::::"+msg);
		MessageResult mr = JSON.parseObject(msg, MessageResult.class);
		return mr;
	}
}
