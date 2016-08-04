package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppUser;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppUserService;
import com.qh.app.util.JSONCommUtils;

/**
 * 用户基本信息业务控制层(需要token)
 * 
 * @author lw
 *
 */
@Controller
@RequestMapping({ "/appUser" })
public class AppUserServiceController {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppUserBaseController.class);
	
	@Resource
	private AppUserService appUserService;
	
	/**
	 * 修改用户基本资料
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/modifyAppuserBaseInfo" })
	@ResponseBody
	public ResponseService modifyAppuserBaseInfo(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****修改用户基本资料****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		user.setCustomerId(userId);
		return appUserService.updateAppUser(user);
	}
	
	/**
	 * 查询用户基本资料
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getUserInfo" })
	@ResponseBody
	public ResponseService getUserInfoById(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****获取用户资料****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		user.setCustomerId(userId);
		return appUserService.queryAppUserById(user);
	}

	/**
	 * 修改用户密码
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/modifyAppuserPwd" })
	@ResponseBody
	public ResponseService modifyAppuserPwd(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****修改用户密码****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		user.setCustomerId(userId);
		return appUserService.updateAppUserPwd(user);
	}

}
