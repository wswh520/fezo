package com.fz.enroll.student.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.DateUtils;
import com.fz.common.util.ExcelUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.entity.student.StuInfo;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.DataField;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.dao.StuApplyDAO;
import com.fz.enroll.student.dao.StuInfoDAO;

@Service("stuInfoService")
public class StuInfoServiceImpl implements StuInfoService {
	private static final Logger logger = LoggerFactory.getLogger(StuInfoServiceImpl.class);

	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private StuApplyService stuApplyService;
	@Autowired
	private StuApplyDAO stuApplyDao;
	@Autowired
	private StuInfoDAO stuInfoDao;
	
	@Override
	public Response loadService(String stuIdStr) {
		int stuId = 0;
		try{
			stuId = Integer.valueOf(stuIdStr);
		}catch(Exception e){}
		StuApply stuApply = null;
		if(stuId!=0){
			stuApply = stuApplyDao.queryById(stuId);
		}else{
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
		}
		if(stuApply==null
				//||stuApply.getStatus()!=StuApplyStatus.REVIEW_PASS.val()
				){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("入学登记表尚未审核通过！");
			return res;
		}
		
		StuInfo stuInfo = stuInfoDao.queryByStuId(stuApply.getId());
		stuInfo = stuInfo!=null?stuInfo:new StuInfo();
		stuInfo.setStuId(stuApply.getId());
		stuInfo.setStuApply(stuApply);
		
		Response checkRes = this.checkOpAuth(stuInfo);
		if(checkRes.getRetCode()!=ReturnCode.SUCCESS){
			stuInfo.setLocked(true);
		}
		/*if(stuInfo.getOther4()==null){
			StringBuilder other4 = new StringBuilder();
			if(stuApply.getOther35()!=null){
				other4.append(stuApply.getOther35());
			}
			stuApply.setOther1(Utils.removeLastChar(stuApply.getOther1(), "省"));
			stuApply.setOther2(Utils.removeLastChar(stuApply.getOther2(), "市"));
			stuApply.setOther3(Utils.removeLastChar(stuApply.getOther3(), "区"));
			stuApply.setOther3(Utils.removeLastChar(stuApply.getOther3(), "县"));
			if(stuApply.getOther1()!=null){
				other4.append(stuApply.getOther1()).append("省");
			}
			if(stuApply.getOther2()!=null){
				other4.append(stuApply.getOther2()).append("市");
			}
			if(stuApply.getOther3()!=null){
				other4.append(stuApply.getOther3()).append("区（县）");
			}
			stuInfo.setOther4(other4.toString());
		}*/
		
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(stuInfo);
		return res;
	}
	
