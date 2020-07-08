package com.fz.enroll.user.service;

import java.util.Map;

import com.fz.base.service.BaseService;
import com.fz.common.res.Response;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserType;

/**
 * @author acer
 *
 */
public interface UserService extends BaseService<User> {

	/**
	 * 生成查询条件
	 * @param keyword	模糊匹配姓名与账号
	 * @param type		用户类型
	 * @return	｛keyword:模糊匹配姓名与账号,type:用户类型｝
	 */
	public Map<String, Object> createQueryParams(String keyword,UserType type);
	/**
	 * 禁用账户
	 * 		只可针对未删除账户进行操作
	 * @param id
	 * @return
	 */
	public Response forbiddenService(String id,UserType type);
	/**
	 * 启用账户
	 * 		只可针对未删除账户进行操作
	 * @param id
	 * @return
	 */
	public Response enabledService(String id,UserType type);
	
}
