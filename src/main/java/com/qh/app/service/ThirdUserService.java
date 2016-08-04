package com.qh.app.service;

import com.qh.app.entity.AppUser;
import com.qh.app.entity.resp.ResponseService;


/**
 * 第三方用户注册
 * 
 * @author lw
 * 
 */
public interface ThirdUserService {
	
	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService addAppUser(AppUser user);
}
