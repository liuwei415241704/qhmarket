package com.qh.app.service.sms;

import com.qh.app.entity.MessageResult;

/**
 * 短信平台接入口
 * 
 */
public interface SmsService {
	
	/**
	 * 发送短信
	 * 
	 * @param mobilePhone
	 * @param smsCode
	 * @return
	 */
	public MessageResult smsSend(String mobilePhone, int smsCode,
			String smsTemplateType);

	/**
	 * 新增短信
	 * 
	 * @return
	 */
	public int addSmsCode();
}
