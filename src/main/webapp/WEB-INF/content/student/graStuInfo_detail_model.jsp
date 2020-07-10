<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

	<div class="base_title">
		<em></em>
		<strong>钟家村寄宿学校毕业信息登记表</strong>
		<strong style="float:right;color:red">
			核对状态：{@if statusStr=="SUBMIT_NONE"}未核对
			{@else if statusStr=="SUBMIT_ONCE"}待复核
			{@else if statusStr=="SUBMIT_TWICE"}核对无误
			{@else if statusStr=="REVIEW_PASS"}待复核
			{@else if statusStr=="REVIEW_REFUSE"}复核未通过
			{@/if}
		</strong>
	</div>
	<form id="form_graStuInfo" class="bs-docs-example">
	<div id="div_graStuInfo_cover" style="background-color: rgba(255, 255, 255, 0);position: absolute;z-index: 999;"></div>
	<input id="ipt_id" name="id" value="$J{id}" style="width: 174px;" type="hidden" />
	<input id="ipt_statusStr" name="statusStr" value="$J{statusStr}" style="width: 174px;" type="hidden" />
	<table class="table table-bordered table-striped">
		<thead>
			<tr>
				<td colspan="8"><strong>学生信息</strong></td>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td width="100">学校标识码</td>
				<td>2142001636</td>
				<td width="100">学校名称</td>
				<td >钟家村寄宿学校</td>
				<td>班级</td>
				<td>$J{bj}</td>
				<td>籍贯</td>
				<td>
					$J{jg1}省$J{jg2}市
				</td>
			</tr>
			<tr>
				<td width="100"></td>
				<td></td>
				<td width="100"></td>
				<td ></td>
				<td></td>
				<td></td>
				<td>更改为</td>
				<td>
					<input value="$J{jg1N}" id="ipt_jg1N" name="jg1N" style="width:25px;color: red;"  type="text" class="inp"  maxlength="8" />省
					<input value="$J{jg2N}" id="ipt_jg2N" name="jg2N" style="width:25px;color: red;"  type="text" class="inp"  maxlength="8" />市（县）
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
				<td></td>
				<td></td>
				<td>更改为</td>
				<td><input id="ipt_xmN" name="xmN" value="$J{xmN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
				<td></td>
				<td></td>
				<td>更改为</td>
				<td><input id="ipt_sfzhN" name="sfzhN" value="$J{sfzhN}" style="width:95%;color: red;"  type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>户口所在地</td>
				<td>$J{hkszd}</td>
				<td>户口地址</td>
				<td colspan="5">$J{hkdz1}，$J{hkdz2}</td>
			</tr>
			<tr>
				<td>更改为</td>
				<td><input id="ipt_hkszdN" name="hkszdN" value="$J{hkszdN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
				<td>更改为</td>
				<td id="td_hkdz" colspan="5">
					<select id="sel_hkdz1N" name="hkdz1N" class="seleCss" style="width:70px;" onchange="javascript:setChangeColor('td_hkdz');">
						<option value="正式">正式</option>
						<option value="搭户">搭户</option>
					</select>
					，
					<input id="ipt_hkdz2N" name="hkdz2N" value="$J{hkdz2N}" style="width:100px;color: red;"  type="text" class="inp" maxlength="8" />
				</td>
			</tr>
			<tr>
				<td>居住性质</td>
				<td>$J{jzxz}</td>
				<td>家庭地址</td>
				<td colspan="5">$J{jtzz}</td>
			</tr>
			<tr>
				<td>更改为</td>
				<td id="td_jzxzN">
					<select id="sel_jzxzN" name="jzxzN" class="seleCss" style="width:70px;" onchange="javascript:setChangeColor('td_jzxzN');">
						<option value="居住">居住</option>
						<option value="租住">租住</option>
					</select>
				</td>
				<td>更改为</td>
				<td colspan="5"><input id="ipt_jtzzN" name="jtzzN" value="$J{jtzzN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
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
				<td>更改为</td>
				<td><input id="ipt_fuxmN" name="fuxmN" value="$J{fuxmN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
				<td>更改为</td>
				<td><input id="ipt_fudhN" name="fudhN" value="$J{fudhN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="11" /></td>
				<td>更改为</td>
				<td colspan="3"><input id="ipt_fudwN" value="$J{fudwN}" name="fudwN" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
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
				<td>更改为</td>
				<td><input id="ipt_muxmN" name="muxmN" value="$J{muxmN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
				<td>更改为</td>
				<td><input id="ipt_mudhN" name="mudhN" value="$J{mudhN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="11" /></td>
				<td>更改为</td>
				<td colspan="3"><input id="ipt_mudwN" value="$J{mudwN}" name="mudwN" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
			</tr>
			<tr>
				<td colspan="8">注： “家庭地址”只能填一处房产地址。</td>
			</tr>
		</tbody>
	</table>
	<table class="table table-bordered table-striped">
		<tbody>
			<tr> 
				<td>报名类别</td>
				<td colspan="5">
					{@if sylbStr=="TYPEA"}华师教职工子弟
					{@else if sylbStr=="TYPEB"}华师教职工第三代
					{@else if sylbStr=="TYPEC"}其他生源
					{@/if}
				</td>
			</tr>
			<tr> 
				<td>更改为</td>
				<td id="td_sylbN" colspan="5">
					<select id="sel_sylbN" name="sylbN" class="seleCss" style="width:120px;" onchange="javascript:setChangeColor('td_sylbN');">
						<option value="TYPEA">华师教职工子弟</option>
						<option value="TYPEB">华师教职工第三代</option>
						<option value="TYPEC">其他生源</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>出生地</td>
				<td>$J{csd}</td>
				<td>民族</td>
				<td>$J{mz}</td>
				<td>户口性质</td>
				<td>$J{hkxz}</td>
			</tr>
			<tr>
				<td>更改为</td>
				<td><input id="ipt_csdN" name="csdN" value="$J{csdN}" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
				<td>更改为</td>
				<td id="td_mzN"><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_nation_mzN.jsp" /></td>
				<td>更改为</td>
				<td id="td_hkxzN">
					<select id="sel_hkxzN" name="hkxzN" class="seleCss" onchange="javascript:setChangeColor('td_hkxzN');">
						<option value="非农业">非农业</option>
						<option value="农业">农业</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>独生子女</td>
				<td>{@if dzStr=="TRUE"}是{@else}否{@/if}</td>
				<td>残疾类型</td>
				<td colspan="3">
					{@if slStr=="FALSE"&&tlStr=="FALSE"&&zlStr=="FALSE"}无残疾
					{@else}
						{@if slStr=="TRUE"}视力残疾{@/if}
						{@if tlStr=="TRUE"}听力残疾{@/if}
						{@if zlStr=="TRUE"}智力残疾{@/if}
					{@/if}
				</td>
			</tr>
			<tr>
				<td>更改为</td>
				<td id="td_dzN">
					<select id="sel_dzN" name="dzN" class="seleCss" onchange="javascript:setChangeColor('td_dzN');">
						<option value="TRUE">是</option>
						<option value="FALSE">否</option>
					</select>
				</td>
				<td>更改为</td>
				<td id="td_disability" colspan="3">
					<select id="sel_disability" name="disability" class="seleCss" style="width:80px;" onchange="javascript:setChangeColor('td_disability');">
						<option value="无残疾">无残疾</option>
						<option value="视力残疾">视力残疾</option>
						<option value="听力残疾">听力残疾</option>
						<option value="智力残疾">智力残疾</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>任何职务</td>
				<td colspan="5"><input id="ipt_zw" name="zw" value="$J{zw}" placeholder="8字内，如：班长、大队委等" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
			</tr>
			<tr>
				<td>特长</td>
				<td colspan="5"><input id="ipt_tc" name="tc" value="$J{tc}" placeholder="8字内，如：钢琴、篮球等" style="width:95%;color: red;"  type="text" class="inp" maxlength="8" /></td>
			</tr>
			<tr>
				<td>三好生</td>
				<td colspan="5"><input id="ipt_shs" name="shs" value="$J{shs}" placeholder="15字内，如：2015区级、2014校级等" style="width:95%;color: red;"  type="text" class="inp" maxlength="15" /></td>
			</tr>
			<tr>
				<td>所获奖励</td>
				<td colspan="5"><input id="ipt_jl" name="jl" value="$J{jl}" placeholder="15字内，如：2014区艺术小人才一等（填写区级以上政府或教育行政部门颁发的奖励，不填写社会机构颁发的奖励）" style="width:95%;color: red;"  type="text" class="inp" maxlength="15" /></td>
			</tr>
		</tbody>
	</table>
	</form>