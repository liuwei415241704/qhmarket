package com.qh.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.qh.app.dao.AppOrdersDao;
import com.qh.app.dao.AppOrdersDetailDao;
import com.qh.app.entity.AppOrders;
import com.qh.app.entity.AppOrdersDetail;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;
import com.qh.app.service.AppOrdersService;

@Service("appOrdersService")
public class AppOrdersServiceImpl implements AppOrdersService {

	private static Logger logger = LoggerFactory
			.getLogger(AppOrdersServiceImpl.class);

	@Resource
	private AppOrdersDao appOrdersDao;

	@Resource
	private AppOrdersDetailDao appOrdersDetailDao;

	@Override
	public ResponseService addAppOrder(AppOrders appOrders) {
		ResponseService result = new ResponseService();
		int ordersResult = appOrdersDao.addAppOrders(appOrders);
		int detailResult = 0;
		List<AppOrdersDetail> detailList = appOrders.getList();
		for (AppOrdersDetail appOrdersDetail : detailList) {
			detailResult = appOrdersDetailDao.addOrdersDetail(appOrdersDetail);
		}
		if (ordersResult > 0 && detailResult > 0) {
			logger.info("****订单生成成功****");
			result.setSuccess();
			Map<String, String> map = new HashMap<String, String>();
			map.put("ordersId", appOrders.getOrdersId());
			result.setData(map);
		} else {
			logger.info("****订单生成失败****");
		}
		return result;
	}

	@Override
	public void updateOrdersState(AppOrders appOrders) {
		logger.info("****更新订单状态****");
		Integer isSuccess = appOrdersDao.updateOrdersState(appOrders);
		if (isSuccess > 0) {
			logger.info("更新订单状态成功");
		} else {
			logger.info("更新订单状态失败");
		}
	}

	@Override
	public ResponseService queryOrders(AppOrders appOrders, Page page) {
		if (page != null) {
			PageHelper.startPage(page.getCurrentPage(), page.getPageSize());
		}
		ResponseService result = new ResponseService();
		List<AppOrders> list = new ArrayList<>();
		List<AppOrders> newList = new ArrayList<>();
		if (appOrders != null) {
			list = appOrdersDao.queryOrders(appOrders);
			if (list.size() > 0) {
				for (AppOrders ordersEntity : list) {
					List<AppOrdersDetail> detailList = appOrdersDetailDao.queryDetailByOrdersId(ordersEntity.getOrdersId());
					ordersEntity.setList(detailList);
					newList.add(ordersEntity);
				}
			}
			result.setSuccess();
			result.setData(newList);
			logger.info("****分类查询订单成功!****");
		}
		return result;
	}
}
