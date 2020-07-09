var student_loadStuApplyUrl = ctx+"/stuApply/load/ajax";
var student_saveStuApplyUrl = ctx+"/stuApply/save/ajax";
var student_submitStuApply2ReviewUrl = ctx+"/stuApply/submit2Review/ajax";
var student_submitStuApply2ReviewTwiceUrl = ctx+"/stuApply/submit2ReviewTwice/ajax";
var student_downloadStuApplyUrl = ctx+"/stuApply/downloadPdf/";
var student_downloadStuApplyTestUrl = ctx+"/stuApply/downloadTestPdf/";
var student_stuApplyListSendSmsUrl = ctx+"/stuApplyList/sendSms/";
var student_printStuApplyUrl = ctx+"/stuApply/print/";
var student_stuApplyHistoryListUrl = ctx+"/stuApply/historyList/ajax";
var student_stuApplyHistoryInfoUrl = ctx+"/stuApply/historyInfo/ajax";
var student_stuForwardGradeUrl = ctx+"/stuApply/forwardGrade/ajax";

var student_loadStuApplyListUrl = ctx+"/stuApplyList/load/ajax";
var student_stuApplyReviewUrl = ctx+"/stuApplyList/review/ajax";
var student_stuApplySaveMsgUrl = ctx+"/stuApplyList/saveMsg/ajax";
var student_stuApplyBatchReviewUrl = ctx+"/stuApplyList/batchReview/ajax";
var student_submitAllUrl = ctx+"/stuApplyList/submitAll/ajax";
var student_resetAllInfoUrl = ctx+"/stuApplyList/resetAllInfo/ajax";
var student_submitAllInfoUrl = ctx+"/stuApplyList/submitAllInfo/ajax";
var student_stuApplyExportUrl_no = ctx+"/stuApplyList/export/ORDER_NO";
var student_stuApplyExportUrl_class = ctx+"/stuApplyList/export/ORDER_CLASS";
var student_stuApplyExportUrl_number = ctx+"/stuApplyList/export/ORDER_NUMBER";
var student_stuInfoExportUrl_no = ctx+"/stuApplyList/exportInfo/ORDER_NO";
var student_stuInfoExportUrl_class = ctx+"/stuApplyList/exportInfo/ORDER_CLASS";
var student_stuInfoExportUrl_number = ctx+"/stuApplyList/exportInfo/ORDER_NUMBER";
var student_stuVaccineExportUrl_no = ctx+"/stuApplyList/exportVaccine/ORDER_NO";
var student_stuVaccineExportUrl_class = ctx+"/stuApplyList/exportVaccine/ORDER_CLASS";
var student_stuVaccineExportUrl_number = ctx+"/stuApplyList/exportVaccine/ORDER_NUMBER";

var student_loadStuInfoUrl = ctx+"/stuInfo/load/ajax";
var student_saveStuInfoUrl = ctx+"/stuInfo/save/ajax";
var student_submitStuInfoUrl = ctx+"/stuInfo/submit/ajax";
var student_downloadStuInfoUrl = ctx+"/stuInfo/downloadPdf/";
var student_printStuInfoUrl = ctx+"/stuInfo/print/";

var student_loadStuVaccineUrl = ctx+"/stuVaccine/load/ajax";
var student_saveStuVaccineUrl = ctx+"/stuVaccine/save/ajax";
var student_submitStuVaccineUrl = ctx+"/stuVaccine/submit/ajax";

var student_importStuInfoUrl = ctx+"/stuInfo/import";
var student_modifyClassUrl = ctx+"/stuInfo/modifyClass";

var student_downloadGraStuInfoUrl = ctx+"/stuGraInfo/downloadPdf/";
var student_printGraStuInfoUrl = ctx+"/stuGraInfo/print/";

var student_importGraStuInfoUrl=ctx+"/stuGraListInfo/import";
var student_exportGraStuInfoUrl = ctx+"/stuGraListInfo/export";
var student_loadGraStuInfoListUrl = ctx+"/stuGraListInfo/load/ajax";
var student_loadGraStuInfoUrl=ctx+"/stuGraInfo/load/ajax";
var student_gratuInfoHistoryListUrl=ctx+"/stuGraInfo/historyList/ajax";
var student_graStuHistoryInfoUrl=ctx+"/stuGraInfo/historyInfo/ajax";

var student_verifyGraStuInfoUrl=ctx+"/stuGraInfo/verify/ajax";
var student_submitGraStuInfoUrl = ctx+"/stuGraInfo/submit/ajax";
var student_reviewGraStuInfoUrl=ctx+"/stuGraInfo/review/ajax";
var student_refuseGraStuInfoUrl = ctx+"/stuGraInfo/refuse/ajax";

var student_loadStuGraInfoAttUrl	= ctx+"/stuGraInfo/loadAtt/ajax";
var student_uploadStuGraInfoAttUrl	= ctx+"/stuGraInfo/uploadAtt/ajax";
var student_delStuGraInfoAttUrl = ctx+"/stuGraInfo/delAtt/ajax";
var student_downloadStuGraInfoAttUrl = ctx+"/stuGraInfo/downloadAtt";
/*function doLoadStuApplyStatus(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");

	var params = {};
	params.url = student_loadStuApplyUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_stuApplyStatusDisplay","div_display",json.data);
			$("#form_stuApply").find("input").attr("disabled",true);
			$("#div_display").find("input[type='radio']").radioFn();
		}
	};
	ajaxJSON(params);
}*/
function doLoadStuApply(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");

	var params = {};
	params.url = student_loadStuApplyUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			json.data.showReview = false;
			json.data.currentYear = getCurrentYear();
			useModel("model_stuApplyDisplay","div_display",json.data);
			//处理低版本IE浏览器下placeholder属性无效的问题
			$("#div_display").find("input").placeholder();
			$("#div_display").find("input[type='radio']").radioFn();
			{
				$("#sel_nation").val(json.data.nation);
				$("#sel_jkzt").val(json.data.jkzt);
				$("#sel_citizenship").val(json.data.citizenship);
				$("#sel_cardType").val(json.data.cardTypeStr);
				$("#sel_gatqw").val(json.data.gatqw);
				$("#sel_citizenship_other35").val(json.data.other35);
				$("#sel_other6").val(json.data.other6);
				$("#sel_other23").val(json.data.other23);
				$("#div_display").find("select").seleFn();
			}
			changeStuApplyType(json.data.typeStr);
			if(json.data.id==null||json.data.id==0){
				if(json.data.locked){
					$("#div_display").html("当前不是填表时间！");
				}else{
					showStuApplyModify(0);
				}
			}else{
				$("#form_stuApply").find("input").attr("disabled",true);
				doloadStuApplyAtt(json.data.id,json.data.typeStr);
				$("#div_stuApply_cover").css({"height":$("#form_stuApply").height(),"width":$("#form_stuApply").width()});
			}
			if(json.data.statusStr=="SUBMIT_NONE" && json.data.currentYear == json.data.year ){//2016-6-22
				alert("尊敬的家长，请先填写“入学登记”，点击提交后，" +
						"才能填写“信息录入”和“预防接种登记”。填写完毕后,请检查信息的准确性，" +
						"点击“提交”将暂时锁定信息，如果不需要检查了，可以再次点击“提交”，"+
						"将永久锁定表格信息，此后将不再允许修改。请注意：三张表格填写完整并且" +
						"永久锁定后，教师才能开始审核");
			}
			if(json.data.statusStr != "REVIEW_PASS" && json.data.currentYear != json.data.year){
				$("#div_stuApply_iptHint").show();
			}
			if(json.data.currentYear == json.data.year){
				if(json.data.statusStr=="SUBMIT_NONE"){
					alert("请您及时完成《入学登记表》的填写并提交，以免影响审核！");
				}else if(json.data.statusStr=="SUBMIT_ONCE"){
					alert("请您再次核对《入学登记表》中的信息，核对无误后请再次提交，以免影响审核！");
				}else if(json.data.statusStr=="SUBMIT_TWICE"){
					if(json.data.infoStatusStr==null){
						alert("请您及时完成《学籍表》的填写并提交，以免影响审核！");
					}else if(json.data.infoStatusStr=="SUBMIT_NONE"){
						alert("请您及时完成《学籍表》的填写并提交，以免影响审核！");
					}else if(json.data.vaccineStatusStr==null){
						alert("请您及时完成《预防表》的填写并提交，以免影响审核！");
					}else if(json.data.vaccineStatusStr=="SUBMIT_NONE"){
						alert("请您及时完成《预防表》的填写并提交，以免影响审核！");
					}
				}
			}
			if(json.data.message && json.data.message!=""){
				alert(json.data.message);
			}
		}
	};
	ajaxJSON(params);
}
function showStuApplyModify(opt){    //opt：0初始登记，1修改操作
	$("#div_stuApply_cover").hide();
	$("#btn_refresh").hide();
	$("#btn_showModify").hide();
	$("#btn_submit2Review").hide();
	$("#btn_print_1").hide();
	$("#btn_print_2").hide();
	$("#btn_print_3").hide();
	$("#btn_review_4").hide();
	$("#btn_modify_5").hide();
	$("#btn_doModify").show();
	$("#btn_doModify_2").hide();
	$(".stuInfo").hide();
	$("#btn_cancelModify").show();
	$("#btn_cancelModify_2").hide();
	$("#form_stuApply").find("input").attr("disabled",false);
//	var menus = initStuApplyAtt_upload();//2016-6-22
//	for (var j = 0; j < menus.length; j++) {
//		$("#stuApply_operation_" + menus[j].otype).attr("style", "display:initial");
//	}

    if (opt == 1) {
		$("#doCho").hide();
		$(".stuInfo").show();
		if ($("#typeStrId").val() == "TYPEB") {
			$("#table_b").show(); // B类表
		} else {
			$("#table_b").hide();
		}
		if ($("#typeStrId").val() == "TYPEC") {
			$("#tr_adr_email").show(); // C类表
		} else {
			$("#tr_adr_email").hide();
		}
		$("#bmType").children().unbind("click");
		$("#bmSchool").children().unbind("click");
    }
}

