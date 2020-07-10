<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.fz.common.security.CurrentUser"%>
<%@page import="com.fz.common.security.ThreadLocalUtils"%>
<%@ page import="com.fz.enroll.enum0.UserType" %>
<%
	CurrentUser user = ThreadLocalUtils.getCurrentUser();
	UserType utype = ThreadLocalUtils.getCurrentUser().getType();
%>

<script id="model_stuApplyListDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">入学登记表</li>
		</ul>
	</div>
	<div id="div_search" class="right">
		<input style="font-size: 12px;width: 200px;" class="searchinp left" type="text" placeholder="姓名/身份证号/家长手机号码" id="ipt_keyword"/>
		<button class="Btn" type="button" onclick="javascript:toPage(1);"><i class="icon icon-search"></i>搜索</button>
		<div style="display:inline-block;">
			<div class="Btn btn-group" style="background:none; padding:0;">
				<button class="btn-small">状态批处理</button>
				<button class="dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
			</div>
			<div class="btn_group" style=" position:relative;">
			<ul class="dropdown-menu" style="display: none;width:170px;">
					<li onclick="javascript:submitAll();" style="cursor:pointer;"><a href="javascript:void(0);">强制提交所有基本信息表</a></li>
					<li onclick="javascript:resetAllInfo();" style="cursor:pointer;"><a href="javascript:void(0);">重置学籍信息表为编辑状态</a></li>
					<li onclick="javascript:submitAllInfo();" style="cursor:pointer;"><a href="javascript:void(0);">强制提交所有学籍信息表</a></li>
			</ul>
			</div>
		</div>
		<div style="display:inline-block;">
			<div class="Btn btn-group" style="background:none; padding:0;">
				<button class="btn-small">导出</button>
				<button class="dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
			</div>
			<div class="btn_group" style=" position:relative;">
			<ul class="dropdown-menu" style="display: none;">
					<li onclick="javascript:doExport(student_stuApplyExportUrl_no);" style="cursor:pointer;"><a href="javascript:void(0);">基本信息</a></li>
					<li onclick="javascript:doExport(student_stuApplyExportUrl_class);" style="cursor:pointer;"><a href="javascript:void(0);">基本信息(班级)</a></li>
					<li onclick="javascript:doExport(student_stuApplyExportUrl_number);" style="cursor:pointer;"><a href="javascript:void(0);">基本信息(学号)</a></li>
					<li onclick="javascript:doExport(student_stuInfoExportUrl_no);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息</a></li>
					<li onclick="javascript:doExport(student_stuInfoExportUrl_class);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息(班级)</a></li>
					<li onclick="javascript:doExport(student_stuInfoExportUrl_number);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息(学号)</a></li>
					<li onclick="javascript:doExport(student_stuVaccineExportUrl_no);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息</a></li>
					<li onclick="javascript:doExport(student_stuVaccineExportUrl_class);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息(班级)</a></li>
					<li onclick="javascript:doExport(student_stuVaccineExportUrl_number);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息(学号)</a></li>
			</ul>
			</div>
		</div>
		<!-- 招生老师才有权限-->
		<% if (utype==UserType.RECRUIT_TEACHER){%>
		<div style="display:inline-block;">
			<div class="Btn btn-group" style="background:none; padding:0;">
				<button id="btn_print_4" onclick="javascript:stuApplyListSendSms('$J{id}');" class="Btn" type="button">${userType}面试通过通知</button>
			</div>
		</div>
		<% } %>
	</div>
	<div id="div_back" class="left" style="display:none;"></div>
	<div id="div_sel" class="left">
		<select id="sel_year" onchange="javascript:toPage(1);">
			<option value="$J{year}">$J{year}年</option>
		{@each  i in range(2015, year)}
			<option value="$J{i}">$J{i}年</option>
		{@/each}
		</select>
		<select id="sel_ageScope" onchange="javascript:toPage(1);">
			<option value="">年龄</option>
			<option value="LESS_THAN_6">未满6周岁</option>
			<option value="GREATER_THAN_6">年满6周岁</option>
		</select>
		<select id="sel_type" onchange="javascript:toPage(1);">
			<option value="">报名类别</option>
			<option value="外教班">外教班</option>
			<option value="普通班">普通班</option>
		</select>
		<select id="sel_sex" onchange="javascript:toPage(1);">
			<option value="">性别</option>
			<option value="TRUE">男</option>
			<option value="FALSE">女</option>
		</select>
		<select id="sel_status" onchange="javascript:toPage(1);">
			<option value="">表单状态</option>
			<option value="SUBMIT_NONE">未提交</option>
			<option value="SUBMIT_ONCE">已提交1次</option>
			<option value="SUBMIT_TWICE">待审核</option>
			<option value="REVIEW_PASS">拟录取</option>
			<option value="REVIEW_REFUSE">不予录取</option>
			<option value="REVIEW_WAITING">待录取</option>
		</select>
		<select id="sel_infoStatus" onchange="javascript:toPage(1);">
			<option value="">学籍表状态</option>
			<option value="TABLE_EMPTY">未填表</option>
			<option value="SUBMIT_NONE">未提交</option>
			<option value="SUBMIT_ONCE">已提交</option>
		</select>
		<select id="sel_vaccineStatus" onchange="javascript:toPage(1);">
			<option value="">预防表状态</option>
			<option value="TABLE_EMPTY">未填表</option>
			<option value="SUBMIT_NONE">未提交</option>
			<option value="SUBMIT_ONCE">已提交</option>
		</select>
		<select id="sel_school" onchange="javascript:toPage(1);">
			<% if (user.getUid()==4699){%>					<%--冷丹 只能看南湖校区的报名--%>
				<option value="NANHU">校区</option>
				<option value="NANHU">南湖校区</option>
			<% }else if (user.getUid()==4684) { %>			<%--刘峥 只能看桂子山校区的报名--%>
				<option value="GUIZISHAN">校区</option>
				<option value="GUIZISHAN">桂子山校区</option>
			<% }else { %>
				<option value="">校区</option>
				<option value="NANHU">南湖校区</option>
				<option value="GUIZISHAN">桂子山校区</option>
			<% } %>
		</select>
	</div>
	<div class="clear"></div>
	<div id="div_display_list"></div>
