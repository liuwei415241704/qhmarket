package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppStoreBusiness;


public interface AppStoreBusinessDao {
	
	/**
	 * 增加收藏
	 * 
	 * @param appStoreBusiness
	 * @return
	 */
	public Integer addStoreBusiness(AppStoreBusiness appStoreBusiness);
	
	/**
	 * 删除收藏
	 * 
	 * @param storeid
	 * @return
	 */
	public Integer deleteStoreBusiness(AppStoreBusiness appStoreBusiness);
	
	/**
	 * 查询所有收藏
	 * 
	 * @param customerid
	 * @return
	 */
	public List<AppStoreBusiness> queryStoreBusinessAll(String customerid);
	
	/**
	 * 查询收藏信息
	 * 
	 * @param appStoreBusiness
	 * @return
	 */
	public AppStoreBusiness queryStoreBusiness(AppStoreBusiness appStoreBusiness);
	
	/**
	 * 检查是否收藏
	 * 
	 * @param appStoreBusiness
	 * @return
	 */
	public AppStoreBusiness queryStoreIsCheck(AppStoreBusiness appStoreBusiness);
}
