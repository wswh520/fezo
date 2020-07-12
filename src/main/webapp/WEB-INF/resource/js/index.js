$(document).ready(function (){
	var userType = $("#ipt_userType").val();
	if(userType=="SUPER"){
		initLeftMenu_super();
	}else if(userType=="ADMIN"){
		initLeftMenu_admin();
	}else if(userType=="PATRIARCH"){
		initLeftMenu_patriarch();
	}else if(userType=="GRADUATE"){
		initLeftMenu_gradute();
	}else if(userType=="GRADUATE_TEACHER"){
		initLeftMenu_gradute_teacher();
	}else if(userType=="HEALTH_TEACHER"){
		initLeftMenu_health_teacher();
	}else if(userType=="RECRUIT_TEACHER"){
		initLeftMenu_recruit_teacher();	
	}
});
function openMenu(menu){
	if($("#"+menu+"_menu_display").is(":visible")==true){
		$("#"+menu+"_menu_display").hide();
		$("#"+menu+"_menu_title").removeClass("hover");
		return ;
	}
	$("#div_menu").find("div[data-node='display']").hide();
	$("#div_menu").find("h5[data-node='title']").removeClass("hover");
	$("#"+menu+"_menu_title").addClass("hover");
	$("#"+menu+"_menu_display").show();
}
function selectMenu(menu,method){
	$("#ipt_pageNo").unbind("click");
	$("#ipt_pageNo").bind("click",method);
	toPage(1);
	$("#div_menu").find("a[data-node='menu']").removeClass("hover");
	$("#"+menu).addClass("hover");
}
function toPage(pn){
	$("#dialog_380").hide();
	$("#dialog_view_380").hide();
	$("#dialog_confirm_380").hide();
	if(pn>0){
		$("#ipt_pageNo").val(pn);
	}
	$("#ipt_pageNo").click();
}
/**
 * @param params
 * 			{
 * 				submitMethod:提交方法,
 * 				submitData:提交方法的参数,
 * 				modelId:模板Id,
 * 				modelData:模板数据,
 * 				title:标题
 * 			}
 * @return
 */
function showDialog380(params){
	if(params.submitData==null){
		params.submitData = {};
	}
	if(params.modelData==null){
		params.modelData = {} ;
	}
	$("#dialog_380_submit").unbind("click");
	$("#dialog_380_submit").bind("click",params.submitData,params.submitMethod);
	
	useModel(params.modelId,"dialog_380_display",params.modelData);
	$("#dialog_380_header").html(params.title);
	$("#dialog_380_submit_temp").hide();
	$("#dialog_380_submit").show();
	$("#dialog_380").jumpBox2(true);
}
function showDialogView380(params){
	if(params.submitData==null){
		params.submitData = {};
	}
	if(params.modelData==null){
		params.modelData = {};
	}
	
	useModel(params.modelId,"dialog_view_380_display",params.modelData);
	$("#dialog_view_380_header").html(params.title);
	$("#dialog_view_380").jumpBox2(true);
}
function showDialogConfirm380(params){
	if(params.submitData==null){
		params.submitData = {};
	}
	if(params.modelData==null){
		params.modelData = {};
	}
	if(params.submitTxt==null){
		params.submitTxt = "确定";
	}
	if(params.cancelTxt==null){
		params.cancelTxt = "取消";
	}
	$("#dialog_confirm_380_submit").unbind("click");
	$("#dialog_confirm_380_submit").bind("click",params.submitData,params.submitMethod);
	
	useModel(params.modelId,"dialog_confirm_380_display",params.modelData);
	$("#dialog_confirm_380_header").html(params.title);
	$("#dialog_confirm_380_submit").val(params.submitTxt);
	$("#dialog_confirm_380_cancel").val(params.cancelTxt);
	$("#dialog_confirm_380").jumpBox2(true);
}

function initLeftMenu_super(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"user","name":"用户管理","children":[]};
		menus[i].children[0] = {"menu":"admin","name":"学籍管理员","callback":"doLoadAdmin"};
		menus[i].children[1] = {"menu":"patriarch","name":"学生家长","callback":"doLoadPatriarch"};
		menus[i].children[2] = {"menu":"graduate_admin","name":"招生老师","callback":"doLoadrecruit_teacher"};
		menus[i].children[3] = {"menu":"health_teacher","name":"保健老师","callback":"doLoadhealth_teacher"};
		menus[i].children[4] = {"menu":"graduate_teacher","name":"毕业班老师","callback":"doLoadgraduate_teacher"};
	
		
		i++;
	}
	{
		menus[i] = {"menu":"timeConfig","name":"招生信息系统设置","children":[]};
		menus[i].children[0] = {"menu":"serve","name":"服务时段","callback":"doLoadTimeConfig_serve"};
		menus[i].children[1] = {"menu":"signIn","name":"注册时段","callback":"doLoadTimeConfig_signIn"};
		menus[i].children[2] = {"menu":"applyInput","name":"填表时段","callback":"doLoadTimeConfig_applyInput"};
		menus[i].children[3] = {"menu":"applyModify","name":"改表时段","callback":"doLoadTimeConfig_applyModify"};
		menus[i].children[4] = {"menu":"infoInput","name":"信息录入时段","callback":"doLoadTimeConfig_infoInput"};
		i++;
	}
	{
		menus[i] = {"menu":"gra_timeConfig","name":"毕业生信息系统设置","children":[]};
		menus[i].children[0] = {"menu":"gra_serve","name":"服务时段","callback":"doLoadTimeConfig_graServe"};
		menus[i].children[1] = {"menu":"gra_applyInput","name":"填表时段","callback":"doLoadTimeConfig_graInfoInput"};
		i++;
	}
	{
		menus[i] = {"menu":"base","name":"密码修改","children":[]};
		menus[i].children[3] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('user');
	selectMenu('user_menu_admin',doLoadAdmin);
	
}
function initLeftMenu_admin(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"user","name":"招生管理","children":[]};
		menus[i].children[0] = {"menu":"stuApplyList","name":"学生信息","callback":"doLoadStuApplyList"};
		menus[i].children[1] = {"menu":"patriarch","name":"学生家长","callback":"doLoadPatriarch"};
