var user_loadUrl = {};
	user_loadUrl["ADMIN"] = ctx+"/admin/load/ajax";
	user_loadUrl["PATRIARCH"] = ctx+"/patriarch/load/ajax";
	user_loadUrl["HEALTH_TEACHER"] = ctx+"/hteacher/load/ajax";
	user_loadUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/load/ajax";
	user_loadUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/load/ajax";
var user_saveUrl = {};
	user_saveUrl["ADMIN"] = ctx+"/admin/save/ajax";
	user_saveUrl["PATRIARCH"] = ctx+"/patriarch/save/ajax";
	user_saveUrl["HEALTH_TEACHER"] = ctx+"/hteacher/save/ajax";
	user_saveUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/save/ajax";
	user_saveUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/save/ajax";
var user_resetPasswordUrl = {};
	user_resetPasswordUrl["ADMIN"] = ctx+"/admin/resetPassword/ajax";
	user_resetPasswordUrl["PATRIARCH"] = ctx+"/patriarch/resetPassword/ajax";
	user_resetPasswordUrl["HEALTH_TEACHER"] = ctx+"/hteacher/resetPassword/ajax";
	user_resetPasswordUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/resetPassword/ajax";
	user_resetPasswordUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/resetPassword/ajax";
var user_delUrl = {};
	user_delUrl["ADMIN"] = ctx+"/admin/del/ajax";
	user_delUrl["PATRIARCH"] = ctx+"/patriarch/del/ajax";
	user_delUrl["HEALTH_TEACHER"] = ctx+"/hteacher/del/ajax";
	user_delUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/del/ajax";
	user_delUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/del/ajax";
var user_forbiddenUrl = {};
	user_forbiddenUrl["ADMIN"] = ctx+"/admin/forbidden/ajax";
	user_forbiddenUrl["PATRIARCH"] = ctx+"/patriarch/forbidden/ajax";
	user_forbiddenUrl["HEALTH_TEACHER"] = ctx+"/hteacher/forbidden/ajax";
	user_forbiddenUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/forbidden/ajax";
	user_forbiddenUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/forbidden/ajax";
var user_enabledUrl = {};
	user_enabledUrl["ADMIN"] = ctx+"/admin/enabled/ajax";
	user_enabledUrl["PATRIARCH"] = ctx+"/patriarch/enabled/ajax";
	user_enabledUrl["HEALTH_TEACHER"] = ctx+"/hteacher/enabled/ajax";
	user_enabledUrl["GRADUATE_TEACHER"] = ctx+"/gteacher/enabled/ajax";
	user_enabledUrl["RECRUIT_TEACHER"] = ctx+"/rteacher/enabled/ajax";

