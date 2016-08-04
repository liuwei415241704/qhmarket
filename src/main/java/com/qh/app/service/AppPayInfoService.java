package com.qh.app.service;

import com.qh.app.entity.AppPayInfo;


public interface AppPayInfoService {
	
	/**
	 * 根据订单ID查询支付信息
	 * 
	 * @param orderId
	 * @return
	 */
	public AppPayInfo queryAppPayInfoByOrdersId(String ordersId);
	
	/**
	 * 新增支付信息
	 * 
	 * @param payInfo
	 */
	public void addAppPayInfo(AppPayInfo payInfo);
	
	/**
	 * 更新支付状态
	 * 
	 * @param payInfo
	 */
	public void updateAppPayInfo(AppPayInfo payInfo);

}
