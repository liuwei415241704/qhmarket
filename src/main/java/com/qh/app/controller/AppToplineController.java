package com.qh.app.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppToplineService;

/**
 * 每日头条
 * 
 * @author lw
 * 
 */
@RequestMapping({ "/topline" })
@Controller
public class AppToplineController {

	public static Logger logger = Logger.getLogger(AppToplineController.class);

	@Resource
	private AppToplineService appToplineService;

	/**
	 * 查询每日头条
	 * 
	 * @return
	 */
	@RequestMapping({ "/getTopLine" })
	@ResponseBody
	public ResponseService getTopLine() {
		logger.info("****获取每日头条****");
		return appToplineService.queryTopLine();
	}

}
