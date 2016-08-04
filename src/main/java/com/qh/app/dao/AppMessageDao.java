package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppMessages;

public interface AppMessageDao {
	
	/**
	 * 提交留言
	 * 
	 * @param appMessage
	 * @return
	 */
	public int addMessage(AppMessages appMessage);
	
	/**
	 * 根据用户ID获取站内留言
	 * 
	 * @param userId
	 * @return
	 */
	public List<AppMessages> queryMessageByUserId(String userId);
}
