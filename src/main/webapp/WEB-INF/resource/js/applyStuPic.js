var stuApply_loadAttUrl = ctx + "/stuApply/loadAtt/ajax";
var stuApply_uploadAttUrl = ctx + "/stuApply/uploadAtt/ajax";
var stuApply_delAttUrl = ctx + "/stuApply/delAtt/ajax";
// var stuApply_getEmptyUrl = ctx + "/stuApply/getEmpty/ajax";
var stuApply_downloadAttUrl = ctx + "/stuApply/downloadAtt/";

/** ******************新生入学图片(包括照片等)**************************** */

function doloadStuApplyAtt(id, type) {
	$("#div_stuApplyAtt").html("加载中...");
	var userType = $("#ipt_userType").val();
	var status = $("#ipt_statusStr").val();
	var params = {};
	params.url = stuApply_loadAttUrl + "?_t=" + new Date().getTime();
	params.postData = {
		oid : id,
		type : type
	};
	params.postType = "get";
	params.error = "加载失败！";
	params.mustCallBack = false;// 是否必须回调
	params.callBack = function(json) {
		if(json.retCode==CODE_SUCCESS){//2016-6-22
			json.userType = userType;
			json.statusStr = status;
			json.type = type;
			json.downloadAtt = stuApply_downloadAttUrl;
			var menus = initStuApplyAtt_upload(type);
			if (json.data.length > 0) {
				for (var i = 0; i < json.data.length; i++) {
					for (var j = 0; j < menus.length; j++) {
						if (json.data[i].otype == menus[j].otype) {
							// json.data[i].otypename = menus[j].otypename;
							// json.data[i].otypeindex = menus[j].otypeindex;
							menus[j].hash = json.data[i].hash;
							menus[j].id = json.data[i].id;
							menus[j].name = json.data[i].name;
							menus[j].oid = json.data[i].oid;
							menus[j].size = json.data[i].size;
							menus[j].uid = json.data[i].uid;
						}
					}
				}
			}
			json.data = menus;
			useModel("model_stuApplyAtt", "div_stuApplyAtt", json);
			if(userType=='PATRIARCH' && status!='REVIEW_PASS'){
				for (var j = 0; j < menus.length; j++) {
					$("#stuApply_operation_" + menus[j].otype).attr("style", "display:initial");
				}
			}
		}
	};
	ajaxJSON(params);
}

