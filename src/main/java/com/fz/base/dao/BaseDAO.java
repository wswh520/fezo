package com.fz.base.dao;

import java.util.List;
import java.util.Map;


public interface BaseDAO<T> {

	/**
	 * 根据条件模糊搜索
	 * @param params
	 * @return
	 */
	public List<T> query(Map<String,Object> params);
	/**
	 * 根据除主键外的唯一索引查询
	 * @param name
	 * @return
	 */
	public T queryByUnique(String name);
	public T queryByUnique(T entity);
	/**
	 * 保存信息
	 * @param entity
	 */
	public void save(T entity);
	/**
	 * 更新信息
	 * @param entity
	 * @return
	 */
	public int update(T entity);
	/**
	 * 删除 id
	 * @param entity
	 * @return
	 */
	public int delete(T entity);
}
