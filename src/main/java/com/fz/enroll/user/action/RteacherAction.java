package com.fz.enroll.user.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fz.enroll.enum0.UserType;

@Controller
@RequestMapping("/rteacher")
public class RteacherAction extends UserAction {

	@Override
	protected UserType getUserType() {
		return UserType.RECRUIT_TEACHER;
	}
}
