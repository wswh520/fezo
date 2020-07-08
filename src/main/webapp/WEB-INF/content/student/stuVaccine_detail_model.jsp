<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="base_title">
		<em></em>
		<strong>入托、入学儿童预防接种证查验登记表</strong>
		<strong style="float:right;color:red">
			申请状态：{@if id==0}未填表
			{@else if statusStr=="SUBMIT_NONE"}未提交
			{@else if statusStr=="SUBMIT_ONCE"}已提交
			{@/if}
		</strong>
	</div>
	<form id="form_stuVaccine" class="bs-docs-example">
	<div id="div_stuVaccine_cover" style="background-color: rgba(255, 255, 255, 0);position: absolute;z-index: 999;"></div>
	<input id="ipt_id" name="id" value="$J{id}" style="width: 174px;" type="hidden" />
	<input id="ipt_stuId" name="stuId" value="$J{stuId}" style="width: 174px;" type="hidden" />
<table class="table table-bordered table-striped">
	<thead>
		<tr>
			<th width="5%">编号</th>
			<th width="15%" colspan="2">项目名称</th>
			<th width="30%">基础数据</th>
			<th width="5%">编号</th>
			<th width="15%">项目名称</th>
			<th width="30%">基础数据</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td>1</td>
			<td colspan="2">姓名</td>
			<td>$J{stuApply.name}</td>
			<td>2</td>
			<td>出生日期</td>
			<td>$J{stuApply.dateOfBirthStr}</td>
		</tr>
		<tr>
			<td>3</td>
			<td colspan="2">儿童编码</td>
			<td><input name="other25" value="$J{other25}" style="width: 95%;" type="text" class="inp" /></td>
			<td>4</td>
			<td>性别</td>
			<td>{@if stuApply.sexStr=="TRUE"}男{@else}女{@/if}</td>
		</tr>
		<tr>
			<td>5</td>
			<td colspan="2">是否有接种证</td>
			<td colspan="4">
				<label><input name="other1" {@if other1==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other1" {@if other1==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>6</td>
			<td colspan="2">是否全种</td>
			<td colspan="4">
				{@if other2==1}√{@else if other2==0}×{@/if}
			</td>
		</tr>
		<tr>
			<td rowspan="3">7</td>
			<td rowspan="3">乙肝疫苗</td>
			<td>1</td><%--乙肝疫苗1 --%>
			<td colspan="4">
				<label><input name="other3" {@if other3==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other3" {@if other3==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--乙肝疫苗2 --%>
			<td colspan="4">
				<label><input name="other4" {@if other4==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other4" {@if other4==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>3</td><%--乙肝疫苗3 --%>
			<td colspan="4">
				<label><input name="other5" {@if other5==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other5" {@if other5==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>8</td>
			<td colspan="2">卡介苗</td>
			<td colspan="4">
				<label><input name="other6" {@if other6==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other6" {@if other6==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td rowspan="4">9</td>
			<td rowspan="4">脊灰疫苗</td>
			<td>1</td><%--脊灰疫苗1 --%>
			<td colspan="4">
				<label><input name="other7" {@if other7==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other7" {@if other7==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--脊灰疫苗2 --%>
			<td colspan="4">
				<label><input name="other8" {@if other8==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other8" {@if other8==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>3</td><%--脊灰疫苗3 --%>
			<td colspan="4">
				<label><input name="other9" {@if other9==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other9" {@if other9==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>4</td><%--脊灰疫苗4 --%>
			<td colspan="4">
				<label><input name="other10" {@if other10==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other10" {@if other10==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td rowspan="4">10</td>
			<td rowspan="4">百白破疫苗</td>
			<td>1</td><%--百白破疫苗1 --%>
			<td colspan="4">
				<label><input name="other11" {@if other11==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other11" {@if other11==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--百白破疫苗2 --%>
			<td colspan="4">
				<label><input name="other12" {@if other12==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other12" {@if other12==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>3</td><%--百白破疫苗3 --%>
			<td colspan="4">
				<label><input name="other13" {@if other13==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other13" {@if other13==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>4</td><%--百白破疫苗4 --%>
			<td colspan="4">
				<label><input name="other14" {@if other14==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other14" {@if other14==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>11</td>
			<td colspan="2">白破疫苗</td>
			<td colspan="4">
				<label><input name="other15" {@if other15==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other15" {@if other15==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>12</td>
			<td colspan="2">麻风疫苗</td>
			<td colspan="4">
				<label><input name="other16" {@if other16==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other16" {@if other16==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>13</td>
			<td colspan="2">麻腮疫苗</td>
			<td colspan="4">
				<label><input name="other17" {@if other17==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other17" {@if other17==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td rowspan="2">14</td>
			<td rowspan="2">乙脑疫苗</td>
			<td>1</td><%--乙脑疫苗1 --%>
			<td colspan="4">
				<label><input name="other18" {@if other18==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other18" {@if other18==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--乙脑疫苗2 --%>
			<td colspan="4">
				<label><input name="other19" {@if other19==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other19" {@if other19==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td rowspan="2">15</td>
			<td rowspan="2">A群流脑疫苗</td>
			<td>1</td><%--A群流脑疫苗1 --%>
			<td colspan="4">
				<label><input name="other20" {@if other20==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other20" {@if other20==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--A群流脑疫苗2 --%>
			<td colspan="4">
				<label><input name="other21" {@if other21==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other21" {@if other21==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td rowspan="2">16</td>
			<td rowspan="2">A+C群流脑疫苗</td>
			<td>1</td><%--A+C群流脑疫苗1 --%>
			<td colspan="4">
				<label><input name="other22" {@if other22==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other22" {@if other22==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>2</td><%--A+C群流脑疫苗2 --%>
			<td colspan="4">
				<label><input name="other23" {@if other23==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other23" {@if other23==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
		<tr>
			<td>17</td>
			<td colspan="2">甲肝疫苗</td>
			<td colspan="4">
				<label><input name="other24" {@if other24==1}checked="checked"{@/if} type="radio" value="TRUE" />√</label>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<label><input name="other24" {@if other24==0}checked="checked"{@/if} type="radio" value="FALSE" />×</label>
			</td>
		</tr>
	</tbody>
</table>
</form>