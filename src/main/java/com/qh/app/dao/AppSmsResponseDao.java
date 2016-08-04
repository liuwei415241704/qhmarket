package com.qh.app.dao;

import com.qh.app.entity.AppSmsResponse;

public interface AppSmsResponseDao {
	
	/**
	 * 查询短信返回码的详情描述
	 * 
	 * @param responseCode
	 * @return
	 */
	public AppSmsResponse querySmsResponse(String code);

}
