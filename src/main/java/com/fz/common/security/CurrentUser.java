package com.fz.common.security;

import java.io.Serializable;

import com.fz.enroll.entity.user.OnLine;
import com.fz.enroll.enum0.UserType;

public class CurrentUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String sid;
	private int uid;
	private String username;
	private String name;
	private String token;
	private UserType type;
	private String bj;
	private Integer year;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserType getType() {
		return type;
	}
	public void setType(UserType type) {
		this.type = type;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getBj() {
		return bj;
	}
	public void setBj(String bj) {
		this.bj = bj;
	}
	
	/*********************************************************/
	public OnLine convertToOnLine(){
		OnLine onLine = new OnLine();
		onLine.setUid(this.getUid());
		onLine.setDt(System.currentTimeMillis());
		return onLine;
	}
	
}
