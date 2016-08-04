package com.qh.app.dao;

import com.qh.app.entity.AppVersion;

public interface AppVersionDao {
	
	/**
	 * 根据设备类型获取版本信息
	 * 
	 * @param deviceType
	 * @return
	 */
	public AppVersion queryVersionByDeviceType(String deviceType);
}