//		menus[i].children[2] = {"menu":"export","name":"数据导出","callback":"doLoadExport"};
		menus[i].children[2] = {"menu":"import","name":"信息导入","callback":"doLoadImport"};
		i++;
	}
	{
		menus[i] = {"menu":"gra_user","name":"毕业生管理","children":[]};
		menus[i].children[0] = {"menu":"graStuInfoList","name":"毕业生信息","callback":"doLoadGraStuInfoList"};
		menus[i].children[1] = {"menu":"graImport","name":"信息导入","callback":"doLoadGraImport"};
		menus[i].children[2] = {"menu":"graExport","name":"信息导出","callback":"doLoadGraExport"};
		i++;
	}
	{
		menus[i] = {"menu":"base","name":"密码修改","children":[]};
		menus[i].children[3] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('user');
	selectMenu('user_menu_stuApplyList',doLoadStuApplyList);
}
function initLeftMenu_patriarch(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"base","name":"基本功能","children":[]};
//		menus[i].children[0] = {"menu":"stuApplyStatus","name":"状态查询","callback":"doLoadStuApplyStatus"};
		menus[i].children[1] = {"menu":"stuApply","name":"学生报名表","callback":"doLoadStuApply"};
		// menus[i].children[2] = {"menu":"stuInfo","name":"学籍表信息录入","callback":"doLoadStuInfo"};
		// menus[i].children[3] = {"menu":"stuVaccine","name":"预防接种登记","callback":"doLoadStuVaccine"};
		menus[i].children[4] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('base');
	selectMenu('base_menu_stuApply',doLoadStuApply);
}

function initLeftMenu_gradute(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"base","name":"基本功能","children":[]};
		menus[i].children[0] = {"menu":"graStuInfo","name":"信息核对","callback":"doLoadGraStuInfo"};
		menus[i].children[1] = {"menu":"graStuInfo_print","name":"预览打印","callback":"doLoadGraStuInfo_print"};
		menus[i].children[2] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('base');
	selectMenu('base_menu_graStuInfo',doLoadGraStuInfo);
}

//function initLeftMenu_admin_gradute(){
//var menus = [];
//var i = 0;
//{
//	menus[i] = {"menu":"base","name":"基本功能","children":[]};
//	menus[i].children[0] = {"menu":"admin_graimport","name":"毕业生信息列表","callback":"doLoadGraStuInfo"};
//	menus[i].children[1] = {"menu":"admin_graimport","name":"毕业生信息导入","callback":"doLoadGraImport"};
//	i++;
//}
//useModel("model_leftMenu","div_menu",{menus:menus});
//openMenu('base');
//selectMenu('base_menu_stuApply',doLoadGraStuInfo);
//}

function initLeftMenu_recruit_teacher(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"base","name":"基本功能","children":[]};
		menus[i].children[0] = {"menu":"stuApplyList","name":"学生信息","callback":"doLoadStuApplyList"};
		menus[i].children[1] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('base');
	selectMenu('base_menu_stuApplyList',doLoadStuApplyList);
	}

function initLeftMenu_health_teacher(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"base","name":"基本功能","children":[]};
		menus[i].children[0] = {"menu":"stuApplyList","name":"学生信息","callback":"doLoadStuApplyList"};
		menus[i].children[1] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('base');
	selectMenu('base_menu_stuApplyList',doLoadStuApplyList);
}

function initLeftMenu_gradute_teacher(){
	var menus = [];
	var i = 0;
	{
		menus[i] = {"menu":"base","name":"基本功能","children":[]};
		menus[i].children[0] = {"menu":"graStuInfo","name":"毕业生信息","callback":"doLoadGraStuInfoList"};
		menus[i].children[1] = {"menu":"pwd","name":"密码修改","callback":"doLoadPwd"};
		i++;
	}
	
	useModel("model_leftMenu","div_menu",{menus:menus});
	openMenu('base');
	selectMenu('base_menu_graStuInfo',doLoadGraStuInfoList);
	}

