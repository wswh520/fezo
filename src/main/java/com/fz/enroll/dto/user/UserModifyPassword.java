package com.fz.enroll.dto.user;

import com.fz.common.util.MD5;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserType;

/**
 * 密码修改参数接收与处理
 * @author zp
 *
 */
public class UserModifyPassword {

	private String id;
	private String password;
	
	public User convert2Entity(UserType type){
		User user = new User();
		try {
			user.setId(Integer.valueOf(this.getId()));
		} catch (Exception e) {
			return null;
		}
		user.setPassword(Utils.emptyToNull(this.getPassword()));
		if(user.getPassword()==null){
			return null;
		}
		user.setPassword(MD5.getMD5String(user.getPassword()));
		user.setType(type.val());
		return user;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
