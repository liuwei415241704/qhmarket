package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppBusiness;

public interface AppBusinessDao {

	/**
	 * 查询首页商户
	 * 
	 * @return
	 */
	public List<AppBusiness> queryFrontBusiness();

	/**
	 * 获取商户所有分类
	 * 
	 * @return
	 */
	public List<AppBusiness> queryBusinessCategoryAll();
	
	/**
	 * 根据分类ID获取商户信息
	 * 
	 * @param categoryId
	 * @return
	 */
	public List<AppBusiness> queryBusinessByCategoryId(String categoryId);
	
	/**
	 * 根据商户ID获取商户信息
	 * 
	 * @param businessId
	 * @return
	 */
	public AppBusiness queryBusinessByBusinessId(String businessId);
	
	/**
	 * 根据商户名搜索商户信息
	 * 
	 * @param businessName
	 * @return
	 */
	public List<AppBusiness> queryBusinessByBusinessName(String businessName);
	
	/**
	 * 查询天天特价商户
	 * 
	 * @return
	 */
	public List<AppBusiness> queryBusinessByGrade();
}
