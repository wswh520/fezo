<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<script type="text/javascript">
	function dd(retCode,msg){
		window.parent.window.import_callBack(retCode,msg);
	}
	</script>
</head>
<body onload="dd(${retCode},'${msg}')">
<input type="text" value="temppage">
</body>
</html>