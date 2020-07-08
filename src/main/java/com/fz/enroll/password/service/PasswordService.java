package com.fz.enroll.password.service;

import com.fz.common.res.Response;

public interface PasswordService{
	
	/**
	 * 检查原始密码是否正确
	 * @param oldPwd
	 * @return
	 */
	public Response checkService(String oldPwd);
	/**
	 * 修改密码
	 * 		同时退出该账号的所有登陆信息
	 * @param newPwd
	 * @param oldPwd
	 * @return
	 */
	public Response updatePasswordService(String newPwd,String oldPwd);
}