	@Override
	public Response saveService(StuInfo entity){
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response();
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		if(entity.getId()==0){//新增
			if(utype!=UserType.PATRIARCH){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				return res;
			}
			res = timeConfigService.checkServeTime(TimeConfigType.INFO_INPUT);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
			entity.setStatus(StuApplyStatus.SUBMIT_NONE.val());
			
			StuApply stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
			if(stuApply==null
					//||stuApply.getStatus()!=StuApplyStatus.REVIEW_PASS.val()
					){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("入学登记表尚未填写！");//审核通过
				return res;
			}
			entity.setStuId(stuApply.getId());
			
			StuInfo exist = stuInfoDao.queryByStuId(stuApply.getId());
			int uc = 0;
			if(exist==null){
				uc = stuInfoDao.save(entity);
			}else{
				uc = stuInfoDao.update(entity);
			}
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}else{//修改
			StuInfo exist = stuInfoDao.queryById(entity.getId());
			res = this.checkOpAuth(exist);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_ONCE.val()){
				//保存存档
				
			}
			
			entity.setStatus(exist.getStatus());
			int uc = stuInfoDao.update(entity);
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}
	}
	private Response checkOpAuth(StuInfo exist){
		Response res = new Response();
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		StuApply stuApply = stuApplyDao.queryById(exist.getStuId());
		if(exist==null||stuApply==null){//表不存在
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			return res;
		}else if(stuApply.getYear()!=DateUtils.getCurrentYear()){
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg(Utils.connectString("该数据为",stuApply.getYear(),"年的数据，当前不可操作！"));
			return res;
		}else if(utype==UserType.PATRIARCH){//家长
			if(stuApply==null
					||stuApply.getUid()!=Utils.getCurrentUid()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_NONE.val()){
				res = timeConfigService.checkServeTime(TimeConfigType.INFO_INPUT);
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
			if(exist.getStatus()!=StuApplyStatus.SUBMIT_ONCE.val()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				return res;
			}
		}else{
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			return res;
		}
		res.setRetCode(ReturnCode.SUCCESS);
		return res;
	}
	
	@Override
	public Response submitService(String idStr,StuApplyStatus targetStatus){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		StuApplyStatus currentStatus = null;
		if(targetStatus==StuApplyStatus.SUBMIT_ONCE){
			Response res = timeConfigService.checkServeTime(TimeConfigType.INFO_INPUT);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
			currentStatus = StuApplyStatus.SUBMIT_NONE;
		}else{
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("uid", Utils.getCurrentUid());
		params.put("currentStatus", currentStatus.val());
		params.put("targetStatus", targetStatus.val());
		
		int uc = stuInfoDao.submit(params);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public void downloadStuInfoService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		StuInfo stuInfo = null;
		StuApply stuApply = null;
		if(id!=0){
			stuInfo = stuInfoDao.queryById(id);
			stuApply = stuInfo==null?null:stuApplyDao.queryById(stuInfo.getStuId());
		}else{
			stuApply = stuApplyDao.queryByUid(Utils.getCurrentUid());
			stuInfo = stuApply==null?null:stuInfoDao.queryByStuId(stuApply.getId());
		}
		CurrentUser cuser = ThreadLocalUtils.getCurrentUser();
		if(stuInfo==null||stuApply==null
				||(cuser.getType()==UserType.PATRIARCH&&stuApply.getUid()!=cuser.getUid())){
			response.setStatus(404);
			return ;
		}

		String docName = stuApply.getName()+"的学籍信息表.pdf";
		String xmlName = "stu_info.xml";
		
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("name", stuApply.getName());
		if(stuApply.getSex()==BooleanEnum.TRUE.val()){
			dataMap.put("sex", "男");
		}else{
			dataMap.put("sex", "女");
		}
		dataMap.put("dateOfBirth", stuApply.getShortDateOfBirthStr());
		dataMap.put("addressOfBirth", stuInfo.getAddressOfBirth());//需要修改为汉字地址
		dataMap.put("birthplace", stuApply.getBirthplace());
		dataMap.put("nation", stuApply.getNation());
		dataMap.put("citizenship", stuApply.getCitizenship());
		dataMap.put("cardType", stuApply.getCardTypeStr().alias());
		dataMap.put("cardNo", stuApply.getCardNo());
		dataMap.put("gatqw", stuApply.getGatqw());
		dataMap.put("jkzt", stuApply.getJkzt());

		dataMap.put("other1", stuInfo.getOther1());
		dataMap.put("other2", stuInfo.getOther2());
		dataMap.put("other3", stuInfo.getOther3());
		dataMap.put("other4", stuInfo.getOther4());//需要修改汉字地址
		dataMap.put("other5", stuInfo.getOther5());
		dataMap.put("other6", stuInfo.getOther6());
		dataMap.put("other7", stuInfo.getOther7());
		dataMap.put("other8", stuInfo.getOther8());
		dataMap.put("other9", stuInfo.getOther9());
		dataMap.put("other10", stuInfo.getOther10());
		dataMap.put("other11", stuInfo.getOther11());
		dataMap.put("other12", stuInfo.getOther12());
		dataMap.put("other13", stuInfo.getOther13());
		dataMap.put("other14", stuInfo.getOther14());
//		dataMap.put("other15", this.cutAddress(stuInfo.getOther15()));
		dataMap.put("other15", stuInfo.getOther15());
		dataMap.put("other16", stuInfo.getOther16());
//		if(stuInfo.getOther17()==null){
//			dataMap.put("other17", stuInfo.getOther17());
//		}else{
//			dataMap.put("other17", stuInfo.getOther17().replace("武汉市", ""));
//		}
		dataMap.put("other17", stuInfo.getOther17());
		dataMap.put("other18", stuInfo.getOther18());
		dataMap.put("other19", stuInfo.getOther19());
		dataMap.put("other20", stuInfo.getOther20());
		dataMap.put("other21", stuInfo.getOther21());
		dataMap.put("other22", stuInfo.getOther22());
		dataMap.put("other23", stuInfo.getOther23());
		dataMap.put("other24", stuInfo.getOther24());
		dataMap.put("other25", stuInfo.getOther25());
		dataMap.put("other26", stuInfo.getOther26());
		dataMap.put("other27", stuInfo.getOther27());
		dataMap.put("other28", stuInfo.getOther28());
		dataMap.put("other29", stuInfo.getOther29());
		dataMap.put("other30", stuInfo.getOther30());
		dataMap.put("other31", stuInfo.getOther31());
		dataMap.put("other32", stuInfo.getOther32());
		dataMap.put("other33", stuInfo.getOther33());
		dataMap.put("other34", stuInfo.getOther34());
		dataMap.put("other35", stuInfo.getOther35());
		dataMap.put("other36", stuInfo.getOther36());
		dataMap.put("other37", stuInfo.getOther37());
		dataMap.put("other38", stuInfo.getOther38());
		dataMap.put("other39", stuInfo.getOther39());
		dataMap.put("other40", stuInfo.getOther40());
//		dataMap.put("other41", this.cutAddress(stuInfo.getOther41()));
		dataMap.put("other41", stuInfo.getOther41());
		dataMap.put("other42", stuInfo.getOther42());
		dataMap.put("other43", stuInfo.getOther43());
		dataMap.put("other44", stuInfo.getOther44());
		dataMap.put("other45", stuInfo.getOther45Str().alias());
		dataMap.put("other46", stuInfo.getOther46());
		dataMap.put("other47", stuInfo.getOther47());
		dataMap.put("other48", stuInfo.getOther48());
		dataMap.put("other49", stuInfo.getOther49());
		dataMap.put("other50", stuInfo.getOther50());
		dataMap.put("other51", stuInfo.getOther51());
		dataMap.put("other52", stuInfo.getOther52());
//		dataMap.put("other53", this.cutAddress(stuInfo.getOther53()));
		dataMap.put("other53", stuInfo.getOther53());
		dataMap.put("other54", stuInfo.getOther54());
		dataMap.put("other55", stuInfo.getOther55());
		dataMap.put("other56", stuInfo.getOther56());
		dataMap.put("other57", stuInfo.getOther57Str().alias());
		dataMap.put("other58", stuInfo.getOther58());
		dataMap.put("other59", stuInfo.getOther59());
		dataMap.put("index", stuApplyService.getPhotoStr(stuInfo.getStuId(), AttOtype.TYPE_CHILDREN_HK_INDEXPAGE, request, response));
		dataMap.put("kind", stuApplyService.getPhotoStr(stuInfo.getStuId(), AttOtype.TYPE_CHILDREN_PAGE, request, response));
		stuApplyService.doDownload(pdf,docName, xmlName, dataMap,request,response);
	}
	/**
	 * 湖北省外只出现“***省”，湖北省内武汉市外只出现“***市”，武汉市内洪山区外只出现“***区”，洪山区内的出现“洪山区***路***号”
	 * @return
	 */
	private String cutAddress(String address){
		if(address==null){
			return address;
		}
		address = address.replace("湖北省", "");
		int index = address.indexOf("省");
		if(index>0){//湖北省外只出现“***省”
			return address.substring(0,index+1);
		}
		address = address.replace("武汉市", "");
		index = address.indexOf("市");
		if(index>0){//湖北省内武汉市外只出现“***市”
			return address.substring(0,index+1);
		}else{
			index = address.indexOf("县");
			if(index>0){//湖北省内武汉市外只出现“***市”
				return address.substring(0,index+1);
			}
		}
		index = address.indexOf("洪山区");
		if(index<0){
			index = address.indexOf("区");
			if(index>0){//武汉市内洪山区外只出现“***区”
				return address.substring(0,index+1);
			}
		}
		return address;//洪山区内的出现“洪山区***路***号”
	}
	@Override
	public Response importService(MultipartFile file, DataField field){
		if(file==null||file.getSize()==0||field==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Workbook wb = ExcelUtils.getWorkbook(file);
		if(wb==null){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("导入失败，请选择正确的文件！");
			return res;
		}

		Sheet sheet = wb.getSheetAt(0);
		Response res = this.readHeader(sheet, field);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		res = this.readBody(sheet, field);
		return res;
	}
	private Response readHeader(Sheet sheet,DataField field){
		String eleName = this.getStringCellValue(sheet.getRow(0).getCell(3));//第4列表头
		if(!field.alias().equals(eleName)){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("导入失败，请确认表单第4列中的内容是否是："+field.alias());
			return res;
		}
		return new Response(ReturnCode.SUCCESS);
	}
	private Response readBody(Sheet sheet,DataField field){
		List<String> smsLost = new ArrayList<String>();//有问题的行
		String noStr = null;
		String value = null;
		Row row = null;
		int success = 0;
		for(int i=1;i<=sheet.getLastRowNum();i++){//第一列是表头
			row = sheet.getRow(i);
			if(row==null){// 过滤掉Excel的空行
				continue;
			}
			noStr = this.getStringCellValue(row.getCell(0));//第一列为报名号
			if(noStr==null){
				smsLost.add(String.valueOf(i+1));
				continue ;
			}
			value = this.getStringCellValue(row.getCell(3));
			if(ReturnCode.SUCCESS!=this.doModify(field,noStr, value)){
				smsLost.add(String.valueOf(i+1));
			}else{
				success++;
			}
		}
		
		Response res = new Response(ReturnCode.SUCCESS);
		StringBuilder sb = new StringBuilder("导入结果如下：成功").append(success).append("条");
		if(smsLost.size()>0){//某些短信生成失败
			String lostName = this.list2String(smsLost);
			sb.append("；失败").append(smsLost.size())
				.append("条，行号分别为").append(lostName);
		}
		res.setErrorMsg(sb.toString());
		return res;
	}
	private int doModify(DataField field,String noStr,String value){
		noStr = Utils.emptyToNull(noStr);
		value = Utils.emptyToNull(value);
		if(noStr==null||value==null){
			return ReturnCode.SERVER_INNER_ERROR;
		}
		int year = 0;
		int type = 0;
		int no = 0;
		try{
			year = Integer.valueOf(noStr.substring(0,4));
			type = Integer.valueOf(noStr.substring(4,5));
			no = Integer.valueOf(noStr.substring(5));
		}catch(Exception e){
			return ReturnCode.SERVER_INNER_ERROR;
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("field", field.field());
		params.put("value", value);
		params.put("year", year);
		params.put("type", type);
		params.put("no", no);
		int uc = stuInfoDao.importField(params);
		return uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR;
	}
	private String getStringCellValue(Cell cell) {
		try {
			if (cell == null) {
				return null;
			}
			String value = null;
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = Utils.emptyToNull(cell.getRichStringCellValue()
						.getString());
				break;
			case Cell.CELL_TYPE_NUMERIC:
//				value = new BigDecimal(cell.getNumericCellValue()).toString();
				if(DateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					value = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue()));
				}else{
					value = new DecimalFormat("#").format(cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				value = String.valueOf(cell.getBooleanCellValue());
				break;
			default:
				break;
			}
			if (value != null) {
				value = Utils.htmlEscape(value).replaceAll("&[^;]*;",
						"");//去掉excel中的隐藏字符
			}
			return value;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return null;
		}
	}
	private String list2String(List<String> names){
		if(names==null||names.size()==0){
			return null;
		}
		StringBuilder sb = new StringBuilder("【");
		for(String name:names){
			sb.append(name).append("、");
		}
		sb.replace(sb.length()-1, sb.length(), "】");
		return sb.toString();
	}
	
	@Override
	public Response modifyClassService(String idStr,String no){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("stuId", id);
		params.put("no", no);
		int uc = stuInfoDao.modifyOther10(params);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

}
