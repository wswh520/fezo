<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_graStuInfoDisplay" type="text/template">
<div id="div_graStuInfo_prompt" class="container-1" {@if statusStr!="SUBMIT_NONE"}style="display:none;"{@/if}>
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">信息核对</li>
		</ul>
	</div>
	<div class="base_title">
		<em></em>
		<strong>温馨提示</strong>
	</div>
	<div  class="bs-docs-example" style=" font-size: 17px; ">
		<p>1、请您拿出“学生户口”原件进行核对，如有错误，请填写在更正栏。</p>
		<p>部分栏目（如户口所在地、户口地址、家庭地址等）修改后，请于2015年4月28日到校提交户口、房产证原件及更正申请（说明更正项目及原因，父母、班主任签名），等待学校审核后，系统才能修改。</p>
		<p>其他栏目由班主任审核。</p>
		<p>2、特别需要说明的是，因为表格栏目较多，上级教育部门要求每个栏目的字数不得超出8个汉字字符。所以在“户口地址”、“家庭地址”、“工作单位”这几栏使用了简称，以保证不超出8个汉字字符。请各位家长在核对时特别注意一下！</p>
		<p>（1）户口地址一般填写为××路××号（洪山区内），洪山区范围外填写的是省名（湖北省以外的）、市名（湖北省内武汉市外）、区名（洪山区外武汉市内）中的一项。</p>
		<p>（2）家庭地址填写的是小区名（洪山区内），洪山区范围外填写的是区名，此栏要求填写为武汉市内的地址。</p>
		<p>（3）居住性质说明：“租住”指租别人房子住，“居住”指自己的房子。居住性质是用来说明“家庭地址”的。</p>
		<p>（4）工作单位因为字数限制（8个汉字字符以内），全部使用的是简称。</p>
		<p>3、个人信息核对完成后，请单击表格下方的“确认无误”按钮，提交核对表。</p>
		<br />
		<p><label><input id="ipt_confirm" type="checkbox" />我已经认真阅读以上“温馨提示”，明白核对要求，现在可以开始核对毕业生基本信息了。</label></p>
		<br />
	</div>
	<div class="page_navi" style="padding-bottom: 10px;text-align: center;">
		<button onclick="javascritp:doShowGraStuInfoDisplay();" class="Btn" type="button">开始核对</button>
	</div>
</div>
<div id="div_graStuInfoDisplay" class="container-1" {@if statusStr=="SUBMIT_NONE"}style="display:none;"{@/if}>
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">信息核对</li>
		</ul>
	</div>
	<div class="right">
		<button id="btn_refresh" onclick="javascript:doLoadGraStuInfo();" class="Btn" type="button"><i class="icon icon-Repeat"></i>刷新</button>
	</div>
	<div class="left">
{@if !locked}
	{@if statusStr=="SUBMIT_NONE"||statusStr=="REVIEW_REFUSE"}
		<button id="btn_showModify" onclick="javascritp:showGraStuInfoModify();" class="Btn" type="button"><i class="icon icon-Modify"></i>开始核对</button>
		<button id="btn_submit" onclick="javascritp:submitGraStuInfo();" class="Btn" type="button">核对完毕，确认无误</button>
	{@/if}
		<button id="btn_doModify" onclick="javascritp:doSaveGraStuInfo(false,student_verifyGraStuInfoUrl);" style="display:none;" class="Btn" type="button">核对完毕，确认无误</button>
		<button id="btn_cancelModify" onclick="javascritp:doCancelGraStuInfoModify();" style="display:none;" class="Btn" type="button">取消</button>
{@/if}
{@if statusStr=="SUBMIT_TWICE"}
		<button onclick="javascript:doLoadGraStuInfo_print('');" class="Btn" type="button">预览打印</button>
{@/if}
	</div>
	<div class="clear"></div>
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfo_detail_model.jsp" />
	<div id="div_stuGraInfoAtt" style="display:none;"></div>
	<div class="page_navi right" style="padding-bottom: 10px;">
		<button id="btn_doModify_2" onclick="javascritp:doSaveGraStuInfo(false,student_verifyGraStuInfoUrl);" style="display:none;" class="Btn" type="button">核对完毕，确认无误</button>
		<button id="btn_cancelModify_2" onclick="javascritp:doCancelGraStuInfoModify();" style="display:none;" class="Btn" type="button">取消</button>
	</div>
	<div  class="bs-docs-example" style=" font-size: 17px; ">
		<p>特别说明：</p>
		<p>请参考以下各栏目的具体要求，每一栏不能超过8个汉字的字符。</p>
		<p>一、籍贯请按照户口填写</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填：**省**市（县）</p>
		<p>二、户口所在地</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、洪山区内填： 洪山区</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、其他（市内、省内、外省）</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;市内填区名：如 武昌区、汉阳区……</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省内填市名：如 宜昌市、黄冈市……</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;省外填省名：如 湖南省、广西省……</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、居住证填：居住证</p>
		<p>三、户口地址请按照户口填写：</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、“正式”+户口地址</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、“搭户”+户口地址（同上）</p>
		<p>四、居住性质：（针对家庭地址而言）</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1、租住：指租别人的房子填 租住</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、居住：自己的房子填 居住</p>
		<p>五、家庭地址</p>
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;填写小区名</p>
	</div>
