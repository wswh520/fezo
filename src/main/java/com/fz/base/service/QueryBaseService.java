package com.fz.base.service;

import java.util.Map;

import com.fz.common.res.Response;

public interface QueryBaseService<T> {

	/**
	 * 获取列表
	 * @param pageNo	当前页码
	 * @param params	查询条件
	 * @return
	 */
	public Response loadService(String pageNo,Map<String,Object> params);

}
