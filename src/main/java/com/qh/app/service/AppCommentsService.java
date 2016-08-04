package com.qh.app.service;

import com.qh.app.entity.AppComments;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;


public interface AppCommentsService {
	
	/**
	 * 新增评论
	 * 
	 * @param coments
	 * @return
	 */
	public ResponseService addComments(AppComments coments);
	
	/**
	 * 查询评论
	 * 
	 * @param coments
	 * @param page
	 * @return
	 */
	public ResponseService queryComments(AppComments coments, Page page);

}