</div>
</script>

<script id="model_graStuInfo_print" type="text/template">
<div id="div_graStuInfo_guide" class="container-1">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">预览打印</li>
		</ul>
	</div>
	<div class="base_title">
		<em></em>
		<strong>操作指南</strong>
	</div>
	<div  class="bs-docs-example" style=" font-size: 17px; ">
		<p>操作流程建议如下：</p>
		<p>第一步：复印户口，上方是学生户口的首页（有户口的住址），下方是学生户口页。注意复印的方向应与背面表格的方向相同。复印件必须清晰，能清楚地看见户口地址和毕业生户口的相关信息。</p>
		<br />
		<p>第二步：将户口复印页反面空白的一面朝上，打印《毕业生信息核对表》。表格打印后，父母双方签名，注意表格右下方的时间是否打印正确。《毕业生信息核对表》上不得有任何涂改痕迹。</p>
		<br />
		<p>第三步：户主非父母及毕业生本人，需复印父母户口首页及信息页（与学生户口复印件要求一致），粘贴到学生户口复印页上方。户主是父母及毕业生本人的可省略第三步。</p>
		<br />
		<p>第四步：将家庭地址的房产证复印件粘贴到表格的反面。</p>
		<br />
		<p>请按照以上要求复印和打印两份，一份上交洪山区教育局，一份学校留存备查。</p>
		<br />
		<p>特别说明：</p>
		<p>请沿着纸张上方边缘粘贴，注意对齐纸张，请不要用订书机进行装订。由于本次打印的《毕业生信息核对表》是要交洪山区教育局存档的，如果不合要求请重做，请各位家长谨慎操作！</p>
		<br />
		<p><label><input id="ipt_confirm" type="checkbox" />我已阅读《操作指南》，明白了交表要求。我会在规定的时间内将户口、房产证件原件（简称“两证”）交学校及相关部门审核认定。</label></p>
		<br />
	</div>
	<div class="page_navi" style="padding-bottom: 10px;text-align: center;">
		<button onclick="javascritp:doShowGraStuInfoPrint();" class="Btn" type="button">进入打印页面</button>
	</div>
