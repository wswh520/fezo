var config_timeGetUrl = ctx+"/timeConfig/get/ajax";
var config_timeSetUrl = ctx+"/timeConfig/set/ajax";

function doLoadTimeConfig_serve(){
	doShowTimeConfig("SERVE","服务时段(家长可登陆)");
}
function doLoadTimeConfig_signIn(){
	doShowTimeConfig("SIGN_IN","注册时段(家长可注册)");
}
function doLoadTimeConfig_applyInput(){
	doShowTimeConfig("APPLY_INPUT","填表时段(家长第一次填写学生报名表)");
}
function doLoadTimeConfig_applyModify(){
	doShowTimeConfig("APPLY_MODIFY","改表时段(家长第二次填写学生报名表)");
}
function doLoadTimeConfig_infoInput(){
	doShowTimeConfig("INFO_INPUT","信息录入时段(家长填写学籍表)");
}
function doLoadTimeConfig_graServe(){
	doShowTimeConfig("GRA_SERVE","服务时段(家长可登陆)");
}
function doLoadTimeConfig_graInfoInput(){
	doShowTimeConfig("GRA_INFO_INPUT","信息录入时段(家长录入信息)");
}

function doShowTimeConfig(type,title){
	useModel("model_timeConfigDisplay","div_display",{title:title});
	$("#ipt_pageNo").unbind("click");
	$("#ipt_pageNo").bind("click",{type:type},doLoadTimeConfig);
	toPage(1);
}
function doLoadTimeConfig(evt){
	var params = {};
	params.url = config_timeGetUrl+"?_t="+new Date().getTime();
	params.postData = {type:evt.data.type};
	params.postType = "post";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_timeConfig","div_display_list",json.data);
		}else{
			alert("加载失败！");
		}
	};
	ajaxJSON(params);
}
function showModifyTimeConfig(){
	$("#ipt_startTimeStr").attr("disabled",false);
	$("#ipt_endTimeStr").attr("disabled",false);
	
	$("#btn_edit").hide();
	$("#btn_submit").show();
	$("#btn_cancel").show();
}
function doModifyTimeConfig(){
	var type = $("#ipt_type").val();
	var startTimeStr = $("#ipt_startTimeStr").val();
	var endTimeStr = $("#ipt_endTimeStr").val();
	if(startTimeStr == ""){
		alert("请选择开始时间!");
		return;
	}
	if(endTimeStr == ""){
		alert("请选择结束时间!");
		return;
	}
	if((Date.parse(startTimeStr.replace(/(年|月|日)/g,"/")) - Date.parse(endTimeStr.replace(/(年|月|日)/g,"/")) > 0)){
		alert("提示：输入开始时间不能大于或等于结束时间!");
		return;
	}
	var params = {};
	params.url = config_timeSetUrl+"?_t="+new Date().getTime();
	params.postData = {type:type,start:startTimeStr,end:endTimeStr};
	params.postType = "post";
	params.error = "保存失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("保存成功！");
			$("#ipt_startTimeStr_copy").val(startTimeStr);
			$("#ipt_endTimeStr_copy").val(endTimeStr);
			cancelModifyTimeConfig();
		}else{
			alert("保存失败！");
		}
	};
	params.disableEles = ["btn_submit","btn_cancel"];
	ajaxJSON(params);
}
function cancelModifyTimeConfig(){
	$("#ipt_startTimeStr").attr("disabled",true);
	$("#ipt_endTimeStr").attr("disabled",true);
	$("#ipt_startTimeStr").val($("#ipt_startTimeStr_copy").val());
	$("#ipt_endTimeStr").val($("#ipt_endTimeStr_copy").val());

	$("#btn_cancel").hide();
	$("#btn_submit").hide();
	$("#btn_edit").show();
}