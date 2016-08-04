package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.constant.ServiceConstants;
import com.qh.app.entity.AppOrders;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppOrdersService;
import com.qh.app.util.JSONCommUtils;

/**
 * 商品订单处理
 * 
 * @author lw
 * 
 */

@Controller
@RequestMapping({ "/order" })
public class AppOrderController {

	private static Logger logger = LoggerFactory
			.getLogger(AppOrderController.class);

	@Resource
	private AppOrdersService appOrdersService;

	/**
	 * 生成订单(购物车)
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/create" })
	@ResponseBody
	public ResponseService addOrder(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****创建订单****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppOrders.class, requestData);
		AppOrders appOrder = (AppOrders) requestData.getBody();
		appOrder.setStatus(ServiceConstants.APP_ORDERS_CREATE);
		return appOrdersService.addAppOrder(appOrder);
	}
	
	/**
	 * 根据订单状态查询订单和明细
	 * 
	 * @param reqData
	 * @param userId
	 * @param page
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getOrders" })
	@ResponseBody
	public ResponseService getOrders(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****获取订单****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppOrders.class, requestData);
		AppOrders appOrders = (AppOrders) requestData.getBody();
		return appOrdersService.queryOrders(appOrders, page);
	}
}
