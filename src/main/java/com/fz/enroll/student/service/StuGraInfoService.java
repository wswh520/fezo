package com.fz.enroll.student.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fz.common.res.Response;
import com.fz.enroll.entity.student.GraStuInfo;

public interface StuGraInfoService {
	public Response loadService(String stuId);
	public Response verifyServiceT(GraStuInfo entity);
	public Response reviewServiceT(GraStuInfo entity);
	public Response submitServiceT(String idStr);
	public Response refuseServiceT(String idStr);
	public Response getHistoryListService(String id);
	public Response getHistoryInfoService(String id);

	public void downloadStuInfoService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response);
}
