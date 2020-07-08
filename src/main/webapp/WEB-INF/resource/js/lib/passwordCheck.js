
(function($){
	$.fn.passwordCheck = function(options) {
		var defaults={};
		defaults["callBack"]=function(){alert(1);};
		defaults["o"]={//旧密码框
				id:"",
				url:"",
				paramName:"password",
				hint_id:"",//提示DIV的ID
				hint_css_ok:"",
				hint_css_error:"",
				hint_css_empty:"",
				hint_text_id:"",//装载提示信息的元素
				hint_text_ok:"",//输入内容正常
				hint_text_error:"",//输入内容不正常
				hint_text_empty:""//输入内容为空
			};
		defaults["n"]={//新密码框
				id:"",
				hint_id:"",//提示DIV的ID
				hint_css_ok:"",
				hint_css_error:"",
				hint_css_default:"",
				hint_css_empty:"",
				hint_text_id:"",//装载提示信息的元素
				hint_text_ok:"",//输入内容正常
				hint_text_error:"",//输入内容不正常
				hint_text_default:"",
				hint_text_empty:"",//输入内容为空
				hint_text_long:"",//输入内容过长
				hint_text_short:"",//输入内容过短
				length_floor:6,//最小长度
				length_top:16//最大长度
			};
		defaults["c"]={//新密码确认框
				id:"",
				hint_id:"",//提示DIV的ID
				hint_css_ok:"",
				hint_css_error:"",
				hint_css_default:"",
				hint_css_empty:"",
				hint_text_id:"",//装载提示信息的元素
				hint_text_ok:"",//输入内容正常
				hint_text_error:"",//输入内容不正常
				hint_text_default:"",
				hint_text_empty:""//输入内容为空
			};
		var opts = $.extend(defaults,options);
		$("#"+opts.o.id).bind("blur",checkOldPassword);
		$("#"+opts.o.id).bind("focus",function(){
			$("#"+opts.o.hint_id).hide();
		});
		$("#"+opts.n.id).bind("blur",checkNewPassword);
		$("#"+opts.n.id).bind("focus",function(){
			$("#"+opts.n.hint_text_id).html(opts.n.hint_text_default);
			$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
			$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
			$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
			$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
			$("#"+opts.n.hint_id).addClass(opts.n.hint_css_default);
		});
		$("#"+opts.c.id).bind("blur",checkPasswordConfirm);
		$("#"+opts.c.id).bind("focus",function(){
			var newPassword = $.trim($("#"+opts.n.id).val());
			if(newPassword==""){
				$("#"+opts.c.hint_id).hide();
				return false;
			}
			$("#"+opts.c.hint_id).show();
			$("#"+opts.c.hint_text_id).html(opts.c.hint_text_default);
			$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_ok);
			$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_error);
			$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_default);
			$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_empty);
			$("#"+opts.c.hint_id).addClass(opts.c.hint_css_default);
		});
		
		$(this).bind("click",submit);
		function submit(){
			if(checkOldPassword()&&checkNewPassword()&&checkPasswordConfirm()){
				opts.callBack();
			}
		}
		
		function checkOldPassword(){
			var oldPassword = $.trim($("#"+opts.o.id).val());
			if(oldPassword==""){
				$("#"+opts.o.hint_text_id).html(opts.o.hint_text_empty);
				$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_ok);
				$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_empty);
				$("#"+opts.o.hint_id).addClass(opts.o.hint_css_empty);
				$("#"+opts.o.hint_id).show();
				return false;
			}
			$.ajax({
				url:opts.o.url,
				type:"post",
				data:opts.o.paramName+"="+oldPassword,
				dataType:"json",
				success:function(json){
					if(json.retCode==CODE_SUCCESS){
						$("#"+opts.o.hint_text_id).html(opts.o.hint_text_ok);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_ok);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_error);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_empty);
						$("#"+opts.o.hint_id).addClass(opts.o.hint_css_ok);
						$("#"+opts.o.hint_id).show();
						return true;
					}else{
						$("#"+opts.o.hint_text_id).html(opts.o.hint_text_error);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_ok);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_error);
						$("#"+opts.o.hint_id).removeClass(opts.o.hint_css_empty);
						$("#"+opts.o.hint_id).addClass(opts.o.hint_css_error);
						$("#"+opts.o.hint_id).show();
						return false;
					}
				}
			});
			return true;
		}
		
		function checkNewPassword(){
			var newPassword = $.trim($("#"+opts.n.id).val());
			if(newPassword.length==0){
				$("#"+opts.n.hint_text_id).html(opts.n.hint_text_empty);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
				$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
				$("#"+opts.n.hint_id).addClass(opts.n.hint_css_empty);
				return false;
			}else if(checkStrong(newPassword)<2){
				$("#"+opts.n.hint_text_id).html(opts.n.hint_text_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
				$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
				$("#"+opts.n.hint_id).addClass(opts.n.hint_css_error);
				return false;
			}else if(newPassword.length<opts.n.length_floor){
				$("#"+opts.n.hint_text_id).html(opts.n.hint_text_short);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
				$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
				$("#"+opts.n.hint_id).addClass(opts.n.hint_css_error);
				return false;
			}else if(newPassword.length>opts.n.length_top){
				$("#"+opts.n.hint_text_id).html(opts.n.hint_text_long);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
				$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
				$("#"+opts.n.hint_id).addClass(opts.n.hint_css_error);
				return false;
			}else{
				$("#"+opts.n.hint_text_id).html(opts.n.hint_text_ok);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_ok);
				$("#"+opts.n.hint_id).removeClass(opts.o.hint_css_error);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_default);
				$("#"+opts.n.hint_id).removeClass(opts.n.hint_css_empty);
				$("#"+opts.n.hint_id).addClass(opts.n.hint_css_ok);
				return true;
			}
		}
		
		function checkPasswordConfirm(){
			var newPassword = $.trim($("#"+opts.n.id).val());
			var passwordConfirm = $.trim($("#"+opts.c.id).val());
			if(passwordConfirm==""){
				if(newPassword==""){
					$("#"+opts.c.hint_id).hide();
					return false;
				}
				$("#"+opts.c.hint_text_id).html(opts.c.hint_text_empty);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_ok);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_error);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_default);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_empty);
				$("#"+opts.c.hint_id).addClass(opts.c.hint_css_empty);
				$("#"+opts.c.hint_id).show();
				return false;
			}else if(newPassword!=passwordConfirm){
				$("#"+opts.c.hint_text_id).html(opts.c.hint_text_error);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_ok);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_error);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_default);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_empty);
				$("#"+opts.c.hint_id).addClass(opts.c.hint_css_error);
				$("#"+opts.c.hint_id).show();
				return false;
			}else{
				$("#"+opts.c.hint_text_id).html(opts.c.hint_text_ok);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_ok);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_error);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_default);
				$("#"+opts.c.hint_id).removeClass(opts.c.hint_css_empty);
				$("#"+opts.c.hint_id).addClass(opts.c.hint_css_ok);
				$("#"+opts.c.hint_id).show();
				return true;
			}
		}

		/**
		 * 计算出当前密码当中一共有多少种符号类型
		 */
		function countType(num){
			modes=0; 
			for (i=0;i<4;i++){ 
				if (num & 1) modes++; 
				num>>>=1;
			} 
			return modes; 
		} 

		function CharMode(iN){ 
			if (iN>=48 && iN <=57) //数字 
			return 1; 
			if (iN>=65 && iN <=90) //大写字母 
			return 2; 
			if (iN>=97 && iN <=122) //小写 
			return 4; 
			else 
			return 8; //特殊字符 
		} 

		/**
		 * 返回密码的强度级别 
		 */
		function checkStrong(sPW){
			Modes=0; 
			for (i=0;i<sPW.length;i++){ 
				//测试每一个字符的类别并统计一共有多少种模式. 
				Modes|=CharMode(sPW.charCodeAt(i));
			} 
			return countType(Modes);
		}
	};
})(jQuery);