//100通用
var CODE_SUCCESS			= 100000;//成功
var CODE_NETWORK_ERROR		= 100001;//网络异常
var CODE_SERVER_INNER_ERROR	= 100002;//失败,内部错误
var CODE_NOT_LOGIN			= 100003;//未登陆
var CODE_UNAUTHORIZED		= 100004;//权限不够
var CODE_PARAMETER_NULL     = 100005;//数据为空
//200用户相关
var CODE_USER_EXIST			= 200001;//用户已存在
var CODE_USER_NOT_EXIST		= 200002;//用户名不存在
var CODE_USER_PASS_ERROR	= 200003;//用户名或密码错误
var CODE_USER_CAPTCHA_ERROR	= 200004;//验证码错误
var CODE_USER_ACCOUNT_ERROR	= 200005;//账号异常
var CODE_USER_NOT_SIGNLN = 200006;//账号未登记
//300学校相关
var CODE_SCHOOL_EXIST		= 300001;//学校已存在
//400图书类型相关
var CODE_BOOKTYPE_EXIST		= 400001;//类别名已存在
//500图书相关
var CODE_BOOK_EXIST			= 500001;//图书已存在
var CODE_BOOK_NOT_EXIST		= 500002;//图书不存在

$(document).ready(function(){
	$(document).delegate("[class='Btn btn-group']","click",function(e){
		e.preventDefault();
		e.stopPropagation();
		if($(this).next().find("ul[class='dropdown-menu']").is(":visible")==true){
			$(this).next().find("ul[class='dropdown-menu']").hide();
			return ;
		}
		$("[class='btn_group']").find("ul[class='dropdown-menu']").hide();
		$(this).next().find("ul[class='dropdown-menu']").show();
	});
	$(document).bind("click",function(){
		$("[class='btn_group']").find("ul[class='dropdown-menu']").hide();
	});
});
var sync_div_display = 0;
function ajaxJSON(params){
	var _sync = params.mustCallBack?0:++sync_div_display;
	relateEleControl(params.disableEles,true);
	var checkSuccess = checkTicket();
	if (!checkSuccess) return;
	$.ajax({
		url: params.url,
		data: params.postData,
		type: params.postType,
		//async: true,
		dataType: "json",
		success: function(json){
			relateEleControl(params.disableEles,false);
			if(!params.mustCallBack&&_sync!=sync_div_display){
				return ;
			}
			if(json.retCode==CODE_NOT_LOGIN){
				alert("请重新登陆！");
				location.href = ctx+"/";
			}else if(json.retCode==CODE_UNAUTHORIZED){
				alert("权限不足！");
			}else{
				params.callBack(json);
			}
		},
		error:function(){
			relateEleControl(params.disableEles,false);
			if(!params.mustCallBack&&_sync!=sync_div_display){
				return ;
			}else if(params.error!=null){
				alert(params.error);
			}
		}
	});
}
function relateEleControl(eles,val){
	if(eles==null||eles.length==0){
		return ;
	}
	for(var i=0;i<eles.length;i++){
		$("#"+eles[i]).attr("disabled",val);
	}
}
function useModel(modelId,contentId,json){
	var _model = $("#"+modelId).html();
	var _html = juicer(_model, json);
	$("#"+contentId).html(_html);
}
function createHtmlUseModel(modelId,json){
	var _model = $("#"+modelId).html();
	var _html = juicer(_model, json);
	return _html;
}
String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};
function getCurrentYear(){
	var Today = new Date();
	return Today.getFullYear();
}

function checkTicket() {
	debugger;
    var isTeacher = $("#ipt_isTeacher").val();
    if (isTeacher==undefined || isTeacher === "false" || isTeacher === "true"){
        return true;
    }
    var isSuccess = false;
    $.ajax({
        type: "get",
        url: ctx + "/cas/check",
        async: false,
        dataType:"json",
        header: {"Access-Control-Allow-Origin":"*","Access-Control-Allow-Methods":"GET,POST"},
        success: function(json, textStatus){
            if (json) {
                if (json.retCode === CODE_SUCCESS) {
                	isSuccess = true;
                }
                if (json.errorMsg){
                    alert(json.errorMsg);
                    isSuccess = false;
                }
            }else {
                location.href = ctx+"/login";
                isSuccess = false;
            }
        },
        error: function(XMLHttpRequest, textStatus, errorThrown){//请求出错处理
            location.href = ctx+"/login";
            isSuccess = false;
        }
    });
    return isSuccess;
}