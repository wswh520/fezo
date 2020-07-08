package com.fz.enroll.student.dao;

import java.util.List;
import java.util.Map;

import com.fz.enroll.entity.student.StuVaccine;

public interface StuVaccineDAO {

	public StuVaccine queryById(int id);
	public StuVaccine queryByStuId(int stuId);
	public List<StuVaccine> queryByStuIds(List<Integer> stuIds);
	
	public int save(StuVaccine entity);
	public int update(StuVaccine entity);
	
	public int submit(Map<String,Object> params);
	/*
	 * 设为本年 级，班级置空，入学年月改为本年9月
	 * status stuId
	 * */
	public int resetStatus(StuVaccine entity);
}
