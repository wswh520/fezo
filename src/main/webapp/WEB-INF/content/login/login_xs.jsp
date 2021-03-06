<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登陆</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/resource/css/login.css" rel="stylesheet" type="text/css" />
	<style>
		.add-ul {
			background: url(${ctx}/resource/img/ul-bg.jpg) no-repeat;
			padding: 45px 55px;
			height: 100px;
		}
		
		.add-ul li {
			float: left;
			width: 95%;
			padding-left: 15px;
			background: url(${ctx}/resource/img/g-tit-a-li.png) no-repeat 0 12px;
			height: 30px;
			line-height: 30px;
			font-size: 14px;
		}
	</style>
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
	<%@ include file="/common/js_utils.jsp" %>
	<%@ include file="/common/js_juicer.jsp" %>
	<script src="${ctx}/resource/js/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/jquery-placeholder.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/login.js" type="text/javascript"></script>
</head>
<body class="login_index">
	<!--Header Start-->
	<div class="IndexHeader clearfix">
		<a class="file_down" href="${ctx}/resource/钟家村寄宿学校新生入学招生系统用户须知.pdf" target="_blank"><i>&nbsp;</i>下载帮助文档</a>
		<a class="file_down" href="${ctx}/resource/钟家村寄宿学校招生系统使用说明.pdf" target="_blank"><i>&nbsp;</i>下载家长用户操作流程</a>
		<!--<div class="logo"><a href="javascript:void(0);"><img src="${ctx}/resource/img/login_logo.png" /></a></div>-->
		<div class="logo" style="font-size: 25px;color: black;">钟家村寄宿学校新生网上登记系统</div>
	</div>
	<!--Header Over--> 
	<!--Main Start-->
	<div class="Main clearfix">
		<div class="indexColumnOne clearfix pdtb10">
			<div class="focus-1 fl">
				<img src="${ctx}/resource/img/1.png" />
			</div>
			<!--#loginbox-->
			<div class="LoginBox fr pd15">
                <h2 class="mgb10" style="display: inline-block;">平台登录</h2>
                <h2 class="mgb10" style="display: inline-block;float: right;"><a href="${ctx}/cas/login">教师登陆</a></h2>
				<form id="form_login">
					<div class="inpLogin">
						<input id="ipt_username" type="text" name="username" placeholder="请输入学生的身份证号码" />
						<span class="loginIco form_user"></span>
					</div>
					<div class="inpLogin mgtb10">
						<input id="ipt_password" type="password" name="password" placeholder="学生的密码" />
						<span class="loginIco form_psw"></span>
					</div>
					<p class="item">
							<label><input name="rememberMe" type="checkbox" value="true" /> 记住我</label>
							<a href="${ctx}/signIn" class="reg fr">&nbsp;&nbsp;&nbsp;注册</a>
							<a href="javascript:void(0);" class="forget fr">忘记密码?</a>
					</p>
					<p id="p_loginFailed" class="LoginTips"></p>
					<input id="ipt_submit" onclick="javascript:doLogin();" type="button" class="LoginSubmit" />
					<input id="ipt_waiting" style="display:none;" onclick="javascript:void(0);" type="button" class="LoginSubmit-1" />
				</form>
			</div>
			<!--loginbox--> 
		</div>
	</div>
	<ul class="add-ul">
		<!--<li>2017年6月1日-11日为系统测试阶段</li>
		<li>2017年6月11日将清空所有数据</li>-->
		<li>2018年5月11日—14日&nbsp;&nbsp;华师教职工子弟、第三代注册、登记</li>
		<li>2018年5月22日&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;华师教职工子弟、第三代现场资格审核</li>
		<li>网上登记对象是本校服务范围内年满6周岁的适龄儿童。</li>
		<li>适龄儿童：2011年9月1日-2012年8月31日之间出生的儿童。</li>
		<div class="clearfix"></div>
	</ul>
	<div class=" IndexFooter">
		<div class="FooterCon clearfix pdtb10">
			<p class="tc">Copyright &copy; 钟家村寄宿学校 </p>
		</div>
	</div>
</body>
</html>