</div>
</script>
<script id="model_stuApplyList" type="text/template">
	<form id="form_stuApplyList" class="bs-docs-example">
		<table class="table table-bordered table-striped">
			<thead><tr id="tr_batchReview" style="display: none;">
				<td colspan="11">
					<span id="span_selCount"></span>
					<input onclick="javascript:doBatchReview('REVIEW_PASS','拟录取');" type="button" value="拟录取" class="Btn" />
					<input onclick="javascript:doBatchReview('REVIEW_REFUSE','不予录取');" type="button" value="不予录取" class="Btn" />
					<input onclick="javascript:doBatchReview('REVIEW_WAITING','待录取');" type="button" value="待录取" class="Btn" />
				</td>
			</tr>
			<tr>
				<th style="width:3%;text-align:center;"><input onclick="javascript:checkAll(this,'form_stuApplyList','ids');" type="checkbox" title="全选/取消选择"></th>
				<th style="width:4%;text-align:center;">序号</th>
				<th width="6%">报名号</th>
				<th width="7%">姓名</th>
				<th width="4%">性别</th>
				<th width="11%">报名类别</th>
				<th width="8%">状态</th>
				<th width="15%">身份证号</th>
				<th width="11%">出生日期</th>
				<th width="9%">家长手机号</th>
				<th width="6%">学籍表</th>
				<th width="6%">预防表</th>
				<th width="10%">&nbsp;</th>
			</tr></thead>
			<tbody>
	{@if totalRecords==0}
			<tr>
				<td colspan="13">暂无记录！</td>
			</tr>
	{@else}
		{@each datas as ele,index}
			<tr>
				<td style="text-align:center;">
					{@if currentYear!=ele.year}$J{ele.year}
					{@else if ele.statusStr=="SUBMIT_TWICE"
						||ele.statusStr=="REVIEW_PASS"
						||ele.statusStr=="REVIEW_REFUSE"
						||ele.statusStr=="REVIEW_WAITING"}
						<input onclick="javascript:showBatchReviewOp();" name="ids" type="checkbox" value="$J{ele.id}" />
					{@/if}
				</td>
				<td style="text-align:center;">$J{index-1+2+(currentPage-1)*15}</td>
				<td>$J{ele.noStr}</td>
				<td id="ele_name_$J{ele.id}">$J{ele.name}</td>
				<td>{@if ele.sexStr=="TRUE"}男{@else}女{@/if}</td>
				<td>
					$J{ele.other54}
