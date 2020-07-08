package com.fz.enroll.student.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.enroll.dto.student.StuVaccineAdd;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.student.service.StuVaccineService;

@Controller
@RequestMapping("/stuVaccine")
public class StuVaccineAction {
	
	@Autowired
	private StuVaccineService stuVaccineService;

	@ResponseBody
	@RequestMapping("load")
	public Response doLoad(String stuId) {
		Response res = stuVaccineService.loadService(stuId);
		return res;
	}
	
	@ResponseBody
	@RequestMapping("save")
	public Response doSave(StuVaccineAdd entity) {
		Response res = stuVaccineService.saveService(entity.convert2Entity());
		return res;
	}
	
	@ResponseBody
	@RequestMapping("submit")
	public Response doSubmit(String id) {
		Response res = stuVaccineService.submitService(id, StuApplyStatus.SUBMIT_ONCE);
		return res;
	}
}
