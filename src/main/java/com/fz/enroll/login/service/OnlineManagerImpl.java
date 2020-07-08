package com.fz.enroll.login.service;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.security.CurrentUser;
import com.fz.enroll.entity.user.OnLine;
import com.fz.enroll.login.dao.OnLineDAO;

@Service("onlineManager")
public class OnlineManagerImpl implements OnlineManager{

	@Autowired
	private  OnLineDAO onLineDao ;

	private static final long expire_time = 60*60*24*30*1000L;

	private static final long datetime_update_limit = 3*60*60*1000L; //当在线超过3小时时进行online活动时间更新
	private static final Logger logger = LoggerFactory.getLogger(OnlineManagerImpl.class);

	public CurrentUser getSession(HttpServletRequest request, HttpServletResponse response) {
		return getSessionById(getSessionIdFromCookie(request));
	}

	public String lineOn(HttpServletRequest request, HttpServletResponse response, CurrentUser obj) {
		if(obj==null){
			lineOff(request,response);
			return null;
		}
		String sessionId = getSessionIdFromCookie(request);
		if(sessionId==null){
			OnLine onLine = obj.convertToOnLine();
			save(request,onLine);
			sessionId = onLine.getId();
		}else{
			OnLine cuser = findOne(sessionId);
			if(cuser==null){
				OnLine onLine = obj.convertToOnLine();
				save(request,onLine);
				sessionId = onLine.getId();
			}else if(cuser.getUid()!=obj.getUid()){
				lineOff(request,response);
				OnLine onLine = obj.convertToOnLine();
				save(request,onLine);
				sessionId = onLine.getId();
			}
		}
		setSessionIdToCookies(String.valueOf(sessionId) , obj ,request, response);
		return sessionId;
	}

	private void setSessionIdToCookies(String session_id ,CurrentUser user, HttpServletRequest request,HttpServletResponse response){

		String remoteHost = request.getServerName();
		if(remoteHost!=null&&remoteHost.endsWith(".fezo.com.cn")){
			response.addHeader("Set-Cookie", SESSION_ID+"="+session_id+";domain=.fezo.com.cn;Path=/;HttpOnly");
		}else{
			response.addHeader("Set-Cookie", SESSION_ID+"="+session_id+";Path=/;HttpOnly");
		}

		if(user.getToken()!=null){
			Date expire = new Date(System.currentTimeMillis()+expire_time);
			response.addHeader("Set-Cookie",USERNAME+"="+user.getUsername()+";Path=/");
			response.addHeader("Set-Cookie",UID+"="+String.valueOf(user.getUid())+";Path=/;expires="+expire.toString()+";HttpOnly");
			response.addHeader("Set-Cookie",TOKEN+"="+user.getToken()+";Path=/;expires="+expire.toString()+";HttpOnly;");
		}
	}

	public void deleteSessionUser(String sessionId) {
		delete(sessionId);
	}

	public void lineOff(HttpServletRequest request,HttpServletResponse response) {
		String sessionId = getSessionIdFromCookie(request);
		delete(sessionId);
		Cookie cookie = new Cookie(SESSION_ID, String.valueOf(sessionId));
		cookie.setMaxAge(0);
		response.addCookie(cookie);

		cookie = new Cookie(UID, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);

		cookie = new Cookie(TOKEN, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}


	public String getSessionIdFromCookie(HttpServletRequest request) {
		Cookie [] cookie = request.getCookies();
		String sessionId = null;
		if(cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
				if(SESSION_ID.equals(cookie[i].getName())) {
					sessionId = cookie[i].getValue();
				}
			}
		}
		if(sessionId==null){//有可能cookie不为null但也不包括SESSION_ID的cookie
			sessionId = request.getParameter(SESSION_ID);
		}
		return sessionId;
	}

	private OnLine findOne(String sessionId){
		return onLineDao.queryOnLineById(sessionId);
	}

	private boolean save(HttpServletRequest request,OnLine onLine){
		request.getSession().invalidate();
		onLine.setId(request.getSession().getId());
		onLine.setDt(System.currentTimeMillis());
		onLineDao.saveOnLine(onLine);
		return true;
	}


	private boolean delete(String id){
		onLineDao.deleteOnLine(id);
		return true;
	}

	public OnLineDAO getOnLineDao() {
		return onLineDao;
	}

	public void setOnLineDao(OnLineDAO onLineDao) {
		this.onLineDao = onLineDao;
	}

	@Override
	public CurrentUser getSessionById(String sessionId) {
		OnLine onLine = findOne(sessionId);
		CurrentUser user = null;
		if(onLine!=null){
			user = onLine.convertToCurrentUser();

			long create = System.currentTimeMillis();
			if(create-onLine.getDt()>=datetime_update_limit){
				onLine.setDt(create);
				onLineDao.updateOnLine(onLine);
				logger.info("update username "+onLine.getUser().getUsername()+" online datetime");
			}
		}
		return user;
	}

}
