package com.qh.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.qh.app.dao.AppVersionDao;
import com.qh.app.entity.AppVersion;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppVersionService;

@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	public static Logger logger = LoggerFactory
			.getLogger(AppVersionServiceImpl.class);

	@Resource
	private AppVersionDao appVersionDao;

	@Override
	public ResponseService queryVersionByDeviceType(AppVersion appVersion) {
		ResponseService result = new ResponseService();
		if (appVersion != null) {
			String deviceType = appVersion.getType();
			if (!StringUtils.isEmpty(deviceType)) {
				appVersion = appVersionDao.queryVersionByDeviceType(appVersion
						.getType());
				result.setSuccess();
				result.setData(appVersion);
				logger.info("****根据设备类型获取最新版本号成功!****");
			}
		}
		return result;
	}
}