function initStuApplyAtt_upload(type) {
	type = !type ? $("#ipt_stuApply_type").val() : type;
	var menus = [];
	{
		menus[0] = {"otype" : 10,"otypename" : "儿童的登记照","otypeindex" : "TYPE_CHILDREN_PHOTO"};
		menus[1] = {"otype" : 11,"otypename" : "户口首页（有地址的那一页）","otypeindex" : "TYPE_CHILDREN_HK_INDEXPAGE"};
		menus[2] = {"otype" : 12,"otypename" : "户主页","otypeindex" : "TYPE_CHILDREN_HOUSEHOLDERPAGE"};
		menus[3] = {"otype" : 13,"otypename" : "儿童户口页","otypeindex" : "TYPE_CHILDREN_PAGE"};
		menus[4] = {"otype" : 14,"otypename" : "儿童出生证","otypeindex" : "TYPE_CHILDREN_BIRTH_CERTIFICATE"};
		//menus[5] = {"otype" : 15,"otypename" : "父母户口页","otypeindex" : "TYPE_PARENT_ACCOUNT_PAGE"};
		menus[5] = {"otype" : 16,"otypename" : "父亲户口页","otypeindex" : "TYPE_FATHER_ACCOUNT_PAGE"};
		menus[6] = {"otype" : 17,"otypename" : "母亲户口页","otypeindex" : "TYPE_MOTHER_ACCOUNT_PAGE"};
		if (type == "TYPEA" || type == "TYPEB") {
			menus[7] = {"otype" : 19,"otypename" : "华师教职工校园一卡通","otypeindex" : "TYPE_WORK_PROVE"};
			if (type == "TYPEB") {
				menus[8] = {"otype" : 18,"otypename" : "三代关系证明（华师教职工与儿童父母关系证明）","otypeindex" : "TYPE_THREE_GENERATION_RELATIONSHIP"};
			}
		}
	}
	return menus;
}
function doUploadstuApplyAtt(otype) {
	var type = $("#ipt_stuApply_type").val();
	var prefix = $("#ipt_prefix_" + otype).val();
	if (prefix == "") {
		alert("请输入图片名称");
		return;
	}
	var fileName = $("#input_pic_" + otype).val();
	if (fileName == "") {
		alert("请选择要上传的照片！");
		return;
	}
	var filetype = fileName.substring(fileName.lastIndexOf('.') + 1,
			fileName.length);// 图片后缀名
	var AllImgExt = "jpg|jpeg|bmp|png|";// 全部图片格式类型
	if (AllImgExt.indexOf(filetype + "|") < 0) {
		alert("该文件类型不允许上传。请上传" + AllImgExt + "类型的文件，\n当前文件类型为" + filetype);
	}
	$("#form_import_" + otype).attr("action", stuApply_uploadAttUrl);
	$("#form_import_" + otype)
			.ajaxSubmit({type : "post", // 提交方式
						dataType : "json", // 数据类型
						data : {"type" : type},
						success : function(json) {
							var msg = json.msg;
							if (msg != null && msg != "null" && msg != "") {
							} else if (json.retCode == CODE_SUCCESS) {
								json.data.otypeindex = $("#att_otype_" + otype).val();
								json.data.otype = otype;
								json.data.downloadAtt = stuApply_downloadAttUrl;
								var _html = createHtmlUseModel("model_stuApplyAtt_showPhoto",json.data);
								$("#apply_modify_photo_" + otype).html(_html);
							} else if (msg == null || msg == "null"
									|| msg == "") {
								msg = "上传失败！";
								$("#form_import_" + otype).hide();
								$("#span_import_res_" + otype).html(msg);
								$("#form_import_res_" + otype).show();
								$("#ipt_prefix_" + otype).attr("value", "");
								$("#span_import_pic_" + otype).html('<input id="input_pic" type="file" name="file" accept="image/*"/ style="width: 174px;padding-top: 5px;"/>');
							}

						},
					});

}
function import_stuApplyRes_hide(otype) {
	$("#form_import_res_" + otype).hide();
	$("#form_import_" + otype).show();
}
function doModifyUploadstuApplyAtt(otype, oid) {
	var otypeindex = $("#att_otype_" + otype).val();
	useModel("model_stuApplyAtt_upload", "apply_modify_photo_" + otype, {
		otype : otype,
		oid : oid,
		otypeindex : otypeindex
	});
}

function doDelstuApplyAtt(otype, id) {
	var otypeindex = $.trim($("#att_otype_" + otype).val());
	var name = $.trim($("#att_name_" + otype).val());
	var userType = $("#ipt_userType").val();
	var oid = $("#ipt_id").val();
	if (!confirm("您确定要删除图片【" + name + "】吗？")) {
		return;
	}
	var params = {};
	params.url = stuApply_delAttUrl;
	params.postData = {
		id : id,
		oid : oid,
		otype : otypeindex
	};
	params.postType = "get";
	params.error = "删除失败";
	params.mustCallBack = false;// 是否必须回调
	params.callBack = function(json) {
		if (json.retCode == CODE_SUCCESS) {
			$("#gstupic_tr_" + id).remove();
			alert("删除成功");
			doModifyUploadstuApplyAtt(otype, oid);
		} else {
			if (json.errorMsg != null) {
				alert("删除失败：" + json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function doDownloadStuApplyAtt(id, hash) {
	window.open(stuApply_downloadAttUrl + hash + "/" + id);
}
function checkStuApplyPhoto() {// 检查新生的相关照片是否全部上传
	var type = $("#ipt_stuApply_type").val();
	var menus = initStuApplyAtt_upload(type);
	var menus2 = [];//{menus[0],menus[1],menus[3]};//2016-6-22
	menus2[0] = menus[0];
	menus2[1] = menus[1];
	menus2[2] = menus[3];
	for (var i = 0; i < menus2.length; i++) {
		var name = $("#att_name_" + menus2[i].otype).val();
		if (!name) {
			alert(menus2[i].otypename + "还没有上传！");
			return false;
		}
	}
	return true;
}