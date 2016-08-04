package com.qh.app.entity;

import java.util.Date;
/**
 * 商户实体类
 * 
 * @author lw
 *
 */
public class AppBusiness {
	
	private String businessId;
	
	private String name;
	
	private String describe;
	
	private String mobile;
	
	private String adress;
	
	private String saletime;
	
	private String portrait;
	
	private String creator;
	
	private Date createTime;
	
	private String orderflag;
	
	private String frontOrder;
	
	private String modifier;
	
	private Date modifytime;
	
	private String status;
	
	private String isSpecial;
	
	private String isDistribution;
	
	/***商户业务属性***/
	private String categoryName;
	
	private String categoryId;
	
	private String score;
	
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getSaletime() {
		return saletime;
	}

	public void setSaletime(String saletime) {
		this.saletime = saletime;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOrderflag() {
		return orderflag;
	}

	public void setOrderflag(String orderflag) {
		this.orderflag = orderflag;
	}

	public String getFrontOrder() {
		return frontOrder;
	}

	public void setFrontOrder(String frontOrder) {
		this.frontOrder = frontOrder;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(String isSpecial) {
		this.isSpecial = isSpecial;
	}

	public String getIsDistribution() {
		return isDistribution;
	}

	public void setIsDistribution(String isDistribution) {
		this.isDistribution = isDistribution;
	}
}
