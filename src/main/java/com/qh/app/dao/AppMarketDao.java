package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppMarket;

public interface AppMarketDao {
	
	/**
	 * 新增市场单品信息
	 * 
	 * @param appMarket
	 * @return
	 */
	public int addKinds(AppMarket appMarket);
	
	/**
	 * 根据市场分类类别查询市场发布信息
	 * 
	 * @param kindName
	 * @return
	 */
	public List<AppMarket> queryKindsByKindName(String kindName);
	
	/**
	 * 更新市场portrait url
	 * 
	 * @param appMarket
	 * @return
	 */
	public int updatePortrait(AppMarket appMarket);
}
