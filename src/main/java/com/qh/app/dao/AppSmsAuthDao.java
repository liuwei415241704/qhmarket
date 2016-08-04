package com.qh.app.dao;

import com.qh.app.entity.AppSmsAuth;

public interface AppSmsAuthDao {

	/**
	 * 添加信息日志
	 * 
	 * @param smsAuth
	 * @return
	 */
	public Integer addSmsLog(AppSmsAuth smsAuth);

}
