package com.qh.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.dao.AppLogoDao;
import com.qh.app.entity.AppLogo;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppLogoService;

@Service("appLogoService")
public class AppLogoServiceImpl implements AppLogoService {

	private static Logger logger = LoggerFactory
			.getLogger(AppLogoServiceImpl.class);
	
	@Resource
	private AppLogoDao appLogoDao;
	
	@Override
	public ResponseService queryLogo(AppLogo appLogo) {
		ResponseService result = new ResponseService();
		if(appLogo != null) {
			List<AppLogo> appLogoList = appLogoDao.queryLogo(appLogo);
			result.setSuccess();
			result.setData(appLogoList);
			logger.info("****获取首页logo成功!****");
		} else {
			logger.info("****获取首页logo失败!****");
		}
		return result;
	}

}
