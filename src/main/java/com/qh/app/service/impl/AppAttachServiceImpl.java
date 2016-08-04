package com.qh.app.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.qh.app.dao.AppAttachDao;
import com.qh.app.entity.AppAttach;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppAttachService;

@Service("appAttachService")
public class AppAttachServiceImpl implements AppAttachService {

	private static Logger logger = LoggerFactory
			.getLogger(AppAttachServiceImpl.class);
	
	@Resource
	private AppAttachDao appAttachDao;
	
	@Override
	public ResponseService addAttach(AppAttach appAttach) {
		logger.info("****新增附件****");
		ResponseService result = new ResponseService();
		int isSuccess = 0;
		if (appAttach != null) {
			// 获取主键
			isSuccess = appAttachDao.addAttach(appAttach);
			if (isSuccess > 0) {
				logger.info("增加附件成功！");
				result.setSuccess();
			} else {
				logger.info("增加附件失败！");
			}
		}
		return result;
	}

}
