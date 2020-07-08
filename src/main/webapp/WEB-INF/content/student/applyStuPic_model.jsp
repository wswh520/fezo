<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<script id="model_stuApplyAtt" type="text/template">
<div class="item" style="color:red;margin:15px 0px -24px 0px;">
	注：图片每张不得超过1M，上传比较缓慢的情况下请记得等一张上传完后再上传下一张。
</div>
<input id="ipt_stuApply_type" value="$J{type}" type="hidden" />
{@each data as it }
<div id="apply_otypeInfo_photo_$J{it.otype}">
<div class="base_title">
	<em></em>
	<strong>$J{it.otypename}</strong>
</div>
<div id="apply_modify_photo_$J{it.otype}" class="item" style="margin-left: 50px;height:auto;">
{@if !it.id }

{@if userType=='PATRIARCH' && statusStr!='REVIEW_PASS'}
<form id="form_import_$J{it.otype}" method="post" name="form_pic"  enctype="multipart/form-data" target="formFrame" class="unfold_left">
	<input id="att_otype_$J{it.otype}" name="otype" value="$J{it.otypeindex}" type="hidden" />
	<input id="att_stuApply_type" value="$J{type}" type="hidden" />
	<!--<input id="att_oid_$J{it.otype}" name="oid" value="$J{it.oid}" type="hidden" />-->
	<div class="item" style="padding-top: 15px;">
		<label class="sele-label ">请重命名图片：</label>
		<input id="ipt_prefix_$J{it.otype}" type="text" class="inp"  placeholder="图片名称"  name="prefix"/>
		<label class="sele-label" >请选择图片文件：</label>
		<span id="span_import_pic" class="sele-label">
			<input id="input_pic_$J{it.otype}" type="file" name="file" accept="image/*"/ style="width: 174px;padding-top: 5px;"/></span>
		<span>
			<input id="pic_import_$J{it.otype}" class="Btn" type="button" value="上传"   onclick="javascritp:doUploadstuApplyAtt('$J{it.otype}'); ">
			<input id="btn_uploading" style="display:none;" class="Btn" type="button" value="正在上传..." onclick="javascritp:void(0);" />
		</span>
	</div>
</form>
<form id="form_import_res_$J{it.otype}" class="unfold_left" style="display:none;">
	<div class="item" style="margin-top: 20px;margin-left: 25px;height: auto;">
		<span id="span_import_res_$J{it.otype}"></span>
	</div>
	<div class="item" style="margin-left: 50px;">
		<input onclick="javascript:import_stuApplyRes_hide($J{it.otype});" type="button" value="返回" class="Btn" />
	</div>
</form>
{@/if}
{@else}
	<input id="att_name_$J{it.otype}" value="$J{it.name}" type="hidden" />
	<input id="att_otype_$J{it.otype}" name="otype" value="$J{it.otypeindex}" type="hidden" />
	<a href="javascript:zoomIn('apply_modify_photo_$J{it.otype}','apply_modify_photo_$J{it.otype}_view','$J{downloadAtt}$J{it.hash}/$J{it.id}?otype=$J{it.otypeindex}');">
			<img src="$J{downloadAtt}$J{it.hash}/$J{it.id}?otype=$J{it.otypeindex}" style="height: 100px;padding-left: 15px;" />
	</a>
	<span style="padding-left: 15px;">名称：&nbsp;$J{it.name}</span>

{@if userType=='PATRIARCH' && statusStr!='REVIEW_PASS'}
<div id="stuApply_operation_$J{it.otype}" style="display: none;">
	<span style="padding-left: 15px;">
	<input onclick="javascript:doModifyUploadstuApplyAtt('$J{it.otype}','$J{it.oid}');" type="button" value="修改" class="Btn" />
	</span>
	<span style="padding-left: 15px;">
		<input onclick="javascript:doDelstuApplyAtt('$J{it.otype}','$J{it.id}');" type="button" value="删除" class="Btn" />
	</span>
	<span style="padding-left: 15px;">
		<input onclick="javascript:doDownloadStuApplyAtt('$J{it.id}','$J{it.hash}');" type="button" value="下载" class="Btn" />
	</span>
