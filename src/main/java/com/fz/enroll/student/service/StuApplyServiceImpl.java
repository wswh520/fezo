package com.fz.enroll.student.service;

import java.io.*;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.fz.common.util.MD5;
import com.fz.common.util.Word2PdfUtil;
import com.fz.enroll.entity.student.*;
import com.fz.enroll.file.dao.FileDAO;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSON;
import com.fz.base.service.BaseServiceUtils;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.dto.user.UserAdd;
import com.fz.enroll.entity.file.Attachment;
import com.fz.enroll.entity.file.FileMeta;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.file.service.AttachmentService;
import com.fz.enroll.student.dao.StuApplyDAO;
import com.fz.enroll.student.dao.StuInfoDAO;
import com.fz.enroll.student.dao.StuVaccineDAO;
import com.fz.enroll.user.service.UserService;

@SuppressWarnings("restriction")
@Service("stuApplyService")
public class StuApplyServiceImpl implements StuApplyService {
	private Logger logger = LoggerFactory.getLogger(StuApplyServiceImpl.class);

	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private StuApplyDAO stuApplyDao;
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private FileDAO fileDao;
	@Autowired
	private StuInfoDAO stuInfoDao;
	@Autowired
	private StuVaccineDAO stuVaccineDao;
	@Autowired
	private UserService userService;

	@Override
	public Response loadService(String idStr) {
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		StuApply stuApply = null;
		if(id!=0){
			stuApply = stuApplyDao.queryById(id);
		}else{
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
			if(stuApply!=null&&stuApply.getStatus()==StuApplyStatus.SUBMIT_TWICE.val()){
				StuInfo info = stuInfoDao.queryByStuId(stuApply.getId());
				StuVaccine vaccine = stuVaccineDao.queryByStuId(stuApply.getId());
				if(info!=null){
					stuApply.setInfoStatus(info.getStatus());
				}
				if(vaccine!=null){
					stuApply.setVaccineStatus(vaccine.getStatus());
				}
			}
		}
		stuApply = stuApply!=null?stuApply:new StuApply();
		
		Response checkRes = this.checkOpAuth(stuApply);
//		if(checkRes.getRetCode()!=ReturnCode.SUCCESS){
//			stuApply.setLocked(true);
//		}
		
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(stuApply);
		return res;
	}
	
