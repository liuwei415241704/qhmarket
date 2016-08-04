package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppOrdersDetail;

public interface AppOrdersDetailDao {
	
	/**
	 * 生成订单详细信息
	 * 
	 * @param appOrdersDetail
	 * @return
	 */
	public int addOrdersDetail(AppOrdersDetail appOrdersDetail);
	
	/**
	 * 根据订单ID获取订单明细
	 * 
	 * @return
	 */
	public List<AppOrdersDetail> queryDetailByOrdersId(String ordersId);
	
	/**
	 * 统计商品销量
	 * 
	 * @param goodsId
	 * @return
	 */
	public int countSales(String goodsId);
}
