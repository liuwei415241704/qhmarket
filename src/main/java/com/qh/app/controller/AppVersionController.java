package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppOrders;
import com.qh.app.entity.AppVersion;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppVersionService;
import com.qh.app.util.JSONCommUtils;

/**
 * 版本管理
 * 
 * @author lw
 * 
 */
@RequestMapping({ "/version" })
public class AppVersionController {

	public static Logger logger = LoggerFactory
			.getLogger(AppVersionController.class);

	@Resource
	private AppVersionService appVersionService;

	@SuppressWarnings("unchecked")
	@RequestMapping({ "/getVersion" })
	@ResponseBody
	public ResponseService getVersion(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****根据设备类型获取最新版本信息****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppOrders.class, requestData);
		AppVersion appVersion = (AppVersion) requestData.getBody();
		return appVersionService.queryVersionByDeviceType(appVersion);
	}
}
