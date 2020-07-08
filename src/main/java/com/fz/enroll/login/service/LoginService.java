package com.fz.enroll.login.service;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fz.common.res.Response;

public interface LoginService {

	/**
	 * 登陆
	 * @param username
	 * @param password
	 * @param rememberMe
	 * @return data:UserLoginView
	 */
	public Response loginService(String username,String password,boolean rememberMe,HttpServletRequest request, HttpServletResponse response);
	/**
	 * token自动登陆
	 * @param id
	 * @param token
	 * @return data:CurrentUse
	 */
	public Response loginByTokenService(int id,String token);

    /**
     * cas自动登陆
     * @return data:CurrentUse
     */
    public Response casLoginService(HttpServletRequest request, HttpServletResponse response);
    /**
     * cas登陆后，检测用户是否一致
     * @return data:CurrentUse
     */
    public Response casCheckService(HttpServletRequest request, HttpServletResponse response);
}
