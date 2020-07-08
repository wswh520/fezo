package com.fz.common.res;

public class ReturnCode {
    public static final String RETURN_KEY = "retCode";
	

    //100通用
	public static final int SUCCESS				= 100000;//成功
	public static final int NETWORK_ERROR		= 100001;//网络异常
	public static final int SERVER_INNER_ERROR	= 100002;//失败,内部错误
	public static final int NOT_LOGIN			= 100003;//未登陆
	public static final int UNAUTHORIZED		= 100004;//权限不足
	public static final int NOT_IN_SERVE_TIME	= 100005;//不在服务时间
    //200用户相关
	public static final int USER_EXIST			= 200001;//用户名已存在
	public static final int USER_NOT_EXIST		= 200002;//用户名不存在
	public static final int USER_PASS_ERROR		= 200003;//用户名或密码错误
	public static final int USER_CAPTCHA_ERROR	= 200004;//验证码错误
	public static final int USER_ACCOUNT_ERROR	= 200005;//账号异常
    public static final int USER_NOT_SIGNLN		= 200006;//用户名未登记
	//300文件相关
	public static final int FILE_EXIST			= 300001;//文件已存在
	public static final int FILE_NOT_EXIST		= 300002;//文件不存在
	public static final int FILE_UNFINISHED		= 300003;//文件不完整
	public static final int FILE_CAPACITY_NOT_ENOUGH	= 300004;//容量不够
	public static final int FILE_ATT_UNUSABLE	= 300005;//附件不可用
}
