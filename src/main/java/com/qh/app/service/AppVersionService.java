package com.qh.app.service;

import com.qh.app.entity.AppVersion;
import com.qh.app.entity.resp.ResponseService;

public interface AppVersionService {
	
	/**
	 * 根据设备类别获取最新版本号
	 * 
	 * @param appVersion
	 * @return
	 */
	public ResponseService queryVersionByDeviceType(AppVersion appVersion);
}
