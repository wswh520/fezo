package com.fz.enroll.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fz.common.security.CurrentUser;

public interface OnlineManager {
	
	public final static String SESSION_ID = "library_ms_sid";
	public final static String USERNAME = "library_ms_username";
	public final static String TOKEN = "library_ms_token";
	public final static String UID = "library_ms_uid";
	
	public CurrentUser getSession(HttpServletRequest request, HttpServletResponse response);
	
	public CurrentUser getSessionById(String sessionId);
	
	public String lineOn(HttpServletRequest request, HttpServletResponse response, CurrentUser obj);
	
	public void deleteSessionUser(String sessionId);
	
	public void lineOff(HttpServletRequest request,HttpServletResponse response);
	
	public String getSessionIdFromCookie(HttpServletRequest request);

}