	@Override
	public synchronized Response saveService(StuApply entity){
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response();
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		if(entity.getId()==0){//新增
			if(utype!=UserType.PATRIARCH){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("没有权限");
				return res;
			}
			res = timeConfigService.checkServeTime(TimeConfigType.APPLY_INPUT);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
			entity.setStatus(StuApplyStatus.SUBMIT_NONE.val());
			
			StuApply exist = stuApplyDao.queryByUnique(entity);
			if(entity.getCardNo()==null
					||!entity.getCardNo().toLowerCase().equals(Utils.getCurrentUsername())){//账号不区分大小写
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("身份证件号必须与登陆账号一致");
				return res;
			}
			if(exist!=null&&exist.getUid()!=entity.getUid()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("身份证件号已被登记");
				return res;
			}else if(exist==null){
				exist = stuApplyDao.queryByUid(entity.getUid());
			}
			// 20200708 身份证号码校验，控制报名学生出生年月在2013年9月2号至 2014年8月31号
			String cardNo = entity.getCardNo();
			String reg = "[\u4e00-\u9fa5]";
			Pattern pat = Pattern.compile(reg);
			String dateOfBirth =pat.matcher(entity.getDateOfBirthStr()).replaceAll("");
			if(!StringUtils.isEmpty(cardNo) && !StringUtils.isEmpty(dateOfBirth)){
				int i = cardNo.indexOf(dateOfBirth+"");
				if(i <= 0){
					res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
					res.setErrorMsg("报名学生出生年月在2013年9月2号至2014年8月31号");
					return res;
				}
			}
			int uc = 0;
			if(exist==null){
				String no = this.getNo(entity.getName());
				if(StringUtils.isEmpty(no)){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚
					res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
					res.setErrorMsg("报名号生成失败，请稍后重试！");
					return res;
				}
				entity.setNo(no);
				uc = stuApplyDao.save(entity);
			}else{
				uc = stuApplyDao.update(entity);
			}
//			if (entity.getId()>0) {
//				res = batchUpdateStuApplyAttService(entity.getId());
//				if (res.getRetCode()!=ReturnCode.SUCCESS) {
//					return res;
//				}
//			}
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}else{//修改
			StuApply exist = stuApplyDao.queryById(entity.getId());
			res = this.checkOpAuth(exist);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_ONCE.val()
					||exist.getStatus()==StuApplyStatus.SUBMIT_TWICE.val()){
				//保存存档
				StuApplyHistory history = new StuApplyHistory();
				history.setMid(exist.getId());
				history.setUid(Utils.getCurrentUid());
				history.setData(JSON.toJSONString(exist));
				history.setTime(System.currentTimeMillis());
				int uc = stuApplyDao.saveHistory(history);
				if(uc==0){
					return new Response(ReturnCode.SERVER_INNER_ERROR);
				}
			}
			
			entity.setUid(exist.getUid());
			entity.setStatus(exist.getStatus());
			exist = stuApplyDao.queryByUnique(entity);
			if(exist!=null&&exist.getUid()!=entity.getUid()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("身份证件号已被登记");
				return res;
			}
			int uc = stuApplyDao.update(entity);
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}
	}

	/**
	 * 学校分三个校区，学生填报后需要按报名顺序依次派发到3个校区中的4个考场里，学生根据报名号来确定面试的考场地点和时间。
	 * 报名号生成规则为 J01XXX，Z01XXX，S01XXX
	 * 报名号规则：
	 * 1 第一个字母 为  校区缩写
	 *   J  钟家村寄宿学校（汉阳区北城路28号）
	 *   Z  钟家村小学（西村路2号）
	 *   S  钟家村小学三里坡校区（马鹦路143号）
	 * 2 每个学校分别有4个考场，报名人员根据填报顺序依次安排至3个学校的4个考场中
	 *   如  第一名报名的，报名号为  J01001
	 *       第二名报名的，报名号为  Z01001
	 *       第三名报名的，报名号为  S01001
	 *       第四名报名的，报名号为  J01002
	 *       ……
	 *   各学校的4个考场最多只能安排60名学生，如果最后还有超过的人员，则依次平均安排到每个学校的4号考场里。
	 * 3 每个考场有3场面试，根据考号分配，来确定学生参与哪一场面试
	 *   0-20号  第一场面试  8：30到指定校区
	 *   21-40号 第二场面试  10：30到指定校区
	 *   41号以后 第三场面试  14：30到指定校区
	 * @return
	 */
	@Override
	public String getNo(String name){//获取报名号
//	@Override
//	public int getNo(int year,int type){//获取报名号
//		Year entity = stuApplyDao.queryYear(year);
//		if(entity==null){
//			entity = new Year();
//			entity.setYear(year);
//			stuApplyDao.saveYear(entity);
//		}
//		if(type==StuType.TYPEA.val()){
//			int uc = stuApplyDao.incYearTypeA(entity);
//			if(uc>0){
//				return entity.getTypeA()+1;
//			}
//		}else if(type==StuType.TYPEB.val()){
//			int uc = stuApplyDao.incYearTypeB(entity);
//			if(uc>0){
//				return entity.getTypeB()+1;
//			}
//		}else if(type==StuType.TYPEC.val()){
//			int uc = stuApplyDao.incYearTypeC(entity);
//			if(uc>0){
//				return entity.getTypeC()+1;
//			}
//		}
//		return 0;

		//20200708
		String str = "";
		//查询编号
		String indexNo = stuApplyDao.queryTbStuno();
		int no = 1;
		if(!StringUtils.isEmpty(indexNo)){
			no = Integer.parseInt(indexNo) + 1;
		}
		//取模（0:S，1:J，2:Z）
		int school = Math.floorMod(no,3);
		//座位号
		DecimalFormat dft = new DecimalFormat("000");
		String seatNo = String.valueOf(dft.format(Math.ceil((float)no/12)));
		//学校
		String schStr = "";
		if(school == 1){
			schStr = "J";
		}else if(school == 2){
			schStr = "Z";
		}else{
			schStr = "S";
		}
		if(no > 720){
			//存储编号
			String noIndex = schStr + "04" + seatNo;
			saveTbStuno(no+"",noIndex,name);
			return noIndex;
		}
		//考场
		str = getSchool(no,schStr);
		//存储编号
		String noIndex = str + seatNo;
		saveTbStuno(no+"",noIndex,name);
		return noIndex;
	}

	//保存配置信息
	private int saveTbStuno (String noIndex,String no,String name){
		TbStuno entity = new TbStuno();
		entity.setNoIndex(noIndex);
		entity.setName(name);
		entity.setNo(no);
		return stuApplyDao.saveTbStuno(entity);
	}

	//获取考场
	private String getSchool(int no,String scStr){
		String str = "";
		String schoolK = no % 12 + "";
		//第一场
		List st1 = Arrays.asList(new String[]{"1","2","3"});
		//第二场
		List st2 = Arrays.asList(new String[]{"4","5","6"});
		//第三场
		List st3 = Arrays.asList(new String[]{"7","8","9"});
		if(st1.contains(schoolK)){
			str += scStr + "01";
		}else if(st2.contains(schoolK)){
			str += scStr + "02";
		}else if(st3.contains(schoolK)){
			str += scStr + "03";
		}else{
			str += scStr + "04";
		}
		return str;
	}

	private Response checkOpAuth(StuApply exist){
		Response res = new Response();
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		if(exist==null){//表不存在
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			return res;
		}else if(exist.getYear()!=DateUtils.getCurrentYear()){
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg(Utils.connectString("该数据为",exist.getYear(),"年的数据，当前不可操作！"));
			return res;
		}else if(utype==UserType.PATRIARCH){//家长
			if(exist.getUid()!=0
					&&exist.getUid()!=Utils.getCurrentUid()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("没有权限");
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_NONE.val()){
				res = timeConfigService.checkServeTime(TimeConfigType.APPLY_INPUT);
				if(res.getRetCode()!=ReturnCode.SUCCESS){
					res.setErrorMsg("当前不能进行此操作");
					return res;
				}
			}else if(exist.getStatus()==StuApplyStatus.SUBMIT_ONCE.val()){
				res = timeConfigService.checkServeTime(TimeConfigType.APPLY_MODIFY);
				if(res.getRetCode()!=ReturnCode.SUCCESS){
					res.setErrorMsg("当前不能进行此操作");
					return res;
				}
			}else{
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
		}else if(utype==UserType.ADMIN){//招生老师
			if(exist.getStatus()!=StuApplyStatus.SUBMIT_ONCE.val()
					&&exist.getStatus()!=StuApplyStatus.SUBMIT_TWICE.val()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("表单状态已变，不能修改！");
				return res;
			}
		}else{
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("没有权限");
			return res;
		}
		res.setRetCode(ReturnCode.SUCCESS);
		return res;
	}
	private Response checkStuApplyAttcomplete(int id, StuType type){//检查所有需要上传的图片是否上传完整
		Map<String, Object> params = this.createQueryParams(id, null, type);
		//List<Integer> otypes = (List<Integer>) params.get("otypes");
		List<Attachment> atts = attachmentService.loadService(params);
		boolean isUserPhoto = false;
		boolean isIndex = false;
		boolean isKind = false;
		for (Attachment attachment : atts) {
			if (attachment.getOtype() == AttOtype.TYPE_CHILDREN_PHOTO.val()) {
				isUserPhoto = true;
			}
			if (attachment.getOtype() == AttOtype.TYPE_CHILDREN_HK_INDEXPAGE.val()) {
				isIndex = true;
			}
			if (attachment.getOtype() == AttOtype.TYPE_CHILDREN_PAGE.val()) {
				isKind = true;
			}
		}
		Response res = new Response(ReturnCode.SUCCESS);
		if (!isUserPhoto ) {
			res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("儿童的登记照没有上传，请检查！");
		}
		if (!isIndex) {
			res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("户口首页（有地址的那一页）没有上传，请检查！");
		}
		if (!isKind) {
			res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("儿童户口主页没有上传，请检查！");
		}
		return res;
	}
	@Override
	public Response submit2ReviewService(String idStr,StuApplyStatus targetStatus){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		StuApplyStatus currentStatus = null;
		if(targetStatus==StuApplyStatus.SUBMIT_ONCE){
			Response res = timeConfigService.checkServeTime(TimeConfigType.APPLY_INPUT);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
			currentStatus = StuApplyStatus.SUBMIT_NONE;
		}else if(targetStatus==StuApplyStatus.SUBMIT_TWICE){
			Response res = timeConfigService.checkServeTime(TimeConfigType.APPLY_MODIFY);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
			currentStatus = StuApplyStatus.SUBMIT_ONCE;
		}else{
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		StuApply stuApply = stuApplyDao.queryById(id);
		if(stuApply==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}else if(stuApply.getType()==StuType.TYPEA.val()){//A类
			if((stuApply.getOther13()==null
						||stuApply.getOther14()==null
						||stuApply.getOther15()==null
						||stuApply.getOther16()==null)
					||(stuApply.getOther18()==null
							||stuApply.getOther19()==null
							||stuApply.getOther20()==null
							||stuApply.getOther21()==null)){
				Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("请至少将家长情况中除“校园一卡通号码”外的所有信息补充完整");
				return res;
			}
		}else if(stuApply.getType()==StuType.TYPEB.val()){//B类
			if(stuApply.getOther24()==null
					||stuApply.getOther25()==null
					||stuApply.getOther26()==null
					||stuApply.getOther27()==null
					||stuApply.getOther28()==null){
				Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("请将“(外)祖父、母”相关的所有信息补充完整");
				return res;
			}
		}
		/*if(stuApply.getOther43()==null||
				!"是否".contains(stuApply.getOther43())){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("“是否在洪山区新生入学服务平台上报名？”一项未勾选");
			return res;
		}else if("否".contains(stuApply.getOther43())){
			if(stuApply.getOther44()==null||
					!"是否".contains(stuApply.getOther44())){
				Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("“是否在其他地区新生服务平台上报名？”一项未勾选");
				return res;
			}else if("是".contains(stuApply.getOther44())){
				if(stuApply.getOther45()==null
						||stuApply.getOther46()==null
						||stuApply.getOther47()==null){
					Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
					res.setErrorMsg("请将“我报名的其他地区新生服务平台所在地区”补充完整");
					return res;
				}
			}
		}*/
		if(stuApply.getCardNo()==null
				||!stuApply.getCardNo().toLowerCase().equals(Utils.getCurrentUsername())){//账号不区分大小写
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("身份证件号与登陆账号不一致，不能提交");
			return res;
		}

		Response res = this.checkStuApplyAttcomplete(id, stuApply.getTypeStr());
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("uid", Utils.getCurrentUid());
		params.put("currentStatus", currentStatus.val());
		params.put("targetStatus", targetStatus.val());
		
		int uc = stuApplyDao.submit2Review(params);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

	@Override
	public void downloadStuApplyService2(boolean pdf,String idStr, HttpServletRequest request, HttpServletResponse response) {
		String docName = "钟家村寄宿学校2020年新生报名信息卡.pdf";
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		StuApply stuApply = null;
		if(id!=0){
			stuApply = stuApplyDao.queryById(id);
		}else{
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
		}
		CurrentUser cuser = ThreadLocalUtils.getCurrentUser();
		if(stuApply==null
				||(cuser.getType()==UserType.PATRIARCH&&stuApply.getUid()!=cuser.getUid())){
			response.setStatus(404);
			return ;
		}
		String xmlName = "stu_test.xml";
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("cardNo",stuApply.getCardNo());
		dataMap.put("name", stuApply.getName());
		String no = stuApply.getNo();
		dataMap.put("no", " "+no);
		if(no.startsWith("J")){
			dataMap.put("addr", "钟家村寄宿学校（汉阳区北城路28号）");
		}else if(no.startsWith("Z")){
			dataMap.put("addr", "钟家村小学（西村路2号）");
		}else if(no.startsWith("S")){
			dataMap.put("addr", "钟家村小学三里坡校区（马鹦路143号）");
		}
		Integer num = Integer.valueOf(no.substring(3));
		if(num <= 20){
			dataMap.put("test", " 1");
			dataMap.put("time1", "上午");
			dataMap.put("time2", " 8:30");
		}else if(num <= 40){
			dataMap.put("test", " 2");
			dataMap.put("time1", "上午");
			dataMap.put("time2", " 10:30");
		}else if(num > 40){
			dataMap.put("test", " 3");
			dataMap.put("time1", "上午");
			dataMap.put("time2", " 14:30");
		}

		dataMap.put("wait", no.substring(2,3));
		dataMap.put("class","  就读方式："+ stuApply.getOther54());

		if(stuApply.getSex()==BooleanEnum.TRUE.val()){
			dataMap.put("sex", "男");
		}else{
			dataMap.put("sex", "女");
		}
		this.doDownload(pdf,docName, xmlName, dataMap,request,response);
	}
	
	@Override
	public void downloadStuApplyService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		StuApply stuApply = null;
		if(id!=0){
			stuApply = stuApplyDao.queryById(id);
		}else{
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
		}
		CurrentUser cuser = ThreadLocalUtils.getCurrentUser();
		if(stuApply==null
				||(cuser.getType()==UserType.PATRIARCH&&stuApply.getUid()!=cuser.getUid())){
			response.setStatus(404);
			return ;
		}

		String docName = stuApply.getName()+"的入学登记表.pdf";
		String xmlName = "";
		if (stuApply.getTypeStr().alias().startsWith("A")) {
			xmlName = "stu_apply_A.xml";
		} else if (stuApply.getTypeStr().alias().startsWith("B")) {
			xmlName = "stu_apply_B.xml";
		} else if (stuApply.getTypeStr().alias().startsWith("C")) {
			xmlName = "stu_apply_C.xml";
		}
		
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("type", stuApply.getTypeStr().alias());
		dataMap.put("tY", stuApply.getTypeStr().alias().substring(0,1));
		dataMap.put("no", stuApply.getNoStr());
		dataMap.put("name", stuApply.getName());
		dataMap.put("dateOfBirth", stuApply.getDateOfBirthStr());
		if(stuApply.getSex()==BooleanEnum.TRUE.val()){
			dataMap.put("sex", "男");
		}else{
			dataMap.put("sex", "女");
		}
		dataMap.put("photo", this.getPhotoStr(id, AttOtype.TYPE_CHILDREN_PHOTO, request, response));
		dataMap.put("index", this.getPhotoStr(id, AttOtype.TYPE_CHILDREN_HK_INDEXPAGE, request, response));
		dataMap.put("kind", this.getPhotoStr(id, AttOtype.TYPE_CHILDREN_PAGE, request, response));
		dataMap.put("nation", stuApply.getNation());
		dataMap.put("addressOfBirth", stuApply.getAddressOfBirth());
		dataMap.put("birthplace", stuApply.getBirthplace());
		dataMap.put("jkzt", stuApply.getJkzt());
		dataMap.put("citizenship", stuApply.getCitizenship());
		dataMap.put("cardType", stuApply.getCardTypeStr().alias());
		dataMap.put("gatqw", stuApply.getGatqw());
		dataMap.put("cardNo", stuApply.getCardNo());
		
		StringBuilder other1 = new StringBuilder();
		if(stuApply.getOther35()!=null){
			other1.append(stuApply.getOther35()).append(" ");
		}
		stuApply.setOther1(Utils.removeLastChar(stuApply.getOther1(), "省"));
		stuApply.setOther2(Utils.removeLastChar(stuApply.getOther2(), "市"));
		stuApply.setOther3(Utils.removeLastChar(stuApply.getOther3(), "区"));
		stuApply.setOther3(Utils.removeLastChar(stuApply.getOther3(), "县"));
		if(stuApply.getOther1()!=null){
			other1.append(stuApply.getOther1()).append("省 ");
		}
		if(stuApply.getOther2()!=null){
			other1.append(stuApply.getOther2()).append("市 ");
		}
		if(stuApply.getOther3()!=null){
			other1.append(stuApply.getOther3()).append("区（县）");
		}
		dataMap.put("other1", other1.toString());
		
//		dataMap.put("other2", stuApply.getOther2());
//		dataMap.put("other3", stuApply.getOther3());
		dataMap.put("other4", stuApply.getOther4());
		dataMap.put("other5", stuApply.getOther5());
		dataMap.put("other6", stuApply.getOther6());
		dataMap.put("other7", stuApply.getOther7());
		dataMap.put("other8", Utils.connectString("武汉市 ",Utils.removeLastChar(stuApply.getOther8(), "区"),"区 ",stuApply.getOther9(),"小区"));
//		dataMap.put("other9", stuApply.getOther9());
		dataMap.put("other10", stuApply.getOther10());
		dataMap.put("other11", stuApply.getOther11());
		dataMap.put("other12", stuApply.getOther12());
		dataMap.put("other13", stuApply.getOther13());
		dataMap.put("other14", stuApply.getOther14());
		dataMap.put("other15", stuApply.getOther15());
		dataMap.put("other16", stuApply.getOther16());
		dataMap.put("other17", stuApply.getOther17());
		dataMap.put("other18", stuApply.getOther18());
		dataMap.put("other19", stuApply.getOther19());
		dataMap.put("other20", stuApply.getOther20());
		dataMap.put("other21", stuApply.getOther21());
		dataMap.put("other22", stuApply.getOther22());
		if(stuApply.getTypeStr()==StuType.TYPEB){
			dataMap.put("other23", stuApply.getOther23());
			dataMap.put("other24", stuApply.getOther24());
			dataMap.put("other25", stuApply.getOther25());
			dataMap.put("other26", stuApply.getOther26());
			dataMap.put("other27", stuApply.getOther27());
			dataMap.put("other28", stuApply.getOther28());
			dataMap.put("other41", stuApply.getOther41());
		}
		if(stuApply.getTypeStr()==StuType.TYPEC){
			dataMap.put("other29", stuApply.getOther29());
			dataMap.put("other30", stuApply.getOther30());
			dataMap.put("other31", stuApply.getOther31());
			dataMap.put("other32", stuApply.getOther32());
			dataMap.put("other33", stuApply.getOther33());
			dataMap.put("other34", stuApply.getOther34());
			dataMap.put("other41", stuApply.getOther41());
		}
		dataMap.put("other36", stuApply.getOther36());
		//"是，否"历史数据处理，其他一，二，三孩
		if (stuApply.getTypeStr().alias().startsWith("A")) {
			if ("是".equals(stuApply.getOther37())) {
				dataMap.put("other37", "一孩");
			} else if ("否".equals(stuApply.getOther37())) {
				dataMap.put("other37", "二孩及以上");
			} else {
				dataMap.put("other37", stuApply.getOther37());
			}
		} else if (stuApply.getTypeStr().alias().startsWith("B")) {
			if ("是".equals(stuApply.getOther37())) {
				dataMap.put("other37", "一孩");
			} else if ("否".equals(stuApply.getOther37())) {
				dataMap.put("other37", "二孩及以上");
			} else {
				dataMap.put("other37", stuApply.getOther37());
			}
		} else if (stuApply.getTypeStr().alias().startsWith("C")) {
            if ("是".equals(stuApply.getOther37())) {
                dataMap.put("other37", "一孩");
            } else if ("否".equals(stuApply.getOther37())) {
                dataMap.put("other37", "二孩及以上");
            } else {
                dataMap.put("other37", stuApply.getOther37());
            }
		}
        //新增
		dataMap.put("other38", stuApply.getOther38());
		dataMap.put("other39", stuApply.getOther39());
		dataMap.put("other40", stuApply.getOther40());
		dataMap.put("other42", stuApply.getOther42());
		dataMap.put("other48", stuApply.getOther48());
		dataMap.put("other49", stuApply.getOther49());
		dataMap.put("other50", stuApply.getOther50());
		dataMap.put("other51", stuApply.getOther51());
		dataMap.put("year", stuApply.getYear()+"");
		dataMap.put("cyear", DateUtils.convertCharCnNumber(stuApply.getYear()));
		//新增20170609
		dataMap.put("extMsg", stuApply.getOther47()==null?"户口所在地："+other1.toString():stuApply.getOther47());
		/*if(stuApply.getOther43()!=null){
			if("是".contains(stuApply.getOther43())){
				dataMap.put("extMsg", "已在洪山区新生入学服务平台上报名");
			}else if(stuApply.getOther44()!=null){
				if("是".contains(stuApply.getOther44())){
					StringBuilder other45 = new StringBuilder();
					if(stuApply.getOther45()!=null){
						other45.append(stuApply.getOther45()).append("省 ");
					}
					if(stuApply.getOther46()!=null){
						other45.append(stuApply.getOther46()).append("市 ");
					}
					if(stuApply.getOther47()!=null){
						other45.append(stuApply.getOther47()).append("区（县）");
					}
					dataMap.put("extMsg", "已在“"+other45.toString()+"”新生服务平台上报名");
				}else{
					dataMap.put("extMsg", "户口所在地："+other1.toString());
				}
			}
		}*/
		
		this.doDownload(pdf,docName, xmlName, dataMap,request,response);
	}
	public void doDownload(boolean pdf,String docName,String xmlName,Map<String,String> dataMap,
			HttpServletRequest request,HttpServletResponse response){
		String tmplBasePath = request.getSession().getServletContext().getRealPath("/")+"/tmpl_xml/";
		
		InputStream fis = null;
		ServletOutputStream fos = null;
		try {
			docName = HtmlUtils.htmlUnescape(docName);
			boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
			if(isIE6){
				response.setHeader( "Content-Disposition", "attachment;filename=" + 
					     new String( docName.getBytes("gb2312"), "ISO8859-1" ) );
			}else{
				String indexName = new String(docName.getBytes(),"ISO-8859-1");//URLEncoder.encode(document.getName(), "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename="+ indexName);
			}
			fos = response.getOutputStream();
			
			if(pdf){
				//将word上传到pdf转换服务器，取得转换后PDF返回给用户
				boolean isSuccess = this.uploadFile(tmplBasePath, fos, dataMap, xmlName);
				if(!isSuccess){
					response.setHeader("Content-Disposition", null);
					response.setStatus(500);
					fos.write("下载失败！".getBytes());
				}
				fos.flush();
			}else{
				byte[] data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><?mso-application progid=\"Word.Document\"?>".toString().getBytes("UTF-8");
				fos.write(data, 0, data.length);

				
				SAXParserFactory factory = SAXParserFactory.newInstance();  
				SAXParser parser = factory.newSAXParser(); 
				fis = new FileInputStream(new File(tmplBasePath+xmlName));
				SaxHandler handler = new SaxHandler(tmplBasePath,fos,dataMap,null);
				parser.parse(fis, handler);  
				fos.flush();
			}
			
		} catch (Exception e) {
			logger.error("写入Word文件错误！",e);
			response.setStatus(500);
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(Exception e1){}
			}
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e1){}
			}
		}
	}
	//将word上传到pdf转换服务器
	private boolean uploadFile(String tmplBasePath,ServletOutputStream fos,Map<String, String> dataMap,String xmlName) {
		InputStream fis = null;
		String hashcode = MD5.getMD5String(tmplBasePath+xmlName);
		BufferedOutputStream os2PdfServer = null;
		try {
			File pdfFile = new File("/tmp/pdfFiles");
			if (!pdfFile.exists()) {
				pdfFile.mkdirs();
			}
			os2PdfServer = new BufferedOutputStream(new FileOutputStream("/tmp/pdfFiles/"+hashcode+".doc"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			byte[] data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><?mso-application progid=\"Word.Document\"?>".toString().getBytes("UTF-8");
			os2PdfServer.write(data, 0, data.length);


			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			fis = new FileInputStream(new File(tmplBasePath+xmlName));
			SaxHandler handler = new SaxHandler(tmplBasePath,os2PdfServer,dataMap,null);
			parser.parse(fis, handler);
			os2PdfServer.flush();
//			fos.flush();



//			File file = writeTmpFile(hashcode, (FileInputStream) fis);
			logger.info("inFile:{}","/tmp/pdfFiles/"+hashcode+".doc");
			logger.info("outFile:{}","/tmp/pdfFiles/"+hashcode+".pdf");
			Word2PdfUtil.doc2pdf("/tmp/pdfFiles/"+hashcode+".doc","/tmp/pdfFiles/"+hashcode+".pdf");
//			wordConverterToPdf(new File("/tmp/pdfFiles/"+hashcode+".doc"),"/tmp/pdfFiles/");


			// 读取URLConnection的响应
			DataInputStream in = new DataInputStream(new FileInputStream("/tmp/pdfFiles/"+hashcode+".pdf"));
			getFile(in,fos);
			return true;
		} catch (Exception e) {
			logger.error("下载PDF失败",e);
			return false;
		}finally{
			if(fis!=null){
				try{
					fis.close();
				}catch(Exception e1){}
			}
			if(os2PdfServer!=null){
				try{
					os2PdfServer.close();
				}catch(Exception e1){}
			}
		}
	}



	// 将word格式的文件转换为pdf格式
	private  int WordToPDFLinux(String startFile, String overFile) {
		// 源文件目录
		File inputFile = new File(startFile);
		if (!inputFile.exists()) {
			System.out.println("源文件不存在！");
			return -1;
		}

		// 输出文件目录
		File outputFile = new File(overFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().exists();
		}

		// 调用openoffice服务线程
		/** 我把openOffice下载到了 C:/Program Files (x86)/下  ,下面的写法自己修改编辑就可以**/
//		String command = "C:/Program Files (x86)/OpenOffice 4/program/soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
//		Process p = Runtime.getRuntime().exec(command);

		// 连接openoffice服务
		OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
		try {
			connection.connect();
		} catch (ConnectException e) {
			e.printStackTrace();
			return -1;
		}

		// 转换
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(inputFile, outputFile);

		// 关闭连接
		connection.disconnect();
		return 1;

		// 关闭进程
//		p.destroy();

	}

	public boolean wordConverterToPdf(File file,String outFile) throws Exception {
		String path = file.getParent();
		try {
			String osName = System.getProperty("os.name");
			String command = "";
			if (osName.contains("Windows")) {
				//soffice --convert-to pdf  -outdir E:/test.docx
				command = "soffice --convert-to pdf  -outdir " + path + " " + file.getAbsolutePath();
			} else {
//				command = "doc2pdf --output=" + outFile + " " + file.getAbsolutePath();
				command="/usr/bin/libreoffice6.2 --convert-to pdf:writer_pdf_Export "+file.getAbsolutePath()+" --outdir "+outFile;
			}
			String result = executeCommand(command);
//	        LOGGER.info("result==" + result);
			System.out.println("生成pdf的result==" + result);
			if (result.equals("") || result.contains("writer_pdf_Export")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}

	public static String executeCommand(String command) {
		StringBuffer output = new StringBuffer();
		Process p;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		try {
			p = Runtime.getRuntime().exec(command);
			p.waitFor();
			inputStreamReader = new InputStreamReader(p.getInputStream(), "UTF-8");
			reader = new BufferedReader(inputStreamReader);
			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}
			//p.destroy();//这个一般不需要
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(inputStreamReader);
		}
		System.out.println(output.toString());
		return output.toString();

	}



	//输出给用户
	private void getFile(DataInputStream in,ServletOutputStream fos) throws IOException {
		try {
			int l = -1;
			byte[] tmp = new byte[1024];
			while ((l = in.read(tmp)) != -1) {
				fos.write(tmp, 0, l);
				// 注意这里如果用OutputStream.write(buff)的话，图片会失真，大家可以试试
			}
		} finally {
			if(in!=null){
				try{
					// 关闭低层流。
					in.close();
				}catch(Exception e1){}
			}
		}
	}

	private File writeTmpFile(String fileName,FileInputStream fileInputStream){
		File file = new File("/tmp/pdfFiles/"+fileName+".doc");
		try {
			FileOutputStream outputStream = new FileOutputStream(file);
			byte bytes[]=new byte[1024];
			int temp=0;  //边读边写
			while((temp=fileInputStream.read(bytes))!=-1){  //读
				outputStream.write(bytes,0,temp);   //写
			}
			fileInputStream.close();
			outputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
	
	@Override
	public Response getHistoryListService(String idStr){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		List<StuApplyHistory> historys = stuApplyDao.queryHistory(id);
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(historys);
		return res;
	}
	
	@Override
	public Response getHistoryInfoService(String idStr){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		StuApplyHistory history = stuApplyDao.queryHistoryById(id);
		if(history==null||history.getData()==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(JSON.parseObject(history.getData(), StuApply.class));
		return res;
	}
	
	@Override
	public Response initPinyinService(){
		int offset = 0;
		List<StuApply> datas = stuApplyDao.query2InitPinyin(offset);
		while(datas.size()>0){
			for(StuApply ele:datas){
				stuApplyDao.initPinyin(ele);
			}
			offset += datas.size();
			datas = stuApplyDao.query2InitPinyin(offset);
		}
		return new Response(ReturnCode.SUCCESS);
	}
	//获取对应的图片字符串
	@Override
	@SuppressWarnings({ "unchecked" })
	public String getPhotoStr(Integer oid, AttOtype otype, HttpServletRequest request, HttpServletResponse response) {
		Response res = attachmentService.loadService(oid, otype);
		if (res.getRetCode() == ReturnCode.SUCCESS) {
			List<Attachment> photo = (List<Attachment>) res.getData();
			Attachment att = null;
			if (photo != null && photo.size() > 0) {// 儿童信息录入中各种图片只考虑一张的情况
				att = photo.get(0);
			}
			if (att != null) {
				FileMeta fileMeta = fileDao.queryDataByHash(att.getHash());
				if (fileMeta != null) {
					byte[] bytes = fileMeta.getData();
					BASE64Encoder encoder = new BASE64Encoder();
					String photoStr = encoder.encode(bytes);
					return photoStr;
				}

			}
		}
		return this.getDefaultPhoto(otype, request, response);
	}
	//获取默认的图片字符串
	private String getDefaultPhoto(AttOtype otype, HttpServletRequest request, HttpServletResponse response) {
		String photo = "";
		if (otype == AttOtype.TYPE_CHILDREN_PHOTO) {
			photo = "usetPhoto.png";
		} else if (otype == AttOtype.TYPE_CHILDREN_HK_INDEXPAGE) {
			photo = "hky.png";
		} else if (otype == AttOtype.TYPE_CHILDREN_PAGE) {
			photo = "ety.png";
		}
		if (!photo.equals("")) {
			String photoStr = request.getSession().getServletContext().getRealPath("/") + "/tmpl_xml/";
			try {
				return encodeBase64File(photoStr + photo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	@Override
	public Response loadAttService(Integer oid, AttOtype otype, StuType type){
		if (oid==null || oid <= 0 || type == null) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		List<Attachment> atts = attachmentService.loadService(this.createQueryParams(oid, otype, type));
		if (atts == null) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(atts);
		return res;
	}
	@Override
	public Map<String, Object> createQueryParams(Integer oid, AttOtype otype, StuType type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("oid", oid);
		if (otype == null) {
			List<Integer> list = new ArrayList<Integer>();
			list.add(AttOtype.TYPE_CHILDREN_PHOTO.val());// 儿童的登记照
			list.add(AttOtype.TYPE_CHILDREN_HK_INDEXPAGE.val());// 户口首页（有地址的那一页）
			list.add(AttOtype.TYPE_CHILDREN_HOUSEHOLDERPAGE.val());// 户主页
			list.add(AttOtype.TYPE_CHILDREN_PAGE.val());// 儿童主页
			list.add(AttOtype.TYPE_CHILDREN_BIRTH_CERTIFICATE.val());// 儿童出生证
			//list.add(AttOtype.TYPE_PARENT_ACCOUNT_PAGE.val());// 父母户口页
			list.add(AttOtype.TYPE_FATHER_ACCOUNT_PAGE.val());// 父亲户口页
			list.add(AttOtype.TYPE_MOTHER_ACCOUNT_PAGE.val());// 母亲户口页
			if (type!=null && (type==StuType.TYPEA || type==StuType.TYPEB)) {
				list.add(AttOtype.TYPE_WORK_PROVE.val());//TYPE_WORK_PROVE(19),//华师教职工校园一卡通
				if (type==StuType.TYPEB) {
					list.add(AttOtype.TYPE_THREE_GENERATION_RELATIONSHIP.val());// 三代关系证明
				}
			}
			list.add(AttOtype.TYPE_FATHER_ACCOUNT_LEARN_PAGE.val());// 父亲最高学历证明
			list.add(AttOtype.TYPE_MOTHER_ACCOUNT_LEARN_PAGE.val());// 母亲最高学历证明
			params.put("otypes", list);
		} else {
			params.put("otypes", Arrays.asList(otype.val()));
		}
		return params;
	}
	@Override
	public Response uploadService(MultipartFile file,String prefix,Integer oid,AttOtype otype, StuType type){
		if (otype == null) {
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response();
		List<Attachment> att = attachmentService.loadService(this.createQueryParams(oid, otype, type));
		if (att != null && att.size() > 0) {
			for (Attachment attachment : att) {
				res = attachmentService.delService(attachment.getId());
				if (res.getRetCode() != ReturnCode.SUCCESS) {
					BaseServiceUtils.setRollbackOnly();
					return res;
				}
			}
		}
		res = attachmentService.uploadService(file, Utils.connectString("【", prefix, "】"), oid, otype.val());
		return res;
	}

	@Override
	public Response forwardGradeServiceT(Integer id){//新增，修改账户为身份证号，默认密码为身份证后6位
		Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
		id = id == null ? 0 : id;
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		if (utype != UserType.ADMIN && utype != UserType.PATRIARCH) {
			res.setRetCode(ReturnCode.UNAUTHORIZED);
			res.setErrorMsg("没有权限");
			return res;
		}
		StuApply stuApply = null;
		if (id != 0) {
			stuApply = stuApplyDao.queryById(id);
		} else {
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
		}
		if (stuApply == null) {
			return res;
		}
		if (utype == UserType.PATRIARCH && stuApply.getUid() != Utils.getCurrentUid()) {
			res.setErrorMsg("id错误！");
			return res;
		}
		if (stuApply.getYear() == DateUtils.getCurrentYear()
				|| stuApply.getStatus() == StuApplyStatus.REVIEW_PASS.val()) {
			return res;
		}

		StuApplyHistory history = new StuApplyHistory();
		history.setMid(stuApply.getId());
		history.setUid(Utils.getCurrentUid());
		history.setData(JSON.toJSONString(stuApply));
		history.setTime(System.currentTimeMillis());


		Integer year = DateUtils.getCurrentYear();
		stuApply.setYear(year);
		stuApply.setMessage("");
		stuApply.setStatus(StuApplyStatus.SUBMIT_NONE.val());
		
		StuInfo stuInfo = new StuInfo();
		String other9 = Utils.connectString(DateUtils.getCurrentYear(), "级");// 年级
		String other10 = "";// 班级
		String other11 = Utils.connectString(DateUtils.getCurrentYear(), "09");// 入学年月
		stuInfo.setStuId(stuApply.getId());
		stuInfo.setOther9(other9);
		stuInfo.setOther10(other10);
		stuInfo.setOther11(other11);
		stuInfo.setStatus(StuApplyStatus.SUBMIT_NONE.val());

		StuVaccine stuVaccine = new StuVaccine();
		stuVaccine.setStuId(stuApply.getId());
		stuVaccine.setStatus(StuApplyStatus.SUBMIT_NONE.val());
		
		UserAdd user = new UserAdd();
		user.setId(stuApply.getUid()+"");
		user.setName(stuApply.getName());
		String cardNo = stuApply.getCardNo();
		user.setUsername(cardNo);
		if (cardNo.length()>6) {
			user.setPassword(cardNo.substring(cardNo.length()-6));
		}
		
//		String no = this.getNo(stuApply.getName());
//		if (StringUtils.isEmpty(no)) {
//			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
//			res.setErrorMsg("报名号生成失败，请稍后重试！");
//			return res;
//		}
//		stuApply.setNo(no);
		int uc = stuApplyDao.forwardGrade(stuApply);
		if (uc <= 0) {
			BaseServiceUtils.setRollbackOnly();
			return res;
		}
		uc = stuApplyDao.saveHistory(history);
		if (uc <= 0) {
			BaseServiceUtils.setRollbackOnly();
			return res;
		}
		stuInfoDao.forwardGrade(stuInfo);
		stuVaccineDao.resetStatus(stuVaccine);
		res = userService.saveService(user.convert2Entity(UserType.PATRIARCH));
//		if (res.getRetCode() != ReturnCode.SUCCESS) {
//			BaseServiceUtils.setRollbackOnly();
//			return res;
//		}
		return res;
	}
	public static void main1(String[] args) {
		InputStream fis = null;
		FileOutputStream fos = null;
		try {
			
			fos = new FileOutputStream("D:\\test.doc");
			byte[] data = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><?mso-application progid=\"Word.Document\"?>".toString().getBytes("UTF-8");
			fos.write(data, 0, data.length);

			
			SAXParserFactory factory = SAXParserFactory.newInstance();  
			SAXParser parser = factory.newSAXParser(); 
			fis = new FileInputStream(new File("D:\\stu_apply.xml"));
			
			Map<String,String> dataMap = new HashMap<String,String>();
			dataMap.put("name", "张鹏22211");
			dataMap.put("photo", encodeBase64File("D:\\40901090342.jpg"));
			
			SaxHandler handler = new SaxHandler("",fos,dataMap,null);
			parser.parse(fis, handler);  
			fos.flush();
		} catch (Exception e) {
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(Exception e1){}
			}
		}
	}
	public static String encodeBase64File(String path) throws Exception {
        File  file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }
	public static void main(String[] args) {
		String cardNo = "H126621098";
		System.out.println(cardNo.substring(cardNo.length()-6));
	}
}
