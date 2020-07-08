package com.fz.enroll.student.dao;

import java.util.List;
import java.util.Map;

import com.fz.enroll.entity.student.StuInfo;

public interface StuInfoDAO {

	public StuInfo queryById(int id);
	public StuInfo queryByStuId(int stuId);
	public List<StuInfo> queryByStuIds(List<Integer> stuIds);
	
	public int save(StuInfo entity);
	public int update(StuInfo entity);
	
	public int submit(Map<String,Object> params);
	public int submitAll(Map<String,Object> params);
	/**
	 * 学生信息导入
	 * @param params
	 * @return
	 */
	public int importField(Map<String,Object> params);
	/**
	 * 分配班级
	 * @param params
	 * @return
	 */
	public int modifyOther10(Map<String,Object> params);
	/*
	 * 设为本年 级，班级置空，入学年月改为本年9月
	 * status,other9,other10,other11, stuId
	 * */
	public int forwardGrade(StuInfo entity);
}
