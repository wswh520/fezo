<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_passwordDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>系统管理</li>
			<li class="last">密码修改</li>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<!-- 动态头部信息 -->
		<div class="container-1" style="padding:0;">
			<div class="account_fold SW_fun_bg" style="margin-top:0;">
				<ul class="account_title clearfix">
					<li class="title_name W_fb"><i class="icon icon-cog-1"></i>修改密码</li>
				</ul>
			</div>
			<div class="unfold_content clearfix">
				<form id="form_pwdModify" class="unfold_left">
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">当前密码：</label>
						<input id="ipt_oldPwd" name="oldPwd" style="width: 174px;" type="password" placeholder="请输入您的原有密码" class="inp" />
						<div id="div_oldPwd_hint" class="u-tips" style="display:none;">
							<em></em><span id="div_oldPwd_hint_text"></span>
						</div>
					</div>
					<div class="item">
						<label class="sele-label label-nick">新密码：</label>
						<input id="ipt_newPwd" name="newPwd" style="width: 174px;" type="password" placeholder="请输入您的新密码" class="inp" />
						<div id="div_newPwd_hint" class="u-tips u-tips-default">
							<em></em><span id="div_newPwd_hint_text"></span>
						</div>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label><span>密码由6——16位字符（字母、数字、符号）组成，区分大小写，且必须至少包含两种字符（如：字母和数字）</span>
					</div>
					<div class="item">
						<label class="sele-label label-nick">确认新密码：</label>
						<input id="ipt_newPwd_c" style="width: 174px;" type="password" placeholder="请再次输入新密码" class="inp" />
						<div id="div_newPwd_c_hint" class="u-tips u-tips-default">
							<em></em><span id="div_newPwd_c_hint_text"></span>
						</div>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label>
						<input id="ipt_submit" type="button" value="修改密码" class="Btn" />
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</script>