package com.qh.app.controller;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.AppMessages;
import com.qh.app.entity.Page;
import com.qh.app.entity.req.RequestData;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppMessageService;
import com.qh.app.util.JSONCommUtils;

/**
 * 站内留言控制层
 * 
 * @author lw
 * 
 */
@RequestMapping({ "/message" })
@Controller
public class AppMessageController {

	public static Logger logger = LoggerFactory
			.getLogger(AppMessageController.class);

	@Resource
	private AppMessageService appMessageService;

	/**
	 * 提交站内留言
	 * 
	 * @param reqData
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping({ "/addMessage" })
	@ResponseBody
	public ResponseService addMessage(
			@ModelAttribute(value = "reqData") JSONObject reqData,
			@ModelAttribute(value = "userId") String userId) {
		logger.info("****提交站内留言****");
		RequestData<Object> requestData = new RequestData<>();
		requestData = (RequestData<Object>) JSONCommUtils.parseRequestBody(
				reqData, AppMessages.class, requestData);
		AppMessages appMessage = (AppMessages) requestData.getBody();
		appMessage.setCustomerId(userId);
		return appMessageService.addMessage(appMessage);
	}
	
	/**
	 * 根据用户ID查询站内留言
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping({ "/getMessage" })
	@ResponseBody
	public ResponseService getMessageByUserId(
			@ModelAttribute(value = "userId") String userId,
			@ModelAttribute(value = "page") Page page) {
		logger.info("****根据用户ID获取站内留言****");
		return appMessageService.queryMessageByUserId(userId, page);
	}
}
