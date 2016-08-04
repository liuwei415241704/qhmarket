package com.qh.app.dao;

import com.qh.app.entity.AppUser;


public interface AppUserDao {
	/**
	 * 根据手机号码查询用户
	 * 
	 * @param mobile
	 * @return
	 */
	public AppUser queryAppUserByMobile(String mobile);

	/**
	 * 注册用户
	 * 
	 * @param user
	 * @return
	 */
	public int addAppUser(AppUser user);

	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	public AppUser queryAppUser(AppUser user);
	
	/**
	 * 第三方登录，根据openId查询用户信息
	 * 
	 * @param openId
	 * @return
	 */
	public AppUser queryAppUserByOpenId(String openId);

	/**
	 * 根据id获取用户基本信息
	 * 
	 * @param userId
	 * @return
	 */
	public AppUser queryAppUserById(String userId);

	/**
	 * 更新用户资料
	 * 
	 * @param user
	 * @return
	 */
	public int updateAppUser(AppUser user);

	/**
	 * 更改用户密码
	 * @param userid
	 * @return
	 */
	public int updateAppUserPwd(AppUser user);

}
