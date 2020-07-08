package com.fz.enroll.user.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.common.util.MD5;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserType;

@Controller
@RequestMapping("/admin")
public class AdminAction extends UserAction {

	@Override
	protected UserType getUserType() {
		return UserType.ADMIN;
	}

	@ResponseBody
    @RequestMapping("doInit")
    public Response doInit(){
		User user = new User();
		user.setUsername("admin");
		user.setPassword(MD5.getMD5String("admin"));
		user.setName("平台管理员");
		user.setType(UserType.SUPER.val());
		user.setCtime(System.currentTimeMillis());
    	Response res = this.getService().saveService(user);
    	return res;
    }

}
