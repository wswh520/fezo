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
			/*background: url(${ctx}/resource/img/ul-bg.jpg) no-repeat;*/
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
		<%--<a class="file_down" href="${ctx}/resource/华师附小新生入学招生系统用户须知.pdf" target="_blank"><i>&nbsp;</i>下载帮助文档</a>
		<a class="file_down" href="${ctx}/resource/华师附小招生系统使用说明.pdf" target="_blank"><i>&nbsp;</i>下载家长用户操作流程</a>--%>
		<!--<div class="logo"><a href="javascript:void(0);"><img src="${ctx}/resource/img/login_logo.png" /></a></div>-->
		<div class="logo" style="font-size: 25px;color: black;">钟家村小学新生网上登记系统</div>
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
                <%--<h2 class="mgb10" style="display: inline-block;float: right;"><a href="${ctx}/cas/login">教师登陆</a></h2>--%>
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
							<%--<label><input name="rememberMe" type="checkbox" value="true" /> 记住我</label>--%>
							<a href="${ctx}/signIn" class="reg fr">&nbsp;&nbsp;&nbsp;注册</a>
							<%--<a href="javascript:void(0);" class="forget fr">忘记密码?</a>--%>
					</p>
					<p id="p_loginFailed" class="LoginTips"></p>
					<input id="ipt_submit" onclick="javascript:doLogin();" type="button" class="LoginSubmit" />
					<input id="ipt_waiting" style="display:none;" onclick="javascript:void(0);" type="button" class="LoginSubmit-1" />
				</form>
			</div>
			<!--loginbox--> 
		</div>
	</div>
<%--	<ul class="add-ul">--%>
<%--		<li>2019年6月8-12日 华师教职工子弟（含第三代）和洪山区人民政府划片入学生源注册、登记</li>--%>
<%--		<li>桂子山校区现场审核时间：2019年6月15日上午9：30-12：00；下午2：00-4：30 附小北教学楼一楼</li>--%>
<%--		<li>南湖校区现场审核时间：2019年6月19日上午9：30-12：00；下午2：00-4：30 附小南湖校区综合楼一楼</li>--%>
<%--		<li>桂子山校区招收2012年9月1日—2013年8月31日之间出生的华师教职工第二代、第三代子弟。</li>--%>
<%--		<li>南湖校区招收2012年9月1日—2013年8月31日之间出生，洪山区人民政府2019年划片对口入学的生源。</li>--%>
<%--		<div class="clearfix"></div>--%>
<%--	</ul>--%>
	<div class=" IndexFooter">
		<div class="FooterCon clearfix pdtb10">
			<p class="tc">Copyright &copy; 钟家村小学 </p>
		</div>
	</div>
</body>
</html>