package com.fz.enroll.user.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fz.enroll.enum0.UserType;

@Controller
@RequestMapping("/hteacher")
public class HteacherAction extends UserAction {

	@Override
	protected UserType getUserType() {
		return UserType.HEALTH_TEACHER;
	}
}
