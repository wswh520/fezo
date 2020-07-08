package com.fz.enroll.password.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.enroll.password.service.PasswordService;

@Controller
@RequestMapping("/password")
public class PasswordAction {
	
	@Autowired
	private PasswordService passwordService;

	/**
	 * 检查原始密码是否正确
	 * @param oldPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("check")
	public Response doCheck(String oldPwd) {
		Response res = passwordService.checkService(oldPwd);
		return res;
	}
	
	/**
	 * 修改密码
	 * @param newPwd
	 * @param oldPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("modify")
	public Response doModify(String newPwd,String oldPwd) {
		Response res = passwordService.updatePasswordService(newPwd, oldPwd);
		return res;
	}
}
