package com.fz.enroll.config.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.dto.config.TimeConfigAdd;

@Controller
@RequestMapping("/timeConfig")
public class TimeConfigAction {
	
	@Autowired
	private TimeConfigService timeConfigService;

	@ResponseBody
	@RequestMapping("get")
	public Response doGet(String type) {
		Response res = timeConfigService.getService(type);
		return res;
	}

	@ResponseBody
	@RequestMapping("set")
	public Response doSet(TimeConfigAdd config) {
		Response res = timeConfigService.setService(config.convert2Entity());
		return res;
	}
	
	

}
