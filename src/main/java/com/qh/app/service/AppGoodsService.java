package com.qh.app.service;

import com.qh.app.entity.AppGoods;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppGoodsService {
	
	/**
	 * 根据商户ID获取商品信息
	 * 
	 * @param appGoods
	 * @return
	 */
	public ResponseService queryGoodsByBusinessId(AppGoods appGoods, Page page);
	
	/**
	 * 根据商品ID获取商品信息
	 * 
	 * @param appGoods
	 * @return
	 */
	public ResponseService queryGoodsByGoodsId(AppGoods appGoods);
	
	/**
	 * 根据商品名称获取商品信息
	 * 
	 * @param appGoods
	 * @return
	 */
	public ResponseService queryGoodsByGoodsName(AppGoods appGoods, Page page);
	
	/**
	 * 根据商品级别名称或ID查询商品信息
	 * 
	 * @param appGoods
	 * @param page
	 * @return
	 */
	public ResponseService queryGoodsByGrade(AppGoods appGoods, Page page);
	
	/**
	 * 根据商品类别名称呢个或ID查询商品信息
	 * 
	 * @param appGoods
	 * @param page
	 * @return
	 */
	public ResponseService queryGoodsByCategory(AppGoods appGoods, Page page);
}
