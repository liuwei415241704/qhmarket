package com.qh.app.service;

import com.qh.app.entity.resp.ResponseService;

public interface AppToplineService {
	
	/**
	 * 获取每日头条
	 * 
	 * @return
	 */
	public ResponseService queryTopLine();
	
}
