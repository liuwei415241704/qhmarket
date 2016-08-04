package com.qh.app.dao;

import java.util.List;

import com.qh.app.entity.AppComments;


public interface AppCommentsDao {
	
	int deleteByPrimaryKey(String commentsid);

	int insert(AppComments record);

	int insertSelective(AppComments record);

	AppComments selectByPrimaryKey(String commentsid);

	int updateByPrimaryKeySelective(AppComments record);

	int updateByPrimaryKey(AppComments record);
	
	/**
	 * 提交评论信息
	 * 
	 * @param coments
	 * @return
	 */
	public int addComments(AppComments coments);
	
	/**
	 * 根据商户ID查询评论
	 * 
	 * @param coments
	 * @return
	 */
	public List<AppComments> queryComments(AppComments coments);
}