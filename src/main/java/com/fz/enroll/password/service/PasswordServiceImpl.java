package com.fz.enroll.password.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.MD5;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserStatus;
import com.fz.enroll.login.dao.OnLineDAO;
import com.fz.enroll.user.dao.UserDAO;

@Service("passwordService")
public class PasswordServiceImpl implements PasswordService{
	
    @Autowired
    private UserDAO userDao;
	@Autowired
	private  OnLineDAO onLineDao;

	@Override
	public Response checkService(String oldPwd) {
		String password = oldPwd==null?null:MD5.md5MatchToPHP(oldPwd);
		
		User user = userDao.queryById(Utils.getCurrentUid());
		if(user==null
				||!user.getPassword().equals(password)){
			return new Response(ReturnCode.USER_PASS_ERROR);
		}else if(user.getStatus()==UserStatus.DELETE.val()
				||user.getStatus()==UserStatus.FORBIDDEN.val()){
			return new Response(ReturnCode.USER_ACCOUNT_ERROR);
		}

		return new Response(ReturnCode.SUCCESS);
	}

	@Override
	public Response updatePasswordService(String newPwd, String oldPwd) {
		newPwd = Utils.emptyToNull(newPwd);
		oldPwd = Utils.emptyToNull(oldPwd);
		if(newPwd==null||oldPwd==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", Utils.getCurrentUid());
		params.put("newPwd", MD5.getMD5String(newPwd));
		params.put("oldPwd", MD5.getMD5String(oldPwd));
		int uc = userDao.modifyPassword(params);
		if(uc>0){
			onLineDao.deleteByUid(Utils.getCurrentUid());
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
}
