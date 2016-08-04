package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppOrders;

public interface AppOrdersDao {
	
	/**
	 * 生成订单
	 * 
	 * @param appOrders
	 * @return
	 */
	public int addAppOrders(AppOrders appOrders);
	
	/**
	 * 更新订单状态
	 * 
	 * @param appOrders
	 * @return
	 */
	public int updateOrdersState(AppOrders appOrders);
	
	/**
	 * 根据状态获取订单
	 * 
	 * @param appOrders
	 * @return
	 */
	public List<AppOrders> queryOrders(AppOrders appOrders);
}
