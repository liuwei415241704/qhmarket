package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppBusinessService;
import com.qh.app.util.JSONCommUtils;

/**
 * 商户处理控制层
 * 
 * @author lw
 * 
 */
@Controller
@RequestMapping({ "/business" })
public class AppBusinessController {

	private static Logger logger = LoggerFactory
			.getLogger(AppBusinessController.class);

	@Resource
	private AppBusinessService appBusinessService;

	/**
	 * 获取首页商户
	 * 
	 * @return
	 */
	@RequestMapping({ "/getFrontBusiness" })
	@ResponseBody
	public ResponseService getFrontBusiness() {
		logger.info("****获取首页商铺信息****");
		return appBusinessService.queryFrontBusiness();
	}

	/**
	 * 获取所有商户分类
	 * 
	 * @return
	 */
	@RequestMapping({ "/getBusinessCategoryAll" })
	@ResponseBody
	public ResponseService getBusinessCategoryAll() {
		logger.info("****获取商户所有分类****");
		return appBusinessService.queryBusinessCategoryAll();
	}

	/**
	 * 根据分类ID获取商户信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getBusinessByCategoryId" })
	@ResponseBody
	public ResponseService getBusinessByCategoryId(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据分类ID获取商户****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppBusiness.class, requestData);
		AppBusiness appBusiness = (AppBusiness) requestData.getBody();
		return appBusinessService.queryBusinessByCategoryId(appBusiness, page);
	}

	/**
	 * 根据商户ID获取商户信息
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getBusinessByBusinessId" })
	@ResponseBody
	public ResponseService getBusinessByBusinessId(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****根据商户ID获取详细信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppBusiness.class, requestData);
		AppBusiness appBusiness = (AppBusiness) requestData.getBody();
		return appBusinessService.queryBusinessByBusinessId(appBusiness);
	}

	/**
	 * 根据商户名搜索商户
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getBusinessByBusinessName" })
	@ResponseBody
	public ResponseService getBusinessByBusinessName(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商户名模糊查询商户****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppBusiness.class, requestData);
		AppBusiness appBusiness = (AppBusiness) requestData.getBody();
		return appBusinessService
				.queryBusinessByBusinessName(appBusiness, page);
	}

	/**
	 * 查询天天特价商户
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getBusinessByGrade" })
	@ResponseBody
	public ResponseService getBusinessByGrade(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商户级别查询所有商户信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppBusiness.class, requestData);
		return appBusinessService.queryBusinessByGrade(page);

	}
}
