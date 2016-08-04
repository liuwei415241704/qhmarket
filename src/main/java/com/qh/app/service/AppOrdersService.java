package com.qh.app.service;

import com.qh.app.entity.AppOrders;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppOrdersService {
	
	/**
	 * 生成订单(购物车)
	 * 
	 * @param appOrders
	 * @return
	 */
	public ResponseService addAppOrder(AppOrders appOrders);
	
	/**
	 * 更新订单状态
	 * 
	 * @param appOrders
	 */
	public void updateOrdersState(AppOrders appOrders);
	
	/**
	 * 根据订单状态获取订单
	 * 
	 * @param appOrders
	 * @return
	 */
	public ResponseService queryOrders(AppOrders appOrders, Page page);
	
}
