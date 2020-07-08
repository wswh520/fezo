<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_timeConfigDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>系统设置</li>
			<li class="last">$J{title}</li>
		</ul>
	</div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<div class="container-1" style="padding:0;">
			<div class="account_fold SW_fun_bg" style="margin-top:0;">
				<ul class="account_title clearfix">
					<li style="width: 300px;" class="title_name W_fb"><i class="icon icon-cog-1"></i>$J{title}</li>
				</ul>
			</div>
			<div id="div_display_list" class="unfold_content clearfix"></div>
		</div>
	</div>
</div>
</script>
<script id="model_timeConfig" type="text/template">
	<input id="ipt_type" type="hidden" value="$J{typeStr}" />
	<div class="unfold_left">
		<ul class="account_title clearfix"><li class="title_name W_fb">&nbsp;</li></ul>
		<div class="item">
			<label class="sele-label label-nick">开始时间：</label>
			<input id="ipt_startTimeStr" disabled="disabled" value="$J{startTimeStr}" style="cursor:pointer;width:146px;" readonly="readonly" type="text" class="inp Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy年MM月dd日',alwaysUseStartDate:true})" maxlength="10" />
			<input id="ipt_startTimeStr_copy" disabled="disabled" type="hidden" value="$J{startTimeStr}" class="inp" />
		</div>
		<div class="item">
			<label class="sele-label label-nick">结束时间：</label>
			<input id="ipt_endTimeStr" disabled="disabled" value="$J{endTimeStr}" style="cursor:pointer;width:146px;" readonly="readonly" type="text" class="inp Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy年MM月dd日',alwaysUseStartDate:true})" maxlength="10" />
			<input id="ipt_endTimeStr_copy" disabled="disabled" type="hidden" value="$J{endTimeStr}" class="inp" />
		</div>
		<div class="item">
			<label class="sele-label label-nick">&nbsp;</label>
			<input id="btn_edit" onclick="javascript:showModifyTimeConfig();" type="button" value="修改" class="Btn" />
			<input id="btn_submit" onclick="javascript:doModifyTimeConfig();" type="button" value="保存" class="Btn" style="display:none;" />
			<input id="btn_cancel" onclick="javascript:cancelModifyTimeConfig();" type="button" value="取消" class="Btn-1" style="display:none;" />
		</div>
		<div class="item">
			<label class="sele-label label-nick">说明：</label>
			<span style="line-height: 27px;">如果要修改该时段，请先点击“修改”按钮，然后选择正确的开始时间与结束时间，最后点击“保存”使得本次修改生效。</span>
		</div>
	</div>
</script>