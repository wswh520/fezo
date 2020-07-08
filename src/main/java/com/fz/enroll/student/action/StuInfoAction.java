package com.fz.enroll.student.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.enroll.dto.student.StuInfoAdd;
import com.fz.enroll.enum0.DataField;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.service.StuInfoService;

@Controller
@RequestMapping("/stuInfo")
public class StuInfoAction {
	
	@Autowired
	private StuInfoService stuInfoService;

	@ResponseBody
	@RequestMapping("load")
	public Response doLoad(String stuId) {
		Response res = stuInfoService.loadService(stuId);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("save")
	public Response doSave(StuInfoAdd entity) {
		Response res = stuInfoService.saveService(entity.convert2Entity());
		return res;
	}
	
	@ResponseBody
	@RequestMapping("submit")
	public Response doSubmit(String id) {
		Response res = stuInfoService.submitService(id, StuApplyStatus.SUBMIT_ONCE);
		return res;
	}
	
	@RequestMapping("download/{id}")
	public void doDownload(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuInfoService.downloadStuInfoService(false,id,request,response);
	}
	
	@RequestMapping("downloadPdf/{id}")
	public void doDownloadPdf(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuInfoService.downloadStuInfoService(true,id,request,response);
	}

	@RequestMapping("print/{id}")
	public String doPrint(@PathVariable("id")String id,Model model){
		model.addAttribute("id", id);
		return "student/print2";
	}

	/**
	 * 学生信息导入
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	public String doImport(@RequestParam("file")MultipartFile file, DataField field,Model model){
		if(ThreadLocalUtils.getCurrentUser()==null){
			model.addAttribute("retCode", ReturnCode.NOT_LOGIN);
			return "student/importFinish";
		}else if(ThreadLocalUtils.getCurrentUser().getType()!=UserType.ADMIN){
			model.addAttribute("retCode", ReturnCode.UNAUTHORIZED);
			return "student/importFinish";
		}
		Response res = stuInfoService.importService(file, field);
		model.addAttribute("retCode", res.getRetCode());
		model.addAttribute("msg", res.getErrorMsg());
		return "student/importFinish";
	}
	
	@ResponseBody
	@RequestMapping("modifyClass")
	public Response doModifyClass(String id,String no) {
		if(ThreadLocalUtils.getCurrentUser()==null){
			return new Response(ReturnCode.NOT_LOGIN);
		}else if(ThreadLocalUtils.getCurrentUser().getType()!=UserType.ADMIN){
			return new Response(ReturnCode.UNAUTHORIZED);
		}
		Response res = stuInfoService.modifyClassService(id, no);
		return res;
	}
}
