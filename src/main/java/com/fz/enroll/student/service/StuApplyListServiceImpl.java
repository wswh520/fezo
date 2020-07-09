package com.fz.enroll.student.service;

import java.util.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.HttpUtils;
import com.fz.enroll.entity.student.StuSmsInfo;
import com.fz.enroll.enum0.*;
import com.fz.enroll.student.dao.*;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.base.dao.BaseDAO;
import com.fz.base.service.QueryBaseServiceImpl;
import com.fz.common.res.PageResData;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.entity.student.StuInfo;
import com.fz.enroll.entity.student.StuVaccine;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service("stuApplyListService")
public class StuApplyListServiceImpl extends QueryBaseServiceImpl<StuApply>
		implements StuApplyListService {
	private static final Logger logger = LoggerFactory.getLogger(StuApplyListServiceImpl.class);

	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private StuApplyListDAO stuApplyListDao;//
	@Autowired
	private StuApplyDAO stuApplyDao;
	@Autowired
	private StuInfoDAO stuInfoDao;
	@Autowired
	private StuVaccineDAO stuVaccineDao;
	@Autowired
	private StuSmsInfoDAO stuSmsInfoDAO;
	
	@Override
	protected BaseDAO<StuApply> getDao() {
		return stuApplyListDao;
	}
	
	@Override
	public Map<String,Object> createQueryParams(ExportOrder order,String keyword,String year,String ageScopeStr,String other54,String sex,
			String status,String infoStatus,String vaccineStatus,String school){
		ageScopeStr = Utils.emptyToNull(ageScopeStr);
//		type = Utils.emptyToNull(type);
		sex = Utils.emptyToNull(sex);
		school = Utils.emptyToNull(school);
		status = Utils.emptyToNull(status);
		infoStatus = Utils.emptyToNull(infoStatus);
		vaccineStatus = Utils.emptyToNull(vaccineStatus);
		Map<String,Object> params = new HashMap<String,Object>();
		if(order!=null){
			params.put("orderField", order.field());
		}
		params.put("keyword", Utils.emptyToNull(keyword));
		int yearInt = 0;
		if(year!=null){
			try {
				yearInt = Integer.valueOf(year);
			} catch (Exception e) {}
		}
		yearInt = yearInt>0?yearInt:DateUtils.getCurrentYear();
		params.put("year", yearInt);
		if(ageScopeStr!=null){
			AgeScope ageScope = AgeScope.valueOf(ageScopeStr);
			params.put("dateOfBirthBegin", DateUtils.getTimeByYear0831(yearInt-ageScope.end()));
			params.put("dateOfBirthEnd", DateUtils.getTimeByYear0831(yearInt-ageScope.begin()));
		}
//		if(type!=null){
//			params.put("type", StuType.valueOf(type).val());
//		}
		if(sex!=null){
			params.put("sex", BooleanEnum.valueOf(sex).val());
		}
		if (school!=null) {
			if ("NANHU".equals(school)) {
				params.put("school", "南湖校区");
			}
			if ("GUIZISHAN".equals(school)) {
				params.put("school", "桂子山校区");
			}
		}
		if(status!=null){
			params.put("status", StuApplyStatus.valueOf(status).val());
		}
		if(infoStatus!=null){
			params.put("infoStatus", StuApplyStatus.valueOf(infoStatus).val());
		}
		if(vaccineStatus!=null){
			params.put("vaccineStatus", StuApplyStatus.valueOf(vaccineStatus).val());
		}
		if(StringUtils.isNotBlank(other54)){
			params.put("other54", other54);
		}
		return params;
	}
	
	@Override
	public Response reviewService(StuApply entity){
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		entity.setYear(DateUtils.getCurrentYear());//只能处理当前年份的数据
		int uc = stuApplyListDao.review(entity);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public Response saveMsgService(Integer id,String message){
		if(id==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		StuApply params = new StuApply();
		params.setId(id);
		params.setMessage(Utils.emptyToNull(message));
		int uc = stuApplyListDao.updateMsg(params);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public Response batchReviewService(String[] idStrs,String statusStr){
		List<Integer> ids = new ArrayList<Integer>();
		int status = 0;
		try{
			status = StuApplyStatus.valueOf(statusStr).val();
			for(String ele:idStrs){
				ids.add(Integer.valueOf(ele));
			}
		}catch(Exception e){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		if(ids.size()==0
				||(status!=StuApplyStatus.REVIEW_PASS.val()
						&&status!=StuApplyStatus.REVIEW_REFUSE.val()
						&&status!=StuApplyStatus.REVIEW_WAITING.val())){
			return new Response(ReturnCode.SUCCESS);
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ids", ids);
		params.put("year", DateUtils.getCurrentYear());//只能处理当前年份的数据
		params.put("status", status);
		params.put("dateOfReview", System.currentTimeMillis());
		stuApplyListDao.batchReview(params);
		return new Response(ReturnCode.SUCCESS);
	}

	@Override
	public void exportService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response){
		if(year==null||year<=0){
			year = DateUtils.getCurrentYear();
		}

		ServletOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		try{
			String filename = year+"入学登记信息";
			boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
			if(isIE6){
				response.setHeader( "Content-Disposition", "attachment;filename=" + 
					     new String( filename.getBytes("gb2312"), "ISO8859-1" )+".xls" );
			}else{
				String indexName = new String(filename.getBytes(),"ISO-8859-1")+".xls";//URLEncoder.encode(document.getName(), "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename="+ indexName);
			}
			fos = response.getOutputStream();
			HSSFSheet s = wb.createSheet("入学登记信息");
			
			this.doExportApply(s, this.createQueryParams(order,keyword,String.valueOf(year),ageScope, type, sex, status, infoStatus, vaccineStatus, school));
			
			wb.write(fos);
			fos.flush();
			
		}catch(Exception e){
			logger.error("写入Excel文件错误！",e);
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(Exception e1){}
			}
		}
	}
	private void doExportApply(HSSFSheet s,Map<String,Object> params){
		int rowIndex = 0;
		{
			//表头
			HSSFRow row = s.createRow(rowIndex++);
			int cellIndex = 0;
			//第一列
			this.writeDataToCell(row, cellIndex++, "报名号");
			this.writeDataToCell(row, cellIndex++, "类别");
			this.writeDataToCell(row, cellIndex++, "姓名");
			this.writeDataToCell(row, cellIndex++, "出生日期");
			this.writeDataToCell(row, cellIndex++, "性别");
			this.writeDataToCell(row, cellIndex++, "民族");
			this.writeDataToCell(row, cellIndex++, "出生地");
			this.writeDataToCell(row, cellIndex++, "籍贯");
			this.writeDataToCell(row, cellIndex++, "健康状况");
			this.writeDataToCell(row, cellIndex++, "身份证件类型");
			this.writeDataToCell(row, cellIndex++, "身份证件号");
			this.writeDataToCell(row, cellIndex++, "国籍/地区");
			this.writeDataToCell(row, cellIndex++, "港澳台侨外");
			this.writeDataToCell(row, cellIndex++, "户口所在地");
			this.writeDataToCell(row, cellIndex++, "户口性质");
			this.writeDataToCell(row, cellIndex++, "户主姓名");
			this.writeDataToCell(row, cellIndex++, "与户主关系");
			this.writeDataToCell(row, cellIndex++, "户口详细地址");
			this.writeDataToCell(row, cellIndex++, "现住址");
			this.writeDataToCell(row, cellIndex++, "现住址类型");
			this.writeDataToCell(row, cellIndex++, "现住址与户口地址是否一致");
			this.writeDataToCell(row, cellIndex++, "邮箱地址");
			this.writeDataToCell(row, cellIndex++, "父亲姓名");
			this.writeDataToCell(row, cellIndex++, "父亲工作单位");
			this.writeDataToCell(row, cellIndex++, "父亲职务");
			this.writeDataToCell(row, cellIndex++, "父亲联系电话");
			this.writeDataToCell(row, cellIndex++, "父亲校园一卡通号码");
			this.writeDataToCell(row, cellIndex++, "母亲姓名");
			this.writeDataToCell(row, cellIndex++, "母亲工作单位");
			this.writeDataToCell(row, cellIndex++, "母亲职务");
			this.writeDataToCell(row, cellIndex++, "母亲联系电话");
			this.writeDataToCell(row, cellIndex++, "母亲校园一卡通号码");
			this.writeDataToCell(row, cellIndex++, "与祖辈关系");
			this.writeDataToCell(row, cellIndex++, "祖辈姓名");
			this.writeDataToCell(row, cellIndex++, "祖辈单位");
			this.writeDataToCell(row, cellIndex++, "祖辈联系电话");
			this.writeDataToCell(row, cellIndex++, "祖辈单位领导姓名");
			this.writeDataToCell(row, cellIndex++, "祖辈单位领导电话");
			this.writeDataToCell(row, cellIndex++, "祖辈校园一卡通号码");
			this.writeDataToCell(row, cellIndex++, "备注");
			this.writeDataToCell(row, cellIndex++, "地区报名类型");
		}
		

		PageResData<StuApply> page = new PageResData<StuApply>();
		page.setLimit(50);
		List<StuApply> stuApplys = null;
		int pageNo = 1;
		do{
			page.setCurrentPage(pageNo);
			stuApplys = this.getStuApplys(params, page);
			
			for(StuApply ele:stuApplys){
				HSSFRow row = s.createRow(rowIndex++);
				int cellIndex = 0;
				this.writeDataToCell(row, cellIndex++, ele.getNoStr());
				this.writeDataToCell(row, cellIndex++, ele.getTypeStr().shortAlias());
				this.writeDataToCell(row, cellIndex++, ele.getName());
				this.writeDataToCell(row, cellIndex++, ele.getShortDateOfBirthStr());
				if(ele.getSex()==BooleanEnum.TRUE.val()){
					this.writeDataToCell(row, cellIndex++, "男");
				}else{
					this.writeDataToCell(row, cellIndex++, "女");
				}
				this.writeDataToCell(row, cellIndex++, ele.getNation());
				this.writeDataToCell(row, cellIndex++, ele.getAddressOfBirth());
				this.writeDataToCell(row, cellIndex++, ele.getBirthplace());
				this.writeDataToCell(row, cellIndex++, ele.getJkzt());
				this.writeDataToCell(row, cellIndex++, ele.getCardTypeStr().alias());
				this.writeDataToCell(row, cellIndex++, ele.getCardNo());
				this.writeDataToCell(row, cellIndex++, ele.getCitizenship());
				this.writeDataToCell(row, cellIndex++, ele.getGatqw());
				ele.setOther1(Utils.removeLastChar(ele.getOther1(), "省"));
				ele.setOther2(Utils.removeLastChar(ele.getOther2(), "市"));
				ele.setOther3(Utils.removeLastChar(ele.getOther3(), "区"));
				ele.setOther3(Utils.removeLastChar(ele.getOther3(), "县"));
				if(ele.getOther1()!=null&&!"湖北".equals(ele.getOther1())){//湖北省外只出现***省
					this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther1(),"省 "));
				}else if(ele.getOther2()!=null&&!"武汉".equals(ele.getOther2())){//湖北省内武汉市外出现***市
					this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther2(),"市 "));
				}else if(ele.getOther3()!=null){//武汉市内只出现***区
					this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther3(),"区"));
				}else if(ele.getOther2()!=null){//显示武汉市
					this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther2(),"市 "));
				}else if(ele.getOther1()!=null){//显示湖北省
					this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther1(),"省 "));
				}else{//显示国籍
					this.writeDataToCell(row, cellIndex++, ele.getOther35());
				}
