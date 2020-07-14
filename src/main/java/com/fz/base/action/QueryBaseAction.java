package com.fz.base.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.base.service.QueryBaseService;
import com.fz.common.res.Response;


public abstract class QueryBaseAction<Entity> {

	protected abstract QueryBaseService<Entity> getService();
	protected abstract Map<String,Object> getParams(HttpServletRequest request);
	
	@ResponseBody
	@RequestMapping("load")
	public Response doLoad(String pageNo,HttpServletRequest request) {
		Response res = this.getService().loadService(pageNo,this.getParams(request));
		CurrentUser user = ThreadLocalUtils.getCurrentUser();
		if(user != null){
			res.setName(user.getName());
		}
		return res;
	}
}
