package com.fz.enroll.login.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.DateUtils;
import com.fz.common.util.IDCard;
import com.fz.common.util.MD5;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.user.service.UserService;

@Service("signInService")
public class SignInServiceImpl implements SignInService {
	
	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private UserService userService;

	@Override
	public Response signInService(String name, String username, String password) {
		Response res = timeConfigService.checkServeTime(TimeConfigType.SIGN_IN);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			res.setErrorMsg("注册服务已暂停");
			return res;
		}
		return userService.saveService(this.createEntity(name, username, password));
	}
	private User createEntity(String name, String username, String password){
		User user = new User();
		user.setName(Utils.emptyToNull(name));
		user.setUsername(Utils.emptyToNull(username));
		user.setPassword(Utils.emptyToNull(password));
		
		if(user.getName()==null
				||user.getUsername()==null
				||user.getPassword()==null){
			return null;
		}
		
//		String patternStr="1\\d{10}"; 
//		if(!Pattern.matches(patternStr, user.getUsername())){
//			return null;
//		}
		Response res = IDCard.IDCardValidate(username, false);
		if(res.getRetCode()!= ReturnCode.SUCCESS){
			return null;
		}
		int birthday = Integer.valueOf(username.substring(6,14));
		int year = DateUtils.getCurrentYear();
		if(birthday<((year-7)*10000+901)//大于6岁
				||birthday>((year-6)*10000+831)){//小于6岁
			return null;
		}
		if(user.getPassword()!=null){
			user.setPassword(MD5.getMD5String(user.getPassword()));
		}
		user.setUsername(user.getUsername().toLowerCase());//账号不区分大小写
		user.setType(UserType.PATRIARCH.val());
		user.setCtime(System.currentTimeMillis());
		return user;
	}
}
