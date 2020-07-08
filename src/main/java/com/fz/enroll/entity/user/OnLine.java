package com.fz.enroll.entity.user;

import com.fz.common.security.CurrentUser;
import com.fz.enroll.enum0.UserType;

public class OnLine {
	
	private String id;
	private int uid;
	private long dt;
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public long getDt() {
		return dt;
	}
	public void setDt(long dt) {
		this.dt = dt;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	/*********************************************************************/
	public CurrentUser convertToCurrentUser(){
		CurrentUser user = new CurrentUser();
		user.setSid(this.getId());
		user.setUid(this.getUid());
		user.setUsername(this.getUser().getUsername());
		user.setName(this.getUser().getName());
		user.setType(UserType.valueOf(this.getUser().getType()));
		user.setBj(this.getUser().getBj());
		user.setYear(this.getUser().getYear());
		return user;
	}
}
