package com.fz.enroll.student.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fz.base.service.QueryBaseService;
import com.fz.common.res.Response;
import com.fz.enroll.entity.student.GraStuInfo;

public interface StuGraInfoListService extends QueryBaseService<GraStuInfo>{
	public Response importService(MultipartFile file,Integer year);
	public void exportService(MultipartFile file,HttpServletRequest request,HttpServletResponse response);
	public Map<String,Object> createQueryParams(String keyword,String sylb,String xb,String bj,String status,String year);
}