function doLoadAdmin(){
	doLoadUser("ADMIN");
}
function doLoadPatriarch(){
	doLoadUser("PATRIARCH");
}
function doLoadrecruit_teacher(){
	doLoadUser("RECRUIT_TEACHER");
}
function doLoadhealth_teacher(){
	doLoadUser("HEALTH_TEACHER");
}
function doLoadgraduate_teacher(){
	doLoadUser("GRADUATE_TEACHER");
}
function doLoadUser(userType){
	useModel("model_userDisplay","div_display",{userType:userType,year:getCurrentYear()});
	$("#ipt_pageNo").unbind("click");
	$("#ipt_pageNo").bind("click",doToPage_user);
	toPage(1);
}
function doToPage_user(){
	var pageNo = $("#ipt_pageNo").val();
	var keyword = $("#ipt_keyword").val();
	var userType = $("#user_ipt_userType").val();
	var year = $("#sel_year").val();
	
	var params = {};
	params.url = user_loadUrl[userType]+"?_t="+new Date().getTime();
	params.postData = {pageNo:pageNo,keyword:keyword,year:year};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			json.data.userType = userType;
			useModel("model_userList","div_display_list",json.data);
		}
	};
	ajaxJSON(params);
}
function showAddUser(){
	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.submitMethod = doSaveUser;
	params.modelId = "model_userEditInfo";
	params.title = "新增账号";
	params.modelData = {userType:userType,isTeacher:isTeacher(userType)};
	showDialog380(params);
}
function doSaveUser(){
	var id = $.trim($("#user_ipt_id").val());
	var name = $.trim($("#user_ipt_name").val());
	var username = $.trim($("#user_ipt_username").val());
	var password = $.trim($("#user_ipt_password").val());
	var year =$.trim($("#user_ipt_year").val());
    var bj =$.trim($("#user_ipt_bj").val());
    var userType = $("#user_ipt_userType").val();

	if(name==""){
		alert("姓名不能为空！");
		return ;
	}
	if(username==""){
		alert("用户名不能为空！");
		return ;
	}else{
		var regex_username = /^[0-9,a-z,A-Z,\(,\)]{6,20}$/;//手机号
		if(!regex_username.test(username)){
			alert("用户名只能包括数字与字母，且长度为6到20！");
			return ;
		}
	}
	if(id==""&&password==""&&!isTeacher(userType)){
		alert("密码不能为空！");
		return ;
	}
	if($("#user_ipt_year").length>0){
		if(year==""){
			alert("年份不能为空！");
			return;
		}else {
			var regex_year= /^2\d{3}$/;
			if(!regex_year.test(year)){
				alert("请输入4位数字的年份，如:2016");
				return;
			}
		}
	}

	if($("#user_ipt_bj").length>0){
		if(bj==""){
			alert("班级不能为空！");
			return;
		}else {
			var regext_bj=/^[0-9]{3}$/;
			if(!regext_bj.test(bj)){
				alert("请输入3位数字的班级，如:601");
				return;
			}
		}
	}	
	var postData = {};
	if(id!=""){
		postData.id = id;
	}
	postData.name = name;
	postData.username = username;
	postData.password = password;
	if(bj!=""){
		postData.bj = bj;
	}
	if(year!=""){
		postData.year = year;
	}
	
	var params = {};
	params.url = user_saveUrl[userType]+"?_t="+new Date().getTime();
	params.postData = postData;
	params.postType = "post";
	params.error = "保存失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			toPage(0);
		}else if(json.retCode==CODE_USER_EXIST){
			alert("保存失败：用户名已存在！");
		}else{
			alert("保存失败！");
		}
	};
	ajaxJSON(params);
}
function showModifyPassword(id){
	var name = $.trim($("#user_name_"+id).html());
	var username = $.trim($("#user_username_"+id).html());
	var params = {};
	params.submitMethod = doResetPassword;
	params.modelId = "model_userEditInfo";
	params.modelData = {id:id};
	params.title = "重置【"+name+"("+username+")】的密码";
	showDialog380(params);
	
	$("#dialog_380_display").find("div[class='item']").hide();
	$("#dialog_380_display").find("div[data-node='password']").show();
}
function doResetPassword(){
	var id = $.trim($("#user_ipt_id").val());
	var password = $.trim($("#user_ipt_password").val());
	if(password==""){
		alert("密码不能为空！");
		return ;
	}
	var postData = {};
	postData.id = id;
	postData.password = password;

	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.url = user_resetPasswordUrl[userType]+"?_t="+new Date().getTime();
	params.postData = postData;
	params.postType = "post";
	params.error = "重置失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("重置成功！");
			$("#dialog_380").hide();
		}else{
			alert("重置失败！");
		}
	};
	ajaxJSON(params);
}
function showEditUser(id){
	var name = $.trim($("#user_name_"+id).html());
	var username = $.trim($("#user_username_"+id).html());
	var bj = $.trim($("#user_bj_"+id).html());
	var year = $.trim($("#user_year_"+id).html());	
	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.submitMethod = doSaveUser;
	params.modelId = "model_userEditInfo";
	params.modelData = {id:id,name:name,username:username,bj:bj,year:year,userType:userType};
	params.title = "编辑账号【"+name+"("+username+")】";
	showDialog380(params);
	
	$("#dialog_380_display").find("div[data-node='password']").hide();
}
function doDelUser(id){
	var name = $.trim($("#user_name_"+id).html());
	var username = $.trim($("#user_username_"+id).html());
	if(!confirm("您确定要删除账号【"+name+"("+username+")】吗？")){
		return ;
	}
	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.url = user_delUrl[userType]+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "post";
	params.error = "删除失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			toPage(0);
		}else{
			if(json.errorMsg!=null){
				alert("删除失败："+json.errorMsg);
			}else{
				alert("删除失败！");
			}
		}
	};
	ajaxJSON(params);
}
function doForbidden(id){//禁用
	var name = $.trim($("#user_name_"+id).html());
	var username = $.trim($("#user_username_"+id).html());
	if(!confirm("您确定要禁用账号【"+name+"("+username+")】吗？")){
		return ;
	}
	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.url = user_forbiddenUrl[userType]+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "post";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			toPage(0);
		}else{
			if(json.errorMsg!=null){
				alert("操作失败："+json.errorMsg);
			}else{
				alert("操作失败！");
			}
		}
	};
	ajaxJSON(params);
}
function doEnabled(id){//启用
	var name = $.trim($("#user_name_"+id).html());
	var username = $.trim($("#user_username_"+id).html());
	if(!confirm("您确定要启用账号【"+name+"("+username+")】吗？")){
		return ;
	}
	var userType = $("#user_ipt_userType").val();
	var params = {};
	params.url = user_enabledUrl[userType]+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "post";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			toPage(0);
		}else{
			if(json.errorMsg!=null){
				alert("操作失败："+json.errorMsg);
			}else{
				alert("操作失败！");
			}
		}
	};
	ajaxJSON(params);
}
function isTeacher(userType) {
    return userType=='ADMIN' || userType=='RECRUIT_TEACHER'
        || userType=='HEALTH_TEACHER' || userType=='GRADUATE_TEACHER'
}