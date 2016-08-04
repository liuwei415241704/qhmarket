package com.qh.app.service;

import com.qh.app.entity.AppBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppBusinessService {
	
	/**
	 * 获取首页商铺
	 * 
	 * @return
	 */
	public ResponseService queryFrontBusiness();
	
	/**
	 * 获取商户所有分类
	 * 
	 * @return
	 */
	public ResponseService queryBusinessCategoryAll();
	
	/**
	 * 根据分类ID获取商户
	 * 
	 * @param appBusiness
	 * @param page
	 * @return
	 */
	public ResponseService queryBusinessByCategoryId(AppBusiness appBusiness, Page page);
	
	/**
	 * 根据商户ID获取商户详细信息
	 * 
	 * @param appBusiness
	 * @return
	 */
	public ResponseService queryBusinessByBusinessId(AppBusiness appBusiness);
	
	/**
	 * 根据商户名搜索商户信息
	 * 
	 * @param appBusiness
	 * @return
	 */
	public ResponseService queryBusinessByBusinessName(AppBusiness appBusiness, Page page);
	
	/**
	 * 查询天天特价商户
	 * 
	 * @param appBusiness
	 * @param page
	 * @return
	 */
	public ResponseService queryBusinessByGrade(Page page);
}
