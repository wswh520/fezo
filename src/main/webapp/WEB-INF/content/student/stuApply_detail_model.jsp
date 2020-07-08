<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<div class="left"><strong style="color:red;font-size: 16px;">温馨提示：2019年6月8-12日为华师二代三代子弟及洪山区划片生源填报时间，其他未尽事宜请关注附小网站通知。
	<br />填表须知：请您及时将《入学登记表》《学籍表》《预防接种登记表》三表填完、提交(提交之后信息将无法修改)、再次提交。直到《入学登记表》状态为：审核中，其他两表状态为：已提交。请您在2019年6月15（桂子山校区）、19日（南湖校区）、20日（南湖校区）到指定校区进行现场审查。
</strong></div>
<div class="clear"></div>
<div class="base_title">
	<em></em>
	<strong>$J{year}年钟家村小学新生入学登记表</strong>
	<strong style="float:right;color:red">
		申请状态：{@if id==0}未填表
		{@else if statusStr=="SUBMIT_NONE"}未提交
		{@else if statusStr=="SUBMIT_ONCE"}{@if locked}审核中{@else}未再次提交{@/if}
		{@else if statusStr=="SUBMIT_TWICE"}审核中
		{@else if statusStr=="REVIEW_PASS"}拟录取
		{@else if statusStr=="REVIEW_REFUSE"}不予录取
		{@else if statusStr=="REVIEW_WAITING"}待录取
		{@/if}
	</strong>
</div>
<div id="div_stuApply_iptHint" style="display:none;">
	<p><strong style="color:red;">尊敬的家长：您好！由于您的孩子是$J{year}年报名尚未被录取的，如果您想为孩子报名$J{currentYear}级，请点击“申请转$J{currentYear}级”按钮。</strong></p>
	<p><strong style="color:red;display:none;">注意：如果孩子的身份证号已经重新注册，请前往学校提交更正账号申请。若账户没有更正或者账户更正不成功，登记信息时会提示“身份证件号已被登记”字样！</strong></p>
</div>
<form id="form_stuApply" class="bs-docs-example">
	<input id="ipt_statusStr" name="statusStr" value="$J{statusStr}" style="width: 174px;" type="hidden" />
	<div id="div_stuApply_cover" style="background-color: rgba(255, 255, 255, 0);position: absolute;z-index: 999;"></div>
	<input id="ipt_id" name="id" value="$J{id}" style="width: 174px;" type="hidden" />
	<table class="table table-bordered table-striped">
		<thead>
		<tr>
			<th width="5%">编号</th>
			<th width="15%">标题</th>
			<th width="30%">填写内容</th>
			<th width="5%">编号</th>
			<th width="15%">标题</th>
			<th width="30%">填写内容</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>1</td>
			<td>报名类别</td>
			<td>
				{@if id==0}
				<ul>
					<li>
						请选择报名类别：
					</li>
					<li id="bmType" style="line-height:48px">
						<%--						<label><input onclick="javascript:changeStuApplyType('TYPEA');" name="type" {@if typeStr=="TYPEA"}checked="checked"{@/if} type="radio" value="TYPEA" />A华师第二代生源</label>--%>
						<%--						<label><input onclick="javascript:changeStuApplyType('TYPEB');" name="type" {@if typeStr=="TYPEB"}checked="checked"{@/if} type="radio" value="TYPEB" />B华师第三代生源</label>--%>
						<%--						<label><input onclick="javascript:changeStuApplyType('TYPEC');" name="type" {@if typeStr=="TYPEC"}checked="checked"{@/if} type="radio" value="TYPEC" />C社会对口生源</label>--%>
						<label><input name="other54" {@if typeStr=="TYPEA"}checked="checked"{@/if} type="radio" value="外教班" />外教班</label>
						<label><input name="other54" {@if typeStr=="TYPEB"}checked="checked"{@/if} type="radio" value="普通班" />普通班</label>
					</li>
