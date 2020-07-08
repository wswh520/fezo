<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>注册</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/resource/css/login.css" rel="stylesheet" type="text/css" />
	<%@ include file="/common/js_utils.jsp" %>
	<%@ include file="/common/js_juicer.jsp" %>
	<script src="${ctx}/resource/js/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/jquery-placeholder.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/IdentifyCode.js?v=15.07.02" type="text/javascript"></script>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<script src="${ctx}/resource/js/login.js?v=170608" type="text/javascript"></script>
</head>
<body class="login_index">
	<!--Header Start-->
	<div class="IndexHeader clearfix">
		<div class="logo"><a href="javascript:void(0);"><!--<img src="${ctx}/resource/img/login_logo.png" />--></a></div>
	</div>
	<!--Header Over--> 
	<!--Main Start-->
	<div class="Main clearfix">
		<div class="indexColumnOne clearfix pdtb10">
			<div class="focus-1 fl">
				<img src="${ctx}/resource/img/1.png" />
			</div>
			<!--#loginbox-->
			<div class="LoginBox fr pd15" style="height:330px;">
				<h2 class="mgb10">平台注册</h2>
				<form id="form_signIn">
					<div class="inpLogin">
						<input id="ipt_name" type="text" name="name" placeholder="请输入学生的真实姓名" />
						<span class="loginIco form_user"></span>
					</div>
					<div class="inpLogin mgtb10">
						<input id="ipt_username" type="text" name="username" placeholder="请输入学生的身份证号" />
						<span class="loginIco form_email"></span>
					</div>
					<div class="inpLogin mgtb10">
						<input id="ipt_password" type="password" name="password" placeholder="请输入学生的密码" />
						<span class="loginIco form_psw"></span>
					</div>
					<div class="inpLogin mgtb10">
						<input id="ipt_password_confirm" type="password" placeholder="请重新输入学生的密码" />
						<span class="loginIco form_psw"></span>
					</div>
					<p class="item fr" style="line-height:30px;">
						已有注册账号请点击<a href="${ctx}/login" class="reg">登录</a>！
					</p>
					<input id="ipt_signIn" onclick="javascript:doSignIn();" type="button" class="RegistrationSubmit" />
				</form>
			</div>
			<!--loginbox--> 
		</div>
	</div>
	
	<div class=" IndexFooter">
		<div class="FooterCon clearfix pdtb10">
			<p class="tc">Copyright &copy; 华中师范大学附属小学 </p>
		</div>
	</div>
</body>
</html>