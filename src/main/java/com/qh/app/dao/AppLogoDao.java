package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppLogo;

public interface AppLogoDao {
	
	/**
	 * 查询首页logo
	 * 
	 * @param appLogo
	 * @return
	 */
	public List<AppLogo> queryLogo(AppLogo appLogo);
}
