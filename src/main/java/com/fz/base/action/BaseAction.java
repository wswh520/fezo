package com.fz.base.action;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fz.base.dto.BaseDTO;
import com.fz.base.service.BaseService;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;


public abstract class BaseAction<Entity,Dto extends BaseDTO<Entity>> extends QueryBaseAction<Entity> {

	protected abstract BaseService<Entity> getService();
	protected abstract Entity getDeleteEntity(int id);
	@ResponseBody
	@RequestMapping("save")
	public Response doSave(Dto entity) {
		Response res = this.getService().saveService(entity.convert2Entity());
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
}
