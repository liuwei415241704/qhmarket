package com.qh.app.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AppPayRelationService {

	public String addWxPayTradeInfoRelation(HttpServletRequest request, HttpServletResponse response);

	public String addAliPayTradeInfoRelation(HttpServletRequest request,
			HttpServletResponse response);

}
