<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>首页</title>
	<%@ include file="/common/meta.jsp"%>
	<link href="${ctx}/resource/css/style.css?v=170615" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/css/xt.css?v=170615" rel="stylesheet" type="text/css" />
	<link href="${ctx}/resource/js/calendar/skin/WdatePicker.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript">
        var ctx = '${ctx}';
    </script>
	<%@ include file="/common/js_utils.jsp" %>
	<%@ include file="/common/js_juicer.jsp" %>
	<script src="${ctx}/resource/js/lib/jquery.form.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/Cmenu.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/global.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/calendar/WdatePicker.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/jquery-placeholder.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/jquery.rotate.1-1.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/password.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/lib/passwordCheck.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/index.js?v=15.08.28" type="text/javascript"></script>
	<script src="${ctx}/resource/js/user.js?v=170608" type="text/javascript"></script>
	<script src="${ctx}/resource/js/config.js" type="text/javascript"></script>
	<script src="${ctx}/resource/js/student.js?v=18.05.28" type="text/javascript"></script>
	<script src="${ctx}/resource/js/IdentifyCode.js?v=15.07.02" type="text/javascript"></script>
	<script src="${ctx}/resource/js/applyStuPic.js?v=17.06.20" type="text/javascript"></script>
</head>
<body class="login_index">
	<iframe id="formFrame" name="formFrame" style="display:none;"></iframe>
	<input id="ipt_pageNo" type="hidden" value="1" />
	<input id="ipt_parentId" type="hidden" value="" />
	<jsp:include flush="true" page="/WEB-INF/content/common/header.jsp" />
	<div id="mkdc_wrapper" class="mkclearfix" style="width:100%;">
		<jsp:include flush="true" page="/WEB-INF/content/index/left.jsp" />
		<div id="div_display" class="mkclearfix-right">
			<jsp:include flush="true" page="/WEB-INF/content/index/index_display.jsp" />
		</div>
		<div class="clear"></div>
	</div>
	<jsp:include flush="true" page="/WEB-INF/content/common/bottom.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/common/dialog.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/password/password_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/index/index_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/user/user_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/config/config_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/student/stuApply_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/student/stuApplyList_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfo_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfoList_model.jsp" />
	<jsp:include flush="true" page="/WEB-INF/content/student/applyStuPic_model.jsp" />
</body>
</html>