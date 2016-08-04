package com.qh.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.qh.app.dao.AppGoodsDao;
import com.qh.app.dao.AppOrdersDetailDao;
import com.qh.app.entity.AppGoods;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppGoodsService;

@Service("appGoodsService")
public class AppGoodsServiceImpl implements AppGoodsService {
	private static Logger logger = LoggerFactory
			.getLogger(AppGoodsServiceImpl.class);

	@Resource
	private AppGoodsDao appGoodsDao;
	
	@Resource
	private AppOrdersDetailDao appOrdersDetailDao;
	
	@Override
	public ResponseService queryGoodsByBusinessId(AppGoods appGoods, Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppGoods> list = new ArrayList<>();
		List<AppGoods> returnList = new ArrayList<>();
		if (appGoods != null) {
			String businessId = appGoods.getBusinessId();
			if (!StringUtils.isEmpty(businessId)) {
				list = appGoodsDao.queryGoodsByBusinessId(businessId);
				if (list.size() > 0) {
					for (AppGoods returnAppGoods : list) {
						int salesCount = appOrdersDetailDao.countSales(returnAppGoods.getGoodsId());
						returnAppGoods.setSalesCount(String.valueOf(salesCount));
						returnList.add(returnAppGoods);
					}
				}
			}
			result.setSuccess();
			result.setData(returnList);
			logger.info("****根据商户ID获取商品信息成功!****");
		}
		return result;
	}

	@Override
	public ResponseService queryGoodsByGoodsId(AppGoods appGoods) {
		ResponseService result = new ResponseService();
		if (appGoods != null) {
			String goodsId = appGoods.getGoodsId();
			if (!StringUtils.isEmpty(goodsId)) {
				appGoods = appGoodsDao.queryGoodsByGoodsId(goodsId);
			}
			result.setSuccess();
			result.setData(appGoods);
			logger.info("****根据商品ID获取商品信息****");
		}
		return result;
	}

	@Override
	public ResponseService queryGoodsByGoodsName(AppGoods appGoods, Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppGoods> list = new ArrayList<>();
		if (appGoods != null) {
			String name = appGoods.getName();
			if (!StringUtils.isEmpty(name)) {
				list = appGoodsDao.queryGoodsByGoodsName(name);
			}
			result.setSuccess();
			result.setData(list);
			logger.info("****根据商品名获取商品信息成功!****");
		}
		return result;
	}

	@Override
	public ResponseService queryGoodsByGrade(AppGoods appGoods, Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppGoods> list = new ArrayList<>();
		if (appGoods != null) {
			String gradeName = appGoods.getGradeName();
			if (!StringUtils.isEmpty(gradeName)) {
				list = appGoodsDao.queryGoodsByGradeName(gradeName);
				logger.info("****根据商品级别名称获取商品信息成功!****");
			}
			String gradeId = appGoods.getGoodsId();
			if (!StringUtils.isEmpty(gradeId)) {
				list = appGoodsDao.queryGoodsByGradeId(gradeId);
				logger.info("****根据商品级别ID获取商品信息成功!****");
			}
			result.setSuccess();
			result.setData(list);

		}
		return result;
	}

	@Override
	public ResponseService queryGoodsByCategory(AppGoods appGoods, Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage().intValue(), page
					.getPageSize().intValue());
		}
		ResponseService result = new ResponseService();
		List<AppGoods> list = new ArrayList<>();
		if (appGoods != null) {
			String categoryName = appGoods.getCategoryName();
			if (!StringUtils.isEmpty(categoryName)) {
				list = appGoodsDao.queryGoodsByCategoryName(categoryName);
				logger.info("****根据商品分类名称获取商品信息成功!****");
			}
			String categoryId = appGoods.getCategoryId();
			if (!StringUtils.isEmpty(categoryId)) {
				list = appGoodsDao.queryGoodsByCategoryId(categoryId);
				logger.info("****根据商品分类ID获取商品信息成功!****");
			}
			result.setSuccess();
			result.setData(list);
		}
		return result;
	}

}