<%--					<li>--%>
<%--						请选择报名校区：--%>
<%--					</li>--%>
<%--					<li id="bmSchool" style="line-height:48px">--%>
<%--						<label><input name="other51" {@if other51=="南湖校区"}checked="checked"{@/if} type="radio" value="南湖校区" />南湖校区</label>--%>
<%--						<label><input name="other51" {@if other51=="桂子山校区"}checked="checked"{@/if} type="radio" value="桂子山校区" />桂子山校区</label>--%>
<%--					</li>--%>
				</ul>
				{@else}
				<ul>
					<li>
						<input id="other54" name="other54" value="$J{other54}" type="hidden" class="inp" />
						$J{other54}
					</li>
				</ul>
				{@/if}
			</td>
			<td>2</td>
			<td>网上报名号</td>
			<td>{@if noStr==null||noStr==""}提示：报名号由系统自动生成，请家长牢记！{@else}$J{noStr}{@/if}</td>
		</tr>
		{@if id==0}
		<tr id="doCho">
			<td colspan="6" style="text-align: right;"><strong style="margin-right: 50px;">（请根据实际情况慎重选择报名类型，确定之后将不能修改）</strong><button onclick="javascript:doChoiceSignUpType();" class="Btn" type="button" style="float: right;"> 确 定 </button></td>
		</tr>
		{@/if}
		<tr id="stuInfo" class="stuInfo">
			<td colspan="6"><strong>学生信息</strong></td>
		</tr>
		<tr class="stuInfo">
			<td>3</td>
			<td>姓名\中文名</td>
			<td><input id="ipt_name" name="name" value="$J{name}" style="width: 174px;" type="text" class="inp" /></td>
			<td>4</td>
			<td>出生日期</td>
			<td>
