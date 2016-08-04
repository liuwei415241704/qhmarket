package com.qh.app.service;

import com.qh.app.entity.AppUser;
import com.qh.app.entity.resp.ResponseService;


/**
 * 用户接口
 * 
 * @author lw
 * 
 */
public interface AppUserService {

	/**
	 * 根据手机号码查询用户
	 * 
	 * @param user
	 * @return
	 */
	public AppUser queryAppUserByMobile(String mobile);

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService addAppUser(AppUser user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService queryAppUserByLogin(AppUser user);
	
	/**
	 * 第三方登录，根据openId查询用户信息
	 * 
	 * @param openId
	 * @return
	 */
	public AppUser queryAppUserByOpenId(String openId);
	
	/**
	 * 第三方注册
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService addThirdAppUser(AppUser user);

	/**
	 * 修改用户资料
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService updateAppUser(AppUser user);

	/**
	 * 根据用户id获取用户信息
	 * 
	 * @param userId
	 * @return
	 */
	public ResponseService queryAppUserById(AppUser user);

	/**
	 * 更新用户头像
	 * 
	 * @param userid
	 * @return
	 */
	public ResponseService updatePortrait(AppUser appUser);

	/**
	 * 更新用户密码
	 * 
	 * @param user
	 * @return
	 */
	public ResponseService updateAppUserPwd(AppUser user);
}
