package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppTopline;

public interface AppToplineDao {
	
	/**
	 * 获取每日头条
	 * 
	 * @return
	 */
	public List<AppTopline> queryTopline();
}