<%--					{@if ele.typeStr=="TYPEA"}A华师第二代生源--%>
<%--					{@else if ele.typeStr=="TYPEB"}B华师第三代生源--%>
<%--					{@else if ele.typeStr=="TYPEC"}C社会对口生源--%>
<%--					{@/if}--%>
				</td>
				<td>
					{@if ele.statusStr=="SUBMIT_NONE"}未提交
					{@else if ele.statusStr=="SUBMIT_ONCE"}{@if locked}审核中{@else}未再次提交{@/if}
					{@else if ele.statusStr=="SUBMIT_TWICE"}审核中
					{@else if ele.statusStr=="REVIEW_PASS"}拟录取
					{@else if ele.statusStr=="REVIEW_REFUSE"}不予录取
					{@else if ele.statusStr=="REVIEW_WAITING"}待录取
					{@/if}
				</td>
				<td id="ele_cardNo_$J{ele.id}">$J{ele.cardNo}</td>
				<td>$J{ele.dateOfBirthStr}</td>
				<!--<td>$J{ele.mobile}</td>-->
				<td>$J{ele.other16}</td>
				<td>
					{@if ele.infoStatusStr==null}未填写
					{@else if ele.infoStatusStr=="SUBMIT_NONE"}未提交
					{@else if ele.infoStatusStr=="SUBMIT_ONCE"}已提交
					{@/if}
				</td>
				<td>
					{@if ele.vaccineStatusStr==null}未填写
					{@else if ele.vaccineStatusStr=="SUBMIT_NONE"}未提交
					{@else if ele.vaccineStatusStr=="SUBMIT_ONCE"}已提交
					{@/if}
				</td>
				<td>
					<span id="ele_statusStr_$J{ele.id}" style="display:none;">$J{ele.statusStr}</span>
					<span id="ele_reviewer_$J{ele.id}" style="display:none;">$J{ele.reviewer}</span>
					<span id="ele_dateOfReviewStr_$J{ele.id}" style="display:none;">$J{ele.dateOfReviewStr}</span>
					<span id="ele_note_$J{ele.id}" style="display:none;">$J{ele.note}</span>
					<span id="ele_message_$J{ele.id}" style="display:none;">$J{ele.message}</span>
					<div class="Btn btn-group" style="background:none; padding:0;">
						<button class="btn-small">操作</button>
						<button class="dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
						</button>
					</div>
					<div class="btn_group" style=" position:relative;">
					<ul class="dropdown-menu" style="display: none;">
							<li onclick="javascript:showMsg('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">给家长留言</a></li>
							<li onclick="javascript:showStuApplyDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">详情</a></li>
						{@if ele.statusStr=="SUBMIT_ONCE"}
							<li onclick="javascript:showStuApplyHistoryList('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">历史存档</a></li>
							<li onclick="javascript:showStuApplyDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">修改</a></li>
						{@else if ele.statusStr=="SUBMIT_TWICE"}
							<li onclick="javascript:showStuApplyHistoryList('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">历史存档</a></li>
							<li onclick="javascript:showStuApplyDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">修改</a></li>
							{@if ele.infoStatusStr=="SUBMIT_ONCE"}
								<li onclick="javascript:showStuInfoDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息</a></li>
								<li onclick="javascript:showStuInfoDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息修改</a></li>
							{@/if}
							{@if ele.vaccineStatusStr=="SUBMIT_ONCE"}
								<li onclick="javascript:showStuVaccineDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息</a></li>
								<li onclick="javascript:showStuVaccineDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息修改</a></li>
							{@/if}
							<li onclick="javascript:showReview('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">审核</a></li>
						{@else if ele.statusStr=="REVIEW_PASS"}
							<li onclick="javascript:showStuApplyHistoryList('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">历史存档</a></li>
							<li onclick="javascript:showStuInfoDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息</a></li>
							<li onclick="javascript:showStuInfoDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">学籍信息修改</a></li>
							<li onclick="javascript:showStuVaccineDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息</a></li>
							<li onclick="javascript:showStuVaccineDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">预防信息修改</a></li>
							<li onclick="javascript:showModifyClass('$J{ele.id}','$J{ele.classNo}');" style="cursor:pointer;"><a href="javascript:void(0);">分配班级</a></li>
							<li onclick="javascript:showReview('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">重新审核</a></li>
						{@else if ele.statusStr=="REVIEW_REFUSE"||ele.statusStr=="REVIEW_WAITING"}
							<li onclick="javascript:showStuApplyHistoryList('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">历史存档</a></li>
							<li onclick="javascript:showReview('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">重新审核</a></li>
						{@/if}
						{@if currentYear!=ele.year&&ele.statusStr!="REVIEW_PASS"}
							<li onclick="javascript:forwardGrade('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">转$J{currentYear}级</a></li>
						{@/if}
					</ul>
					</div>
				</td>
			</tr>
		{@/each}
	{@/if}
			<tr id="tr_batchReview_2" style="display: none;">
				<td colspan="11">
					<span id="span_selCount_2"></span>
					<input onclick="javascript:doBatchReview('REVIEW_PASS','拟录取');" type="button" value="拟录取" class="Btn" />
					<input onclick="javascript:doBatchReview('REVIEW_REFUSE','不予录取');" type="button" value="不予录取" class="Btn" />
					<input onclick="javascript:doBatchReview('REVIEW_WAITING','待录取');" type="button" value="待录取" class="Btn" />
				</td>
			</tr>
			</tbody>
		</table>
	</form>
	<jsp:include flush="true" page="/WEB-INF/content/common/page_model.jsp" />
    <div class="left" style="height:36px; line-height:36px; "><span>共$J{totalRecords}条数据</span></div>
