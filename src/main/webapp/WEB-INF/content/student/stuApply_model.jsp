<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_stuApplyDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">钟家村小学新生入学登记</li>
		</ul>
	</div>
	<div class="right">
		<button id="btn_refresh" onclick="javascript:doLoadStuApply();" class="Btn" type="button"><i class="icon icon-Repeat"></i>刷新</button>
	</div>
	<div class="left">
{@if !locked}
		<button id="btn_showModify" onclick="javascritp:showStuApplyModify(1);" class="Btn" type="button"><i class="icon icon-Modify"></i>修改</button>
	{@if statusStr=="SUBMIT_NONE"}
		<button id="btn_submit2Review" onclick="javascritp:submit2Review();" class="Btn" type="button">提交</button>
	{@else if statusStr=="SUBMIT_ONCE"}
		<button id="btn_submit2Review" onclick="javascritp:submit2ReviewTwice();" class="Btn" type="button">再次提交</button>
	{@/if}
		<button id="btn_doModify" onclick="javascritp:doSaveStuApply(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify" onclick="javascritp:doCancelStuApplyModify();" style="display:none;" class="Btn" type="button">取消</button>
{@/if}
{@if statusStr!="SUBMIT_NONE"}
		<button id="btn_print_1" onclick="javascript:showDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
		<button id="btn_print_3" onclick="javascript:{window.open('${ctx}/resource/钟家村小学打印帮助.pdf');};" class="Btn" type="button">打印须知</button>
{@/if}
{@if currentYear!=year&&statusStr!="REVIEW_PASS"}<!--2016-6-23-->
	<button onclick="javascript:forwardGrade('$J{id}',true);"  class="Btn" type="button">申请转$J{currentYear}级</button>
{@/if}
	</div>
	<div class="clear"></div>
	<jsp:include flush="true" page="/WEB-INF/content/student/stuApply_detail_model.jsp" />
	<div id="div_stuApplyAtt"></div>
	<div class="page_navi right" style="padding-bottom: 10px;">
		<button id="btn_doModify_2" onclick="javascritp:doSaveStuApply(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify_2" onclick="javascritp:doCancelStuApplyModify();" style="display:none;" class="Btn" type="button">取消</button>
	</div>
</div>
</script>

<script id="model_stuInfoDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">全国中小学学籍管理系统学生基本信息</li>
		</ul>
	</div>
	<div class="right">
		<button id="btn_refresh" onclick="javascript:doLoadStuInfo();" class="Btn" type="button"><i class="icon icon-Repeat"></i>刷新</button>
	</div>
	<div class="left">
{@if !locked}
		<button id="btn_showModify" onclick="javascritp:showStuInfoModify();" class="Btn" type="button"><i class="icon icon-Modify"></i>修改</button>
	{@if statusStr=="SUBMIT_NONE"}
		<button id="btn_submit2Review" onclick="javascritp:submitStuInfo();" class="Btn" type="button">提交</button>
	{@/if}
		<button id="btn_doModify" onclick="javascritp:doSaveStuInfo(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify" onclick="javascritp:doCancelStuInfoModify();" style="display:none;" class="Btn" type="button">取消</button>
		<button onclick="javascript:{window.open('${ctx}/resource/doc/钟家村小学新生入学学生基本信息表填表说明.pdf');};" class="Btn" type="button">填表说明</button>
		<button onclick="javascript:{window.open('${ctx}/resource/doc/行政区划代码查询表.xls');};" class="Btn" type="button">行政区划代码查询表</button>
{@else if statusStr=="SUBMIT_NONE"}
{@/if}
{@if statusStr!="SUBMIT_NONE"&&other7!=null&&other7!=""&&other8!=null&&other8!=""}
		<button id="btn_print_1" onclick="javascript:showStuInfoDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showStuInfoPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
{@/if}
	</div>
	<div class="clear"></div>
	<jsp:include flush="true" page="/WEB-INF/content/student/stuInfo_detail_model.jsp" />
	<div class="page_navi right" style="padding-bottom: 10px;">
		<button id="btn_doModify_2" onclick="javascritp:doSaveStuInfo(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify_2" onclick="javascritp:doCancelStuInfoModify();" style="display:none;" class="Btn" type="button">取消</button>
	</div>
