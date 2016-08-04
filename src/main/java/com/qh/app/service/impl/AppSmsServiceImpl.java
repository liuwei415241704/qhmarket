package com.qh.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.dao.AppSmsAuthDao;
import com.qh.app.dao.AppSmsResponseDao;
import com.qh.app.entity.AppSmsAuth;
import com.qh.app.entity.AppSmsResponse;
import com.qh.app.service.AppSmsService;

@Service("appSmsService")
public class AppSmsServiceImpl implements AppSmsService {
	
	private static Logger logger = LoggerFactory.getLogger(AppSmsServiceImpl.class);
	
	@Resource
	private AppSmsAuthDao appSmsAuthDao;

	@Resource
	private AppSmsResponseDao appSmsResponseDao;

	@Override
	public int addSmsLog(AppSmsAuth smsAuth) {
		logger.info("****短信日志插入****");
		Integer isSuccess = appSmsAuthDao.addSmsLog(smsAuth);
		// 插入成功
		if (isSuccess > 0) {
			logger.info("短信日志插入成功");
		} else {
			logger.info("短信日志插入失败");
		}
		return isSuccess;
	}

	@Override
	public AppSmsResponse querySmsResponse(String responseCode) {
		logger.info("****查询返回状态详情描述****");
		AppSmsResponse appSmsResponse = appSmsResponseDao
				.querySmsResponse(responseCode);
		return appSmsResponse;
	}

}
