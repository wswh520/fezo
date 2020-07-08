package com.fz.enroll.user.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.base.dao.BaseDAO;
import com.fz.base.service.BaseServiceImpl;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.UserStatus;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.login.dao.OnLineDAO;
import com.fz.enroll.user.dao.UserDAO;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
//	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDAO userDao;
	@Autowired
	private  OnLineDAO onLineDao ;
	
	@Override
	protected BaseDAO<User> getDao() {
		return userDao;
	}

	@Override
	protected Response checkUnique(User entity) {
		User exist = entity.getUsername()==null?null:userDao.queryByUnique(entity.getUsername());
		if(exist!=null&&exist.getId()!=entity.getId()){
			return new Response(ReturnCode.USER_EXIST);
		}
		return new Response(ReturnCode.SUCCESS);
	}
	
	@Override
	protected Response deleteFailed(User delEntity) {
		Response res = new Response();
		res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
		res.setErrorMsg("已有关联数据，不能删除！");
		return res;
		//物理删除失败改用标记删除
//		User user = new User();
//		user.setId(delEntity.getId());
//		user.setStatus(UserStatus.DELETE.val());
//		user.setDtime(System.currentTimeMillis());
//		int count = userDao.update(user);
//		return new Response(count>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

	@Override
	public Map<String, Object> createQueryParams(String keyword,UserType type) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("keyword", Utils.emptyToNull(keyword));
		params.put("type", type.val());
		return params;
	}

	@Override
	public Response forbiddenService(String userId,UserType type){
		int id = 0;
		try {
			id = Integer.valueOf(userId);
		} catch (Exception e) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		User user = new User();
		user.setId(id);
		user.setType(type.val());
		user.setStatus(UserStatus.FORBIDDEN.val());
		//禁用之前删除用户的登陆信息
		onLineDao.deleteByUid(id);
		
		int count = userDao.update(user);
		return new Response(count>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

	@Override
	public Response enabledService(String userId,UserType type){
		int id = 0;
		try {
			id = Integer.valueOf(userId);
		} catch (Exception e) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		User user = new User();
		user.setId(id);
		user.setType(type.val());
		user.setStatus(UserStatus.NORMAL.val());
		int count = userDao.update(user);
		return new Response(count>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

}
