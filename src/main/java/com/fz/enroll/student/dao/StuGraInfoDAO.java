package com.fz.enroll.student.dao;

import java.util.List;
import java.util.Map;

import com.fz.base.dao.BaseDAO;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.entity.student.GraStuInfoHistory;

public interface StuGraInfoDAO extends BaseDAO<GraStuInfo>{
	
	public GraStuInfo queryById(Integer id);
	public GraStuInfo queryByUid(int uid);
	/**
	 * 根据用户名列表去获取核对信息列表
	 * @param usernameList
	 * @return
	 */
	public List<GraStuInfo> queryByUsernames(List<String> usernameList);
	
	public int batchInsert(List<GraStuInfo> list);
	public int saveHistory(GraStuInfoHistory entity);
	public int submit(Map<String,Object> params);
	public int refuse(Map<String,Object> params);
	public List<GraStuInfoHistory> queryHistory(int id);
	public GraStuInfoHistory queryHistoryById(int id);
	
	/**
	 * 保存核对信息
	 * @param entity
	 * @return
	 */
	public int verify(GraStuInfo entity);
	public int review(GraStuInfo entity);
}
