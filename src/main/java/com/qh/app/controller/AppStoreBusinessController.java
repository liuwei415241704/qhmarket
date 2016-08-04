package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppStoreBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppStoreBusinessService;
import com.qh.app.util.JSONCommUtils;

/**
 * 用户收藏
 * 
 * @author lw
 * 
 */
@Controller
@RequestMapping({ "/store" })
public class AppStoreBusinessController {

	private static Logger logger = LoggerFactory
			.getLogger(AppStoreBusinessController.class);

	@Resource
	private AppStoreBusinessService appStoreBusinessService;

	/**
	 * 增加商户收藏
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/addStore" })
	@ResponseBody
	public ResponseService addStore(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****添加收藏****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppStoreBusiness.class, requestData);
		AppStoreBusiness storeBusiness = (AppStoreBusiness) requestData
				.getBody();
		storeBusiness.setCustomerId(userId);
		return appStoreBusinessService.addStoreBusiness(storeBusiness);
	}

	/**
	 * 删除收藏
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/delStore" })
	@ResponseBody
	public ResponseService delStore(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****删除收藏****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppStoreBusiness.class, requestData);
		AppStoreBusiness storeBusiness = (AppStoreBusiness) requestData
				.getBody();
		storeBusiness.setCustomerId(userId);
		return appStoreBusinessService.deleteStoreBusiness(storeBusiness);
	}

	/**
	 * 查询用户所有收藏
	 * 
	 * @param reqData
	 * @param userId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/queryStoreAll" })
	@ResponseBody
	public ResponseService queryStoreAll(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****获取收藏****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppStoreBusiness.class, requestData);
		AppStoreBusiness storeBusiness = (AppStoreBusiness) requestData
				.getBody();
		storeBusiness.setCustomerId(userId);
		return appStoreBusinessService.queryStoreBusinessAll(storeBusiness,
				page);
	}

	/**
	 * 判断是否已经收藏
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/checkIsStored" })
	@ResponseBody
	public ResponseService isStored(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****判断该商户是否已经收藏****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppStoreBusiness.class, requestData);
		AppStoreBusiness storeBusiness = (AppStoreBusiness) requestData
				.getBody();
		storeBusiness.setCustomerId(userId);
		return appStoreBusinessService.queryStoreIsCheck(storeBusiness);
	}
}
