package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppLogo;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppLogoService;
import com.qh.app.util.JSONCommUtils;

/**
 * 首页LOGO
 * 
 * @author lw
 * 
 */

@Controller
@RequestMapping({ "/logo" })
public class AppLogoController {

	private static Logger logger = LoggerFactory
			.getLogger(AppLogoController.class);

	@Resource
	private AppLogoService appLogoService;
	
	/**
	 * 获取首页头图
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getLogo" })
	@ResponseBody
	public ResponseService getStartLogo(
			@ModelAttribute(value = "reqData") JSONObject reqData) {
		logger.info("****获取首页LOGO****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppLogo.class, requestData);
		AppLogo appLogo = (AppLogo) requestData.getBody();
		return appLogoService.queryLogo(appLogo);
	}

}