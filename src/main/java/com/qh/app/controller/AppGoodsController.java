package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppGoods;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppGoodsService;
import com.qh.app.util.JSONCommUtils;

/**
 * 商品控制层
 * 
 * @author lw
 * 
 */
@RequestMapping({ "/goods" })
@Controller
public class AppGoodsController {

	private static Logger logger = LoggerFactory
			.getLogger(AppGoodsController.class);

	@Resource
	private AppGoodsService appGoodsService;

	/**
	 * 根据商户ID获取商品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getGoodsByBusinessId" })
	@ResponseBody
	public ResponseService getGoodsByBusinessId(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商户ID获取商品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppGoods.class, requestData);
		AppGoods appGoods = (AppGoods) requestData.getBody();
		return appGoodsService.queryGoodsByBusinessId(appGoods, page);
	}

	/**
	 * 根据商品ID获取商品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getGoodsByGoodsID" })
	@ResponseBody
	public ResponseService getGoodsByGoodsId(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****根据商品ID获取商品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppGoods.class, requestData);
		AppGoods appGoods = (AppGoods) requestData.getBody();
		return appGoodsService.queryGoodsByGoodsId(appGoods);
	}

	/**
	 * 根据商品名称模糊查询商品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getGoodsByGoodsName" })
	@ResponseBody
	public ResponseService getGoodsByGoodsName(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商品名称获取商品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppGoods.class, requestData);
		AppGoods appGoods = (AppGoods) requestData.getBody();
		return appGoodsService.queryGoodsByGoodsName(appGoods, page);
	}

	/**
	 * 根据商品级别或级别ID获取商品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getGoodsByGrade" })
	@ResponseBody
	public ResponseService getGoodsByGrade(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商品级别名称或级别ID获取商品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppGoods.class, requestData);
		AppGoods appGoods = (AppGoods) requestData.getBody();
		return appGoodsService.queryGoodsByGrade(appGoods, page);
	}

	/**
	 * 根据商品类别名称或ID获取商品信息
	 * 
	 * @param reqData
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getGoodsByCategory" })
	@ResponseBody
	public ResponseService getGoodsByCategory(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据商品类别名称或级别ID获取商品信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppGoods.class, requestData);
		AppGoods appGoods = (AppGoods) requestData.getBody();
		return appGoodsService.queryGoodsByCategory(appGoods, page);

	}
}
