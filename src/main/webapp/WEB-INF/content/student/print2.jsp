<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
	Cookie [] cookie = request.getCookies();
	String sessionId = null;
	if(cookie != null) {
		for (int i = 0; i < cookie.length; i++) {
			if("library_ms_sid".equals(cookie[i].getName())) {
				sessionId = cookie[i].getValue();
			}
		}
	}
	String http = "";
	if(request.getServerPort()==80||request.getServerPort()==443){
		http = request.getScheme()+"://"+request.getServerName();
	}else{
		http = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
	}
	int id = 0;
	try{
		id = Integer.valueOf(request.getAttribute("id").toString());
	}catch(Exception e){}
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>打印</title>
	<SCRIPT LANGUAGE=javascript FOR=WebOffice1 EVENT=NotifyCtrlReady>
		var obj=document.getElementById("WebOffice1");
		obj.LoadOriginalFile("<%=http%>/stuInfo/download/<%=id%>?library_ms_sid=<%=sessionId%>", "doc");  //下载模板
		if(obj.is2007==1)
			obj.HideMenuArea("hideall","","",""); //隐藏所有菜单
		else
			obj.HideMenuArea("","","",""); //隐藏所有菜单
			obj.HideMenuItem(0x01+0x02+0x04+0x4000);//隐藏TOOLBAR中的一些按钮
			obj.OptionFlag |= 0x0080;  //打开WROD下载进度条
			obj.SetTrackRevisions(0); //不记录修订信息
			obj.ProtectDoc(1,2,"this123"); //保护WORD文档，不允许修改
			obj.PrintDoc(1); //自动弹出打印框
	</SCRIPT>
</head>
<body>
	<div style="width:100%;height:600px;display:block">
		<object id="WebOffice1" align="middle" style="LEFT: 0px; WIDTH: 100%; TOP: 0px; HEIGHT: 100%;"
				classid="clsid:E77E049B-23FC-4DB8-B756-60529A35FAD5" codebase="${ctx}/resource/WebOffice.cab#Version=7,0,0,5">
			<param name="_Version" value="65536" /><param name="_ExtentX" value="17410" />
			<param name="_ExtentY" value="10874" /><param name="_StockProps" value="0" />
		</object>
	</div>
</body>
</html>
