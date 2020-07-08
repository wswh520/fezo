<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>登陆</title>
    <%@ include file="/common/meta.jsp"%>
    <link href="${ctx}/resource/css/login.css" rel="stylesheet" type="text/css" />
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
    <a class="file_down" href="javascript:void(0)" onclick="{window.open('${ctx}/resource/doc/校编学号查询表.xls');};" ><i>&nbsp;</i>校编学号查询表</a>
    <div class="logo"><a href="javascript:void(0);" style="text-decoration;font-size: 30px; color: black; ">华师附小毕业生信息核对系统</a></div>
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
                    <input id="ipt_username" type="text" name="username" placeholder="校编学号1110320001~1110320320" />
                    <span class="loginIco form_user"></span>
                </div>
                <div class="inpLogin mgtb10">
                    <input id="ipt_password" type="password" name="password" placeholder="学生身份证号后六位,如有X,请大写" />
                    <span class="loginIco form_psw"></span>
                </div>
                <p class="item">
                    <label><input name="rememberMe" type="checkbox" value="true" /> 记住我</label>
                </p>
                <p id="p_loginFailed" class="LoginTips"></p>
                <input id="ipt_submit" onclick="javascript:doLogin();" type="button" class="LoginSubmit" />
                <input id="ipt_waiting" style="display:none;" onclick="javascript:void(0);" type="button" class="LoginSubmit-1" />
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