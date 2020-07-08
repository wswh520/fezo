var password_checkUrl = ctx+"/password/check/ajax";
var password_modifyUrl = ctx+"/password/modify/ajax";
function doLoadPwd(){
	useModel("model_passwordDisplay","div_display",null);
	pwdInit();
}
function pwdInit(){
	var options={};
	options["callBack"]=doModifyPassword;
	options["o"]={//旧密码框
			id:"ipt_oldPwd",
			url:password_checkUrl,
			paramName:"oldPwd",
			hint_id:"div_oldPwd_hint",//提示DIV的ID
			hint_css_ok:"",
			hint_css_error:"u-tips-err",
			hint_css_empty:"u-tips-err",
			hint_text_id:"div_oldPwd_hint_text",//装载提示信息的元素
			hint_text_ok:"初始正确",//输入内容正常
			hint_text_error:"初始错误",//输入内容不正常
			hint_text_empty:"请输入您的初始密码"//输入内容为空
		};
	options["n"]={//新密码框
			id:"ipt_newPwd",
			hint_id:"div_newPwd_hint",//提示DIV的ID
			hint_css_ok:"",
			hint_css_error:"u-tips-err",
			hint_css_default:"u-tips-default",
			hint_css_empty:"u-tips-err",
			hint_text_id:"div_newPwd_hint_text",//装载提示信息的元素
			hint_text_ok:"密码合法",//输入内容正常
			hint_text_error:"密码至少包含两种字符，比如同时包含数字与字母。",//输入内容不正常
			hint_text_default:"请输入新密码",
			hint_text_empty:"请输入新密码",//输入内容为空
			hint_text_long:"密码过长，应为6——16个字符",//输入内容过长
			hint_text_short:"密码过短，应为6——16个字符",//输入内容过短
			length_floor:6,//最小长度
			length_top:16//最大长度
		};
	options["c"]={//新密码确认框
			id:"ipt_newPwd_c",
			hint_id:"div_newPwd_c_hint",//提示DIV的ID
			hint_css_ok:"",
			hint_css_error:"u-tips-err",
			hint_css_default:"u-tips-default",
			hint_css_empty:"u-tips-err",
			hint_text_id:"div_newPwd_c_hint_text",//装载提示信息的元素
			hint_text_ok:"",//输入内容正常
			hint_text_error:"两次输入的密码不一致",//输入内容不正常
			hint_text_default:"请再次输入新密码",
			hint_text_empty:"请输入新密码"//输入内容为空
		};
	$("#ipt_submit").passwordCheck(options);
}
function doModifyPassword(){
	var postData = $("#form_pwdModify").formSerialize();
	$.ajax({
		url:password_modifyUrl,
		type:"post",
		data:postData,
		dataType:"json",
		success:function(json){
			if(json.retCode==CODE_SUCCESS){
				alert("密码修改成功！");
				location.href=location.href;
			}else{
				alert("密码修改失败！");
			}
		}
	});
}