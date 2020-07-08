package com.fz.enroll.user.dao;
 
import java.io.Serializable;
import java.util.Map;

import com.fz.base.dao.BaseDAO;
import com.fz.enroll.entity.user.User;

/**
 * 1、注意存在逻辑删除的账号，status为2
 * 2、不允许删除超级管理员，type为1{@link com.fz.enroll.enum0.UserType}
 * @author zp
 *
 */
public interface UserDAO extends BaseDAO<User>, Serializable {

	public User queryById(int id);
	
	/**
	 * 用户登陆后更新用户的最后登陆时间，以及自己登陆的token
	 * @param user
	 */
	public int updateUserAfterLogin(User user);
	/**
	 * 修改账号密码
	 * @param params
	 * @return
	 */
	public int modifyPassword(Map<String,Object> params);
 
}