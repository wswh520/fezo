package com.fz.enroll.dto.user;

import java.util.Date;
import java.util.regex.Pattern;

import com.fz.common.util.MD5;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserType;

/**
 * 新增参数接收与处理
 * @author zp
 *
 */
public class UserAdd {

	private String id;
	private String name;
	private String username;
	private String password;
	private Integer year;
	private String bj;
	

	public User convert2Entity(UserType type){
		User user = new User();
		if(this.getId()!=null){
			try {
				user.setId(Integer.valueOf(this.getId()));
			} catch (Exception e) {
				return null;
			}
		}
		user.setName(Utils.emptyToNull(this.getName()));
		user.setUsername(Utils.emptyToNull(this.getUsername()));

		String pwd = Utils.emptyToNull(this.getPassword());
		if (pwd == null && UserType.isTecherUser(type.val())){
		    pwd = "123456";//老师用户设置默认密码
        }

		user.setPassword(pwd);
		user.setYear(this.getYear());
		user.setBj(Utils.emptyToNull(this.getBj()));
		
		if(user.getName()==null
				||user.getUsername()==null
				||(user.getId()==0&&user.getPassword()==null)){
			return null;
		}
		
		String patternStr="[0-9,a-z,A-Z,\\(,\\)]{5,20}";
		if(!Pattern.matches(patternStr, user.getUsername())){
			return null;
		}
		if(user.getPassword()!=null){
			user.setPassword(MD5.getMD5String(user.getPassword()));
		}
		user.setUsername(user.getUsername().toLowerCase());//账号不区分大小写
		user.setType(type.val());
//		user.setCtime(System.currentTimeMillis());
		user.setCtime(new Date());
		
		return user;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
}
