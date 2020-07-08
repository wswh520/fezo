package com.fz.enroll.student.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fz.base.service.QueryBaseService;
import com.fz.common.res.Response;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.ExportOrder;

public interface StuApplyListService extends QueryBaseService<StuApply> {

	/**
	 * 
	 * @param keyword	根据：学生姓名、身份证号码、家长注册时的手机号码进行查找
	 * @param ageScope	年龄范围
	 * @param type	类别
	 * @param sex	性别
	 * @param status	状态
	 * @param status	学籍信息表状态
	 * @param status	预防接种表状态
	 * @return
	 */
	public Map<String,Object> createQueryParams(ExportOrder order,String keyword,String year,String ageScope,String type,String sex,
													String status,String infoStatus,String vaccineStatus,String school);
	
	public Response reviewService(StuApply entity);
	public Response saveMsgService(Integer id,String message);
	public Response batchReviewService(String[] ids,String status);
	
	public void exportService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response);
	public void exportInfoService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response);
	public void exportVaccineService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response);
	
	
	public Response submitAllService();
	public Response resetAllInfoService();
	public Response submitAllInfoService();
}