</div>
</script>

<script id="model_stuVaccineDisplay" type="text/template">
<div class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">入托、入学儿童预防接种证查验登记表</li>
		</ul>
	</div>
	<div class="right">
		<button id="btn_refresh" onclick="javascript:doLoadStuVaccine();" class="Btn" type="button"><i class="icon icon-Repeat"></i>刷新</button>
	</div>
	<div class="left">
{@if !locked}
		<button id="btn_showModify" onclick="javascritp:showStuVaccineModify();" class="Btn" type="button"><i class="icon icon-Modify"></i>修改</button>
	{@if statusStr=="SUBMIT_NONE"}
		<button id="btn_submit2Review" onclick="javascritp:submitStuVaccine();" class="Btn" type="button">提交</button>
	{@/if}
		<button id="btn_doModify" onclick="javascritp:doSaveStuVaccine(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify" onclick="javascritp:doCancelStuVaccineModify();" style="display:none;" class="Btn" type="button">取消</button>
{@else if statusStr=="SUBMIT_NONE"}
{@/if}
	</div>
	<div class="clear"></div>
	<jsp:include flush="true" page="/WEB-INF/content/student/stuVaccine_detail_model.jsp" />
	<div class="page_navi right" style="padding-bottom: 10px;">
		<button id="btn_doModify_2" onclick="javascritp:doSaveStuVaccine(false);" style="display:none;" class="Btn" type="button">保存修改</button>
		<button id="btn_cancelModify_2" onclick="javascritp:doCancelStuVaccineModify();" style="display:none;" class="Btn" type="button">取消</button>
	</div>
</div>
</script>

<script id="model_stuApplyHistoryList" type="text/template">
	<table class="table table-bordered table-striped">
		<thead><tr>
			<th width="20%">存档人</th>
			<th width="60%">存档时间</th>
			<th width="20%">&nbsp;</th>
		</tr></thead>
		<tbody>
	{@if totalRecords==0}
		<tr>
			<td colspan="3">暂无记录！</td>
		</tr>
	{@else}
		{@each data as ele}
		<tr>
			<td id="dialog_ele_name_$J{ele.id}">$J{ele.name}</td>
			<td id="dialog_ele_timeStr_$J{ele.id}">$J{ele.timeStr}</td>
			<td>
				<a href="javascript:loadStuApplyHistoryInfo('$J{ele.id}');">详情</a>
			</td>
		</tr>
		{@/each}
	{@/if}
		</tbody>
	</table>
</script>
<script id="model_sutApplyHistoryInfo" type="text/template">
	<div class="base_title">
		<strong style="color:red">$J{title}</strong>
	</div>
	<jsp:include flush="true" page="/WEB-INF/content/student/stuApply_detail_model.jsp" />
</script>
<script id="model_downloadExplain" type="text/template">
	<div class="item" style="height: auto;">
		<p>&nbsp;&nbsp;&nbsp;1、请确认您的电脑安装有PDF阅读器；</p>
		<p>&nbsp;&nbsp;&nbsp;2、下载后不得对文档进行任何仿制和修改；</p>
		<p>&nbsp;&nbsp;&nbsp;3、本文档共有4页，请将第一页《2017年钟家村小学新生入学基本信息登记表（一）》和第二页《家长诚信承诺书》正反打印在一页A4纸上；将第三页《2017年钟家村小学新生入学招生审核登记表（二）》和第四页正反打印在一页A4纸上；</p>
	</div>
</script>
<script id="model_stuInfoDownloadExplain" type="text/template">
	<div class="item" style="height: auto;">
		<p>&nbsp;&nbsp;&nbsp;1、请确认您的电脑安装有PDF阅读器；</p>
		<p>&nbsp;&nbsp;&nbsp;2、下载后不得对文档进行任何仿制和修改；</p>
	</div>
</script>
<script id="model_printExplain" type="text/template">
	<div class="item" style="height: auto;">
		<p>&nbsp;&nbsp;&nbsp;1、请确认当前浏览器为IE浏览器，并按提示安装相关的打印组件，您的电脑连接着工作中的打印机；</p>
		<p>&nbsp;&nbsp;&nbsp;2、如果无法在线打印，请“下载后打印”；</p>
	</div>
</script>
