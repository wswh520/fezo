package com.fz.base.service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.enroll.entity.BaseEntity;

public abstract class BaseServiceImpl<T extends BaseEntity> extends QueryBaseServiceImpl<T> implements BaseService<T> {

	/**
	 * 检查记录的唯一性
	 * @param entity
	 * @return
	 */
	protected abstract Response checkUnique(T entity);
	/**
	 * 删除失败
	 * @param entity
	 * @return
	 */
	protected abstract Response deleteFailed(T entity);

	@Override
	public Response saveService(T entity) {
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = this.checkUnique(entity);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		this.prepareSave(entity);
		if(entity.getId()==0){//新增
			this.getDao().save(entity);
			if(entity.getId()!=0){
//				logService.addSchool(entity);
				return this.saveSuccess(entity);
			}
		}else {//更新
			int count = this.getDao().update(entity);
			if(count>0){
//				logService.modifySchool(oldInfo, entity);
				return new Response(ReturnCode.SUCCESS);
			}
		}
		return new Response(ReturnCode.SERVER_INNER_ERROR);
	}
	protected void prepareSave(T entity){}
	protected Response saveSuccess(T entity){
		return new Response(ReturnCode.SUCCESS);
	}

	@Override
	public Response delService(T entity) {
		if(entity==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		try {
			int count = this.getDao().delete(entity);
			return new Response(count > 0 ? ReturnCode.SUCCESS
					: ReturnCode.SERVER_INNER_ERROR);
		} catch (Exception e) {
			return this.deleteFailed(entity);
		}
	}

}
