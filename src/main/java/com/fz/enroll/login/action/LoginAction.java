package com.fz.enroll.login.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fz.enroll.student.service.StuApplyListService;
import com.fz.enroll.student.service.StuApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.common.res.Response;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.MD5;
import com.fz.enroll.enum0.SysType;
import com.fz.enroll.login.service.LoginService;
import com.fz.enroll.login.service.OnlineManager;

@Controller
@RequestMapping("/")
public class LoginAction {

	@Autowired
	private LoginService loginService;
	@Autowired
	private OnlineManager onlineManager;
	@Autowired
	private StuApplyListService stuApplyListService;
	@Autowired
	private StuApplyService stuApplyService;

//	@RequestMapping("login/tips")
//	public String doForwardTips(HttpServletRequest request,HttpServletResponse response) {
//		if(ThreadLocalUtils.getCurrentUser()!=null){
//			onlineManager.lineOff(request,response);
//		}
//		return "tips";
//	}
//	@RequestMapping("{sysType}/login")
//	public String doForward(@PathVariable("sysType") SysType sysType) {
//		sysType = sysType == null ? SysType.xs:sysType;
//		return "login/login_" + sysType;
//	}
	@RequestMapping("login")
	public String doForward() {
		return "login/login";
	}
	@RequestMapping("login_in")
	public @ResponseBody Response doLogin(String username,String password,boolean rememberMe,
			HttpServletRequest request,HttpServletResponse response) {
		//Response res = loginService.loginService(username, password==null?null:MD5.md5MatchToPHP(password), rememberMe,  request, response);
		Response res = loginService.loginService(username, password, rememberMe,  request, response);
		return res;
	}

	@RequestMapping("login_out")
	public String doLogout(HttpServletRequest request,HttpServletResponse response){
		onlineManager.lineOff(request,response);
//		return "login/login";
		return "redirect:login";
//		return "redirect:login/tips";
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

	@RequestMapping("downloadTestPdf/{id}")
	public void downloadTestPdf(@PathVariable("id")String id,HttpServletRequest request,HttpServletResponse response) {
		stuApplyService.downloadStuApplyService2(true,id,request,response);
	}

}