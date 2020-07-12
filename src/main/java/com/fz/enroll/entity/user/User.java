package com.fz.enroll.entity.user;

import com.fz.common.security.CurrentUser;
import com.fz.common.util.DateUtils;
import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.UserStatus;
import com.fz.enroll.enum0.UserType;
import org.apache.log4j.helpers.DateTimeDateFormat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class User extends BaseEntity {
	
	private String username;
	private String password;
	private String name;
	/**
	 * {@link com.fz.enroll.enum0.UserType}
	 */
	private int type;
	private String token;
	/**
	 * {@link com.fz.enroll.enum0.UserStatus}
	 */
	private Integer status;
	private long dtime;
	private Date ctime;
	private String bj;
	private Integer year;
	
	public CurrentUser convertToCurrentUser(){
		CurrentUser user = new CurrentUser();
		user.setUid(this.getId());
		user.setUsername(this.getUsername());
		user.setName(this.getName());
		user.setToken(this.getToken());
		user.setType(UserType.valueOf(this.getType()));
		return user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public long getDtime() {
		return dtime;
	}

	public void setDtime(long dtime) {
		this.dtime = dtime;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getBj() {
		return bj;
	}

	public void setBj(String bj) {
		this.bj = bj;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	/******************************/
	public UserStatus getStatusStr(){
		return UserStatus.valueOf(this.getStatus());
	}
	public String getUsernameStr(){
		if (this.getUsername()!=null) {
			return this.getUsername().toUpperCase();
		}
		return "";
	}
	public String getCtimeStr(){
		SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		return ctime!=null?DateUtils.timeToString(ctime, "yyyy-MM-dd HH:mm:ss"):"不详";
		return ctime!=null?formatter.format(ctime):"不详";
	}

}
