package com.fz.enroll.auth;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fz.common.listener.PropertyHolder;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.enroll.enum0.UserType;
 
public class AuthorityInterceptor extends HandlerInterceptorAdapter {
	
	private Map<String,List<String>> authMap = new HashMap<String,List<String>>();
	private List<String> getAuth(String authKey){
		if(authMap.get(authKey)==null){
			String authStr = PropertyHolder.get(authKey);
			authMap.put(authKey, authStr==null?new ArrayList<String>():Arrays.asList(authStr.split(",")));
		}
		return authMap.get(authKey);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(handler==null||!(handler instanceof HandlerMethod)
				||ThreadLocalUtils.getCurrentUser()==null){//未登陆
			return true;
		}
		UserType cuserType = ThreadLocalUtils.getCurrentUser().getType();
		List<String> authUris = this.getAuth("authUris");
		List<String> myAuth = this.getAuth(cuserType.toString());
		String reqUri = this.getReqUri(request);
		
		boolean hasAuth = true;
		while(reqUri!=null&&!reqUri.isEmpty()){
			if (myAuth.contains(reqUri)) {
				hasAuth = true;
				break ;
			}else if(authUris.contains(reqUri)){
				hasAuth = false;
				break ;
			}
			reqUri = this.getParentUri(reqUri);
		}

		if(false == hasAuth){
			try {
				response.getWriter().write("{\"retCode\":\""+ReturnCode.UNAUTHORIZED+"\"}");
				response.getWriter().flush();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
			return false;
		}
		return true;
	}
	private String getReqUri(HttpServletRequest request){
		String reqUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		reqUri = reqUri.substring(contextPath.length());
		return reqUri;
	}
	private String getParentUri(String uri){
		if(uri.indexOf("/")<0){
			return null;
		}
		return uri.substring(0, uri.lastIndexOf("/"));
	}
}