</div>
<div id="div_graStuInfo_print" class="container-1" style="display:none;">
	<div id="location">
		<ul>
			<li class="right"></li>
			<li class="left"><a href="${ctx}/">首页</a></li>
			<li>基本功能</li>
			<li class="last">预览打印</li>
		</ul>
	</div>
	<div class="left">
{@if statusStr=="SUBMIT_TWICE"}
		<button id="btn_print_1" onclick="javascript:showGraStuInfoDownloadConfirm('$J{id}');" class="Btn" type="button">下载后打印</button>
		<button id="btn_print_2" onclick="javascript:showGraStuInfoPrintConfirm('$J{id}');" class="Btn" type="button">在线打印</button>
		<!--<button id="btn_print_3" onclick="javascript:{window.open('${ctx}/resource/华师附小打印帮助.pdf');};" class="Btn" type="button">打印须知</button>-->
{@/if}
	</div>
	<div class="clear"></div>
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfo_print_model.jsp" />
	<div class="bs-docs-example" style=" font-size: 17px; ">
		<br />
		<p>学生类别（请在□中画“√”）</p>
		<p>1、华师教职工子弟$J{sylb1}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2、华师教职工第三代$J{sylb2}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;3、其他生源$J{sylb3}</p>
		<br />
		<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;《家长诚信承诺书》&nbsp;&nbsp;&nbsp;我郑重承诺：以上信息真实有效。如有虚假，本人愿意承担相应后果。如果我不在规定时间将户口、房产证件原件（简称“两证”）交学校及相关部门审核认定，视同无证参加“小升初”毕业分配。</p>
		<p>承诺人（父亲）：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;承诺人（母亲）：<span style="float:right;">时间：$J{year}年$J{month}月$J{day}日</span></p>
		<br />
		<image src="${ctx}/resource/doc/gra_print_intro.png?v=160422" />
	</div>
</div>
</script>

<script id="model_graStuInfoHistoryList" type="text/template">
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
				<a href="javascript:loadGraStuInfoHistoryInfo('$J{ele.id}');">详情</a>
			</td>
		</tr>
		{@/each}
	{@/if}
		</tbody>
	</table>
</script>
<script id="model_graStuHistoryInfo" type="text/template">
	<div class="base_title">
		<strong style="color:red">$J{title}</strong>
	</div>
	<jsp:include flush="true" page="/WEB-INF/content/student/graStuInfo_detail_model.jsp" />
</script>

<script id="model_stuGraInfoAtt" type="text/template">
<div class="base_title">
	<em></em>
	<strong>图片附件</strong>
</div>
{@if !locked}
<form id="form_uploadStuGraInfoAtt" method="post" enctype="multipart/form-data" class="unfold_left">
	<div class="item" style="padding-top: 15px;">
		<label class="sele-label ">图片名：</label>
		<input id="ipt_prefix" type="text" class="inp"  placeholder="如：户口首页"  name="prefix" />
		<label class="sele-label" >请选择图片：</label>
		<span id="span_file" class="sele-label">
			<input id="ipt_file" type="file" name="file" accept="image/*"/ style="width: 174px;padding-top: 5px;" />
		</span>
		<span>
			<input id="btn_upload" class="Btn" type="button" value="上传" onclick="javascritp:doUploadStuGraInfoAtt();" />
			<input id="btn_uploading" style="display:none;" class="Btn" type="button" value="正在上传..." onclick="javascritp:void(0);" />
		</span>
	</div>
</form>
{@/if}
<div class="bs-docs-example">
	<table class="table table-bordered table-striped"  >
		<thead><tr>
			<th width="25%">图片名</th>
			<th >&nbsp;</th>
		</tr></thead>
		<tbody id="tbody_stuGraInfoAttList">
			<tr id="tr_noneStuGraInfoAtt" style="{@if data.length>0}display:none;{@/if}">
				<td colspan="2">暂无记录！</td>
			</tr>
{@if data.length>0}
	{@each data as att }
			<tr id="tr_stuGraInfoAtt_$J{att.id}">
				<td id="td_name_$J{att.id}">$J{att.name}</td>
				<td>
					<a href="javascript:doDownloadStuGraInfoAtt('$J{att.id}','$J{att.hash}');">下载</a>
					{@if !locked}&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:doDelStuGraInfoAtt('$J{att.id}');">删除</a>{@/if}
				</td>
			</tr>
	{@/each}
{@/if}
		</tbody>
	</table>
</div>
</script>
<script id="model_stuGraInfoAttEle" type="text/template">
	<tr id="tr_stuGraInfoAtt_$J{data.id}">
		<td id="td_name_$J{data.id}">$J{data.name}</td>
		<td>
			<a href="javascript:doDownloadStuGraInfoAtt('$J{data.id}','$J{data.hash}');">下载</a>
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:doDelStuGraInfoAtt('$J{data.id}');">删除</a>
		</td>
	</tr>
</script>