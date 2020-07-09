package com.fz.enroll.student.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.base.action.QueryBaseAction;
import com.fz.base.service.QueryBaseService;
import com.fz.common.res.Response;
import com.fz.enroll.dto.student.StuApplyReview;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.ExportOrder;
import com.fz.enroll.student.service.StuApplyListService;

@Controller
@RequestMapping("/stuApplyList")
public class StuApplyListAction extends QueryBaseAction<StuApply> {
	
	@Autowired
	private StuApplyListService stuApplyListService;

	@Override
	protected QueryBaseService<StuApply> getService() {
		return stuApplyListService;
	}

	@Override
	protected Map<String, Object> getParams(HttpServletRequest request) {
		String keyword = request.getParameter("keyword");
		String year = request.getParameter("year");
		String ageScope = request.getParameter("ageScope");
//		String type = request.getParameter("type");
		String sex = request.getParameter("sex");
		String status = request.getParameter("status");
		String infoStatus = request.getParameter("infoStatus");
		String vaccineStatus = request.getParameter("vaccineStatus");
		String school = request.getParameter("school");
		String other54 = request.getParameter("other54");
		return stuApplyListService.createQueryParams(null,keyword, year, ageScope, other54, sex, status, infoStatus, vaccineStatus, school);
	}
	
	@ResponseBody
	@RequestMapping("review")
	public Response doReview(StuApplyReview entity) {
		Response res = stuApplyListService.reviewService(entity.convert2Entity());
		return res;
	}
	
	@ResponseBody
	@RequestMapping("saveMsg")
	public Response doSaveMsg(Integer id,String message) {
		Response res = stuApplyListService.saveMsgService(id, message);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("batchReview")
	public Response doBatchReview(String[] ids,String status) {
		Response res = stuApplyListService.batchReviewService(ids, status);
		return res;
	}
	
	@RequestMapping("export/{order}")
	public void doExport(@PathVariable("order")ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response) {
		stuApplyListService.exportService(order,keyword,year,ageScope, type, sex, status, infoStatus, vaccineStatus, school, request, response);
	}
	
	@RequestMapping("exportInfo/{order}")
	public void doExportInfo(@PathVariable("order")ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response) {
		stuApplyListService.exportInfoService(order,keyword,year,ageScope, type, sex, status, infoStatus, vaccineStatus, school, request, response);
	}
	
	@RequestMapping("exportVaccine/{order}")
	public void doExportVaccine(@PathVariable("order")ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response) {
		stuApplyListService.exportVaccineService(order,keyword,year,ageScope, type, sex, status, infoStatus, vaccineStatus, school, request, response);
	}

	@ResponseBody
	@RequestMapping("submitAll")
	public Response doSubmitAll() {
		Response res = stuApplyListService.submitAllService();
		return res;
	}

	/**
	 * 重置所有学籍信息表为未提交状态
	 * @return
	 */
	@ResponseBody
	@RequestMapping("resetAllInfo")
	public Response doResetAllInfo() {
		Response res = stuApplyListService.resetAllInfoService();
		return res;
	}

	/**
	 * 强制提交所有未提交的学籍信息表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("submitAllInfo")
	public Response doSubmitAllInfo() {
		Response res = stuApplyListService.submitAllInfoService();
		return res;
	}

	/**
	 * 发送短信接口
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("sendSms")
	public Response sendSms(HttpServletRequest request){
		Response res = stuApplyListService.sendSms(request);
		return res;
	}

}
