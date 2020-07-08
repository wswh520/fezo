package com.fz.base.service;

import com.fz.common.res.Response;

public interface BaseService<T> extends QueryBaseService<T> {
	/**
	 * 添加(修改)
	 */
	public Response saveService(T entity);
	/**
	 * 删除
	 * 		不支持级联删除
	 * @param entity
	 * @return
	 */
	public Response delService(T entity);
}
