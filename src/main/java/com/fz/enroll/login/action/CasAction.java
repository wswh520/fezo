package com.fz.enroll.login.action;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.enroll.login.service.LoginService;
import com.fz.enroll.login.service.OnlineManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cas")
public class CasAction {

	@Autowired
	private LoginService loginService;
	@Autowired
	private OnlineManager onlineManager;

	@RequestMapping("login")
	public String doLoginByTicket(HttpServletRequest request, HttpServletResponse response, Model model) {
        Response res = loginService.casLoginService(request, response);
//        System.out.println(ticket);
        if (res.getRetCode() != ReturnCode.SUCCESS){
            model.addAttribute("errorMsg", res.getErrorMsg());
            return "redirect:/login";
        }
        return "redirect:loginByUsername";
	}
    @RequestMapping("loginByUsername")
    public String loginByUsername(HttpServletRequest request, HttpServletResponse response, Model model) {
        Response res = loginService.casCheckService(request, response);
        if (res.getRetCode() != ReturnCode.SUCCESS){
            model.addAttribute("errorMsg", res.getErrorMsg());
            return "redirect:/login";
        }
        return "redirect:/";
    }

	@ResponseBody
    @RequestMapping("check")
    public Response checkTicket(HttpServletRequest request, HttpServletResponse response) {
        return loginService.casCheckService(request,response);
    }

}