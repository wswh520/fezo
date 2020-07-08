package com.fz.enroll.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.enroll.login.service.SignInService;

@Controller
@RequestMapping("/")
public class SignInAction {
	
	@Autowired
	private SignInService signInService;

	@RequestMapping("signIn")
	public String doForward() {
		return "login/signIn";
	}

	@RequestMapping("signIn/save")
	public @ResponseBody Response doLogin(String name,String username,String password,
			HttpServletRequest request,HttpServletResponse response) {
		String referer = request.getHeader("referer");
		System.out.println(referer);
		if(referer==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = signInService.signInService(name, username, password);
		return res;
	}

}