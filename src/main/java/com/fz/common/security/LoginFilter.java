package com.fz.common.security;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fz.common.listener.PropertyHolder;
import com.fz.enroll.login.service.LoginService;
import com.fz.enroll.login.service.OnlineManager;

public class LoginFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	private static final String DELIMITER = ",";
	private static final String PAGE_TO_FORWARD = "/login";

	private OnlineManager onlineManager;
	private LoginService loginService;

	private String skipPages = null;
	private String checkReturnJson = null;

	public static  boolean isHttps = false;

	public void init(FilterConfig filterConfig) throws ServletException {
		skipPages = PropertyHolder.get("skipPages");
		if (skipPages == null || "".equals(skipPages)) {
			throw new ServletException("没有指定登陆页面或主页!");
		}
		checkReturnJson = filterConfig.getInitParameter("check-return-json");
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		onlineManager = (OnlineManager)context.getBean("onlineManager");
		if(onlineManager==null){
			logger.error("online manager is null");
			throw new ServletException("online manager is null");
		}
		loginService = (LoginService)context.getBean("loginService");
		if(loginService==null){
			logger.error("userService is null");
			throw new ServletException("userService is null");
		}

		String https = PropertyHolder.get("isHttps");
		if(https!=null&&"true".equals(https)){
			isHttps = true;
		}
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		try {
			HttpServletRequest request = (HttpServletRequest) req;
			HttpServletResponse response = (HttpServletResponse) res;
			String reqUri = request.getRequestURI();
			String contextPath = request.getContextPath();
			if(contextPath.endsWith("/")){
				logger.error("context path is "+contextPath+" ,can not end with /");
				throw new ServletException("context path is "+contextPath+" ,can not end with /");
			}
			reqUri = reqUri.substring(contextPath.length());

			CurrentUser user = onlineManager.getSession(request, response);
			if(user ==null){
				user = autoLogin(request, response);
			}
			if (user == null) {
				if (skipPages != null && !"".equals(skipPages)) {
					StringTokenizer sp = new StringTokenizer(skipPages, DELIMITER);
					while (sp.hasMoreTokens()) {
						String spn = sp.nextToken().trim();
						if (reqUri.indexOf(spn) >= 0) {
							chain.doFilter(req, res);
							return;
						}
					}
				}
				if(checkReturnJson != null && !"".equals(checkReturnJson)){
					StringTokenizer sp = new StringTokenizer(checkReturnJson, DELIMITER);
					while (sp.hasMoreTokens()) {
						String spn = sp.nextToken().trim();
						if (reqUri.indexOf(spn) >= 0) {
							logger.debug("request url:"+reqUri+",not login.");
							//redirect(getUrlPrefix(request, response,false)+PAGE_TO_JSON, request, response);
							/*http请求重定向后也变成https了*/
							try {
								response.getWriter().write("{\"retCode\":\"100003\"}");
								response.getWriter().flush();
							} catch (IOException e) {
								throw new RuntimeException(e.getMessage(), e);
							}
							return;
						}
					}
				}
				redirectLogin(request, response);
				return;
			} else {
				ThreadLocalUtils.setCurrentUser(user);
			}
			chain.doFilter(req, res);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			ThreadLocalUtils.clearUser();
		}
	}
	private CurrentUser autoLogin(HttpServletRequest request,HttpServletResponse response)throws IOException, ServletException{
		/*String uid  = Struts2Utils.getCookieValue(request, OnlineManager.UID);
		String token  = Struts2Utils.getCookieValue(request, OnlineManager.TOKEN);
		if(uid!=null&&token!=null){
			//如果是记住密码用户自动登录，然后重定向到用户主页
			Response res = loginService.loginByTokenService(uid, token);
			if(res.getRetCode() == ReturnCode.SUCCESS){
				CurrentUser currentUser = (CurrentUser)res.getData();
				onlineManager.lineOn(request, response, currentUser);
				return currentUser;
			}
			Cookie cookie = new Cookie(OnlineManager.TOKEN, "");
			cookie.setMaxAge(0);
			cookie.setPath("/");
			response.addCookie(cookie);
			logger.warn("uid "+uid +" rember password login failed");
		}*/
		return null;
	}

	private String getUrlPrefix(HttpServletRequest request,HttpServletResponse response){
     	String scheme = isHttps?"https://":"http://";
		String http = "";
		if(request.getServerPort()==80||request.getServerPort()==443){
			http = scheme+request.getServerName();
		}else{
			http = scheme+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		}
		return http;
	}

	public void destroy() {
		// TODO 自动生成方法存根

	}

	private void redirectLogin(HttpServletRequest request,
			HttpServletResponse response)throws ServletException, IOException{
		redirect(getUrlPrefix(request, response)+PAGE_TO_FORWARD, request, response);
	}

	private void redirect(String page, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(page);
	}

}