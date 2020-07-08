<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>登陆</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/resource/css/login.css" rel="stylesheet" type="text/css" />
	<%@ include file="/common/js_utils.jsp" %>
	<%@ include file="/common/js_juicer.jsp" %>
	<script src="${ctx}/resource/js/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/jquery-placeholder.js" type="text/javascript"></script>
	<script type="text/javascript">
		var ctx = '${ctx}';
	</script>
	<script src="${ctx}/resource/js/login.js" type="text/javascript"></script>
</head>
<body class="login_index">
	<div  style="margin-left: 335px;margin-top: 100px;">
		<div style="text-decoration;font-size: 50px; color: black; ">钟家村小学</div>
	</div>
	<div class="Main clearfix">
		<div class="indexColumnOne clearfix pdtb10">
			<div class="login">
				<ul style="display: flex;margin-top: 50px;">
					<li style="margin-right: 305px;"><a href="${ctx}/xs/login"><img src="${ctx}/resource/img/login-1.jpg" /></a></li>
					<li><a href="${ctx}/bys/login"><img src="${ctx}/resource/img/login-2.jpg?v=16.0121" /></a></li>
				</ul>
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