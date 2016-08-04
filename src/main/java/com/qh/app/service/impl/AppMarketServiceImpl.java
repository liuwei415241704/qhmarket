package com.qh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.dao.AppAttachDao;
import com.qh.app.dao.AppMarketDao;
import com.qh.app.entity.AppAttach;
import com.qh.app.entity.AppMarket;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppMarketService;

@Service("appMarketService")
public class AppMarketServiceImpl implements AppMarketService {

	private Logger logger = LoggerFactory.getLogger(AppMarketServiceImpl.class);

	@Resource
	private AppMarketDao appMarketDao;
	
	@Resource
	private AppAttachDao appAttachDao;
	
	@Override
	public ResponseService addKinds(AppMarket appMarket) {
		logger.info("****市场信息新增****");
		ResponseService result = new ResponseService();
		int isSuccess = 0;
		if (appMarket != null) {
			// 获取主键
			isSuccess = appMarketDao.addKinds(appMarket);
			if (isSuccess > 0) {
				logger.info("增加单品成功！");
				result.setSuccess();
				result.setData(appMarket.getMarketId());
			} else {
				logger.info("增加单品失败！");
			}
		}
		return result;
	}

	@Override
	public ResponseService queryKindsByKindName(AppMarket appMarket, Page page) {
		logger.info("****查询市场信息****");
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppMarket> resultList = new ArrayList<>();
		if (appMarket != null) {
			List<AppMarket> list = appMarketDao.queryKindsByKindName(appMarket
					.getKindName());
			for (AppMarket appMarketNew : list) {
				String marketId = appMarketNew.getMarketId();
				List<AppAttach> attachList = appAttachDao.queryAttachByIdAndType(marketId, ServiceConstants.ATTACH_TYPE_MARKET);
				appMarketNew.setList(attachList);
				resultList.add(appMarketNew);
			}
			if (list.size() > 0) {
				logger.info("查询市场信息成功！");
				result.setSuccess();
				result.setData(resultList);
			}
		}
		return result;
	}

	@Override
	public ResponseService updatePortrait(AppMarket appMarket) {
		logger.info("****更新头图url****");
		ResponseService result = new ResponseService();
		int isSuccess = 0;
		if (appMarket != null) {
			// 获取主键
			isSuccess = appMarketDao.updatePortrait(appMarket);
			if (isSuccess > 0) {
				logger.info("修改头图成功！");
				result.setSuccess();
			} else {
				logger.info("修改头图失败！");
			}
		}
		return result;
	}

}
