package com.qh.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageHelper;
import com.qh.app.constant.ServiceConstants;
import com.qh.app.dao.AppStoreBusinessDao;
import com.qh.app.entity.AppStoreBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppStoreBusinessService;

@Service("appStoreBusinessService")
public class AppStoreBusinessServiceImpl implements AppStoreBusinessService {

	private static Logger logger = LoggerFactory
			.getLogger(AppStoreBusinessServiceImpl.class);

	@Resource
	private AppStoreBusinessDao appStoreBusinessDao;

	@Override
	public ResponseService addStoreBusiness(AppStoreBusiness appStoreBusiness) {
		ResponseService result = new ResponseService();
		if (appStoreBusiness != null) {
			Map<String, String> map = new HashMap<String, String>();
			// 判断商户是否已收藏
			AppStoreBusiness oldStoreBusiness = appStoreBusinessDao
					.queryStoreBusiness(appStoreBusiness);
			if (oldStoreBusiness == null) {
				Integer isSuccess = appStoreBusinessDao
						.addStoreBusiness(appStoreBusiness);
				if (isSuccess > 0) {
					logger.info("****添加收藏成功****");
					map.put("storeid", appStoreBusiness.getStoreId());
					map.put("businessid", appStoreBusiness.getBusinessId());
					result.setData(map);
					result.setSuccess();
				} else {
					logger.info("****添加收藏失败****");
				}
			} else {
				logger.info("****添加收藏失败,已收藏该商户****");
				result.setMsg(ServiceConstants.ADD_STOREBUSINESS_ALREADY_EXITS_MSG);
				result.setCode(ServiceConstants.ADD_STOREBUSINESS_ALREADY_EXITS_CODE);
				map.put("storeid", oldStoreBusiness.getStoreId());
				map.put("businessid", oldStoreBusiness.getBusinessId());
				result.setData(map);
			}
		}
		return result;
	}

	@Override
	public ResponseService deleteStoreBusiness(AppStoreBusiness appStoreBusiness) {
		ResponseService result = new ResponseService();
		if (appStoreBusiness != null) {
			Integer isSuccess = appStoreBusinessDao
					.deleteStoreBusiness(appStoreBusiness);
			if (isSuccess > 0) {
				logger.info("****删除收藏成功****");
				result.setSuccess();
			} else {
				logger.info("****删除收藏失败****");
			}
		}
		return result;
	}

	@Override
	public ResponseService queryStoreBusinessAll(
			AppStoreBusiness appStoreBusiness, Page page) {
		ResponseService result = new ResponseService();
		if (appStoreBusiness != null) {
			if (page != null) {
				PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
			}
			List<AppStoreBusiness> list = appStoreBusinessDao
					.queryStoreBusinessAll(appStoreBusiness.getCustomerId());
			logger.info("****获取收藏记录成功!****");
			result.setData(list);
			result.setSuccess();
		}
		return result;
	}

	@Override
	public ResponseService queryStoreIsCheck(AppStoreBusiness appStoreBusiness) {
		ResponseService result = new ResponseService();
		if (appStoreBusiness != null) {
			AppStoreBusiness storeEntity = appStoreBusinessDao
					.queryStoreIsCheck(appStoreBusiness);
			if (storeEntity != null) {
				result.setCode(ServiceConstants.ADD_STOREBUSINESS_ALREADY_EXITS_CODE);
				result.setMsg(ServiceConstants.ADD_STOREBUSINESS_ALREADY_EXITS_MSG);
				logger.info("****该商户已经被收藏!****");
			} else {
				result.setSuccess();
			}
		}
		return result;
	}

}
