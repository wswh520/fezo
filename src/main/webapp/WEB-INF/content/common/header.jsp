<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.fz.common.security.CurrentUser"%>
<%@page import="com.fz.common.security.ThreadLocalUtils"%>
<%@ page import="com.fz.enroll.enum0.UserType" %>
<%
	CurrentUser user = ThreadLocalUtils.getCurrentUser();
	boolean isTeacher = UserType.isTecherUser(user.getType().val());
%>
<!--导航 开始-->
<div class="top">
	<div class="base-top" style="width:100%;">
		<div class="top-right" style="float:right;">
			<ul>
                <% if (isTeacher){%>
                <%--<li><a href="https://account.ccnu.edu.cn/cas/logout?service=http://hsfxzs.fezo.com.cn:82/login_out">退出</a></li>--%>
                <%--<li><a href="${ctx}/cas/login_out">退出</a></li>--%>
				<li><a href="${ctx}/login_out">退出</a></li>
                <% }else { %>
				<li><a href="${ctx}/login_out">退出</a></li>
                <% } %>
				<li>|</li>
				<li>您好，<a href="javascript:void(0);"><%=user.getName() %></a></li>
			</ul>
		</div>
		<div class="logo" style="width:25%;">
			<span>钟家村小学</span>
		</div>
	</div>
	<input type="hidden" id="ipt_userType" value="<%=user.getType() %>")>
    <input type="hidden" id="ipt_isTeacher" value="<%=isTeacher %>" )>
</div>
<!--导航 结束-->