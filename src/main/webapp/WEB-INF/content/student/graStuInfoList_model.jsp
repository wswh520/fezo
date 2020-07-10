<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_graStuInfoListDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">毕业生信息表</li>
		</ul>
	</div>
	<div id="div_search" class="right">
		<input style="font-size: 12px;width: 150px;" class="searchinp left" type="text" placeholder="姓名/学号" id="ipt_keyword"/>
		<button class="Btn" type="button" onclick="javascript:toPage(1);"><i class="icon icon-search"></i>搜索</button>
	</div>
	<div id="div_back" class="left" style="display:none;"></div>
	<div id="div_sel" class="left">
{@if userType!=='GRADUATE_TEACHER'}
		<select id="sel_bj" onchange="javascript:toPage(1);">
			<option value="">班级</option>
			<option value="601">601</option>
			<option value="602">602</option>
			<option value="603">603</option>
			<option value="604">604</option>
			<option value="605">605</option>
		</select>
{@/if}
		<select id="sel_sylb" onchange="javascript:toPage(1);">
			<option value="">报名类别</option>
			<option value="TYPEA">1子弟</option>
			<option value="TYPEB">2第三代</option>
			<option value="TYPEC">3其他生源</option>
		</select>
		<select id="sel_xb" onchange="javascript:toPage(1);">
			<option value="">性别</option>
			<option value="TRUE">男</option>
			<option value="FALSE">女</option>
		</select>
		<select id="sel_status" onchange="javascript:toPage(1);">
			<option value="">表单状态</option>
			<option value="SUBMIT_NONE">未核对</option>
			<option value="SUBMIT_ONCE">待复核</option>
			<option value="REVIEW_PASS">需二次复核</option>
			<option value="REVIEW_REFUSE">复核未通过</option>
			<option value="SUBMIT_TWICE">核对无误</option>
		</select>
	</div>
	<div class="clear"></div>
	<div id="div_display_list"></div>
</div>
</script>
<script id="model_graStuInfoList" type="text/template">
	<form id="form_graStuInfoList" class="bs-docs-example">
		<table class="table table-bordered table-striped">
			<thead>
			<tr>
				<th style="width:4%;text-align:center;">序号</th>
				<th width="6%">学号</th>
				<th width="7%">姓名</th>
				<th width="4%">性别</th>
				<th width="11%">报名类别</th>
				<th width="8%">状态</th>
				<th width="15%">身份证号</th>
				<th width="9%">班级</th>
				<th width="40%">&nbsp;</th>
			</tr></thead>
			<tbody>
	{@if totalRecords==0}
			<tr>
				<td colspan="9">暂无记录！</td>
			</tr>
	{@else}
		{@each datas as ele,index}
			<tr>
				<td style="text-align:center;">$J{index-1+2+(currentPage-1)*15}</td>
				<td>$J{ele.xbxh}</td>
				<td id="ele_xm_$J{ele.id}">$J{ele.xm}</td>
				<td>{@if ele.xbStr=="TRUE"}男{@else}女{@/if}</td>
				<td>
					{@if ele.sylbStr=="TYPEA"}1子弟
					{@else if ele.sylbStr=="TYPEB"}2第三代
					{@else if ele.sylbStr=="TYPEC"}3其他生源
					{@/if}
				</td>
				<td>
					{@if ele.statusStr=="SUBMIT_NONE"}未核对
					{@else if ele.statusStr=="SUBMIT_ONCE"}待复核
					{@else if ele.statusStr=="SUBMIT_TWICE"}核对无误
					{@else if ele.statusStr=="REVIEW_PASS"}需二次复核
					{@else if ele.statusStr=="REVIEW_REFUSE"}复核未通过
					{@/if}
				</td>
				<td id="ele_sfzh_$J{ele.id}">$J{ele.sfzh}</td>
				<td>$J{ele.bj}</td>
				<td>
					<span id="ele_statusStr_$J{ele.id}" style="display:none;">$J{ele.statusStr}</span>
					<div class="Btn btn-group" style="background:none; padding:0;">
						<button class="btn-small">操作</button>
						<button class="dropdown-toggle" data-toggle="dropdown">
							<span class="caret"></span>
						</button>
					</div>
					<div class="btn_group" style=" position:relative;">
					<ul class="dropdown-menu" style="display: none;">
							<li onclick="javascript:showGraStuInfoDetail('$J{ele.id}',false);" style="cursor:pointer;"><a href="javascript:void(0);">详情</a></li>
							<li onclick="javascript:showGraStuInfoHistoryList('$J{ele.id}');" style="cursor:pointer;"><a href="javascript:void(0);">历史存档</a></li>
						{@if ele.statusStr=="SUBMIT_ONCE"||ele.statusStr=="REVIEW_PASS"}
							<li onclick="javascript:showGraStuInfoDetail('$J{ele.id}',true);" style="cursor:pointer;"><a href="javascript:void(0);">复核</a></li>
						{@/if}
					</ul>
					</div>
				</td>
			</tr>
		{@/each}
	{@/if}
			</tbody>
		</table>
	</form>
	<jsp:include flush="true" page="/WEB-INF/content/common/page_model.jsp" />
    <div class="left" style="height:36px; line-height:36px; "><span>共$J{totalRecords}条数据</span></div>
