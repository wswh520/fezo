package com.fz.enroll.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserStatus;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.user.dao.UserDAO;

@Service("loginService")
public class LoginServiceImpl implements LoginService {

	@Autowired
	private OnlineManager onlineManager;
	@Autowired
	private UserDAO userDao;
	@Autowired
	private TimeConfigService timeConfigService;
	@Override
    public Response casLoginService(HttpServletRequest request, HttpServletResponse response){
        String username = request.getRemoteUser();
        if (!CommonUtils.isNotBlank(username)) {
            Response res = new Response(ReturnCode.NETWORK_ERROR);
            res.setErrorMsg("当前一卡通账号登陆失败");
            return res;
        }
        User user = userDao.queryByUnique(username.toLowerCase());
        if(user==null){
            Response res = new Response(ReturnCode.USER_NOT_SIGNLN);
            res.setErrorMsg("当前一卡通账号不能使用本系统");
            return res;
        }else if(user.getStatus()!=UserStatus.NORMAL.val()){
            Response res = new Response(ReturnCode.USER_ACCOUNT_ERROR);
            res.setErrorMsg("一卡通账号异常，请联系账号管理员！");
            return res;
        }
        return loginService(user,false,request,response);
    }

	@Override
	public Response loginService(String username,String password,boolean rememberMe,
			HttpServletRequest request, HttpServletResponse response){
		username = Utils.emptyToNull(username);
		password = Utils.emptyToNull(password);
		if(username==null||(password==null)){
			return new Response(ReturnCode.USER_PASS_ERROR);
		}
		User user = userDao.queryByUnique(username.toLowerCase());
        if(user==null){
            return new Response(ReturnCode.USER_NOT_EXIST);
        /*}else if (UserType.isTecherUser(user.getType())){//老师账户
            Response res = new Response(ReturnCode.NETWORK_ERROR);
            res.setErrorMsg("服务已暂停,请使用登陆窗口右上角链接\"教师登陆\"登陆");
            return res;*/
        }else if (!user.getPassword().equals(password)){
            return new Response(ReturnCode.USER_PASS_ERROR);
        }else if(user.getStatus()!=UserStatus.NORMAL.val()){
            return new Response(ReturnCode.USER_ACCOUNT_ERROR);
        }
        return loginService(user,rememberMe,request,response);
	}
	private Response loginService(User user, boolean rememberMe,
                                  HttpServletRequest request, HttpServletResponse response){
        if(user.getType()==UserType.PATRIARCH.val()){
            Response res = timeConfigService.checkServeTime(TimeConfigType.SERVE);
            if(res.getRetCode()!=ReturnCode.SUCCESS){
                res.setErrorMsg("服务已暂停");
                return res;
            }
        }else if(user.getType()==UserType.GRADUATE.val()){
            Response res = timeConfigService.checkServeTime(TimeConfigType.GRA_SERVE);
            if(res.getRetCode()!=ReturnCode.SUCCESS){
                res.setErrorMsg("服务已暂停");
                return res;
            }
        }

        String token = null;
        if(rememberMe){
            token = Utils.getRandomString(30);
        }

        this.loginSuccess(user, token,request, response);

        Response res = new Response();
        res.setRetCode(ReturnCode.SUCCESS);
        res.setData(user);
        return res;
    }
	@Override
	public Response loginByTokenService(int uid,String token){
		token = Utils.emptyToNull(token);
		if(uid<0||token==null){
			return new Response(ReturnCode.USER_PASS_ERROR);
		}
		User user  = userDao.queryById(uid);
		if(user==null
				||!token.equals(user.getToken())){
			return new Response(ReturnCode.USER_PASS_ERROR);
		}else if(user.getStatus()!=UserStatus.NORMAL.val()){
			return new Response(ReturnCode.USER_ACCOUNT_ERROR);
		}

		Response res = new Response();
		res.setData(user.convertToCurrentUser());
		res.setRetCode(ReturnCode.SUCCESS);
		return res;
	}

	private void loginSuccess(User user,String token,
			HttpServletRequest request,
			HttpServletResponse response){
		user.setToken(token);
		userDao.updateUserAfterLogin(user);//更新上次访问时间和记住密码token
		CurrentUser cuser = user.convertToCurrentUser();
		onlineManager.lineOn(request, response, cuser);
	}
	@Override
    public Response casCheckService(HttpServletRequest request, HttpServletResponse response){
        String username = request.getRemoteUser();
        if (!CommonUtils.isNotBlank(username)
                || !username.equals(Utils.getCurrentUsername())) {
            onlineManager.lineOff(request,response);
            return new Response(ReturnCode.NOT_LOGIN);
        }
        return new Response(ReturnCode.SUCCESS);
    }
}
