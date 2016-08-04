package com.qh.app.service;

import com.qh.app.entity.AppMarket;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppMarketService {
	
	/**
	 * 新增市场单品信息
	 * 
	 * @param appMarket
	 * @return
	 */
	public ResponseService addKinds(AppMarket appMarket);
	
	/**
	 * 根据类别名称查询分类信息
	 * 
	 * @param appMarket
	 * @param page
	 * @return
	 */
	public ResponseService queryKindsByKindName(AppMarket appMarket, Page page);
	
	/**
	 * 更新portrait url
	 * 
	 * @param appMarket
	 * @return
	 */
	public ResponseService updatePortrait(AppMarket appMarket);
}
