package com.qh.app.service;

import com.qh.app.entity.AppSmsAuth;
import com.qh.app.entity.AppSmsResponse;


public interface AppSmsService {
	
	/**
	 * 生成短信记录
	 * 
	 * @param smsAuth
	 * @return
	 */
	public int addSmsLog(AppSmsAuth smsAuth);

	/**
	 * 获取短信返回状态码详情
	 * 
	 * @param smsAuth
	 * @return
	 */
	public AppSmsResponse querySmsResponse(String responseCode);
}