</script>
<script id="model_stuApplyView" type="text/template">
	<div class="left">
		<button onclick="javascript:showStuApplyList();" class="Btn" type="button">返回</button>
{@if statusStr!="SUBMIT_NONE"}
		<button id="btn_print_1" onclick="javascript:showDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
		<button id="btn_print_3" onclick="javascript:{window.open('${ctx}/resource/钟家村寄宿学校打印帮助.pdf');};" class="Btn" type="button">打印须知</button>
{@/if}
{@if statusStr=="SUBMIT_ONCE"}<!--2016-6-23-->
		<button id="btn_modify_5" onclick="javascript:showStuApplyDetail('$J{id}',true);" class="Btn" type="button">修改</button>
{@else if statusStr=="SUBMIT_TWICE"}
		<button id="btn_modify_5" onclick="javascript:showStuApplyDetail('$J{id}',true);" class="Btn" type="button">修改</button>
		<button id="btn_review_4" onclick="javascript:showReview('$J{id}');" class="Btn" type="button">审核</button>
{@else if statusStr=="REVIEW_PASS"||statusStr=="REVIEW_REFUSE"||statusStr=="REVIEW_WAITING"}
		<button id="btn_review_4" onclick="javascript:showReview('$J{id}');" class="Btn" type="button">重新审核</button>
{@/if}
	</div>
	<br />
	<jsp:include flush="true" page="/WEB-INF/content/student/stuApply_detail_model.jsp" />
	<div id="div_stuApplyAtt"></div>
	<div id="div_modify" class="page_navi right" style="display:none;padding-bottom: 10px;">
		<button onclick="javascritp:doSaveStuApply(true);" class="Btn" type="button">保存修改</button>
		<button onclick="javascritp:showStuApplyList();" class="Btn" type="button">返回</button>
	</div>
</script>
<script id="model_stuInfoView" type="text/template">
	<div class="left">
		<button onclick="javascript:showStuApplyList();" class="Btn" type="button">返回</button>
{@if statusStr!="SUBMIT_NONE"}
		<button id="btn_print_1" onclick="javascript:showStuInfoDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showStuInfoPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
{@/if}
	</div>
	<br />
	<jsp:include flush="true" page="/WEB-INF/content/student/stuInfo_detail_model.jsp" />
	<div id="div_modify" class="page_navi right" style="display:none;padding-bottom: 10px;">
		<button onclick="javascritp:doSaveStuInfo(true);" class="Btn" type="button">保存修改</button>
		<button onclick="javascritp:showStuApplyList();" class="Btn" type="button">返回</button>
	</div>
