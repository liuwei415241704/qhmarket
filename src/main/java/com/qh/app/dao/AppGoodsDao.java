package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppGoods;

public interface AppGoodsDao {
	
	/**
	 * 根据商户ID获取商品信息
	 * 
	 * @param businessId
	 * @return
	 */
	public List<AppGoods> queryGoodsByBusinessId(String businessId);
	
	/**
	 * 根据商品ID获取商品信息
	 * 
	 * @param goodsId
	 * @return
	 */
	public AppGoods queryGoodsByGoodsId(String goodsId);
	
	/**
	 * 根据商品名称获取商品信息
	 * 
	 * @param name
	 * @return
	 */
	public List<AppGoods> queryGoodsByGoodsName(String name);
	
	/**
	 * 根据商品级别名称查询商品信息
	 * 
	 * @param gradeName
	 * @return
	 */
	public List<AppGoods> queryGoodsByGradeName(String gradeName);
	
	/**
	 *根据商品级别ID查询商品信息 
	 * 
	 * @param gradeId
	 * @return
	 */
	public List<AppGoods> queryGoodsByGradeId(String gradeId);
	
	/**
	 * 根据商品分类名称查询商品信息
	 * 
	 * @param categoryName
	 * @return
	 */
	public List<AppGoods> queryGoodsByCategoryName(String categoryName);
	
	/**
	 * 根据商品费雷ID查询商品信息
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<AppGoods> queryGoodsByCategoryId(String categoryId);
}
