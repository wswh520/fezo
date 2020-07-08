package com.fz.enroll.student.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.enroll.entity.student.StuInfo;
import com.fz.enroll.enum0.DataField;
import com.fz.enroll.enum0.StuApplyStatus;

public interface StuInfoService {

	public Response loadService(String stuId);
	/**
	 * 修改学籍信息
	 * 		1、家长只能在相应的时间段内修改对应状态的自己的信息
									SUBMIT_NONE(1),//未提交
	 * 		2、招生老师任何时刻可以修改相应状态的信息
									SUBMIT_ONCE(2),//初次提交
	 * @param entity
	 * @return
	 */
	public Response saveService(StuInfo entity);
	
	public Response submitService(String id,StuApplyStatus targetStatus);

	public void downloadStuInfoService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response);
	/**
	 * 学生信息导入
	 * @param file
	 * @param field
	 * @return
	 */
	public Response importService(MultipartFile file, DataField field);
	/**
	 * 分配班级
	 * @param idStr
	 * @param no
	 * @return
	 */
	public Response modifyClassService(String idStr,String no);
	
}
