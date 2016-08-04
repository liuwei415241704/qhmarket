package com.qh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.qh.app.dao.AppBusinessDao;
import com.qh.app.entity.AppBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppBusinessService;

@Service("appBusinessService")
public class AppBusinessImpl implements AppBusinessService {

	public static Logger logger = LoggerFactory
			.getLogger(AppBusinessImpl.class);

	@Resource
	private AppBusinessDao appBusinessDao;

	@Override
	public ResponseService queryFrontBusiness() {
		ResponseService result = new ResponseService();
		List<AppBusiness> appBusinessList = appBusinessDao.queryFrontBusiness();
		result.setSuccess();
		result.setData(appBusinessList);
		logger.info("****获取首页商户成功!****");
		return result;
	}

	@Override
	public ResponseService queryBusinessCategoryAll() {
		ResponseService result = new ResponseService();
		List<AppBusiness> appBusinessList = appBusinessDao
				.queryBusinessCategoryAll();
		result.setSuccess();
		result.setData(appBusinessList);
		logger.info("****获取商户所有分类成功!****");
		return result;
	}

	@Override
	public ResponseService queryBusinessByCategoryId(AppBusiness appBusiness,
			Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppBusiness> list = new ArrayList<>();
		if (appBusiness != null) {
			String businessCategoryId = appBusiness.getCategoryId();
			if (!StringUtils.isEmpty(businessCategoryId)) {
				list = appBusinessDao
						.queryBusinessByCategoryId(businessCategoryId);
			}
			result.setSuccess();
			result.setData(list);
			logger.info("****根据分类ID获取商户信息成功!****");
		}
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public ResponseService queryBusinessByBusinessId(AppBusiness appBusiness) {
		ResponseService result = new ResponseService();
		if (appBusiness != null) {
			AppBusiness business = appBusinessDao
					.queryBusinessByBusinessId(appBusiness.getBusinessId());
			// 根据商户ID获取商户得分
			result.setSuccess();
			result.setData(business);
			logger.info("****获取商户信息成功!****");
		}
		return result;
	}

	@Override
	public ResponseService queryBusinessByBusinessName(AppBusiness appBusiness,
			Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppBusiness> list = new ArrayList<>();
		if (appBusiness != null) {
			list = appBusinessDao.queryBusinessByBusinessName(appBusiness
					.getName());
			// 获取商户得分
			result.setSuccess();
			result.setData(list);
			logger.info("****搜索商户信息成功!****");
		}
		return result;
	}

	@Override
	public ResponseService queryBusinessByGrade(Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppBusiness> list = appBusinessDao.queryBusinessByGrade();
		result.setSuccess();
		result.setData(list);
		logger.info("****获取天天特价商户成功!****");
		return result;
	}

}
