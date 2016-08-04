package com.qh.app.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 订单类
 * 
 * @author lw
 *
 */
public class AppOrders {
	
	private String ordersId;
	
	private String customerId;
	
	private Date ordersTime;
	
	private String status;
	
	private BigDecimal totalamount;
	
	private String adress;
	
	private List<AppOrdersDetail> list = new ArrayList<>();

	public String getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(String ordersId) {
		this.ordersId = ordersId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Date getOrdersTime() {
		return ordersTime;
	}

	public void setOrdersTime(Date ordersTime) {
		this.ordersTime = ordersTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(BigDecimal totalamount) {
		this.totalamount = totalamount;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public List<AppOrdersDetail> getList() {
		return list;
	}

	public void setList(List<AppOrdersDetail> list) {
		this.list = list;
	}
}
