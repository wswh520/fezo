package com.fz.enroll.user.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fz.enroll.enum0.UserType;

@Controller
@RequestMapping("/patriarch")
public class PatriarchAction extends UserAction {

	@Override
	protected UserType getUserType() {
		return UserType.PATRIARCH;
	}
	@Override
	protected Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, Object> params = super.getParams(request);
		try {
			params.put("year", Integer.valueOf(request.getParameter("year")));
		} catch (Exception e) {}
		return params;
	}
}