</script>
<script id="model_stuVaccineView" type="text/template">
	<div class="left">
		<button onclick="javascript:showStuApplyList();" class="Btn" type="button">返回</button>
	</div>
	<br />
	<jsp:include flush="true" page="/WEB-INF/content/student/stuVaccine_detail_model.jsp" />
	<div id="div_modify" class="page_navi right" style="display:none;padding-bottom: 10px;">
		<button onclick="javascritp:doSaveStuVaccine(true);" class="Btn" type="button">保存修改</button>
		<button onclick="javascritp:showStuApplyList();" class="Btn" type="button">返回</button>
	</div>
</script>
<script id="model_classInfo" type="text/template">
	<input id="class_ipt_id" value="$J{id}" type="hidden" />
	<div class="item">
		<label class="sele-label label-nick">班级编号：</label>
		<input id="class_ipt_no" value="$J{no}" type="text" class="inp" style="width: 174px;" />
		<label class="sele-label red">* 必填项</label>
	</div>
	<div class="item"></div>
</script>
<script id="model_msgInfo" type="text/template">
	<input id="review_ipt_id" value="$J{id}" type="hidden" />
	<div class="item" style="height: 64px;">
		<textarea id="review_ipt_message" class="textarea" style="border-color:#ddd; border-style: solid; border-width: 1px; font-size: 12px; font-family: Tahoma,宋体; word-wrap: break-word; line-height: 18px; overflow: hidden; outline: medium none; height:110px; border-radius: 2px; width:95%;">$J{message}</textarea>
	</div>
	<div class="item"></div>
</script>
<script id="model_reviewInfo" type="text/template">
	<input id="review_ipt_id" value="$J{id}" type="hidden" />
	<div class="item">
		<label class="sele-label label-nick">审核人：</label>
		<input id="review_ipt_reviewer" value="$J{reviewer}" type="text" class="inp" style="width: 174px;" />
		<label class="sele-label red">* 必填项</label>
	</div>
	<div class="item">
		<label class="sele-label label-nick">审核日期：</label>
		<input id="review_ipt_dateOfReview" value="$J{dateOfReview}" style="cursor:pointer;width:146px;" readonly="readonly" type="text" class="inp Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy年MM月dd日',alwaysUseStartDate:true})" maxlength="10" />
		<label class="sele-label red">* 必填项</label>
	</div>
	<div class="item">
		<label class="sele-label label-nick">审核结果：</label>
		<select id="review_sel_status">
			<option {@if status=="REVIEW_PASS"}selected="selected"{@/if} value="REVIEW_PASS">拟录取</option>
			<option {@if status=="REVIEW_REFUSE"}selected="selected"{@/if} value="REVIEW_REFUSE">不予录取</option>
			<option {@if status=="REVIEW_WAITING"}selected="selected"{@/if} value="REVIEW_WAITING">待录取</option>
		</select>
		<label class="sele-label red">* 必填项</label>
	</div>
	<div class="item">
		<label class="sele-label label-nick">备注：</label>
		<textarea id="review_ipt_note" class="textarea" style="margin: 0px 0px 3px; padding: 4px 4px 0px; border-color:#ddd; border-style: solid; border-width: 1px; font-size: 12px; font-family: Tahoma,宋体; word-wrap: break-word; line-height: 18px; overflow: hidden; outline: medium none; height:60px; border-radius: 2px; width:174px;">$J{note}</textarea>
	</div>
	<div class="item" style="height: 64px;">
		<label class="sele-label label-nick" style="margin-top: 39px;">给家长留言：</label>
		<textarea id="review_ipt_message" class="textarea" style="margin: 40px 0px 3px; padding: 4px 4px 0px; border-color:#ddd; border-style: solid; border-width: 1px; font-size: 12px; font-family: Tahoma,宋体; word-wrap: break-word; line-height: 18px; overflow: hidden; outline: medium none; height:60px; border-radius: 2px; width:174px;">$J{message}</textarea>
	</div>
	<div class="item"></div>
