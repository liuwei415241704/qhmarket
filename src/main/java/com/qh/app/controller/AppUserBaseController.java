package com.qh.app.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.constant.ClientConstants;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppUser;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppUserService;
import com.qh.app.service.ThirdUserService;
import com.qh.app.util.JSONCommUtils;
import com.qh.app.util.RedisUtil;
import com.qh.app.util.StringUtil;
import com.qh.app.util.TokenHelper;

/**
 * 用户逻辑控制
 * 
 * @author lw
 * 
 */

@Controller
@RequestMapping({ "/base/appUser" })
public class AppUserBaseController {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppUserBaseController.class);

	@Resource
	private AppUserService appUserService;
	
	@Resource
	private ThirdUserService thirdUserService;
	
	/**
	 * 用户普通登录
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/login" })
	@ResponseBody
	public ResponseService login(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****登陆开始****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		return appUserService.queryAppUserByLogin(user);
	}

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @param response
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/registerUser" }, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public ResponseService registerUser(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****注册开始****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		user.setType(ClientConstants.LOGIN_NOMAL_TYPE);
		// 用户注册
		return appUserService.addAppUser(user);
	}

	/**
	 * 第三方登录
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/third/login" })
	@ResponseBody
	public ResponseService thirdLogin(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****登陆开始****");
		ResponseService result = new ResponseService();
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据openid判断该第三方用户是否登录过,没有登录给第三方注册一个本地用户
		if (StringUtil.isNotEmpty(user.getOpenId())) {
			AppUser appUser = appUserService.queryAppUserByOpenId(user
					.getOpenId());
			if (appUser != null) {
				String token = TokenHelper.gennerateToken();
				map.put("userid", appUser.getCustomerId());
				map.put("token", token);
				// 将token放到缓存里
				RedisUtil.set(appUser.getCustomerId(), token);
				result.setData(map);
				result.setSuccess();
			} else {
				// 第三方用户注册
				result = thirdUserService.addAppUser(user);
			}

		}
		return result;
	}

	/**
	 * 检测手机号是否存在
	 * 
	 * @param request
	 * @param response
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/checkMobilePhone" })
	@ResponseBody
	public ResponseService checkMobilePhone(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****检查手机号是否存在****");
		ResponseService result = new ResponseService();
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		if (user != null) {
			// 用户已注册
			user = appUserService.queryAppUserByMobile(user.getMobile());
			if (user != null) {
				result.setMsg(ServiceConstants.MOBILE_EXIST_MSG);
				result.setCode(ServiceConstants.MOBILE_EXIST_CODE);
			} else {
				result.setSuccess();
			}
		}
		return result;
	}

	/**
	 * 忘记密码
	 * 
	 * @param request
	 * @param response
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/forgotPwd" })
	@ResponseBody
	public ResponseService forgetPwd(HttpServletRequest request,
			HttpServletResponse response,
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****忘记密码****");
		ResponseService result = new ResponseService();
		RequestData<Object> requestData = new RequestData<>(); 
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppUser.class, requestData);
		AppUser user = (AppUser) requestData.getBody();
		if (user != null) {
			user = appUserService.queryAppUserByMobile(user.getMobile());
			if (user != null) {
				return appUserService.updateAppUserPwd(user);
			} 
		}
		return result;
	}


}
