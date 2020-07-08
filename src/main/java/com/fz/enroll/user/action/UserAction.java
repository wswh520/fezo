package com.fz.enroll.user.action;
 
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.base.action.QueryBaseAction;
import com.fz.base.service.BaseService;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.enroll.dto.user.UserAdd;
import com.fz.enroll.dto.user.UserModifyPassword;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.user.service.UserService;

public abstract class UserAction extends QueryBaseAction<User> {

	protected abstract UserType getUserType();
	
	@Autowired
	private UserService userService;

	@Override
	protected BaseService<User> getService() {
		return userService;
	}
	
	@ResponseBody
	@RequestMapping("save")
	public Response doSave(UserAdd entity) {
		Response res = this.getService().saveService(entity.convert2Entity(this.getUserType()));
		return res;
	}

	@ResponseBody
	@RequestMapping("del")
	public Response doDel(String id){
		int int_id = 0;
		try {
			int_id = Integer.valueOf(id);
		} catch (Exception e) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		Response res = this.getService().delService(this.getDeleteEntity(int_id));
		return res;
	}
	private User getDeleteEntity(int id){
		User entity = new User();
		entity.setId(id);
		entity.setType(this.getUserType().val());
		return entity;
	}

	@Override
	protected Map<String, Object> getParams(HttpServletRequest request) {
		String keyword = request.getParameter("keyword");
		return userService.createQueryParams(keyword,this.getUserType());
	}

	@ResponseBody
	@RequestMapping("resetPassword")
	public Response doResetPassword(UserModifyPassword entity) {
		Response res = userService.saveService(entity.convert2Entity(this.getUserType()));
		return res;
	}

	@ResponseBody
	@RequestMapping("forbidden")
	public Response doForbidden(String id) {
		Response res = userService.forbiddenService(id,this.getUserType());
		return res;
	}

	@ResponseBody
	@RequestMapping("enabled")
	public Response doEnabled(String id) {
		Response res = userService.enabledService(id,this.getUserType());
		return res;
	}

}