</script>
<script id="model_export" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">数据导出</li>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<!-- 动态头部信息 -->
		<div class="container-1" style="padding:0;">
			<div class="unfold_content clearfix">
				<form class="unfold_left">
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">年龄：</label>
						<select id="sel_ageScope">
							<option value="">全部</option>
							<option value="LESS_THAN_6">未满6周岁</option>
							<option value="GREATER_THAN_6">年满6周岁</option>
						</select>
					</div>
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">报名类别：</label>
						<select id="sel_type">
							<option value="">全部</option>
							<option value="外教班">外教班</option>
							<option value="普通班">普通班</option>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">性别：</label>
						<select id="sel_sex">
							<option value="">全部</option>
							<option value="TRUE">男</option>
							<option value="FALSE">女</option>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">表单状态：</label>
						<select id="sel_status">
							<option value="">全部</option>
							<option value="SUBMIT_NONE">未提交</option>
							<option value="SUBMIT_ONCE">已提交1次</option>
							<option value="SUBMIT_TWICE">待审核</option>
							<option value="REVIEW_PASS">拟录取</option>
							<option value="REVIEW_REFUSE">不予录取</option>
							<option value="REVIEW_WAITING">待录取</option>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">校区：</label>
						<select id="sel_school">
							<% if (user.getUid()==4699){%>					<%--冷丹 只能看南湖校区的报名--%>
								<option value="NANHU">全部</option>
								<option value="NANHU">南湖校区</option>
							<% }else if (user.getUid()==4684) { %>			<%--刘峥 只能看桂子山校区的报名--%>
								<option value="GUIZISHAN">全部</option>
								<option value="GUIZISHAN">桂子山校区</option>
							<% }else { %>
								<option value="">全部</option>
								<option value="NANHU">南湖校区</option>
								<option value="GUIZISHAN">桂子山校区</option>
							<% } %>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label>
						<input onclick="javascript:doExport();" type="button" value="导出" class="Btn" />
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</script>
<script id="model_import" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">信息导入</li>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<!-- 动态头部信息 -->
		<div class="container-1" style="padding:0;">
			<div class="unfold_content clearfix">
				<form id="form_import" method="post" target="formFrame" enctype="multipart/form-data" class="unfold_left">
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">导入的数据项：</label>
						<select id="sel_dataField" name="field">
							<option value="">请选择</option>
							<option value="FIELD_BJ">班级</option>
							<option value="FIELD_XJFH">学籍辅号</option>
							<option value="FIELD_BNXH">班内学号</option>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">数据文件：</label>
						<span id="span_file"><input id="ipt_file" name="file" type="file" accept="application/vnd.ms-excel" style="width: 174px;padding-top: 5px;" /></span>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label>
						<input onclick="javascript:doImport();" type="button" value="导入" class="Btn" />
						<input onclick="javascript:downloadModel();" type="button" value="下载模板" class="Btn" />
					</div>
					<div class="item">
						<label style="height: 100px;" class="sele-label label-nick">说明：</label>
						<span id="span_file">1、进入“学生信息”界面，导出相关学生的“基本信息”；<br />2、在该界面选择相应的“导入的数据项”点击“下载模板”；<br />3、从导出学生的“基本信息”中把相关的数据复制到模板中；<br />4、补充完整要导入的数据项并保存；<br />5、选择以上步骤得到的文件，点击“导入”完成相关数据项的导入操作。</span>
					</div>
				</form>
				<form id="form_import_res" class="unfold_left" style="display:none;">
					<div class="item" style="margin-top: 20px;margin-left: 25px;">
						<span id="span_import_res"></span>
					</div>
					<div class="item" style="margin-left: 50px;">
						<input onclick="javascript:import_res_hide();" type="button" value="返回" class="Btn" />
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</script>
<script id="model_stuInfoDownloadExplain1" type="text/template">
	<div class="item" style="height: auto;">
		<p>&nbsp确认发送面试通过短信；</p>
	</div>
</script>