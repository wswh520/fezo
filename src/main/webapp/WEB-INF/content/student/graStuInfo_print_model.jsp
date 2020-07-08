<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="base_title">
		<em></em>
		<strong>2016届小学毕业生基本信息核对表</strong>
		<strong style="float:right;color:red">
			{@if statusStr=="SUBMIT_NONE"}未核对，暂不能打印
			{@else if statusStr=="SUBMIT_ONCE"}待复核，暂不能打印
			{@else if statusStr=="SUBMIT_TWICE"}核对无误
			{@else if statusStr=="REVIEW_PASS"}待复核，暂不能打印
			{@else if statusStr=="REVIEW_REFUSE"}复核未通过，暂不能打印
			{@/if}
		</strong>
	</div>
	<form id="form_graStuInfo" class="bs-docs-example">
	<div id="div_graStuInfo_cover" style="background-color: rgba(255, 255, 255, 0);position: absolute;z-index: 999;"></div>
	<input id="ipt_id" name="id" value="$J{id}" style="width: 174px;" type="hidden" />
	<br />
	<p style="font-size:20px;">学校收取“双证”时间：2016年5月3日（收件截止时间）</p>
	<table class="table table-bordered table-striped">
		<tbody>
			<tr>
				<td width="100">学校标识码</td>
				<td>2142001636</td>
				<td width="100">学校名称</td>
				<td >华师附小</td>
				<td>班级</td>
				<td>$J{bj}</td>
				<td>籍贯</td>
				<td>
					{@if jg1!=""}$J{jg1}省{@/if}{@if jg2!=""}$J{jg2}市（县）{@/if}
				</td>
			</tr>
			<tr>
				<td>学籍辅号</td>
				<td>$J{xbxh}</td>
				<td>学生姓名</td>
				<td>$J{xm}</td>
				<td>性别</td>
				<td>
					{@if xbStr=="TRUE"}男{@else}女{@/if}
				</td>
				<td>身份证号</td>
				<td>$J{sfzh}</td>
			</tr>
			<tr>
				<td>户口所在地</td>
				<td>$J{hkszd}</td>
				<td>户口地址</td>
				<td colspan="5">$J{hkdz1}，$J{hkdz2}</td>
			</tr>
			<tr>
				<td>居住性质</td>
				<td>$J{jzxz}</td>
				<td>家庭地址</td>
				<td colspan="5">$J{jtzz}</td>
			</tr>
			<tr>
				<td>父亲姓名</td>
				<td>$J{fuxm}</td>
				<td>联系电话</td>
				<td>$J{fudh}</td>
				<td>工作单位</td>
				<td colspan="3">$J{fudw}</td>
			</tr>
			<tr>
				<td>母亲姓名</td>
				<td>$J{muxm}</td>
				<td>联系电话</td>
				<td>$J{mudh}</td>
				<td>工作单位</td>
				<td colspan="3">$J{mudw}</td>
			</tr>
			<tr>
				<td colspan="8">注： “家庭地址”只能填一处房产地址。</td>
			</tr>
		</tbody>
	</table>
	</form>