//				this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther1(),"省 ",ele.getOther2(),"市 ",ele.getOther3(),"区（县）"));
				this.writeDataToCell(row, cellIndex++, ele.getOther4());
				this.writeDataToCell(row, cellIndex++, ele.getOther5());
				this.writeDataToCell(row, cellIndex++, ele.getOther6());
				this.writeDataToCell(row, cellIndex++, ele.getOther7());
				this.writeDataToCell(row, cellIndex++, Utils.connectString(ele.getOther9(),"小区"));
//				this.writeDataToCell(row, cellIndex++, Utils.connectString("武汉市 ",ele.getOther8(),"区 ",ele.getOther9(),"小区"));
				this.writeDataToCell(row, cellIndex++, ele.getOther10());
				this.writeDataToCell(row, cellIndex++, ele.getOther11());
				this.writeDataToCell(row, cellIndex++, ele.getOther12());
				this.writeDataToCell(row, cellIndex++, ele.getOther13());
				this.writeDataToCell(row, cellIndex++, ele.getOther14());
				this.writeDataToCell(row, cellIndex++, ele.getOther15());
				this.writeDataToCell(row, cellIndex++, ele.getOther16());
				this.writeDataToCell(row, cellIndex++, ele.getOther17());
				this.writeDataToCell(row, cellIndex++, ele.getOther18());
				this.writeDataToCell(row, cellIndex++, ele.getOther19());
				this.writeDataToCell(row, cellIndex++, ele.getOther20());
				this.writeDataToCell(row, cellIndex++, ele.getOther21());
				this.writeDataToCell(row, cellIndex++, ele.getOther22());
				if(ele.getType()==StuType.TYPEB.val()){
					this.writeDataToCell(row, cellIndex++, ele.getOther23());
					this.writeDataToCell(row, cellIndex++, ele.getOther24());
					this.writeDataToCell(row, cellIndex++, ele.getOther26());
					this.writeDataToCell(row, cellIndex++, ele.getOther25());
					this.writeDataToCell(row, cellIndex++, ele.getOther27());
					this.writeDataToCell(row, cellIndex++, ele.getOther28());
					this.writeDataToCell(row, cellIndex++, ele.getOther41());
				}else{
					cellIndex += 7;
				}
				this.writeDataToCell(row, cellIndex++, ele.getOther36());
				if(ele.getOther43()!=null){
					if("是".contains(ele.getOther43())){
						this.writeDataToCell(row, cellIndex++, "区内已报名");
					}else if(ele.getOther44()!=null){
						if("是".contains(ele.getOther44())){
							this.writeDataToCell(row, cellIndex++, "区外已报名");
						}else{
							this.writeDataToCell(row, cellIndex++, "未报名");
						}
					}
				}else{
					cellIndex++;
				}
			}
			
			pageNo++;
		}while(page.getTotalPages()>=pageNo);
	}
	private List<StuApply> getStuApplys(Map<String,Object> params,PageResData<StuApply> page){
		Page<?> _page = PageHelper.startPage(page.getCurrentPage(), page.getLimit());
		List<StuApply> eles = stuApplyListDao.query(params);
		page.setTotalRecords((int)_page.getTotal());
		return eles;
	}
	private void writeDataToCell(HSSFRow row,int index ,String value){
		HSSFCell cell = row.createCell(index);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(value));
	}

	@Override
	public void exportInfoService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response){
		if(year==null||year<=0){
			year = DateUtils.getCurrentYear();
		}

		ServletOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		try{
			String filename = year+"学籍信息表";
			boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
			if(isIE6){
				response.setHeader( "Content-Disposition", "attachment;filename=" + 
					     new String( filename.getBytes("gb2312"), "ISO8859-1" )+".xls" );
			}else{
				String indexName = new String(filename.getBytes(),"ISO-8859-1")+".xls";//URLEncoder.encode(document.getName(), "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename="+ indexName);
			}
			fos = response.getOutputStream();
			HSSFSheet s = wb.createSheet("学籍信息表");
			
			this.doExportInfo(s, this.createQueryParams(order,keyword,String.valueOf(year),ageScope, type, sex, status, infoStatus, vaccineStatus, school));
			
			wb.write(fos);
			fos.flush();
			
		}catch(Exception e){
			logger.error("写入Excel文件错误！",e);
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(Exception e1){}
			}
		}
	}
	private void doExportInfo(HSSFSheet s,Map<String,Object> params){
		int rowIndex = 0;
		{
			//表头
			HSSFRow row = s.createRow(rowIndex++);
			int cellIndex = 0;
			//第一列
			this.writeDataToCell(row, cellIndex++, "学校标识码");
//			this.writeDataToCell(row, cellIndex++, "报名号");
			this.writeDataToCell(row, cellIndex++, "姓名");
			this.writeDataToCell(row, cellIndex++, "性别");
			this.writeDataToCell(row, cellIndex++, "出生日期");
			this.writeDataToCell(row, cellIndex++, "出生地");
			this.writeDataToCell(row, cellIndex++, "籍贯");
			this.writeDataToCell(row, cellIndex++, "民族");
			this.writeDataToCell(row, cellIndex++, "国籍/地区");
			this.writeDataToCell(row, cellIndex++, "身份证件类型");
			this.writeDataToCell(row, cellIndex++, "港澳台侨外");
			this.writeDataToCell(row, cellIndex++, "健康状况");
			this.writeDataToCell(row, cellIndex++, "政治面貌");
			this.writeDataToCell(row, cellIndex++, "身份证件号");

			this.writeDataToCell(row, cellIndex++, "户口性质");//5
			this.writeDataToCell(row, cellIndex++, "户口所在地");//4
			this.writeDataToCell(row, cellIndex++, "班级");//10
			this.writeDataToCell(row, cellIndex++, "入学年月");//11
			this.writeDataToCell(row, cellIndex++, "入学方式");//12
			this.writeDataToCell(row, cellIndex++, "就读方式");//13
			this.writeDataToCell(row, cellIndex++, "现住址");//15
			this.writeDataToCell(row, cellIndex++, "通信地址");//16
			this.writeDataToCell(row, cellIndex++, "家庭地址");//17
			this.writeDataToCell(row, cellIndex++, "联系电话");//18
			this.writeDataToCell(row, cellIndex++, "邮政编码");//19
			this.writeDataToCell(row, cellIndex++, "是否独生子女");//22
			this.writeDataToCell(row, cellIndex++, "是否受过学前教育");//23
			this.writeDataToCell(row, cellIndex++, "是否留守儿童");//24
			this.writeDataToCell(row, cellIndex++, "是否需要申请资助");//31
			this.writeDataToCell(row, cellIndex++, "是否享受一补");//32
			this.writeDataToCell(row, cellIndex++, "是否孤儿");//26
			this.writeDataToCell(row, cellIndex++, "是否烈士或优抚子女");//27
			this.writeDataToCell(row, cellIndex++, "上下学距离（公里）");//33
			this.writeDataToCell(row, cellIndex++, "上下学交通方式");//34
			this.writeDataToCell(row, cellIndex++, "是否需要乘坐校车");//35
//			this.writeDataToCell(row, cellIndex++, "姓名拼音");//1
			this.writeDataToCell(row, cellIndex++, "曾用名");//2
			this.writeDataToCell(row, cellIndex++, "身份证件有效期");//3
			this.writeDataToCell(row, cellIndex++, "血型");//xx
			this.writeDataToCell(row, cellIndex++, "特长");//6
			this.writeDataToCell(row, cellIndex++, "学籍辅号");//7
			this.writeDataToCell(row, cellIndex++, "班内学号");//8
			this.writeDataToCell(row, cellIndex++, "学生来源");//14
//			this.writeDataToCell(row, cellIndex++, "年级");//9
			this.writeDataToCell(row, cellIndex++, "电子信箱");//20
			this.writeDataToCell(row, cellIndex++, "主页地址");//21
			this.writeDataToCell(row, cellIndex++, "残疾类型");//29
			this.writeDataToCell(row, cellIndex++, "是否由政府购买学位");//30
			this.writeDataToCell(row, cellIndex++, "随班就读");//28
			this.writeDataToCell(row, cellIndex++, "成员1姓名");//36
			this.writeDataToCell(row, cellIndex++, "成员1关系");//37
			this.writeDataToCell(row, cellIndex++, "成员1关系说明");//38
			this.writeDataToCell(row, cellIndex++, "成员1现住址");//41
			this.writeDataToCell(row, cellIndex++, "成员1户口所在地");//42
			this.writeDataToCell(row, cellIndex++, "成员1联系电话");//43
			this.writeDataToCell(row, cellIndex++, "成员1是否监护人");//44
			this.writeDataToCell(row, cellIndex++, "成员1身份证件类型");//45
			this.writeDataToCell(row, cellIndex++, "成员1身份证件号");//46
			this.writeDataToCell(row, cellIndex++, "成员1民族");//39
			this.writeDataToCell(row, cellIndex++, "成员1工作单位");//40
			this.writeDataToCell(row, cellIndex++, "成员1职务");//47
			this.writeDataToCell(row, cellIndex++, "成员2姓名");//48
			this.writeDataToCell(row, cellIndex++, "成员2关系");//49
			this.writeDataToCell(row, cellIndex++, "成员2关系说明");//50
			this.writeDataToCell(row, cellIndex++, "成员2现住址");//53
			this.writeDataToCell(row, cellIndex++, "成员2户口所在地");//54
			this.writeDataToCell(row, cellIndex++, "成员2联系电话");//55
			this.writeDataToCell(row, cellIndex++, "成员2是否监护人");//56
			this.writeDataToCell(row, cellIndex++, "成员2身份证件类型");//57
			this.writeDataToCell(row, cellIndex++, "成员2身份证件号");//58
			this.writeDataToCell(row, cellIndex++, "成员2民族");//51
			this.writeDataToCell(row, cellIndex++, "成员2工作单位");//52
			this.writeDataToCell(row, cellIndex++, "成员2职务");//59
			this.writeDataToCell(row, cellIndex++, "是否进城务工人员随迁子女");//25
		}
		

		PageResData<StuApply> page = new PageResData<StuApply>();
		page.setLimit(50);
		List<StuApply> stuApplys = null;
		Map<Integer,StuInfo> infoMap = null;
		StuInfo stuInfo = null;
		int pageNo = 1;
//		int index = 1;//编号
		do{
			page.setCurrentPage(pageNo);
			stuApplys = this.getStuApplys(params, page);
			infoMap = this.createStuInfoMap(stuApplys);
			
			for(StuApply ele:stuApplys){
				stuInfo = infoMap.get(ele.getId());
				HSSFRow row = s.createRow(rowIndex++);
				int cellIndex = 0;
				this.writeDataToCell(row, cellIndex++, "2142001636");
//				this.writeDataToCell(row, cellIndex++, ele.getNoStr());
				this.writeDataToCell(row, cellIndex++, ele.getName());
				if(ele.getSex()==BooleanEnum.TRUE.val()){
					this.writeDataToCell(row, cellIndex++, "男");
				}else{
					this.writeDataToCell(row, cellIndex++, "女");
				}
				this.writeDataToCell(row, cellIndex++, ele.getShortDateOfBirthStr());
				if(stuInfo!=null){
					this.writeDataToCell(row, cellIndex++, stuInfo.getAddressOfBirth());
				}else{
					cellIndex++;
				}
				this.writeDataToCell(row, cellIndex++, ele.getBirthplace());
				this.writeDataToCell(row, cellIndex++, ele.getNation());
				this.writeDataToCell(row, cellIndex++, ele.getCitizenship());
				this.writeDataToCell(row, cellIndex++, ele.getCardTypeStr().alias());
				this.writeDataToCell(row, cellIndex++, ele.getGatqw());
				this.writeDataToCell(row, cellIndex++, ele.getJkzt());
				this.writeDataToCell(row, cellIndex++, "群众");//政治面貌
				this.writeDataToCell(row, cellIndex++, ele.getCardNo());
				if(stuInfo!=null){
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther5());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther4());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther10());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther11());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther12());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther13());
					this.writeDataToCell(row, cellIndex++, this.cutAddress(stuInfo.getOther15()));
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther16());
					if(stuInfo.getOther17()==null){
						this.writeDataToCell(row, cellIndex++, stuInfo.getOther17());
					}else{
						this.writeDataToCell(row, cellIndex++, stuInfo.getOther17().replace("武汉市", ""));
					}
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther18());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther19());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther22());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther23());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther24());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther31());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther32());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther26());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther27());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther33());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther34());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther35());
//					this.writeDataToCell(row, cellIndex++, stuInfo.getOther1());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther2());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther3());
					cellIndex++;//血型
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther6());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther7());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther8());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther14());
//					this.writeDataToCell(row, cellIndex++, stuInfo.getOther9());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther20());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther21());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther29());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther30());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther28());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther36());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther37());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther38());
					this.writeDataToCell(row, cellIndex++, this.cutAddress(stuInfo.getOther41()));
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther42());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther43());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther44());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther45Str().alias());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther46());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther39());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther40());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther47());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther48());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther49());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther50());
					this.writeDataToCell(row, cellIndex++, this.cutAddress(stuInfo.getOther53()));
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther54());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther55());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther56());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther57Str().alias());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther58());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther51());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther52());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther59());
					this.writeDataToCell(row, cellIndex++, stuInfo.getOther25());
				}
			}
			
			pageNo++;
		}while(page.getTotalPages()>=pageNo);
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
	private Map<Integer,StuInfo> createStuInfoMap(List<StuApply> applys){
		Map<Integer,StuInfo> map = new HashMap<Integer,StuInfo>();
		List<Integer> stuIds = new ArrayList<Integer>();
		for(StuApply ele:applys){
			stuIds.add(ele.getId());
		}
		if(stuIds.size()==0){
			return map;
		}
		List<StuInfo> stuInfos = stuInfoDao.queryByStuIds(stuIds);
		for(StuInfo ele:stuInfos){
			map.put(ele.getStuId(), ele);
		}
		return map;
	}

	@Override
	public void exportVaccineService(ExportOrder order,String keyword,Integer year,String ageScope,String type,String sex,
			String status,String infoStatus,String vaccineStatus,String school,
			HttpServletRequest request,HttpServletResponse response){
		if(year==null||year<=0){
			year = DateUtils.getCurrentYear();
		}

		ServletOutputStream fos = null;
		HSSFWorkbook wb = new HSSFWorkbook();
		try{
			String filename = year+"入学儿童预防接种查验登记表";
			boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
			if(isIE6){
				response.setHeader( "Content-Disposition", "attachment;filename=" + 
					     new String( filename.getBytes("gb2312"), "ISO8859-1" )+".xls" );
			}else{
				String indexName = new String(filename.getBytes(),"ISO-8859-1")+".xls";//URLEncoder.encode(document.getName(), "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename="+ indexName);
			}
			fos = response.getOutputStream();
			HSSFSheet s = wb.createSheet("入学儿童预防接种查验登记表");
			
			this.doExportVaccine(s, this.createQueryParams(order,keyword,String.valueOf(year),ageScope, type, sex, status, infoStatus, vaccineStatus, school));
			
			wb.write(fos);
			fos.flush();
			
		}catch(Exception e){
			logger.error("写入Excel文件错误！",e);
		}finally{
			if(fos!=null){
				try{
					fos.close();
				}catch(Exception e1){}
			}
		}
	}
	private void doExportVaccine(HSSFSheet s,Map<String,Object> params){
		int rowIndex = 0;
		{
			//表头
			HSSFRow row = s.createRow(rowIndex++);
			int cellIndex = 0;
			//第一列
			this.writeDataToCell(row, cellIndex++, "编号");
			this.writeDataToCell(row, cellIndex++, "报名号");
			this.writeDataToCell(row, cellIndex++, "姓名");
			this.writeDataToCell(row, cellIndex++, "出生日期");
			this.writeDataToCell(row, cellIndex++, "儿童编码");
			this.writeDataToCell(row, cellIndex++, "性别");
			this.writeDataToCell(row, cellIndex++, "是否有接种证");
			this.writeDataToCell(row, cellIndex++, "是否全种");
			this.writeDataToCell(row, cellIndex++, "乙肝疫苗1");
			this.writeDataToCell(row, cellIndex++, "乙肝疫苗2");
			this.writeDataToCell(row, cellIndex++, "乙肝疫苗3");
			this.writeDataToCell(row, cellIndex++, "卡介苗");
			this.writeDataToCell(row, cellIndex++, "脊灰疫苗1");
			this.writeDataToCell(row, cellIndex++, "脊灰疫苗2");
			this.writeDataToCell(row, cellIndex++, "脊灰疫苗3");
			this.writeDataToCell(row, cellIndex++, "脊灰疫苗4");
			this.writeDataToCell(row, cellIndex++, "百白破疫苗1");
			this.writeDataToCell(row, cellIndex++, "百白破疫苗2");
			this.writeDataToCell(row, cellIndex++, "百白破疫苗3");
			this.writeDataToCell(row, cellIndex++, "百白破疫苗4");
			this.writeDataToCell(row, cellIndex++, "白破疫苗");
			this.writeDataToCell(row, cellIndex++, "麻风疫苗");
			this.writeDataToCell(row, cellIndex++, "麻腮疫苗");
			this.writeDataToCell(row, cellIndex++, "乙脑疫苗1");
			this.writeDataToCell(row, cellIndex++, "乙脑疫苗2");
			this.writeDataToCell(row, cellIndex++, "A群流脑疫苗1");
			this.writeDataToCell(row, cellIndex++, "A群流脑疫苗2");
			this.writeDataToCell(row, cellIndex++, "A+C群流脑疫苗1");
			this.writeDataToCell(row, cellIndex++, "A+C群流脑疫苗2");
			this.writeDataToCell(row, cellIndex++, "甲肝疫苗");
		}
		

		PageResData<StuApply> page = new PageResData<StuApply>();
		page.setLimit(50);
		List<StuApply> stuApplys = null;
		Map<Integer,StuVaccine> vaccineMap = null;
		StuVaccine stuVaccine = null;
		int pageNo = 1;
		int index = 1;//编号
		do{
			page.setCurrentPage(pageNo);
			stuApplys = this.getStuApplys(params, page);
			vaccineMap = this.createVaccinfInfoMap(stuApplys);
			
			for(StuApply ele:stuApplys){
				stuVaccine = vaccineMap.get(ele.getId());
				HSSFRow row = s.createRow(rowIndex++);
				int cellIndex = 0;
				this.writeDataToCell(row, cellIndex++, String.valueOf(index++));
				this.writeDataToCell(row, cellIndex++, ele.getNoStr());
				this.writeDataToCell(row, cellIndex++, ele.getName());
				this.writeDataToCell(row, cellIndex++, ele.getShortDateOfBirthStr());
//				cellIndex++;//户籍
				this.writeDataToCell(row, cellIndex++, stuVaccine.getOther25());
				if(ele.getSex()==BooleanEnum.TRUE.val()){
					this.writeDataToCell(row, cellIndex++, "男");
				}else{
					this.writeDataToCell(row, cellIndex++, "女");
				}
				if(stuVaccine!=null){
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther1()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther2()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther3()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther4()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther5()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther6()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther7()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther8()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther9()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther10()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther11()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther12()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther13()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther14()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther15()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther16()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther17()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther18()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther19()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther20()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther21()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther22()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther23()));
					this.writeDataToCell(row, cellIndex++, this.convertInt2Symbol(stuVaccine.getOther24()));
				}
			}
			
			pageNo++;
		}while(page.getTotalPages()>=pageNo);
	}
	private Map<Integer,StuVaccine> createVaccinfInfoMap(List<StuApply> applys){
		Map<Integer,StuVaccine> map = new HashMap<Integer,StuVaccine>();
		List<Integer> stuIds = new ArrayList<Integer>();
		for(StuApply ele:applys){
			stuIds.add(ele.getId());
		}
		if(stuIds.size()==0){
			return map;
		}
		List<StuVaccine> vaccineInfos = stuVaccineDao.queryByStuIds(stuIds);
		for(StuVaccine ele:vaccineInfos){
			map.put(ele.getStuId(), ele);
		}
		return map;
	}
	private String convertInt2Symbol(Integer val){
		if(val==null){
			return null;
		}else if(val==BooleanEnum.TRUE.val()){
			return "√";
		}else if(val==BooleanEnum.FALSE.val()){
			return "×";
		}else{
			return null;
		}
	}
	
	@Override
	public Response submitAllService(){
		Response res = timeConfigService.isTimeEnd(TimeConfigType.APPLY_MODIFY);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			res.setErrorMsg("此操作只能在“改表时段”结束后才能执行！");
			return res;
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("year", DateUtils.getCurrentYear());//只能处理当前年份的数据
		params.put("currentStatus", StuApplyStatus.SUBMIT_ONCE.val());
		params.put("targetStatus", StuApplyStatus.SUBMIT_TWICE.val());
		
		int uc = stuApplyDao.submitAll(params);
		res.setRetCode(ReturnCode.SUCCESS);
		res.setErrorMsg(Utils.connectString("共有 ",uc," 条记录被提交！"));
		return res;
	}
	
	@Override
	public Response resetAllInfoService(){
		Response res = timeConfigService.isTimeEnd(TimeConfigType.INFO_INPUT);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			res.setErrorMsg("此操作只能在“学籍信息录入”结束后才能执行！");
			return res;
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("year", DateUtils.getCurrentYear());//只能处理当前年份的数据
		params.put("currentStatus", StuApplyStatus.SUBMIT_ONCE.val());
		params.put("targetStatus", StuApplyStatus.SUBMIT_NONE.val());
		
		int uc = stuInfoDao.submitAll(params);
		res.setRetCode(ReturnCode.SUCCESS);
		res.setErrorMsg(Utils.connectString("共有 ",uc," 条记录被重置！"));
		return res;
	}
	
	@Override
	public Response submitAllInfoService(){
		Response res = timeConfigService.isTimeEnd(TimeConfigType.INFO_INPUT);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			res.setErrorMsg("此操作只能在“学籍信息录入”结束后才能执行！");
			return res;
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("year", DateUtils.getCurrentYear());//只能处理当前年份的数据
		params.put("currentStatus", StuApplyStatus.SUBMIT_NONE.val());
		params.put("targetStatus", StuApplyStatus.SUBMIT_ONCE.val());
		
		int uc = stuInfoDao.submitAll(params);
		res.setRetCode(ReturnCode.SUCCESS);
		res.setErrorMsg(Utils.connectString("共有 ",uc," 条记录被提交！"));
		return res;
	}

	@Override
	public Response sendSms(HttpServletRequest request) {
		List<StuSmsInfo> smsInfos = stuSmsInfoDAO.findAll();
		//进行遍历，发送短信
		for (StuSmsInfo sms : smsInfos) {
			int id = sms.getId();
			String phone = sms.getPhone();
			String param = "test";
			sendSms(phone, param,id);
		};
		return new Response(ReturnCode.SUCCESS);
	}

	public void sendSms(String phone,String param,Integer id) {
		String host = "http://smsmsgs.market.alicloudapi.com";
		String path = "/smsmsgs";
		String method = "GET";
		String appcode = "146b886fdad1499b9d36232b8a2a71e1";
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("phone", phone);
		querys.put("sign", "175622");
		querys.put("skin", "1");
		querys.put("param", param);
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			stuSmsInfoDAO.sendSms(id);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
