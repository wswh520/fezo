package com.fz.enroll.login.dao;

import com.fz.enroll.entity.user.OnLine;

public interface OnLineDAO {

	/**
	 * 根据ID获取用户登陆信息
	 * @param id
	 * @return
	 */
	public OnLine queryOnLineById(String id);
	
	/**
	 * 保存用户登陆信息
	 * @param entity
	 */
	public void saveOnLine(OnLine entity);
	public void updateOnLine(OnLine entity);
	
	/**
	 * 删除用户登陆信息
	 * @param id
	 */
	public void deleteOnLine(String id);
	/**
	 * 删除用户的所有登陆信息
	 * @param uid
	 */
	public void deleteByUid(int uid);
	/**
	 * 删除学生用户的所有登陆信息
	 * @param uid
	 */
	public void deleteStudentOnLineByUid(int uid);
	/**
	 * 清除过期的登陆信息
	 */
	public void clearTimeoutSession(long deadline);
}
