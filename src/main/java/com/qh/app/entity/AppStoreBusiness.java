package com.qh.app.entity;

import java.util.Date;

/**
 * 客户收藏商户实体类
 * 
 * @author lw
 * 
 */
public class AppStoreBusiness {
	private String storeId;

	private String customerId;

	private String businessId;

	private Date storeTime;

	private AppBusiness appBusiness;

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}

	public AppBusiness getAppBusiness() {
		return appBusiness;
	}

	public void setAppBusiness(AppBusiness appBusiness) {
		this.appBusiness = appBusiness;
	}

}