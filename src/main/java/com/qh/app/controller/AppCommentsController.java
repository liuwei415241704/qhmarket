package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppComments;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppCommentsService;
import com.qh.app.util.JSONCommUtils;

/**
 * 评论处理层
 * 
 * @author lw
 * 
 */

@Controller
@RequestMapping({ "/comments" })
public class AppCommentsController {
	
	private static Logger logger = LoggerFactory
			.getLogger(AppCommentsController.class);

	@Resource
	private AppCommentsService appCommentsService;

	/**
	 * 提交评论
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/addComments" })
	@ResponseBody
	public ResponseService submitComments(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****提交评论开始****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppComments.class, requestData);
		AppComments coments = (AppComments) requestData.getBody();
		coments.setCustomerId(userId);
		return appCommentsService.addComments(coments);
	}

	/**
	 * 获取评论
	 * 
	 * @param reqData
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "/getComments" })
	@ResponseBody
	public ResponseService getComments(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****获取评论****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppComments.class, requestData);
		AppComments coments = (AppComments) requestData.getBody();
		return appCommentsService.queryComments(coments,page);
	}
}
