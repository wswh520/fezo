package com.fz.base.service;

import java.util.List;
import java.util.Map;

import com.fz.base.dao.BaseDAO;
import com.fz.common.res.PageResData;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

public abstract class QueryBaseServiceImpl<T> implements QueryBaseService<T> {

	/**
	 * 获取数据库访问DAO
	 * @return
	 */
	protected abstract BaseDAO<T> getDao();
	
	@Override
	public Response loadService(String pageNo, Map<String, Object> params) {
		PageResData<T> page = new PageResData<T>();
		try {
			page.setCurrentPage(Integer.valueOf(pageNo));
		} catch (Exception e) {}
		
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(page);
		Page<?> _page = PageHelper.startPage(page.getCurrentPage(), page.getLimit());
		List<T> eles = this.getDao().query(params);
		page.setTotalRecords((int)_page.getTotal());
		page.setDatas(eles);
		return res;
	}

}
