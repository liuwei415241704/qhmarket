package com.qh.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.dao.AppToplineDao;
import com.qh.app.entity.AppTopline;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppToplineService;

@Service("appToplineService")
public class AppToplineServiceImpl implements AppToplineService {

	public static Logger logger = LoggerFactory
			.getLogger(AppToplineServiceImpl.class);

	@Resource
	private AppToplineDao appToplineDao;

	@Override
	public ResponseService queryTopLine() {
		ResponseService result = new ResponseService();
		List<AppTopline> list = appToplineDao.queryTopline();
		result.setSuccess();
		result.setData(list);
		logger.info("****获取头条信息成功!****");
		return result;
	}

}
