var loginUrl = ctx+"/login_in";
var singInUrl = ctx+"/signIn/save";

$(document).ready(function(){
	//处理低版本IE浏览器下placeholder属性无效的问题
	$("#form_login").find("input").placeholder();
	$("#form_signIn").find("input").placeholder();
	formIptClass();
	$("#ipt_password").bind("keydown",function(evt){
		if(evt.keyCode == 13){
			doLogin();
		}
    });
	var errorMsg = getQueryString("errorMsg");
    if(errorMsg != null && errorMsg != ""){
        $("#p_loginFailed").html(errorMsg);
        $("#p_loginFailed").show();
    }else {
        $("#p_loginFailed").hide();
    }
});
/**
 * 输入框样式
 * @return
 */
function formIptClass(){
	$("#ipt_name").bind("focus",function(e){
		$(this).parent().addClass("hover");
	});
	$("#ipt_name").bind("blur",function(e){
		$(this).parent().removeClass("hover");
	});
	$("#ipt_username").bind("focus",function(e){
		$(this).parent().addClass("hover");
	});
	$("#ipt_username").bind("blur",function(e){
		$(this).parent().removeClass("hover");
	});
	$("#ipt_password").bind("focus",function(e){
		$(this).parent().addClass("hover");
	});
	$("#ipt_password").bind("blur",function(e){
		$(this).parent().removeClass("hover");
	});
	$("#ipt_password_confirm").bind("focus",function(e){
		$(this).parent().addClass("hover");
	});
	$("#ipt_password_confirm").bind("blur",function(e){
		$(this).parent().removeClass("hover");
	});
}

function doLogin(){
	var username = $("#ipt_username").val();
	var password = $("#ipt_password").val();
	if(username==""||password==""){
		alert("请输入用户名与密码！");
		return ;
	}
	var params = {};
	params.url = loginUrl+"?_t="+new Date().getTime();
	params.postData = $("#form_login").serialize();
	params.postType = "post";
	params.error = "登陆失败";
	params.mustCallBack = true;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			location.href = ctx+"/";
		}else if(json.retCode==CODE_USER_PASS_ERROR){
			$("#p_loginFailed").html("用户名或密码错误！");
			$("#p_loginFailed").show();
		}else if(json.retCode==CODE_USER_ACCOUNT_ERROR){
			$("#p_loginFailed").html("账号异常，请联系账号管理员！");
			$("#p_loginFailed").show();
		}else{
			if(json.errorMsg==null){
				$("#p_loginFailed").html("登陆失败！");
				$("#p_loginFailed").show();
			}else{
				$("#p_loginFailed").html(json.errorMsg);
				$("#p_loginFailed").show();
			}
		}
		$("#ipt_waiting").hide();
		$("#ipt_submit").show();
	};
	params.disableEles = null;
	$("#p_loginFailed").hide();
	$("#ipt_submit").hide();
	$("#ipt_waiting").show();
	ajaxJSON(params);
}
function doSignIn(){///^((\+\d{1,})?((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1})|(14[0-9]{1})|(17[0-9]{1}))+\d{8})$/
	var regex_mobile = /^1\d{10}$/;//手机号
	var name = $.trim($("#ipt_name").val());
	var username = $.trim($("#ipt_username").val());
	var password = $.trim($("#ipt_password").val());
	var password_confirm = $.trim($("#ipt_password_confirm").val());
	if(name==""){
		alert("请输入姓名！");
		return ;
	}
	if(username==""){
		//alert("请输入手机号！");
		alert("请输入身份证号！");
		return ;
	}/*else if(!regex_mobile.test(username)){
		alert("手机号输入有误！");
		return ;
	}*/
	var checkCardNoRes = IdentifyCodeValid_2(username, false);
	if (!checkCardNoRes.pass) {
		alert(checkCardNoRes.msg);
		return ;
	}
	var year = getCurrentYear();//（2010年9月1日——2011年8月31日出生）
	var birthday = parseInt(username.substring(6,14));
	if(birthday<((year-7)*10000+901)//大于6岁
			||birthday>((year-6)*10000+831)){//小于6岁
		alert("只有【"+(year-7)+"年9月1日至"+(year-6)+"年8月31日】出生的小孩才能注册");
		return ;
	}
	if(password==""){
		alert("请输入密码！");
		return ;
	}
	if(password_confirm==""){
		alert("请再次输入密码！");
		return ;
	}
	if(password!=password_confirm){
		alert("两次输入的密码不一致！");
		return ;
	}
	var params = {};
	params.url = singInUrl+"?_t="+new Date().getTime();
	params.postData = $("#form_signIn").serialize();
	params.postType = "post";
	params.error = "注册失败";
	params.mustCallBack = true;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("注册成功");
			location.href = ctx+"/login";
		}else if(json.retCode==CODE_USER_EXIST){
			alert("注册失败：身份证号已注册！");
		}else{
			if(json.errorMsg==null){
				alert("注册失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	params.disableEles = ["ipt_signIn"];
	ajaxJSON(params);
}
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]); return null;
}