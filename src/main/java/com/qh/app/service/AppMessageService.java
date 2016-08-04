package com.qh.app.service;

import com.qh.app.entity.AppMessages;
import com.qh.app.entity.Page;
import com.qh.app.entity.resp.ResponseService;

public interface AppMessageService {
	
	/**
	 * 提交站内留言
	 * 
	 * @param appMessage
	 * @return
	 */
	public ResponseService addMessage(AppMessages appMessage);
	
	/**
	 * 根据用户ID查询站内留言
	 * 
	 * @param userId
	 * @return
	 */
	public ResponseService queryMessageByUserId(String userId, Page page);
}