</script>
<script id="model_graStuInfoView" type="text/template">
	<div class="left">
		<button onclick="javascript:showGraStuInfoList();" class="Btn" type="button">返回</button>
{@if statusStr!="SUBMIT_NONE"}
		<button id="btn_print_1" onclick="javascript:showGraStuInfoDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showGraStuInfoPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
		<!--<button id="btn_print_3" onclick="javascript:{window.open('${ctx}/resource/钟家村寄宿学校打印帮助.pdf');};" class="Btn" type="button">打印须知</button>-->
{@/if}
	</div>
	<br />
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfo_detail_model.jsp" />
	<div id="div_stuGraInfoAtt" style="display:none;"></div>
	<div id="div_modify" class="page_navi right" style="display:none;padding-bottom: 10px;">
		<button onclick="javascritp:doReviewGraStuInfo_refuse();" class="Btn" type="button">不通过</button>
		<button onclick="javascritp:doSaveGraStuInfo(true,student_verifyGraStuInfoUrl);" class="Btn" type="button">需二次审核</button>
		<button onclick="javascritp:doSaveGraStuInfo(true,student_reviewGraStuInfoUrl);" class="Btn" type="button">复核完毕，确认无误</button>
		<button onclick="javascritp:showGraStuInfoList();" class="Btn" type="button">返回</button>
	</div>
</script>
<script id="model_gra_import" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">毕业生信息导入</li>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<!-- 动态头部信息 -->
		<div class="container-1" style="padding:0;">
			<div class="unfold_content clearfix">
				<form id="form_import" method="post" target="formFrame" enctype="multipart/form-data" class="unfold_left">
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">届数：</label>
						<select id="ipt_year" name="year">
							<option value="">请选择</option>
							<option value="2016">2016届</option>
						</select>
					</div>
					<div class="item">
						<label class="sele-label label-nick">数据文件：</label>
						<span id="span_file"><input id="ipt_file" name="file" type="file" accept="application/vnd.ms-excel" style="width: 174px;padding-top: 5px;" /></span>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label>
						<input onclick="javascript:doGraImport();" type="button" value="导入" class="Btn" />
					</div>
				</form>
				<form id="form_import_res" class="unfold_left" style="display:none;">
					<div class="item" style="margin-top: 20px;margin-left: 25px;height: auto;">
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
<script id="model_gra_export" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">毕业生信息导出</li>
		</ul>
	</div>
	<div class="clear"></div>
	<div class="mkclearfix-right" style="min-height:600px; width:100%;">
		<!-- 动态头部信息 -->
		<div class="container-1" style="padding:0;">
			<div class="unfold_content clearfix">
				<form id="form_export" method="post" target="formFrame" enctype="multipart/form-data" class="unfold_left">
					<div class="item" style="padding-top: 15px;">
						<label class="sele-label label-nick">基础数据：</label>
						<span id="span_file"><input id="ipt_file" name="file" type="file" accept="application/vnd.ms-excel" style="width: 174px;padding-top: 5px;" /></span>
					</div>
					<div class="item">
						<label class="sele-label label-nick">&nbsp;</label>
						<input id="btn_export" onclick="javascript:doGraExport();" type="button" value="导出" class="Btn" />
						<input id="btn_export_waiting" type="button" value="正在导出，请稍等..." style="display:none;" class="Btn" />
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
</script>