<%--				<input id="ipt_dateOfBirth" name="dateOfBirth" value="$J{dateOfBirthStr}" style="cursor:pointer;width:146px;" readonly="readonly" type="text" class="inp Wdate" onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy年MM月dd日',alwaysUseStartDate:true})" maxlength="10" />--%>
				<input id="ipt_dateOfBirth" name="dateOfBirth" value="$J{dateOfBirthStr}" style="cursor:pointer;width:146px;" readonly="readonly" type="text" class="inp Wdate"
			<%--						   onFocus="WdatePicker({startDate:'%y-%M-%d',dateFmt:'yyyy年MM月dd日',alwaysUseStartDate:true})"--%>
					   onFocus="WdatePicker({skin:'whyGreen',dateFmt:'yyyy年MM月dd日',minDate:'2013-09-02',maxDate:'2014-08-31',alwaysUseStartDate:true})"
					   maxlength="10" />
			</td>
		</tr>
		<tr class="stuInfo">
			<td>5</td>
			<td>性别</td>
			<td>
				<label><input name="sex" {@if sexStr=="TRUE"}checked="checked"{@/if} type="radio" value="TRUE" />男</label>
				<label><input name="sex" {@if sexStr=="FALSE"}checked="checked"{@/if} type="radio" value="FALSE" />女</label>
			</td>
			<td>6</td>
			<td>民族</td>
			<td><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_nation.jsp" /></td>
		</tr>
		<tr class="stuInfo">
			<td>7</td>
			<td>出生地</td>
			<td><input id="ipt_addressOfBirth" name="addressOfBirth" value="$J{addressOfBirth}" style="width: 95%;" type="text" class="inp" placeholder="如：湖北省武汉市" /></td>
			<td>8</td>
			<td>籍贯</td>
			<td><input id="ipt_birthplace" name="birthplace" value="$J{birthplace}" style="width: 174px;" type="text" class="inp" placeholder="如：湖北省阳新县" /></td>
		</tr>
		<tr class="stuInfo">
			<td>9</td>
			<td>国籍/地区</td>
			<td><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_citizenship.jsp" /></td>
			<td>10</td>
			<td>港澳台侨外</td>
			<td><jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_gatqw.jsp" /></td>
		</tr>
		<tr class="stuInfo">
			<td>11</td>
			<td>健康状况</td>
			<td colspan="4">
				<select id="sel_jkzt" name="jkzt" class="seleCss" style="width: 100px;">
					<option value="健康或良好">健康或良好</option>
					<option value="一般或较弱">一般或较弱</option>
					<option value="有慢性病">有慢性病</option>
					<option value="有生理缺陷">有生理缺陷</option>
					<option value="残疾">残疾</option>
				</select>
			</td>
		</tr>
		<tr class="stuInfo">
			<td>12</td>
			<td>身份证件类型</td>
			<td>
				<select id="sel_cardType" name="cardType" class="seleCss" style="width: 100px;">
					<option value="CARD_TYPE1">居民身份证</option>
					<option value="CARD_TYPE2">香港特区护照、身份证明</option>
					<option value="CARD_TYPE3">澳门特区护照/身份证明</option>
					<option value="CARD_TYPE4">台湾居民来往大陆通行证</option>
					<option value="CARD_TYPE5">境外永久居住证</option>
					<option value="CARD_TYPE6">护照</option>
					<option value="CARD_TYPE6">外籍</option>
					<option value="CARD_TYPE7">其他</option>
				</select>
			</td>
			<td>13</td>
			<td>身份证件号</td>
			<td><input id="ipt_cardNo" name="cardNo" value="$J{cardNo}" style="width: 174px;" type="text" class="inp" /></td>
		</tr>
		<tr class="stuInfo">
			<td>14</td>
			<td>户口所在地</td>
			<td>
				<jsp:include flush="true" page="/WEB-INF/content/student/sel/sel_citizenship_other35.jsp" />
				<input id="ipt_other1" name="other1" value="$J{other1}" style="width: 60px;" type="text" class="inp" />
				<label>省 </label>
				<input id="ipt_other2" name="other2" value="$J{other2}" style="width: 60px;" type="text" class="inp" />
				<label>市</label>
				<input id="ipt_other3" name="other3" value="$J{other3}" style="width: 60px;" type="text" class="inp" />
				<label>区(县) </label>
			</td>
			<td>15</td>
			<td>户口性质</td>
			<td>
				<label><input name="other4" {@if other4=="非农业户口"}checked="checked"{@/if} type="radio" value="非农业户口" />非农业户口</label>
				<label><input name="other4" {@if other4=="农业户口"}checked="checked"{@/if} type="radio" value="农业户口" />农业户口</label>
				<!-- <label><input name="other4" {@if other4=="其它"}checked="checked"{@/if} type="radio" value="其它" />其它</label> -->
			</td>
		</tr>
		<tr class="stuInfo">
			<td>16</td>
			<td>户主姓名</td>
			<td><input id="ipt_other5" name="other5" value="$J{other5}" style="width: 174px;" type="text" class="inp" /></td>
			<td>17</td>
			<td>学生与户主关系</td>
			<td>
				<select id="sel_other6" name="other6" class="seleCss" style="width: 100px;">
					<option value="户主本人">户主本人</option>
					<option value="儿子">儿子</option>
					<option value="女儿">女儿</option>
					<option value="孙子">孙子</option>
					<option value="孙女">孙女</option>
					<option value="外孙子">外孙子</option>
					<option value="外孙女">外孙女</option>
					<option value="非亲属">非亲属</option>
					<option value="其他">其他</option>
				</select>
			</td>
		</tr>
		<tr class="stuInfo">
			<td>18</td>
			<td>户口详细地址</td>
			<td colspan="4"><input id="ipt_other7" name="other7" value="$J{other7}" style="width: 98%;" type="text" class="inp" placeholder="按照户口簿如实填写。" /></td>
		</tr>
		<tr class="stuInfo" id="tr_nowAdr">
			<td>19</td>
			<td>现住址</td>
			<td id="td_nowadr">
				<label>武汉市 </label>
				<input id="ipt_other8" name="other8" value="$J{other8}" style="width: 60px;" type="text" class="inp" />
				<label>区</label>
				<input id="ipt_other9" name="other9" value="$J{other9}" style="width: 115px;" type="text" class="inp" />
				<label>小区</label>
			</td>
			<td name="td_type">20</td>
			<td name="td_type">现住址类型</td>
			<td name="td_type">
				<label><input name="other10" {@if other10=="产权"}checked="checked"{@/if} type="radio" value="产权" />产权</label>
				<label><input name="other10" {@if other10=="租住"}checked="checked"{@/if} type="radio" value="租住" />租住</label>
			</td>
		</tr>
		<tr class="stuInfo" id="tr_adr_email">
			<td>21</td>
			<td>现住址是否与户口地址一致</td>
			<td>
				<label><input name="other11" {@if other11=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other11" {@if other11=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
			<td>22</td>
			<td>邮箱地址</td>
			<td><input id="ipt_other12" name="other12" value="$J{other12}" style="width: 98%;" type="text" class="inp" /></td>
		</tr>
		<tr class="stuInfo" id="tr_zn">
			<td class="resort">22</td>
			<%--<td name="td_dszn">是否独生子女</td>
            <td name="td_dszn">
                <label><input name="other37" {@if other37=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
                <label><input name="other37" {@if other37=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
            </td>--%>
			<td name="td_znqk">子女情况</td>
			<td name="td_znqk">
				<label><input name="other37" {@if other37=="一孩"}checked="checked"{@/if} type="radio" value="一孩" />一孩</label>
				<label><input name="other37" {@if other37=="二孩"}checked="checked"{@/if} type="radio" value="二孩" />二孩</label>
				<label><input name="other37" {@if other37=="三孩及以上"}checked="checked"{@/if} type="radio" value="三孩及以上" />三孩及以上</label>
			</td>
			<td class="resort">23</td>
			<td>是否上过幼儿园</td>
			<td>
				<label><input name="other38" {@if other38=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other38" {@if other38=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
		</tr>
		<tr class="stuInfo" id="tr_jcwgzn">
			<td name="td_jcwgzn" class="resort">24</td>
			<td name="td_jcwgzn">是否进城务工人员随迁子女</td>
			<td name="td_jcwgzn">
				<label><input name="other39" {@if other39=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other39" {@if other39=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
			<td class="resort">25</td>
			<td>残疾类型</td>
			<td name="td_cjlx">
				<label><input name="other40" {@if other40=="有"}checked="checked"{@/if} type="radio" value="有" />有</label>
				<label><input name="other40" {@if other40=="无"}checked="checked"{@/if} type="radio" value="无" />无</label>
				<label><input id="ipt_other42" placeholder="如有残疾，请注明残疾类型"  style="width: 60%;" type="text" class="inp" /></label>
			</td>
		</tr>
		<tr class="stuInfo" id="tr_dqge">
			<td class="resort">26</td>
			<td>是否单亲</td>
			<td>
				<label><input name="other48" {@if other48=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other48" {@if other48=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
			<td class="resort">27</td>
			<td>是否孤儿</td>
			<td>
				<label><input name="other49" {@if other49=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other49" {@if other49=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
		</tr>
		<tr class="stuInfo" id="tr_lsyf">
			<td class="resort">28</td>
			<td>是否烈士或优抚子女</td>
			<td colspan="4">
				<label><input name="other50" {@if other50=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input name="other50" {@if other50=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
			<%--<td class="resort">29</td>
            <td>报名校区</td>
            <td id="td_twoxq">
                <label><input name="other51" {@if other51=="南湖校区"}checked="checked"{@/if} type="radio" value="南湖校区" />南湖校区</label>
                <label><input name="other51" {@if other51=="桂子山校区"}checked="checked"{@/if} type="radio" value="桂子山校区" />桂子山校区</label>
            </td>
            <td id="td_onexq">
                <label><input name="other51" {@if other51=="南湖校区"}checked="checked"{@/if} type="radio" value="南湖校区" />南湖校区</label>
            </td>--%>
		</tr>
		</tbody>
	</table>
	<table class="table table-bordered table-striped stuInfo">
		<tbody>
		<tr>
			<th width="2%" rowspan="3">监护人信息</th>
			<th width="5%">关系</th>
			<th width="10%">姓名</th>
			<th id="th_comp_dept" width="30%">工作单位(院系或部门)</th>
			<th width="10%">职务</th>
			<th width="15%">联系电话</th>
			<th width="8%">最高学历</th>
			<th class="xyykt_phon" width="15%">校园一卡通号码</th>
		</tr>
		<tr>
			<td>父亲</td>
			<td><input id="ipt_other13" name="other13" value="$J{other13}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other14" name="other14" value="$J{other14}" style="width: 95%;" type="text" class="inp" /></td>
			<td><input id="ipt_other15" name="other15" value="$J{other15}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other16" name="other16" value="$J{other16}" style="width: 95%;" type="text" class="inp" /></td>
			<td><input id="ipt_other52" name="other52" value="$J{other52}" style="width: 90%;" type="text" class="inp" /></td>
			<td class="xyykt_phon"><input id="ipt_other17" name="other17" value="$J{other17}" style="width: 95%;" type="text" class="inp" /></td>
		</tr>
		<tr>
			<td>母亲</td>
			<td><input id="ipt_other18" name="other18" value="$J{other18}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other19" name="other19" value="$J{other19}" style="width: 95%;" type="text" class="inp" /></td>
			<td><input id="ipt_other20" name="other20" value="$J{other20}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other21" name="other21" value="$J{other21}" style="width: 95%;" type="text" class="inp" /></td>
			<td><input id="ipt_other53" name="other53" value="$J{other53}" style="width: 90%;" type="text" class="inp" /></td>
			<td class="xyykt_phon"><input id="ipt_other22" name="other22" value="$J{other22}" style="width: 95%;" type="text" class="inp" /></td>
		</tr>
		</tbody>
	</table>
	<table id="table_b" style="display:none;" class="table table-bordered table-striped stuInfo">
		<tbody>
		<tr>
			<td colspan="6"><strong>“三代生源”华师亲属信息（三代直系血亲）</strong></td>
		</tr>
		<tr>
			<th width="10%">关系</th>
			<th width="10%">姓名</th>
			<th width="15%">联系电话</th>
			<th width="25%">单位</th>
			<th width="10%">单位领导姓名</th>
			<th width="15%">单位领导电话</th>
			<th width="15%">校园一卡通号码</th>
		</tr>
		<tr>
			<td>
				<select id="sel_other23" name="other23" class="seleCss" style="width:100px;">
					<option value="祖父">祖父</option>
					<option value="祖母">祖母</option>
					<option value="外祖父">外祖父</option>
					<option value="外祖母">外祖母</option>
				</select>
			</td>
			<td><input name="other24" value="$J{other24}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input name="other25" value="$J{other25}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input name="other26" value="$J{other26}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other27" name="other27" value="$J{other27}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other28" name="other28" value="$J{other28}" style="width: 90%;" type="text" class="inp" /></td>
			<td><input id="ipt_other41" name="other41" value="$J{other41}" style="width: 90%;" type="text" class="inp" /></td>
		</tr>
		</tbody>
	</table>
	<%--<table id="table_c" style="display:none;" class="table table-bordered table-striped">
		<tbody>
			<tr>
				<td colspan="4"><strong>推荐人情况（属华师子弟及孙辈的不填）</strong></td>
			</tr>
			<tr>
				<th width="10%">推荐人姓名</th>
				<th width="35%">推荐人单位</th>
				<th width="15%">推荐人职务</th>
				<th width="30%">联系电话</th>
			</tr>
			<tr>
				<td><input name="other29" value="$J{other29}" style="width: 90%;" type="text" class="inp" /></td>
				<td><input name="other30" value="$J{other30}" style="width: 90%;" type="text" class="inp" /></td>
				<td><input name="other31" value="$J{other31}" style="width: 90%;" type="text" class="inp" /></td>
				<td><input name="other32" value="$J{other32}" style="width: 90%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>推荐人推荐语</td>
				<td colspan="3"><input name="other33" value="$J{other33}" style="width: 97%;" type="text" class="inp" /></td>
			</tr>
			<tr>
				<td>是否洪山区教育局协议生源</td>
				<td colspan="3">
					<label><input name="other34" {@if other34=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
					<label><input name="other34" {@if other34=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
				</td>
			</tr>
		</tbody>
	</table> --%>
	<table class="table table-bordered table-striped stuInfo">
		<tbody>
		<tr>
			<th id="th_note_name" width="10%">备注</th>
			<td width="90%"><input name="other36" placeholder="请说明相关情况：" value="$J{other36}" style="width: 98%;" type="text" class="inp" /></td>
		</tr>
		</tbody>
	</table>
	<table class="table table-bordered table-striped stuInfo">
		<tbody>
		<tr style="display: none">
			<td colspan="2">
				是否在洪山区新生入学服务平台上报名？
				<label><input onclick="javascript:stuApplyOther43Click(true);" name="other43" {@if other43=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input onclick="javascript:stuApplyOther43Click(false);" name="other43" {@if other43=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
		</tr>
		<tr id="tr_other44" style="display: none" style="{@if other43=='是'}display:none;{@/if}">
			<td colspan="2">
				是否在其他地区新生服务平台上报名？
				<label><input onclick="javascript:stuApplyOther44Click(true);" name="other44" {@if other44=="是"}checked="checked"{@/if} type="radio" value="是" />是</label>
				<label><input onclick="javascript:stuApplyOther44Click(false);" name="other44" {@if other44=="否"}checked="checked"{@/if} type="radio" value="否" />否</label>
			</td>
		</tr>
		<tr id="tr_other45" style="{@if other43=='是'||other44=='否'}display:none;{@/if}">
			<td width="10%">报名平台</td>
			<td>
				<input style="display: none" id="ipt_other45" name="other45" value="$J{other45}" style="width: 60px;" type="text" class="inp" />
				<label style="display: none">省 </label>
				<input style="display: none" id="ipt_other46" name="other46" value="$J{other46}" style="width: 60px;" type="text" class="inp" />
				<label style="display: none">市</label>
				<input id="ipt_other47" name="other47" value="$J{other47}" style="width: 98%;" type="text" class="inp" placeholder="例如：洪山区新生入学服务平台" />
				<label style="display: none">区(县) </label>
			</td>
		</tr>
		</tbody>
	</table>
	<span id="ele_statusStr_$J{id}" style="display:none;">$J{statusStr}</span>
	<span id="ele_reviewer_$J{id}" style="display:none;">$J{reviewer}</span>
	<span id="ele_dateOfReviewStr_$J{id}" style="display:none;">$J{dateOfReviewStr}</span>
	<span id="ele_note_$J{id}" style="display:none;">$J{note}</span>
	<span id="ele_message_$J{id}" style="display:none;">$J{message}</span>
	{@if showReview}
	<table class="table table-bordered table-striped">
		<thead>
		<tr>
			<th width="5%">编号</th>
			<th width="15%">标题</th>
			<th width="30%">填写内容</th>
			<th width="5%">编号</th>
			<th width="15%">标题</th>
			<th width="30%">填写内容</th>
		</tr>
		</thead>
		<tbody>
		<tr>
			<td>22</td>
			<td>审批人</td>
			<td><input name="reviewer" value="$J{reviewer}" style="width: 174px;" type="text" class="inp" /></td>
			<td>23</td>
			<td>审批日期</td>
			<td><input name="dateOfReview" value="$J{dateOfReviewStr}" style="width: 174px;" type="text" class="inp" /></td>
		</tr>
		<tr>
			<td>24</td>
			<td>备注</td>
			<td colspan="4"><input name="note" value="$J{note}" style="width: 98%;" type="text" class="inp" /></td>
		</tr>
		</tbody>
	</table>
	{@/if}
</form>