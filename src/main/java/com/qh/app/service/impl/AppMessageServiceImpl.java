package com.qh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.qh.app.dao.AppMessageDao;
import com.qh.app.entity.AppMessages;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppMessageService;

@Service("appMessageService")
public class AppMessageServiceImpl implements AppMessageService {

	public static Logger logger = LoggerFactory
			.getLogger(AppMessageServiceImpl.class);

	@Resource
	private AppMessageDao appMessageDao;

	@Override
	public ResponseService addMessage(AppMessages appMessage) {
		ResponseService result = new ResponseService();
		if (appMessage != null) {
			int success = appMessageDao.addMessage(appMessage);
			if (success > 0) {
				result.setSuccess();
				logger.info("****新增站内留言成功!****");
			}
		}
		return result;
	}

	@Override
	public ResponseService queryMessageByUserId(String userId, Page page) {
		ResponseService result = new ResponseService();
		if(page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		List<AppMessages> list = new ArrayList<>();
		if (!StringUtils.isEmpty(userId)) {
			list = appMessageDao.queryMessageByUserId(userId);
			result.setSuccess();
			result.setData(list);
			logger.info("****根据用户ID获取站内留言成功!****");
		}
		return result;
	}
}
