package com.qh.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qh.app.entity.AppAttach;

public interface AppAttachDao {
	
	/**
	 * 新增附件信息
	 * 
	 * @param appAttach
	 * @return
	 */
	public int addAttach(AppAttach appAttach);
	
	/**
	 * 根据单品ID,附件类型查询附件信息(1:BUSINESS 2:GOODS 3:MARKET)
	 * 
	 * @param id
	 * @return
	 */
	public List<AppAttach> queryAttachByIdAndType(@Param("id")String id, @Param("type")String type);
}