</div>
{@/if}{@/if}
</div>
<div id="apply_modify_photo_$J{it.otype}_view" class="media subMessage" style="display:none;width: 100%;">
	<ul class="feedbacks list-inline">
		<li><a href="javascript:zoomOut('apply_modify_photo_$J{it.otype}','apply_modify_photo_$J{it.otype}_view');"><i class="icon icon-Put-away "></i> 收起</a></li>
		<li><a target="_blank" href="$J{downloadAtt}$J{it.hash}/$J{it.id}?otype=$J{it.otypeindex}"><i class="icon icon-Amplification"></i> 查看大图</a></li>
		<li><a href="javascript:{$('#apply_modify_photo_$J{it.otype}_view_img').rotateLeft(90);}"><i class="icon icon-Towards-left"></i> 向左转</a></li>
		<li><a href="javascript:{$('#apply_modify_photo_$J{it.otype}_view_img').rotateRight(90);}"><i class="icon icon-Towards-right"></i> 向右转</a></li>
	</ul>
	<div class="clear"></div>
	<div class="subMessage-img"><img id="apply_modify_photo_$J{it.otype}_view_img" onclick="javascript:zoomOut('apply_modify_photo_$J{it.otype}','apply_modify_photo_$J{it.otype}_view');" /></div>
</div>
</div>
{@/each}
</script>

<script id="model_stuApplyAtt_upload" type="text/template">
<form id="form_import_$J{otype}" method="post" name="form_pic"  enctype="multipart/form-data" target="formFrame" class="unfold_left">
	<input  id="att_otype_$J{otype}" name="otype" value="$J{otypeindex}" type="hidden" />
	<!--<input id="att_oid_$J{otype}" name="oid" value="$J{oid}" type="hidden" />-->
	<div class="item" style="padding-top: 15px;">
		<label class="sele-label ">请重命名图片：</label>
		<input id="ipt_prefix_$J{otype}" type="text" class="inp"  placeholder="图片名称"  name="prefix"/>
		<label class="sele-label" >请选择图片文件：</label>
		<span id="span_import_pic" class="sele-label">
			<input id="input_pic_$J{otype}" type="file" name="file" accept="image/*"/ style="width: 174px;padding-top: 5px;"/></span>
		<span>
			<input id="pic_import_$J{otype}" class="Btn" type="button" value="上传"   onclick="javascritp:doUploadstuApplyAtt('$J{otype}'); ">
			<input id="btn_uploading" style="display:none;" class="Btn" type="button" value="正在上传..." onclick="javascritp:void(0);" />
		</span>
	</div>
</form>
<form id="form_import_res_$J{otype}" class="unfold_left" style="display:none;">
	<div class="item" style="margin-top: 20px;margin-left: 25px;height: auto;">
		<span id="span_import_res_$J{otype}"></span>
	</div>
	<div class="item" style="margin-left: 50px;">
		<input onclick="javascript:import_res_hide('$J{otype}');" type="button" value="返回" class="Btn" />
	</div>
</form>
</script>
<script id="model_stuApplyAtt_showPhoto" type="text/template">
<input id="att_name_$J{otype}" value="$J{name}" type="hidden" />
<input id="att_otype_$J{otype}" name="otype" value="$J{otypeindex}" type="hidden" />
<a target="_Blank" href="$J{downloadAtt}$J{hash}/$J{id}?otype=$J{otypeindex}">
	<img src="$J{downloadAtt}$J{hash}/$J{id}?otype=$J{otypeindex}" style="height: 100px;padding-left: 15px;" />
<a>
<span style="padding-left: 15px;">名称：&nbsp;$J{name}</span>
<div id="stuApply_operation_$J{otype}" style="display: initial;">
<span style="padding-left: 15px;">
	<input onclick="javascript:doModifyUploadstuApplyAtt('$J{otype}','$J{oid}');" type="button" value="修改" class="Btn" />
</span>
<span style="padding-left: 15px;">
	<input onclick="javascript:doDelstuApplyAtt('$J{otype}','$J{id}');" type="button" value="删除" class="Btn" />
</span>
<span style="padding-left: 15px;">
	<input onclick="javascript:doDownloadStuApplyAtt('$J{id}','$J{hash}');" type="button" value="下载" class="Btn" />
</span>
</div>
</script>