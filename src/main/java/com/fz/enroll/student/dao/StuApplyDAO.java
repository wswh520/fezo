package com.fz.enroll.student.dao;

import java.util.List;
import java.util.Map;

import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.entity.student.StuApplyHistory;
import com.fz.enroll.entity.student.Year;

public interface StuApplyDAO {

	public StuApply queryById(int id);
	public StuApply queryByUid(int uid);
	
	public StuApply queryByUnique(StuApply entity);
	public int save(StuApply entity);
	public int update(StuApply entity);
	
	public int saveHistory(StuApplyHistory history);
	public List<StuApplyHistory> queryHistory(int mid);
	public StuApplyHistory queryHistoryById(int id);
	
	public int submit2Review(Map<String,Object> params);
	public int submitAll(Map<String,Object> params);
	
	public Year queryYear(int year);
	public int saveYear(Year entity);
	public int incYearTypeA(Year entity);
	public int incYearTypeB(Year entity);
	public int incYearTypeC(Year entity);
	
	public List<StuApply> query2InitPinyin(int offset);
	public void initPinyin(StuApply entity);
	
	/*
	 * 以下更新为转级是使用
	 * */
	/*
	 * 年份重置为本年，报名号重新生成
	 * no,year,status , id
	 * */
	public int forwardGrade(StuApply entity);
	/*
	 * 登记的历史信息：重新生成json
	 * params data,time
	 * */
	//public int updateHistory(StuApplyHistory history);
	
}
