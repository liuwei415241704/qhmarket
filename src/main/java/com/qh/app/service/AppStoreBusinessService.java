package com.qh.app.service;

import com.qh.app.entity.AppStoreBusiness;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppStoreBusinessService {

	/**
	 * 添加收藏
	 * 
	 * @param appBusiness
	 * @return
	 */
	public ResponseService addStoreBusiness(AppStoreBusiness appStoreBusiness);

	/**
	 * 删除收藏
	 * 
	 * @param appBusiness
	 * @return
	 */
	public ResponseService deleteStoreBusiness(AppStoreBusiness appStoreBusiness);

	/**
	 * 查询所有收藏
	 * 
	 * @param appBusiness
	 * @param page
	 * @return
	 */
	public ResponseService queryStoreBusinessAll(
			AppStoreBusiness appStoreBusiness, Page page);

	/**
	 * 检查是否收藏
	 * 
	 * @param appBusiness
	 * @return
	 */
	public ResponseService queryStoreIsCheck(AppStoreBusiness appStoreBusiness);
}
