package com.qh.app.service;

import com.qh.app.entity.AppLogo;
import com.qh.app.entity.resp.ResponseService;

public interface AppLogoService {
	
	/**
	 * 获取首页logo
	 * 
	 * @param appLogo
	 * @return
	 */
	public ResponseService queryLogo(AppLogo appLogo);
}
