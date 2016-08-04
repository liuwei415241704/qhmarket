package com.qh.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.dao.AppPayInfoDao;
import com.qh.app.entity.AppPayInfo;
import com.qh.app.service.AppPayInfoService;
import com.qh.app.util.GenerateUUID;

@Service("appPayInfoService")
public class AppPayInfoServiceImpl implements AppPayInfoService {

	private static Logger logger = LoggerFactory
			.getLogger(AppPayInfoServiceImpl.class);
	
	@Resource
	private AppPayInfoDao appPayInfoDao;

	@Override
	public AppPayInfo queryAppPayInfoByOrdersId(String ordersId) {
		logger.info("****查询支付信息****");
		AppPayInfo payInfo = appPayInfoDao.queryAppPayInfoByOrdersId(ordersId);
		return payInfo;
	}

	@Override
	public void addAppPayInfo(AppPayInfo payInfo) {
		logger.info("****添加支付信息****");
		String payId = GenerateUUID.getShortUuid();
		payInfo.setPayid(payId);
		appPayInfoDao.addAppPayInfo(payInfo);
	}

	@Override
	public void updateAppPayInfo(AppPayInfo payInfo) {
		logger.info("****更新支付信息****");
		appPayInfoDao.updateAppPayInfo(payInfo);
	}

}