function doChoiceSignUpType () {
	$("#doCho").hide();
	$(".stuInfo").show();
    $("#btn_doModify_2").show();
    $("#btn_cancelModify_2").show();
    if ($("label[name='type']").eq(1).find("span").eq(0).hasClass('radioBtn_on')) {
        $("#table_b").show();
	} else {
        $("#table_b").hide();
	}
	if ($("label[name='type']").eq(2).find("span").eq(0).hasClass('radioBtn_on')) {
        $("#tr_adr_email").show(); // C类表
	} else {
        $("#tr_adr_email").hide();
	}
	$("#bmType").children().unbind("click");
    $("#bmSchool").children().unbind("click");
}

function showGraStuInfoModify(){
	$("#div_graStuInfo_cover").hide();
	$("#btn_refresh").hide();
	$("#btn_showModify").hide();
	$("#btn_submit").hide();
//	$("#btn_print_1").hide();
//	$("#btn_print_2").hide();
//	$("#btn_print_3").hide();
	$("#btn_doModify").show();
	$("#btn_doModify_2").show();
	$("#btn_cancelModify").show();
	$("#btn_cancelModify_2").show();
	$("#form_graStuInfo").find("input").attr("disabled",false);
}

function changeStuApplyType(type){
	if(type=="TYPEA"){
		//$("label[name='other37']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(1).find("span").eq(0).attr("class","radioBtn");
        $("label[name='other51']").eq(1).show();
//      $("#tr_zn td[name='td_dszn']").hide();
        $("#tr_zn td[name='td_znqk']").show();
        $("#tr_nowAdr td[name='td_type']").hide();
        $("#td_nowadr").attr("colspan",4);
        $("#tr_adr_email").hide();
        $(".xyykt_phon").show();
        /*$("#td_twoxq").show();
        $("#td_onexq").hide();*/
        $("#tr_jcwgzn td[name='td_jcwgzn']").hide();
        $("#tr_jcwgzn td[name='td_jcwgzn']").eq(0).attr("class", "")
        $("#tr_jcwgzn td[name='td_cjlx']").attr("colspan",4);
        $("#ipt_other42").css("width","895px");
        $(".resort").each(function(index,element){
            $(element).text(20+index);
        });
		$("#table_b").hide();
//		$("#table_c").hide();
		$("#table_b").find("input").attr("disabled",true);
//		$("#table_c").find("input").attr("disabled",true);
//		$("#apply_otypeInfo_photo_18").hide();//隐藏三代关系证明的拖上传
        $("#th_comp_dept").html("工作单位(院系或部门)");
		$("#th_note_name").html("备注");
	}else if(type=="TYPEB"){
        //$("label[name='other37']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(1).find("span").eq(0).attr("class","radioBtn");
        $("label[name='other51']").eq(1).show();
//      $("#tr_zn td[name='td_dszn']").hide();
        $("#tr_zn td[name='td_znqk']").show();
        $("#tr_nowAdr td[name='td_type']").hide();
        $("#td_nowadr").attr("colspan",4);
        $("#tr_adr_email").hide();
        $(".xyykt_phon").show();
        /*$("#td_twoxq").show();
        $("#td_onexq").hide();*/
        $("#tr_jcwgzn td[name='td_jcwgzn']").hide();
        $("#tr_jcwgzn td[name='td_jcwgzn']").eq(0).attr("class", "")
        $("#tr_jcwgzn td[name='td_cjlx']").attr("colspan",4);
        $("#ipt_other42").css("width","895px");
        $(".resort").each(function(index,element){
            $(element).text(20+index);
        });
//		$("#table_c").hide();
		if ($("#stuInfo").is(':hidden')) {
            $("#table_b").hide();
		} else {
            $("#table_b").show();
		}
		$("#table_b").find("input").attr("disabled",false);
//		$("#table_c").find("input").attr("disabled",true);
//		$("#apply_otypeInfo_photo_18").hide();//隐藏三代关系证明的拖上传
        $("#th_comp_dept").html("工作单位(院系或部门)");
		$("#th_note_name").html("备注");
	}else if(type=="TYPEC"){
        //$("label[name='other37']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(0).find("span").eq(0).attr("class","radioBtn_on");
        $("label[name='other51']").eq(1).find("span").eq(0).attr("class","radioBtn");
        $("label[name='other51']").eq(1).hide();
//      $("#tr_zn td[name='td_dszn']").show();
        $("#tr_zn td[name='td_znqk']").show();
        $("#tr_nowAdr td[name='td_type']").show(); // C类表
        $("#td_nowadr").removeAttr("colspan");
        if ($("#stuInfo").is(':hidden')) {
            $("#tr_adr_email").hide(); // C类表
		} else {
            $("#tr_adr_email").show(); // C类表
		}
        $(".xyykt_phon").hide(); // C类表
        /*$("#td_twoxq").hide();
        $("#td_onexq").show();*/
        $("#tr_jcwgzn td[name='td_jcwgzn']").show();
        $("#tr_jcwgzn td[name='td_jcwgzn']").eq(0).attr("class", "resort")
        $("#tr_jcwgzn td[name='td_cjlx']").removeAttr("colspan");
        $("#ipt_other42").css("width","275px");
        $(".resort").each(function(index,element){
            $(element).text(23+index);
        });
		$("#table_b").hide();
//		$("#table_c").show();
		$("#table_b").find("input").attr("disabled",true);
//		$("#table_c").find("input").attr("disabled",false);
//		$("#apply_otypeInfo_photo_18").show();//显示三代关系证明的拖上传
        $("#th_comp_dept").html("工作单位");
		$("#th_note_name").html("备注");
	}
}
function doCancelStuApplyModify(){
	if(!confirm("您确定要取消当前的修改吗？")){
		return ;
	}
	doLoadStuApply();
}
function removeLastChar(source,c){
	if(source!=""&&source.lastIndexOf(c)==source.length-1){
		return source.substring(0, source.length-1);
	}
	return source;
}
function stuApplyOther43Click(isTrue){
	if(isTrue){
		$("#tr_other44").hide();
		$("#tr_other45").hide();
	}else{
		$("#tr_other44").show();
		if($("#form_stuApply").find("input:radio[name='other44']:checked").val()=="是"){
			$("#tr_other45").show();
		}
	}
}
function stuApplyOther44Click(isTrue){
	if(isTrue){
		$("#tr_other45").show();
	}else{
		$("#tr_other45").hide();
		$("#ipt_other45").val("");
		$("#ipt_other46").val("");
		$("#ipt_other47").val("");
	}
}
function doSaveStuApply(isAdmin){
	debugger;
	var id = $.trim($("#ipt_id").val());
	if(id==""||id=="0"){
		var type = $("#form_stuApply").find("input:radio[name='type']:checked").val();
		var typeMsg = "";
		if(type=="TYPEA"){
			typeMsg = "您当前所选的报名类别是“A华师第二代生源”，保存后报名类别将不可修改，是否继续保存？";
		}else if(type=="TYPEB"){
			typeMsg = "您当前所选的报名类别是“B华师第三代生源”，保存后报名类别将不可修改，是否继续保存？";
		}else if(type=="TYPEC"){
			typeMsg = "您当前所选的报名类别是“C社会对口生源”，保存后报名类别将不可修改，是否继续保存？";
		}
		// if(!confirm(typeMsg)){
		// 	return ;
		// }
	}
	var regex_mobile = /^1\d{10}$/;//手机号
	var regex_card = /^[0-9a-zA-z\(\)]+$/;//证件号，香港身份证有括号

	var name = $.trim($("#ipt_name").val());
	var dateOfBirth = $.trim($("#ipt_dateOfBirth").val());
	var addressOfBirth = $.trim($("#ipt_addressOfBirth").val());
	var birthplace = $.trim($("#ipt_birthplace").val());
	var nation = $.trim($("#sel_nation").val());
	var citizenship = $.trim($("#sel_citizenship").val());
	var cardType = $.trim($("#sel_cardType").val());
	var cardNo = $.trim($("#ipt_cardNo").val());
	var other1 = $.trim($("#ipt_other1").val());
	var other2 = $.trim($("#ipt_other2").val());
	var other3 = $.trim($("#ipt_other3").val());
	var other5 = $.trim($("#ipt_other5").val());
	var other7 = $.trim($("#ipt_other7").val());
	var other8 = $.trim($("#ipt_other8").val());
	var other9 = $.trim($("#ipt_other9").val());
	var other12 = $.trim($("#ipt_other12").val());
	var other13 = $.trim($("#ipt_other13").val());
	var other14 = $.trim($("#ipt_other14").val());
	var other15 = $.trim($("#ipt_other15").val());
	var other16 = $.trim($("#ipt_other16").val());
    var other17 = $.trim($("#ipt_other17").val());
	var other18 = $.trim($("#ipt_other18").val());
	var other19 = $.trim($("#ipt_other19").val());
	var other20 = $.trim($("#ipt_other20").val());
	var other21 = $.trim($("#ipt_other21").val());
    var other22 = $.trim($("#ipt_other22").val());
    var other27 = $.trim($("#ipt_other27").val());
    var other28 = $.trim($("#ipt_other28").val());
    var other41 = $.trim($("#ipt_other41").val());
	other8 = removeLastChar(other8,"区");
	if(name==""){
		alert("姓名不能为空!");
		return ;
	}
	if(cardType!="CARD_TYPE1"&&dateOfBirth==""){
		alert("出生日期不能为空!");
		return ;
	}
	if(nation==""){
		alert("民族不能为空!");
		return ;
	}
	if(addressOfBirth==""){
		alert("出生地不能为空!");
		return ;
	}
	if(birthplace==""){
		alert("籍贯不能为空!");
		return ;
	}
	if(citizenship==""){
		alert("国籍/地区不能为空!");
		return ;
	}
	if(cardNo==""){
		alert("身份证件号不能为空!");
		return ;
	}else if(cardType=="CARD_TYPE1"){
		var checkCardNoRes = IdentifyCodeValid(cardNo);
		if(!checkCardNoRes.pass){
			// alert(checkCardNoRes.msg);
			return ;
		}
	}else if(cardType=="CARD_TYPE7"){
	}else{
		if(!regex_card.test(cardNo)){
			alert("身份证件号只能包含数字与字母！");
			return ;
		}
	}
	/*if(other1==""||other2==""||other3==""){
		alert("请将户口所在地信息填写完整！");
		return ;
	}*/
	if(other5==""){
		alert("户主姓名不能为空！");
		return ;
	}
	if(other7==""){
		alert("户口详细地址不能为空！");
		return ;
	}
	if(other8==""||other9==""){
		alert("请将现住址信息填写完整！");
		return ;
	}
	if(other12=="" && type=="TYPEC"){
		alert("邮箱地址不能为空！");
		return ;
	}else if(other12.indexOf("@")<0 && type=="TYPEC"){
		alert("邮箱地址输入错误！");
		return ;
	}
	if(other16!=""&&!regex_mobile.test(other16)){
		alert("父亲联系电话输入错误！");
		return ;
	}
	if (type=="TYPEA") {
		if (other17=="" && other22=="") {
            alert("校园一卡通号码必须要填最少一个！");
            return ;
		}
	} else if (type=="TYPEB") {
        if (other17=="" && other22=="" && other41=="") {
            alert("校园一卡通号码必须要填最少一个！");
            return ;
        }
        if (other27=="" || other28=="") {
            alert("请将“(外)祖父、母”相关的所有信息补充完整");
            return ;
		}
	}
	if(other21!=""&&!regex_mobile.test(other21)){
		alert("母亲联系电话输入错误！");
		return ;
	}
	if((other13==""
			||other14==""
			||other15==""
			||other16=="")
		||(other18==""
				||other19==""
				||other20==""
				||other21=="")){
		alert("请至少将家长情况中除“校园一卡通号码”外的所有信息补充完整！");
		return ;
	}

	var sex = $("#form_stuApply").find("input:radio[name='sex']:checked").val();
	var sexCheck = {"TRUE":"1,3,5,7,9,儿子,孙子,外孙子,非亲属,其他","FALSE":"0,2,4,6,8,女儿,孙女,外孙女,非亲属,其他"};

	var cardSex = "";
	var birthStr = "";
	if(cardType=="CARD_TYPE1"){//身份证
		birthStr = cardNo.substring(6,14);
		cardSex = cardNo.substring(16,17);
	}else{
		birthStr = dateOfBirth.replaceAll("(年|月|日|-)","");
	}
	if(cardSex!=""
		&&sexCheck[sex].indexOf(cardSex)<0){
		alert("性别选择有误！");
		return ;
	}
	var other6 = $.trim($("#sel_other6").val());
	if(other6=="户主本人"){
		if(other5!=name){
			alert("与户主关系选择有误！");
			return ;
		}
	}else if(sexCheck[sex].indexOf(other6)<0){
		alert("与户主关系选择有误！");
		return ;
	}
	checkBirthday8(birthStr,6);
	var postData = $("#form_stuApply").formSerialize();
	var params = {};
	params.url = student_saveStuApplyUrl+"?_t="+new Date().getTime();
	params.postData = postData;
	params.postType = "get";
	params.error = "修改失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			if(isAdmin){
				alert("修改成功！");
				showStuApplyDetail($("#ipt_id").val(),false);
			}else{
				alert("重要提示：保存成功，确定所填信息无误后请及时将表单提交，未在规定时间内提交的表单将被视为无效表单！");
				doLoadStuApply();
			}
		}else{
			if(json.errorMsg==null){
				alert("修改失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}

function submit2Reviewpromise(){
	//弹出提示框 20200709
	var params = {};
	params.submitMethod = promise;
	// params.submitData = {id:id};
	params.modelId = "model_promise";
	// params.title = "下载提示";
	// params.submitTxt = "马上下载";
	// params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);
}
function submit2ReviewTwicewpromise(){
	//弹出提示框 20200709
	var params = {};
	params.submitMethod = promiseTwice;
	// params.submitData = {id:id};
	params.modelId = "model_promise";
	// params.title = "下载提示";
	// params.submitTxt = "马上下载";
	// params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);
}

function submit2Review(){

	$("#dialog_confirm_380").hide();

	if(!checkStuApplyPhoto()){//验证新生的相关图片是否上传完整
		return;
	}

	if(!confirm("您确定要将表单提交吗，提交后表单将暂时锁定，不可修改！")){
		return ;
	}
	var id = $("#ipt_id").val();
	var params = {};
	params.url = student_submitStuApply2ReviewUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			doLoadStuApply();
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert("操作失败："+json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function submit2ReviewTwice(){
	$("#dialog_confirm_380").hide();

	if(!checkStuApplyPhoto()){//验证新生的相关图片是否上传完整
		return;
	}

	if(!confirm("您确定要将表单提交吗，本次提交后表单内容将“永久锁定”！")){
		return ;
	}
	var id = $("#ipt_id").val();
	var params = {};
	params.url = student_submitStuApply2ReviewTwiceUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			doLoadStuApply();
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert("操作失败："+json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showDownloadConfirm(id){
	var params = {};
	params.submitMethod = doDownload;
	params.submitData = {id:id};
	params.modelId = "model_downloadExplain";
	params.title = "下载提示";
	params.submitTxt = "马上下载";
	params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);

	$("#dialog_380").find("select").seleFn();
}
//打印报名表
function showDownloadTestPdf(id){
	var params = {};
	params.submitMethod = doDownloadTest;
	params.submitData = {id:id};
	params.modelId = "model_stuInfoDownloadExplain";
	params.title = "下载提示";
	params.submitTxt = "马上下载";
	params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);

	$("#dialog_380").find("select").seleFn();
}
//面试通过通知
function stuApplyListSendSms(id){
	debugger;
	var params = {};
	params.submitMethod = sendSms;
	params.submitData = {id:id};
	params.modelId = "model_stuInfoDownloadExplain1";
	params.title = "提示";
	params.submitTxt = "确认";
	params.cancelTxt = "取消";
	showDialogConfirm380(params);

	$("#dialog_380").find("select").seleFn();
}
function showPrintConfirm(id){
	var params = {};
	params.submitMethod = doPrint;
	params.submitData = {id:id};
	params.modelId = "model_printExplain";
	params.title = "打印提示";
	params.submitTxt = "马上打印";
	params.cancelTxt = "暂不打印";
	showDialogConfirm380(params);

	$("#dialog_380").find("select").seleFn();
}
function doDownload(evt){
	$("#dialog_confirm_380").hide();
	window.open(student_downloadStuApplyUrl+evt.data.id);
}
function promise(evt){
	// window.open(student_downloadStuApplyUrl+evt.data.id);
	submit2Review();
}
function promiseTwice() {
	submit2ReviewTwice();
}
function doDownloadTest(evt){
	$("#dialog_confirm_380").hide();
	window.open(student_downloadStuApplyTestUrl+evt.data.id);
}
function sendSms(evt){
	$("#dialog_confirm_380").hide();
	var params = {};
	params.url = student_stuApplyListSendSmsUrl+"?_t="+new Date().getTime();
	params.postType = "get";
	params.error = "失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		console.log(json);
		if(json.retCode==CODE_SUCCESS){
			alert("发送成功！");
		}else{
			alert(json.errorMsg);
		}
	};
	ajaxJSON(params);
	// window.open(student_stuApplyListSendSmsUrl);
}
function doPrint(evt){
	var isIE = (document.all) ? true : false;
	if(!isIE){
		alert("当前浏览器可能无法使用打印功能，请确保您当前是在IE浏览器下进行的操作！");
	}
	$("#dialog_confirm_380").hide();
	window.open(student_printStuApplyUrl+evt.data.id);
}
function showStuApplyHistoryList(id){
	var params = {};
	params.url = student_stuApplyHistoryListUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "获取失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			var name = $("#ele_name_"+id).html();
			var params = {};
			params.modelId = "model_stuApplyHistoryList";
			params.modelData = json;
			params.title = "【"+name+"】的入学登记表历史存档";
			showDialogView380(params);
		}else{
			if(json.errorMsg==null){
				alert("获取失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function loadStuApplyHistoryInfo(id){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();
	
	var params = {};
	params.url = student_stuApplyHistoryInfoUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "获取失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			$("#dialog_view_380").hide();
			json.data.title = "【"+$("#dialog_ele_name_"+id).html()+"】于"+$("#dialog_ele_timeStr_"+id).html()+"修改前的存档";
			json.data.showReview = (json.data.statusStr=="REVIEW_PASS"||json.data.statusStr=="REVIEW_REFUSE"||json.data.statusStr=="REVIEW_WAITING");
			useModel("model_sutApplyHistoryInfo","div_display_list",json.data);
			
			$("#div_display_list").find("input[type='radio']").radioFn();
			{
				$("#sel_nation").val(json.data.nation);
				$("#sel_jkzt").val(json.data.jkzt);
				$("#sel_citizenship").val(json.data.citizenship);
				$("#sel_cardType").val(json.data.cardTypeStr);
				$("#sel_gatqw").val(json.data.gatqw);
				$("#sel_citizenship_other35").val(json.data.other35);
				$("#sel_other6").val(json.data.other6);
				$("#sel_other23").val(json.data.other23);
				$("#div_display_list").find("select").seleFn();
			}
			changeStuApplyType(json.data.typeStr);
			$("#form_stuApply").find("input").attr("disabled",true);
			$("#div_stuApply_cover").css({"height":$("#form_stuApply").height(),"width":$("#form_stuApply").width()});
			
		}else{
			if(json.errorMsg==null){
				alert("获取失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
/*************************全国中小学学籍管理系统学生基本信息表*******************************/
function doLoadStuInfo(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");
	
	var params = {};
	params.url = student_loadStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_stuInfoDisplay","div_display",json.data);
			//处理低版本IE浏览器下placeholder属性无效的问题
			$("#div_display").find("input").placeholder();
			$("#div_display").find("input[type='radio']").radioFn();
			{
				$("#sel_other28").val(json.data.other28);
				$("#sel_other29").val(json.data.other29);
				$("#sel_other34").val(json.data.other34);
				$("#sel_other39").val(json.data.other39);
				$("#sel_other45").val(json.data.other45);
				$("#sel_other51").val(json.data.other51);
				$("#sel_other57").val(json.data.other57);
				$("#div_display").find("select").seleFn();
			}
			if(json.data.id==null||json.data.id==0){
				if(json.data.locked){
					$("#div_display").html("当前不是填表时间！");
				}else{
					showStuInfoModify();
				}
			}else{
				$("#form_stuInfo").find("input").attr("disabled",true);
				$("#div_stuInfo_cover").css({"height":$("#form_stuInfo").height(),"width":$("#form_stuInfo").width()});
			}
		}else{
			if(json.errorMsg==null){
				$("#div_display").html("加载失败！");
			}else{
				$("#div_display").html(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showStuInfoModify(){
	$("#div_stuInfo_cover").hide();
	$("#btn_refresh").hide();
	$("#btn_showModify").hide();
	$("#btn_submit2Review").hide();
	$("#btn_print_1").hide();
	$("#btn_print_2").hide();
	$("#btn_print_3").hide();
	$("#btn_doModify").show();
	$("#btn_doModify_2").show();
	$("#btn_cancelModify").show();
	$("#btn_cancelModify_2").show();
	$("#div_stuInfo_iptHint").show();
	$("#form_stuInfo").find("input").attr("disabled",false);
}
function doCancelStuInfoModify(){
	if(!confirm("您确定要取消当前的修改吗？")){
		return ;
	}
	doLoadStuInfo();
}
function doSaveStuInfo(isAdmin){
	var regex_mobile = /^1\d{10}$/;//手机号
	var regex_addressCode = /^\d{12}$/;//户口所在地
	var regex_card = /^[0-9a-zA-z]+$/;//证件号
	var regex_other1 = /^[a-zA-z ]+$/;//姓名拼音
	var regex_other19 = /^\d{6}$/;//邮政编码
	var regex_other33 = /^\d{1,}$/;//上下学距离

	var addressOfBirth = $.trim($("#ipt_addressOfBirth").val());//出生地
	if(addressOfBirth==""){
		alert("出生地不能为空!");
		return ;
	}else if(!regex_addressCode.test(addressOfBirth)){
		alert("出生地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
		return ;
	}
	var other1 = $.trim($("#ipt_other1").val());//姓名拼音
	var other2 = $.trim($("#ipt_other2").val());//曾用名
	var other3 = $.trim($("#ipt_other3").val());//身份证件有效期
	var other4 = $.trim($("#ipt_other4").val());//户口所在地
	var other6 = $.trim($("#ipt_other6").val());//特长
	if(other1!=""&&!regex_other1.test(other1)){
		alert("姓名拼音输入有误!");
		return ;
	}
//	if(other2==""){
//		alert("曾用名不能为空!");
//		return ;
//	}
//	if(other3==""){
//		alert("身份证件有效期不能为空!");
//		return ;
//	}
	if(other4==""){
		alert("户口所在地不能为空!");
		return ;
	}else if(!regex_addressCode.test(other4)){
		alert("户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
		return ;
	}
//	if(other6==""){
//		alert("特长不能为空!");
//		return ;
//	}
	//学生个人联系信息
	var other15 = $.trim($("#ipt_other15").val());//现住址
	var other16 = $.trim($("#ipt_other16").val());//通信地址
	var other17 = $.trim($("#ipt_other17").val());//家庭地址
	var other18 = $.trim($("#ipt_other18").val());//联系电话
	var other19 = $.trim($("#ipt_other19").val());//邮政编码
	var other20 = $.trim($("#ipt_other20").val());//电子信箱
	if(other15==""||other16==""
			||other17==""||other18==""
			||other19==""||other20==""){
		alert("请将“学生个人联系信息”补充完整!");
		return ;
	}else if(!regex_mobile.test(other18)){
		alert("“学生个人联系信息”中联系电话输入有误，联系电话应为11手机号!");
		return ;
	}else if(!regex_other19.test(other19)){
		alert("“学生个人联系信息”中邮政编码输入有误，邮政编码应为6位数字!");
		return ;
	}else if(other20.indexOf("@")<0){
		alert("“学生个人联系信息”中电子信箱输入有误!");
		return ;
	}
	//学生个人扩展信息
	var other22 = $("#form_stuInfo").find("input:radio[name='other22']:checked").val();//是否独生子女
	var other23 = $("#form_stuInfo").find("input:radio[name='other23']:checked").val();//是否受过学前教育
	var other26 = $("#form_stuInfo").find("input:radio[name='other26']:checked").val();//是否孤儿
	var other27 = $("#form_stuInfo").find("input:radio[name='other27']:checked").val();//是否烈士或优抚子女
	var other28 = $.trim($("#sel_other28").val());//随班就读
	var other29 = $.trim($("#sel_other29").val());//残疾类型
	var other32 = $("#form_stuInfo").find("input:radio[name='other32']:checked").val();//是否享受一补
	if(other22==null||other23==null
			||other26==null||other27==null
			||other28==""||other29==""
			||other32==null){
		alert("请将“学生个人扩展信息”补充完整，正常儿童请选择“非随班就读”与“无残疾”!");
		return ;
	}
	var other29Check = {"非随班就读":"无残疾","视力残疾随班就读":"视力残疾","听力残疾随班就读":"听力残疾","智力残疾随班就读":"智力残疾","其他残疾随班就读":"言语残疾,肢体残疾,精神残疾,多重残疾,其他残疾"};
	if(other29Check[other28].indexOf(other29)<0){
		alert("“随班就读”与“残疾类型”选择不符！");
		return ;
	}
	//学生上下学交通方式
	var other33 = $.trim($("#ipt_other33").val());//上下学距离
	var other34 = $.trim($("#sel_other34").val());//上下学交通方式
	var other35 = $("#form_stuInfo").find("input:radio[name='other35']:checked").val();//是否需要乘坐校车
	if(other33==""){
		alert("上下学距离不能为空!");
		return ;
	}else if(!regex_other33.test(other33)){
		alert("上下学距离输入有误，请输入一个整数值!");
		return ;
	}
	if(other34==""){
		alert("请选择上下学交通方式!");
		return ;
	}
	if(other35==null){
		alert("请点选是否需要乘坐校车!");
		return ;
	}
	//学生家庭成员或监护人信息一
	var other36 = $.trim($("#ipt_other36").val());//姓名
//	var other37 = $.trim($("#ipt_other37").val());//关系
//	var other38 = $.trim($("#ipt_other38").val());//关系说明
	var other39 = $.trim($("#sel_other39").val());//民族
	var other40 = $.trim($("#ipt_other40").val());//工作单位
	var other41 = $.trim($("#ipt_other41").val());//现住址
	var other42 = $.trim($("#ipt_other42").val());//户口所在地
	var other43 = $.trim($("#ipt_other43").val());//联系电话
	var other44 = $("#form_stuInfo").find("input:radio[name='other44']:checked").val();//是否监护人
	var other45 = $.trim($("#sel_other45").val());//身份证件类型
	var other46 = $.trim($("#ipt_other46").val());//身份证件号
	var other47 = $.trim($("#ipt_other47").val());//职务
	if(other36==""||other39==""
			||other40==""||other41==""
//			||other42==""
			||other43==""
			||other44==null||other45==""
			||other46==""||other47==""){
		alert("请将“学生家庭成员或监护人信息一”补充完整，外籍人士“户口所在地”可不填写，但请选择正确的“身份证件类型”!");
		return ;
//	}else if(!regex_addressCode.test(other42)){
//		alert("“学生家庭成员或监护人信息一”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
//		return ;
	}else if(!regex_mobile.test(other43)){
		alert("“学生家庭成员或监护人信息一”中联系电话输入有误，联系电话应为11手机号!");
		return ;
	}else if(other45=="CARD_TYPE1"){
		var checkCardNoRes = IdentifyCodeValid_2(other46,false);
		if(!checkCardNoRes.pass){
			alert("“学生家庭成员或监护人信息一”中身份证件号输入有误："+checkCardNoRes.msg);
			return ;
		}
	}else if(other45=="CARD_TYPE7"){
	}else{
		if(!regex_card.test(other46)){
			alert("“学生家庭成员或监护人信息一”中身份证件号输入有误，身份证件号只能包含数字与字母！");
			return ;
		}
	}
	
	if(other45=="CARD_TYPE1"){//身份证
		if(other42==""||!regex_addressCode.test(other42)){
			alert("“学生家庭成员或监护人信息一”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
			return ;
		}
	}else{
		if(other42!=""&&!regex_addressCode.test(other42)){
			alert("“学生家庭成员或监护人信息一”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值，外籍人士可不填写!");
			return ;
		}
	}
	
	//学生家庭成员或监护人信息二
	var other48 = $.trim($("#ipt_other48").val());//姓名
//	var other49 = $.trim($("#ipt_other49").val());//关系
//	var other50 = $.trim($("#ipt_other50").val());//关系说明
	var other51 = $.trim($("#sel_other51").val());//民族
	var other52 = $.trim($("#ipt_other52").val());//工作单位
	var other53 = $.trim($("#ipt_other53").val());//现住址
	var other54 = $.trim($("#ipt_other54").val());//户口所在地
	var other55 = $.trim($("#ipt_other55").val());//联系电话
	var other56 = $("#form_stuInfo").find("input:radio[name='other56']:checked").val();//是否监护人
	var other57 = $.trim($("#sel_other57").val());//身份证件类型
	var other58 = $.trim($("#ipt_other58").val());//身份证件号
	var other59 = $.trim($("#ipt_other59").val());//职务
	if(other48==""||other51==""
			||other52==""||other53==""
//			||other54==""
			||other55==""
			||other56==null||other57==""
			||other58==""||other59==""){
		alert("请将“学生家庭成员或监护人信息二”补充完整，外籍人士“户口所在地”可不填写，但请选择正确的“身份证件类型”!");
		return ;
//	}else if(!regex_addressCode.test(other54)){
//		alert("“学生家庭成员或监护人信息二”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
//		return ;
	}else if(!regex_mobile.test(other55)){
		alert("“学生家庭成员或监护人信息二”中联系电话输入有误，联系电话应为11手机号!");
		return ;
	}else if(other57=="CARD_TYPE1"){
		var checkCardNoRes = IdentifyCodeValid_2(other58,false);
		if(!checkCardNoRes.pass){
			alert("“学生家庭成员或监护人信息二”中身份证件号输入有误："+checkCardNoRes.msg);
			return ;
		}
	}else if(other57=="CARD_TYPE7"){
	}else{
		if(!regex_card.test(other58)){
			alert("“学生家庭成员或监护人信息二”中身份证件号输入有误，身份证件号只能包含数字与字母！");
			return ;
		}
	}
	
	if(other57=="CARD_TYPE1"){//身份证
		if(other54==""||!regex_addressCode.test(other54)){
			alert("“学生家庭成员或监护人信息二”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值!");
			return ;
		}
	}else{
		if(other54!=""&&!regex_addressCode.test(other54)){
			alert("“学生家庭成员或监护人信息二”户口所在地输入有误，请下载“行政区划代码查询表”并参照该表输入相应值，外籍人士可不填写!");
			return ;
		}
	}
	
	var postData = $("#form_stuInfo").formSerialize();
	var params = {};
	params.url = student_saveStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = postData;
	params.postType = "get";
	params.error = "修改失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			if(isAdmin){
				alert("修改成功！");
				showStuInfoDetail($("#ipt_stuId").val(),false);
			}else{
				alert("重要提示：保存成功，确定所填信息无误后请及时将表单提交，未在规定时间内提交的表单将被视为无效表单！");
				doLoadStuInfo();
			}
		}else{
			if(json.errorMsg==null){
				alert("修改失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function submitStuInfo(){
	if(!confirm("您确定要将表单提交吗，提交后表单内容将“永久锁定”！")){
		return ;
	}
	var id = $("#ipt_id").val();
	var params = {};
	params.url = student_submitStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			doLoadStuInfo();
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showStuInfoDownloadConfirm(id){
	var params = {};
	params.submitMethod = doStuInfoDownload;
	params.submitData = {id:id};
	params.modelId = "model_stuInfoDownloadExplain";
	params.title = "下载提示";
	params.submitTxt = "马上下载";
	params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function showStuInfoPrintConfirm(id){
	var params = {};
	params.submitMethod = doStuInfoPrint;
	params.submitData = {id:id};
	params.modelId = "model_printExplain";
	params.title = "打印提示";
	params.submitTxt = "马上打印";
	params.cancelTxt = "暂不打印";
	showDialogConfirm380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function doStuInfoDownload(evt){
	$("#dialog_confirm_380").hide();
	window.open(student_downloadStuInfoUrl+evt.data.id);
}
function doStuInfoPrint(evt){
	var isIE = (document.all) ? true : false;
	if(!isIE){
		alert("当前浏览器可能无法使用打印功能，请确保您当前是在IE浏览器下进行的操作！");
	}
	$("#dialog_confirm_380").hide();
	window.open(student_printStuInfoUrl+evt.data.id);
}
/*************************入托、入学儿童预防接种证查验登记*******************************/
function doLoadStuVaccine(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");
	
	var params = {};
	params.url = student_loadStuVaccineUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_stuVaccineDisplay","div_display",json.data);
			$("#div_display").find("input[type='radio']").radioFn();
			if(json.data.id==null||json.data.id==0){
				if(json.data.locked){
					$("#div_display").html("当前不是填表时间！");
				}else{
					showStuInfoModify();
				}
			}else{
				$("#form_stuVaccine").find("input").attr("disabled",true);
				$("#div_stuVaccine_cover").css({"height":$("#form_stuVaccine").height(),"width":$("#form_stuVaccine").width()});
			}
		}else{
			if(json.errorMsg==null){
				$("#div_display").html("加载失败！");
			}else{
				$("#div_display").html(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function convertInt2BooleanEnum(val){
	if(val==0){
		return "FALSE";
	}else if(val==1){
		return "TRUE";
	}else{
		return "";
	}
}
function showStuVaccineModify(){
	$("#div_stuVaccine_cover").hide();
	$("#btn_refresh").hide();
	$("#btn_showModify").hide();
	$("#btn_submit2Review").hide();
	$("#btn_doModify").show();
	$("#btn_doModify_2").show();
	$("#btn_cancelModify").show();
	$("#btn_cancelModify_2").show();
	$("#form_stuVaccine").find("input").attr("disabled",false);
}
function doCancelStuVaccineModify(){
	if(!confirm("您确定要取消当前的修改吗？")){
		return ;
	}
	doLoadStuVaccine();
}
function doSaveStuVaccine(isAdmin){
	var other1 = $("#form_stuVaccine").find("input:radio[name='other1']:checked").val();//是否有接种证
//	var other2 = $("#form_stuVaccine").find("input:radio[name='other2']:checked").val();//是否全种
	var other3 = $("#form_stuVaccine").find("input:radio[name='other3']:checked").val();//乙肝疫苗1
	var other4 = $("#form_stuVaccine").find("input:radio[name='other4']:checked").val();//乙肝疫苗2
	var other5 = $("#form_stuVaccine").find("input:radio[name='other5']:checked").val();//乙肝疫苗3
	var other6 = $("#form_stuVaccine").find("input:radio[name='other6']:checked").val();//卡介苗
	var other7 = $("#form_stuVaccine").find("input:radio[name='other7']:checked").val();//脊灰疫苗1
	var other8 = $("#form_stuVaccine").find("input:radio[name='other8']:checked").val();//脊灰疫苗2
	var other9 = $("#form_stuVaccine").find("input:radio[name='other9']:checked").val();//脊灰疫苗3
	var other10 = $("#form_stuVaccine").find("input:radio[name='other10']:checked").val();//脊灰疫苗4
	var other11 = $("#form_stuVaccine").find("input:radio[name='other11']:checked").val();//百白破疫苗1
	var other12 = $("#form_stuVaccine").find("input:radio[name='other12']:checked").val();//百白破疫苗2
	var other13 = $("#form_stuVaccine").find("input:radio[name='other13']:checked").val();//百白破疫苗3
	var other14 = $("#form_stuVaccine").find("input:radio[name='other14']:checked").val();//百白破疫苗4
	var other15 = $("#form_stuVaccine").find("input:radio[name='other15']:checked").val();//白破疫苗
	var other16 = $("#form_stuVaccine").find("input:radio[name='other16']:checked").val();//麻风疫苗
	var other17 = $("#form_stuVaccine").find("input:radio[name='other17']:checked").val();//麻腮疫苗
	var other18 = $("#form_stuVaccine").find("input:radio[name='other18']:checked").val();//乙脑疫苗1
	var other19 = $("#form_stuVaccine").find("input:radio[name='other19']:checked").val();//乙脑疫苗2
	var other20 = $("#form_stuVaccine").find("input:radio[name='other20']:checked").val();//A群流脑疫苗1
	var other21 = $("#form_stuVaccine").find("input:radio[name='other21']:checked").val();//A群流脑疫苗2
	var other22 = $("#form_stuVaccine").find("input:radio[name='other22']:checked").val();//A+C群流脑疫苗1
	var other23 = $("#form_stuVaccine").find("input:radio[name='other23']:checked").val();//A+C群流脑疫苗2
	var other24 = $("#form_stuVaccine").find("input:radio[name='other24']:checked").val();//甲肝疫苗
	if(other1==null){alert("是否有接种证未选！");return ;}
//	if(other2==null){alert("是否全种未选！");return ;}
	if(other3==null){alert("乙肝疫苗1未选！");return ;}
	if(other4==null){alert("乙肝疫苗2未选！");return ;}
	if(other5==null){alert("乙肝疫苗3未选！");return ;}
	if(other6==null){alert("卡介苗未选！");return ;}
	if(other7==null){alert("脊灰疫苗1未选！");return ;}
	if(other8==null){alert("脊灰疫苗2未选！");return ;}
	if(other9==null){alert("脊灰疫苗3未选！");return ;}
	if(other10==null){alert("脊灰疫苗4未选！");return ;}
	if(other11==null){alert("百白破疫苗1未选！");return ;}
	if(other12==null){alert("百白破疫苗2未选！");return ;}
	if(other13==null){alert("百白破疫苗3未选！");return ;}
	if(other14==null){alert("百白破疫苗4未选！");return ;}
	if(other15==null){alert("白破疫苗未选！");return ;}
	if(other16==null){alert("麻风疫苗未选！");return ;}
	if(other17==null){alert("麻腮疫苗未选！");return ;}
	if(other18==null){alert("乙脑疫苗1未选！");return ;}
	if(other19==null){alert("乙脑疫苗2未选！");return ;}
	if(other20==null){alert("A群流脑疫苗1未选！");return ;}
	if(other21==null){alert("A群流脑疫苗2未选！");return ;}
	if(other22==null){alert("A+C群流脑疫苗1未选！");return ;}
	if(other23==null){alert("A+C群流脑疫苗2未选！");return ;}
	if(other24==null){alert("甲肝疫苗未选！");return ;}
	
	var postData = $("#form_stuVaccine").formSerialize();
	var params = {};
	params.url = student_saveStuVaccineUrl+"?_t="+new Date().getTime();
	params.postData = postData;
	params.postType = "get";
	params.error = "修改失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			if(isAdmin){
				alert("修改成功！");
				showStuVaccineDetail($("#ipt_stuId").val(),false);
			}else{
				alert("重要提示：保存成功，确定所填信息无误后请及时将表单提交，未在规定时间内提交的表单将被视为无效表单！");
				doLoadStuVaccine();
			}
		}else{
			if(json.errorMsg==null){
				alert("修改失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function submitStuVaccine(){
	if(!confirm("您确定要将表单提交吗，提交后表单内容将“永久锁定”！")){
		return ;
	}
	var id = $("#ipt_id").val();
	var params = {};
	params.url = student_submitStuVaccineUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			doLoadStuVaccine();
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}

/********************招生老师操作（入学登记表）*****************************/
function doLoadStuApplyList(){
	useModel("model_stuApplyListDisplay","div_display",{year:getCurrentYear()});
	$("#ipt_pageNo").unbind("click");
	$("#ipt_pageNo").bind("click",doToPage_stuApplyList);
//	$("#div_sel").find("select").seleFn();
	toPage(1);
}
function doToPage_stuApplyList(){
	var pageNo = $("#ipt_pageNo").val();
	var keyword = $("#ipt_keyword").val();
	var year = $("#sel_year").val();
	var ageScope = $("#sel_ageScope").val();
	var other54 = $("#sel_type").val();
	var sex = $("#sel_sex").val();
	var status = $("#sel_status").val();
	var infoStatus = $("#sel_infoStatus").val();
	var vaccineStatus = $("#sel_vaccineStatus").val();
    var school = $("#sel_school").val();
	
	var params = {};
	params.url = student_loadStuApplyListUrl+"?_t="+new Date().getTime();
	params.postData = {pageNo:pageNo,keyword:keyword,year:year,ageScope:ageScope,other54:other54,sex:sex,status:status,infoStatus:infoStatus,vaccineStatus:vaccineStatus,school:school};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			json.data.currentYear = getCurrentYear();
			useModel("model_stuApplyList","div_display_list",json.data);
		}
	};
	ajaxJSON(params);
}
function showStuApplyList(){
	$("#div_back").hide();
	$("#div_sel").show();
	$("#div_search").show();
	toPage(0);
}
function showStuApplyDetail(id,isEdit){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();

	var params = {};
	params.url = student_loadStuApplyUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			json.data.showReview = (json.data.statusStr=="REVIEW_PASS"||json.data.statusStr=="REVIEW_REFUSE"||json.data.statusStr=="REVIEW_WAITING");
			useModel("model_stuApplyView","div_display_list",json.data);
			$("#div_display_list").find("input[type='radio']").radioFn();
			{
				$("#sel_nation").val(json.data.nation);
				$("#sel_jkzt").val(json.data.jkzt);
				$("#sel_citizenship").val(json.data.citizenship);
				$("#sel_cardType").val(json.data.cardTypeStr);
				$("#sel_gatqw").val(json.data.gatqw);
				$("#sel_citizenship_other35").val(json.data.other35);
				$("#sel_other6").val(json.data.other6);
				$("#sel_other23").val(json.data.other23);
				$("#div_display_list").find("select").seleFn();
			}
			changeStuApplyType(json.data.typeStr);
			var id= $("#ipt_id").val();
			doloadStuApplyAtt(id,json.data.typeStr);
			if(isEdit){
				$("#div_modify").show();
			}else{
				$("#form_stuApply").find("input").attr("disabled",true);
				$("#div_stuApply_cover").css({"height":$("#form_stuApply").height(),"width":$("#form_stuApply").width()});
			}
		}
	};
	ajaxJSON(params);
}
function submitAll(){
	if(!confirm("此操作只有在“改表时段”结束后才能执行成功,您确定要进行此操作吗？")){
		return ;
	}

	var params = {};
	params.url = student_submitAllUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功："+json.errorMsg);
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert("操作失败："+json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function resetAllInfo(){
	if(!confirm("此操作只有在“学籍信息录入”结束后才能执行成功,您确定要进行此操作吗？")){
		return ;
	}

	var params = {};
	params.url = student_resetAllInfoUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功："+json.errorMsg);
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert("操作失败："+json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function submitAllInfo(){
	if(!confirm("此操作只有在“学籍信息录入”结束后才能执行成功,您确定要进行此操作吗？")){
		return ;
	}

	var params = {};
	params.url = student_submitAllInfoUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功："+json.errorMsg);
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert("操作失败："+json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showModifyClass(id,no){
	var name = $("#ele_name_"+id).html();
	var params = {};
	params.submitMethod = doModifyClass;
	params.modelId = "model_classInfo";
	params.modelData = {id:id,no:no};
	params.title = "给【"+name+"】分配班级";
	showDialog380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function doModifyClass(){
	var id = $.trim($("#class_ipt_id").val());
	var no = $.trim($("#class_ipt_no").val());
	if(no==""){
		alert("请填写班级编号！");
		return ;
	}
	
	var params = {};
	params.url = student_modifyClassUrl+"?_t="+new Date().getTime();
	params.postData = {id:id,no:no};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功");
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showMsg(id){
	var name = $("#ele_name_"+id).html();
	var message = $("#ele_message_"+id).html();
	var params = {};
	params.submitMethod = doSaveMsg;
	params.modelId = "model_msgInfo";
	params.modelData = {id:id,message:message};
	params.title = "给【"+name+"】的家长留言";
	showDialog380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function doSaveMsg(){
	var id = $.trim($("#review_ipt_id").val());
	var message = $.trim($("#review_ipt_message").val());
	
	var params = {};
	params.url = student_stuApplySaveMsgUrl+"?_t="+new Date().getTime();
	params.postData = {id:id,message:message};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功");
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
function showReview(id){
	var name = $("#ele_name_"+id).html();
	var status = $("#ele_statusStr_"+id).html();
	var reviewer = $("#ele_reviewer_"+id).html();
	var dateOfReview = $("#ele_dateOfReviewStr_"+id).html();
	var note = $("#ele_note_"+id).html();
	var message = $("#ele_message_"+id).html();
	var params = {};
	params.submitMethod = doReview;
	params.modelId = "model_reviewInfo";
	params.modelData = {id:id,status:status,reviewer:reviewer,dateOfReview:dateOfReview,note:note,message:message};
	params.title = "审核学生【"+name+"】";
	showDialog380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function doReview(){
	var id = $.trim($("#review_ipt_id").val());
	var status = $.trim($("#review_sel_status").val());
	var reviewer = $.trim($("#review_ipt_reviewer").val());
	var dateOfReview = $.trim($("#review_ipt_dateOfReview").val());
	var note = $.trim($("#review_ipt_note").val());
	var message = $.trim($("#review_ipt_message").val());
	if(reviewer==""){
		alert("请填写审核人！");
		return ;
	}
	if(dateOfReview==""){
		alert("请选择审核日期！");
		return ;
	}
	
	var params = {};
	params.url = student_stuApplyReviewUrl+"?_t="+new Date().getTime();
	params.postData = {id:id,status:status,reviewer:reviewer,dateOfReview:dateOfReview,note:note,message:message};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			alert("操作成功");
			toPage(0);
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}
/********************批量审核****************************************/
function checkAll(obj,formId,eleName){
	$("#"+formId).find("input[name='"+eleName+"']").attr("checked",obj.checked);
	if(obj.checked){
		var count = $("#"+formId).find("input[name='"+eleName+"']").length;
		$("#span_selCount").html("已选 <span style='color:red;'>"+count+"</span> 人");
		$("#span_selCount_2").html("已选 <span style='color:red;'>"+count+"</span> 人");
		$("#tr_batchReview").show();
		$("#tr_batchReview_2").show();
	}else{
		$("#tr_batchReview").hide();
		$("#tr_batchReview_2").hide();
	}
}
function showBatchReviewOp(){
	var eles = $("#form_stuApplyList").find("input[name='ids']");
	var hasSelected = false;
	var count = 0;
	for(var i=0;i<eles.length;i++){
		if(eles[i].checked){
			hasSelected = true;
			count++ ;
		}
	}
	if(hasSelected){
		$("#span_selCount").html("已选 <span style='color:red;'>"+count+"</span> 人");
		$("#span_selCount_2").html("已选 <span style='color:red;'>"+count+"</span> 人");
		$("#tr_batchReview").show();
		$("#tr_batchReview_2").show();
	}else{
		$("#tr_batchReview").hide();
		$("#tr_batchReview_2").hide();
	}
}
function doBatchReview(status,desc){
	if(!confirm("您确定要将所选学生的状态置为【"+desc+"】吗？")){
		return ;
	}
	var postData = $("#form_stuApplyList").formSerialize();
	if(postData==""){
		return ;
	}
	postData += ("&status="+status);
	$.ajax({
		 url: student_stuApplyBatchReviewUrl+"?_t="+new Date().getTime(),
		 type: "post",
		 data:	postData,
		// async: true,
		 dataType: "json",
		 success: function(json){
			if(json.retCode==CODE_SUCCESS){
				alert("操作成功");
				toPage(0);
			}else{
				if(json.error_msg==null){
					alert("操作失败！");
				}else{
					alert(json.error_msg);
				}
			}
		},
		error:function(){
			alert("操作失败！");
		}
	});
}
/********************招生老师操作（“学生基本信息表”、“入托、入学儿童预防接种证查验登记表”）*****************************/
function showStuInfoDetail(stuId,isEdit){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();

	var params = {};
	params.url = student_loadStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {stuId:stuId};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_stuInfoView","div_display_list",json.data);
			$("#div_display_list").find("input[type='radio']").radioFn();
			
			//处理低版本IE浏览器下placeholder属性无效的问题
			$("#div_display").find("input").placeholder();
			{
				$("#sel_other28").val(json.data.other28);
				$("#sel_other29").val(json.data.other29);
				$("#sel_other34").val(json.data.other34);
				$("#sel_other39").val(json.data.other39);
				$("#sel_other45").val(json.data.other45);
				$("#sel_other51").val(json.data.other51);
				$("#sel_other57").val(json.data.other57);
				$("#div_display_list").find("select").seleFn();
			}
			
			if(isEdit){
				if(json.data.statusStr=="SUBMIT_ONCE"){
					$("#div_modify").show();
				}else{
					alert("家长未提交学籍信息，不可修改！");
					$("#form_stuInfo").find("input").attr("disabled",true);
					$("#div_stuInfo_cover").css({"height":$("#form_stuInfo").height(),"width":$("#form_stuInfo").width()});
				}
			}else{
				$("#form_stuInfo").find("input").attr("disabled",true);
				$("#div_stuInfo_cover").css({"height":$("#form_stuInfo").height(),"width":$("#form_stuInfo").width()});
			}
		}
	};
	ajaxJSON(params);
}
function showStuVaccineDetail(stuId,isEdit){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();

	var params = {};
	params.url = student_loadStuVaccineUrl+"?_t="+new Date().getTime();
	params.postData = {stuId:stuId};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_stuVaccineView","div_display_list",json.data);
			$("#div_display_list").find("input[type='radio']").radioFn();
			
			if(isEdit){
				if(json.data.statusStr=="SUBMIT_ONCE"){
					$("#div_modify").show();
				}else{
					alert("家长未提交预防信息，不可修改！");
					$("#form_stuVaccine").find("input").attr("disabled",true);
					$("#div_stuVaccine_cover").css({"height":$("#form_stuVaccine").height(),"width":$("#form_stuVaccine").width()});
				}
			}else{
				$("#form_stuVaccine").find("input").attr("disabled",true);
				$("#div_stuVaccine_cover").css({"height":$("#form_stuVaccine").height(),"width":$("#form_stuVaccine").width()});
			}
		}
	};
	ajaxJSON(params);
}
/********************数据导出*****************************/
function doLoadExport(){
	$("#ipt_pageNo").unbind("click");
	useModel("model_export","div_display",null);
	$("#div_display").find("select").seleFn();
}
function doExport(exportUrl){
	var keyword = $.trim($("#ipt_keyword").val());
	var year = $("#sel_year").val();
	var ageScope = $("#sel_ageScope").val();
	var type = $("#sel_type").val();
	var sex = $("#sel_sex").val();
	var status = $("#sel_status").val();
	var infoStatus = $("#sel_infoStatus").val();
	var vaccineStatus = $("#sel_vaccineStatus").val();
    var school = $("#sel_school").val();
	var param = "?_t="+new Date().getTime();
	if(keyword!=""){
		param += ("&keyword="+keyword);
	}
	param += ("&year="+year);
	if(ageScope!=""){
		param += ("&ageScope="+ageScope);
	}
	if(type!=""){
		param += ("&type="+type);
	}
	if(sex!=""){
		param += ("&sex="+sex);
	}
    if(school!=""){
        param += ("&school="+school);
    }
	if(status!=""){
		param += ("&status="+status);
	}
	if(infoStatus!=""){
		param += ("&infoStatus="+infoStatus);
	}
	if(vaccineStatus!=""){
		param += ("&vaccineStatus="+vaccineStatus);
	}
	alert("即将导出，请勿重复点击！");
	location.href = exportUrl+param;
}
/********************数据导入*****************************/
function doLoadImport(){
	$("#ipt_pageNo").unbind("click");
	useModel("model_import","div_display",null);
	$("#div_display").find("select").seleFn();
}
function doLoadGraImport(){
	$("#ipt_pageNo").unbind("click");
	useModel("model_gra_import","div_display",null);
	$("#div_display").find("select").seleFn();
}
function doLoadGraExport(){
	$("#ipt_pageNo").unbind("click");
	useModel("model_gra_export","div_display",null);
	$("#div_display").find("select").seleFn();
}
function doImport(){
	var dataField = $.trim($("#sel_dataField").val());
	var fileName = $("#ipt_file").val();
	if(dataField==""){
		alert("请选择要导入的数据项！");
		return ;
	}
	if (fileName=="") {
		alert("请选择要导入的文件！");
		return ;
	}
	$("#form_import").attr("action",student_importStuInfoUrl);
	$("#form_import").submit();
}
function doGraImport(){
	var year = $.trim($("#ipt_year").val());
	var fileName = $("#ipt_file").val();
	if(year==""){
		alert("请选择届数！");
		return ;
	}
	if (fileName=="") {
		alert("请选择要导入的文件！");
		return ;
	}
	$("#form_import").attr("action",student_importGraStuInfoUrl);
	$("#form_import").submit();
}
function doGraExport(){
	var fileName = $("#ipt_file").val();
	if (fileName=="") {
		alert("请选择基础数据文件！");
		return ;
	}
	$("#btn_export").hide();
	$("#btn_export_waiting").show();
	$("#form_export").attr("action",student_exportGraStuInfoUrl);
	$("#form_export").submit();
}
/**
 * 批量发送结果显示
 * @param retCode
 * @param msg
 * @return
 */
function import_callBack(retCode,msg){
	if(retCode==CODE_NOT_LOGIN){
		alert("请重新登陆！");
		location.href = ctx+"/";
		return ;
	}else if(msg!=null&&msg!="null"&&msg!=""){
	}else if(retCode==CODE_SUCCESS){
		msg = "导入成功！";
	}else if(msg==null||msg=="null"||msg==""){
		msg = "导入失败！";
	}
	$("#span_import_res").html(msg);
	$("#form_import").hide();
	$("#form_import_res").show();
	$("#span_file").html('<input id="ipt_file" name="file" type="file" accept="application/vnd.ms-excel" style="width: 174px;padding-top: 5px;" />');
}
/**
 * 隐藏批量发送结果
 * @return
 */
function import_res_hide(){
	$("#form_import_res").hide();
	$("#form_import").show();
}

/***************************毕业生信息列表***************************************/

function showGraStuInfoDownloadConfirm(id){
	var params = {};
	params.submitMethod = doGraStuInfoDownload;
	params.submitData = {id:id};
	params.modelId = "model_stuInfoDownloadExplain";
	params.title = "下载提示";
	params.submitTxt = "马上下载";
	params.cancelTxt = "暂不下载";
	showDialogConfirm380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function showGraStuInfoPrintConfirm(id){
	var params = {};
	params.submitMethod = doGraStuInfoPrint;
	params.submitData = {id:id};
	params.modelId = "model_printExplain";
	params.title = "打印提示";
	params.submitTxt = "马上打印";
	params.cancelTxt = "暂不打印";
	showDialogConfirm380(params);
	
	$("#dialog_380").find("select").seleFn();
}
function doGraStuInfoDownload(evt){
	$("#dialog_confirm_380").hide();
	window.open(student_downloadGraStuInfoUrl+evt.data.id);
}
function doGraStuInfoPrint(evt){
	var isIE = (document.all) ? true : false;
	if(!isIE){
		alert("当前浏览器可能无法使用打印功能，请确保您当前是在IE浏览器下进行的操作！");
	}
	$("#dialog_confirm_380").hide();
	window.open(student_printGraStuInfoUrl+evt.data.id);
}
/***
 * 毕业生信息载入
 */
function doLoadGraStuInfoList(){
	var userType = $("#ipt_userType").val();
	useModel("model_graStuInfoListDisplay","div_display",{userType:userType});
	$("#ipt_pageNo").unbind("click");
	$("#ipt_pageNo").bind("click",doToPage_graStuInfoList);
//	$("#div_sel").find("select").seleFn();
	toPage(1);
}
function doToPage_graStuInfoList(){
	var pageNo = $("#ipt_pageNo").val();
	var keyword = $("#ipt_keyword").val();
	var sylb = $("#sel_sylb").val();
	var xb = $("#sel_xb").val();
	var bj = $("#sel_bj").val();;
	var status = $("#sel_status").val();
	var params = {};
	params.url = student_loadGraStuInfoListUrl+"?_t="+new Date().getTime();
	params.postData = {pageNo:pageNo,keyword:keyword,sylb:sylb,xb:xb,bj:bj,status:status};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_graStuInfoList","div_display_list",json.data);
		}
	};
	ajaxJSON(params);
}

function graStuShowUnifiedHandling(json,modelId,displayId){
	useModel(modelId,displayId,json.data);
	//处理低版本IE浏览器下placeholder属性无效的问题
	$("#"+displayId).find("input").placeholder();
	{
		if(json.data.hkdz1N!=null){
			$("#td_hkdz").css("color","red");
			$("#sel_hkdz1N").val(json.data.hkdz1N);
		}else{
			$("#sel_hkdz1N").val(json.data.hkdz1);
		}
		if(json.data.jzxzN!=null){
			$("#td_jzxzN").css("color","red");
			$("#sel_jzxzN").val(json.data.jzxzN);
		}else{
			$("#sel_jzxzN").val(json.data.jzxz);
		}
		if(json.data.sylbNStr!=null){
			$("#td_sylbN").css("color","red");
			$("#sel_sylbN").val(json.data.sylbNStr);
		}else{
			$("#sel_sylbN").val(json.data.sylbStr);
		}
		if(json.data.mzN!=null){
			$("#td_mzN").css("color","red");
			$("#sel_mzN").val(json.data.mzN);
		}else{
			$("#sel_mzN").val(json.data.mz);
		}
		if(json.data.hkxzN!=null){
			$("#td_hkxzN").css("color","red");
			$("#sel_hkxzN").val(json.data.hkxzN);
		}else{
			$("#sel_hkxzN").val(json.data.hkxz);
		}
		if(json.data.dzNStr!=null){
			$("#td_dzN").css("color","red");
			$("#sel_dzN").val(json.data.dzNStr);
		}else{
			$("#sel_dzN").val(json.data.dzStr);
		}
		if((json.data.slNStr!=null)
				||(json.data.tlNStr!=null)
				||(json.data.zlNStr!=null)){
			$("#td_disability").css("color","red");
			if(json.data.slNStr==null){
				json.data.slNStr = json.data.slStr;
			}
			if(json.data.tlNStr==null){
				json.data.tlNStr = json.data.tlStr;
			}
			if(json.data.zlNStr==null){
				json.data.zlNStr = json.data.zlStr;
			}
			if(json.data.slNStr=="TRUE"){
				$("#sel_disability").val("视力残疾");
			}else if(json.data.tlNStr=="TRUE"){
				$("#sel_disability").val("听力残疾");
			}else if(json.data.zlNStr=="TRUE"){
				$("#sel_disability").val("智力残疾");
			}else{
				$("#sel_disability").val("无残疾");
			}
		}else{
			if(json.data.slStr=="TRUE"){
				$("#sel_disability").val("视力残疾");
			}else if(json.data.tlStr=="TRUE"){
				$("#sel_disability").val("听力残疾");
			}else if(json.data.zlStr=="TRUE"){
				$("#sel_disability").val("智力残疾");
			}else{
				$("#sel_disability").val("无残疾");
			}
		}
		$("#"+displayId).find("input[type='radio']").radioFn();
		$("#"+displayId).find("input[type='checkbox']").checkboxFn();
		$("#"+displayId).find("select").seleFn();
	}
}
function setChangeColor(td){
	$("#"+td).css("color","red");
}
function disabilityStatusControl(isNoDisability){
	
}
function doLoadGraStuInfo_print(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");
	
	var params = {};
	params.url = student_loadGraStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			useModel("model_graStuInfo_print","div_display",json.data);
		}
	};
	ajaxJSON(params);
}
function doShowGraStuInfoDisplay(){
	if(!$("#ipt_confirm")[0].checked){
		alert("请确认是否已阅读“温馨提示”！");
	}else{
		$("#div_graStuInfo_prompt").hide();
		$("#div_graStuInfoDisplay").show();
		showGraStuInfoModify();
	}
}
function doShowGraStuInfoPrint(){
	if(!$("#ipt_confirm")[0].checked){
		alert("请确认是否已阅读“操作指南”！");
	}else{
		$("#div_graStuInfo_guide").hide();
		$("#div_graStuInfo_print").show();
	}
}
function doLoadGraStuInfo(){
	$("#ipt_pageNo").unbind("click");
	$("#div_display").html("加载中...");
	
	var params = {};
	params.url = student_loadGraStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			json.data.showReview = false;
			graStuShowUnifiedHandling(json,"model_graStuInfoDisplay","div_display");
			if(json.data.id==null||json.data.id==0){
				if(json.data.locked){
					$("#div_display").html("当前不是填表时间！");
				}else{
					showGraStuInfoModify();
				}
			}else{
				$("#form_graStuInfo").find("input").attr("disabled",true);
				doLoadStuGraInfoAtt(json.data.id);
				$("#div_graStuInfo_cover").css({"height":$("#form_graStuInfo").height(),"width":$("#form_graStuInfo").width()});
			}
		}
	};
	ajaxJSON(params);
}

function showGraStuInfoDetail(id,isEdit){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();

	var params = {};
	params.url = student_loadGraStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "加载失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			graStuShowUnifiedHandling(json,"model_graStuInfoView","div_display_list");
			doLoadStuGraInfoAtt(id);
			if(isEdit){
				$("#div_modify").show();
			}else{
				$("#form_graStuInfo").find("input").attr("disabled",true);
				$("#div_graStuInfo_cover").css({"height":$("#form_graStuInfo").height(),"width":$("#form_graStuInfo").width()});
			}
		}
	};
	ajaxJSON(params);
}

function showGraStuInfoList(){
	$("#div_back").hide();
	$("#div_sel").show();
	$("#div_search").show();
	toPage(0);
}

function showGraStuInfoHistoryList(id){
	var params = {};
	params.url = student_gratuInfoHistoryListUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "获取失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			var name = $("#ele_name_"+id).html();
			var params = {};
			params.modelId = "model_graStuInfoHistoryList";
			params.modelData = json;
			params.title = "【"+name+"】的毕业登记表历史存档";
			showDialogView380(params);
		}else{
			if(json.errorMsg==null){
				alert("获取失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}



function loadGraStuInfoHistoryInfo(id){
	$("#div_sel").hide();
	$("#div_search").hide();
	$("#div_back").show();
	
	var params = {};
	params.url = student_graStuHistoryInfoUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "获取失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			$("#dialog_view_380").hide();
			json.data.title = "【"+$("#dialog_ele_name_"+id).html()+"】于"+$("#dialog_ele_timeStr_"+id).html()+"修改前的存档";
			graStuShowUnifiedHandling(json,"model_graStuHistoryInfo","div_display_list");
			$("#form_graStuInfo").find("input").attr("disabled",true);
			$("#div_graStuInfo_cover").css({"height":$("#form_graStuInfo").height(),"width":$("#form_graStuInfo").width()});
			
		}else{
			if(json.errorMsg==null){
				alert("获取失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}

function submitGraStuInfo(){
	if(!confirm("您确定所有信息已核对完毕吗，提交后表单内容将“永久锁定”！")){
		return ;
	}
	var id = $("#ipt_id").val();
	var params = {};
	params.url = student_submitGraStuInfoUrl+"?_t="+new Date().getTime();
	params.postData = {id:id};
	params.postType = "get";
	params.error = "操作失败";
	params.mustCallBack = false;//是否必须回调
	params.callBack = function (json){
		if(json.retCode==CODE_SUCCESS){
			doLoadGraStuInfo();
		}else{
			if(json.errorMsg==null){
				alert("操作失败！");
			}else{
				alert(json.errorMsg);
			}
		}
	};
	ajaxJSON(params);
}

function showGraStuApplyList(){
	$("#div_back").hide();
	$("#div_sel").show();
	$("#div_search").show();
	toPage(0);
}

/********************毕业信息登记表*****************************/
	function validategraInfo(){
		var regex_mobile = /^1\d{10}$/;//手机号
		
		var fudhN= $.trim($("#ipt_fudhN").val());
		var mudhN= $.trim($("#ipt_mudhN").val());
		if(fudhN!=""&&!regex_mobile.test(fudhN)){
			alert("父亲联系电话输入有误！");
			return false;
		}
		if(mudhN!=""&&!regex_mobile.test(mudhN)){
			alert("母亲联系电话输入有误！");
			return false;
		}
		return true;
	}
	
	function doReviewGraStuInfo_refuse(){//审核不通过
		if(!confirm("您确定要将该学生的变更信息标记为“审核不通过”吗？")){
			return ;
		}
		var id = $("#ipt_id").val();
		var params = {};
		params.url = student_refuseGraStuInfoUrl+"?_t="+new Date().getTime();
		params.postData = {id:id};
		params.postType = "get";
		params.error = "操作失败";
		params.mustCallBack = false;//是否必须回调
		params.callBack = function (json){
			if(json.retCode==CODE_SUCCESS){
				showGraStuInfoDetail($("#ipt_id").val(),false);
			}else{
				if(json.errorMsg==null){
					alert("操作失败！");
				}else{
					alert("操作失败："+json.errorMsg);
				}
			}
		};
		ajaxJSON(params);
	}
	/**
	 * 保存毕业生信息
	 */
	function doSaveGraStuInfo(isAdmin,url){
		if(!validategraInfo()){
			return;
		};
		if(!confirm("您确定所有信息已核对完毕吗？")){
			return ;
		}
		//ajax请求
		var postData = $("#form_graStuInfo").formSerialize();
		var params = {};
		params.url = url+"?_t="+new Date().getTime();
		params.postData = postData;
		params.postType = "get";
		params.error = "修改失败";
		params.mustCallBack = false;//是否必须回调
		params.callBack = function (json){
			if(json.retCode==CODE_SUCCESS){
				if(isAdmin){
					alert("操作成功");
					showGraStuInfoDetail($("#ipt_id").val(),false);
				}else{
					alert("操作成功");
					doLoadGraStuInfo();
				}
			}else{
				if(json.errorMsg==null){
					alert("修改失败，该表单的状态已经锁定！");
				}else{
					alert(json.errorMsg);
				}
			}
		};
		ajaxJSON(params);
	}
	function doCancelGraStuInfoModify(){
		if(!confirm("您确定要取消当前的修改吗？")){
			return ;
		}
		doLoadGraStuInfo();
	}
/********************毕业生图片*****************************/
	function doLoadStuGraInfoAtt(id){
		$("#div_stuGraInfoAtt").html("加载中...");
		$("#div_stuGraInfoAtt").show();
		var userType = $("#ipt_userType").val();
		var sta = $("#ipt_statusStr").val();
		var params = {};
		params.url = student_loadStuGraInfoAttUrl+"?_t="+new Date().getTime();	
		params.postData = {oid:id};
		params.postType = "get";
		params.error = "加载失败！";
		params.mustCallBack = true;//是否必须回调
		params.callBack = function (json){
			json.locked = userType!="GRADUATE";
			useModel("model_stuGraInfoAtt","div_stuGraInfoAtt",json);
		};
		ajaxJSON(params);
	}
	function doUploadStuGraInfoAtt(){
		var id= $("#ipt_id").val();
		var prefix=$.trim($("#ipt_prefix").val());
		if (prefix==""){
			alert("请输入图片名称");
			return ;
		}
		var fileName = $("#ipt_file").val();
		if (fileName=="") {
			alert("请选择要上传的图片！");
			return ;
		}else if (!fileName.toLowerCase().match(/.jpg|.jpeg|.gif|.png|.bmp/i)) {
			alert("图片格式不正确，请选择图片上传！");
			return ;
		}
		$("#btn_upload").hide();
		$("#btn_uploading").show();
		$("#form_uploadStuGraInfoAtt").attr("action",student_uploadStuGraInfoAttUrl); 
		$("#form_uploadStuGraInfoAtt").ajaxSubmit({ 
				type:"post",  //提交方式  
				dataType:"json", //数据类型  
				success: function(json){
					$("#btn_uploading").hide();
					$("#btn_upload").show();
					if(json.retCode==CODE_SUCCESS){
						$("#tr_noneStuGraInfoAtt").hide();
						var	_html=createHtmlUseModel("model_stuGraInfoAttEle",json);
						$("#tbody_stuGraInfoAttList").append(_html);
						alert("上传成功！");
						$("#ipt_prefix").val("");
						$("#span_file").html('<input id="ipt_file" type="file" name="file" accept="image/*"/ style="width: 174px;padding-top: 5px;"/>');
					}else{
						if(json.errorMsg==null){
							alert("上传失败！");
						}else{
							alert(json.errorMsg);
						}
					}
				}
		});  
	}
	function doDelStuGraInfoAtt(id){
		var name = $.trim($("#td_name_"+id).html());
		if(!confirm("您确定要删除图片【"+name+"】吗？")){
			return ;
		}
		var params={};
		params.url = student_delStuGraInfoAttUrl;
		params.postData = {id:id};
		params.postType = "post";
		params.error = "删除失败";
		params.mustCallBack = true;//是否必须回调
		params.callBack = function (json){
			if(json.retCode==CODE_SUCCESS){
				$("#tr_stuGraInfoAtt_"+id).remove();
				alert("删除成功");
				if($("#tbody_stuGraInfoAttList").find("tr").length==1){
					$("#tr_noneStuGraInfoAtt").show();
				}
			}else{
				if(json.errorMsg==null){
					alert("删除失败！");
				}else{
					alert("删除失败："+json.errorMsg);
				}
			}
		};
		ajaxJSON(params);
	}	
	function doDownloadStuGraInfoAtt(id,hash){
		window.open(student_downloadStuGraInfoAttUrl+"/"+hash+"/"+id);
	}
	function forwardGrade(id,isStu){
		var params = {};
		params.url = student_stuForwardGradeUrl+"?_t="+new Date().getTime();	
		params.postData = {id:id};
		params.postType = "get";
		params.error = "加载失败！";
		params.mustCallBack = true;//是否必须回调
		params.callBack = function (json){
			if(json.retCode==CODE_SUCCESS){
				if(isStu){
					var cardNo = $("#ipt_cardNo").val();
					alert("转级成功！账号重置为身份证号["+cardNo+"]，密码为身份证后六位！");
					doLoadStuApply();
				}else{
					alert("转级成功！");
					var pageNo = $("#ipt_pageNo").val();
					toPage(pageNo);
				}
			}else if(json.retCode==CODE_USER_EXIST){
				if(isStu){
					doLoadStuApply();
					alert("转级成功！");
				}else{
					var pageNo = $("#ipt_pageNo").val();
					var cardNo = $("#ele_cardNo_"+id).text();
					toPage(pageNo);
					alert("转级成功！但身份证号["+cardNo+"]已被注册！");
				}
			}else{
				if($.trim(json.errorMsg)==""){
					alert("转级失败！");
				}else{
					alert("转级失败："+json.errorMsg);
				}
			}
		};
		ajaxJSON(params);
	}
function downloadModel(){
	var dataField = $.trim($("#sel_dataField").val());
	var dataFieldStr = $.trim($("#sel_dataField").find("option:selected").text());
	if(dataField==""){
		alert("请选择要导入的数据项！");
		return ;
	}
	window.open(ctx+'/resource/doc/'+dataFieldStr+'模板.xlsx');
}