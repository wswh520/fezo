package com.fz.enroll.student.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.fz.base.dao.BaseDAO;
import com.fz.base.service.QueryBaseServiceImpl;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.DateUtils;
import com.fz.common.util.ExcelUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.dto.student.GraduateImportData;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.entity.user.User;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.GraduateImportField;
import com.fz.enroll.enum0.SexEnum;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.dao.StuGraInfoDAO;
import com.fz.enroll.user.dao.UserDAO;

@Service("stuGraInfoListService")
public class StuGraInfoListServiceImpl extends QueryBaseServiceImpl<GraStuInfo> implements StuGraInfoListService{
	private static final Logger logger = LoggerFactory.getLogger(StuGraInfoListServiceImpl.class);
	@Autowired
	private UserDAO userDao;
	@Autowired
	private StuGraInfoDAO stuGraInfoDAO;
	
	@Override
	protected BaseDAO<GraStuInfo> getDao() {
		return stuGraInfoDAO;
	}
	
	@Override
	public Response importService(MultipartFile file,Integer year) {
		if(file==null||file.getSize()==0
				||year==null||year==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}else if(DateUtils.getCurrentYear()!=year){
			return new Response(ReturnCode.SERVER_INNER_ERROR,"当前应该为"+DateUtils.getCurrentYear()+"届");
		}
		Workbook wb = ExcelUtils.getWorkbook(file);
		if(wb==null){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("导入失败，请选择正确的文件！");
			return res;
		}
		Sheet sheet = wb.getSheetAt(0);
		Response res = this.readHeader(sheet);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		
		List<GraduateImportData> datas = this.readBody(sheet);
		this.checkDataValue(datas);
		this.checkUnique(datas);
		
		try {
			List<GraStuInfo> infoList = new ArrayList<GraStuInfo>();
			User _user = null;
			int success = 0;
			int failed = 0;
			List<Integer> repeats = new ArrayList<Integer>();;
			List<String> errorMsgs = new ArrayList<String>();;
			for (GraduateImportData ele : datas) {
				if (!ele.isDataValid()||!ele.isXbxhValid()) {
					failed++;
					if(!ele.isDataValid()){
						errorMsgs.add(ele.getErrorMsg());
					}else if(!ele.isXbxhValid()){
						repeats.add(ele.getLine());
					}
					continue;
				}
				_user = ele.convert2User();
				userDao.save(_user);
				if(_user.getId()==0){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();       
					return new Response(ReturnCode.SERVER_INNER_ERROR);
				}
				infoList.add(ele.convert2Info(_user.getId(),year));
				success++;
			}
			
			int uc = infoList.size()==0?0:stuGraInfoDAO.batchInsert(infoList);
			if(uc!=infoList.size()){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();       
				return new Response(ReturnCode.SERVER_INNER_ERROR);
			}
			
			res.setRetCode(ReturnCode.SUCCESS);
			res.setErrorMsg(this.createMsg(success,failed,repeats,errorMsgs));
			return res;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();       
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
	}
	private Response readHeader(Sheet sheet){
		Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
		for (GraduateImportField dataField :GraduateImportField.values()) {
			String eleName = Utils.emptyToNull(ExcelUtils.getStringCellValue(sheet.getRow(0).getCell(dataField.ordinal())));//第arr[i]列表头
			if(!dataField.alias().equals(eleName)){
				res.setErrorMsg("导入失败，请确认表单第"+(dataField.ordinal()+1)+"列中的内容是否是："+dataField.alias());
				return res;
			}
		}
		return new Response(ReturnCode.SUCCESS);
	}
	private List<GraduateImportData> readBody(Sheet sheet){
		List<GraduateImportData> datas = new ArrayList<GraduateImportData>();
		Row row = null;
		GraduateImportData _data = null;
		for(int i=1;i<=sheet.getLastRowNum();i++){//第一行是表头
			_data = new GraduateImportData(i+1,true,true);
			datas.add(_data);
			
			row = sheet.getRow(i);
			if(row==null){// 过滤掉Excel的空行
				continue;
			}
			_data.setXbxh(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(0))));
			_data.setBj(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(1))));
			_data.setXm(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(2))));
			_data.setXb(SexEnum.valueOfAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(3)))));
			_data.setJg1(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(4))));
			_data.setJg2(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(5))));
			_data.setCsd(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(6))));
			_data.setMz(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(7))));
			_data.setHkxz(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(8))));
			_data.setSfzh(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(9))));
			_data.setDz(BooleanEnum.valueOfAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(10)))));
			_data.setSl(BooleanEnum.valueOfAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(11)))));
			_data.setTl(BooleanEnum.valueOfAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(12)))));
			_data.setZl(BooleanEnum.valueOfAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(13)))));
			_data.setJzxz(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(14))));
			_data.setJtzz(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(15))));
			_data.setHkszd(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(16))));
			String hkdz = ExcelUtils.getStringCellValue(row.getCell(17));
			if(hkdz!=null){
				int index = hkdz.indexOf(GraStuInfo.HKDZ_SPLIT);
				if(index>0){
					_data.setHkdz1(Utils.clearBlank(hkdz.substring(0,index)));
					_data.setHkdz2(Utils.clearBlank(hkdz.substring(index+1)));
				}else{
					_data.setHkdz2(Utils.clearBlank(hkdz));
				}
			}
			_data.setFuxm(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(18))));
			_data.setFudw(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(19))));
			_data.setFudh(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(20))));
			_data.setMuxm(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(21))));
			_data.setMudw(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(22))));
			_data.setMudh(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(23))));
			_data.setSylb(StuType.valueOfGraAlias(Utils.clearBlank(ExcelUtils.getStringCellValue(row.getCell(24)))));
		}
		return datas;
	}
	private void checkDataValue(List<GraduateImportData> datas){
		for(GraduateImportData ele:datas){
			if(ele.getXbxh()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行校编学号为空！"));
				continue ;
			}
			if(ele.getBj()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行班级为空！"));
				continue ;
			}
			if(ele.getXm()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行学生姓名为空！"));
				continue ;
			}
			if(ele.getXb()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行姓别为空或不正确！"));
				continue ;
			}
			if(ele.getJg1()==null){
//				ele.setDataValid(false);
//				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行籍贯省为空！"));
//				continue ;
				ele.setJg1("");//籍贯允许为空，暂不修改数据库
			}
			if(ele.getJg2()==null){
//				ele.setDataValid(false);
//				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行籍贯市为空！"));
//				continue ;
				ele.setJg2("");//籍贯允许为空，暂不修改数据库
			}
			if(ele.getCsd()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行出生地为空！"));
				continue ;
			}
			if(ele.getMz()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行民族为空！"));
				continue ;
			}
			if(ele.getHkxz()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行户口性质为空！"));
				continue ;
			}
			if(ele.getSfzh()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行身份证号为空！"));
				continue ;
			}
			if(ele.getDz()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行独生子女为空或不正确！"));
				continue ;
			}
			if(ele.getSl()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行视力残疾为空或不正确！"));
				continue ;
			}
			if(ele.getTl()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行听力残疾为空或不正确！"));
				continue ;
			}
			if(ele.getZl()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行智力残疾为空或不正确！"));
				continue ;
			}
			if(ele.getJzxz()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行居住性质为空！"));
				continue ;
			}
			if(ele.getJtzz()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行居住地址为空！"));
				continue ;
			}
			if(ele.getHkszd()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行户口所在地为空！"));
				continue ;
			}
			if(ele.getHkdz1()==null||ele.getHkdz2()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行户口地址为空或不正确！！"));
				continue ;
			}
			if(ele.getFuxm()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行父亲姓名为空！"));
				continue ;
			}
			if(ele.getFudw()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行父亲工作单位为空！"));
				continue ;
			}
			if(ele.getFudh()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行父亲电话为空！"));
				continue ;
			}
			if(ele.getMuxm()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行母亲姓名为空！"));
				continue ;
			}
			if(ele.getMudw()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行母亲工作单位为空！"));
				continue ;
			}
			if(ele.getMudh()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行母亲电话为空！"));
				continue ;
			}
			if(ele.getSylb()==null){
				ele.setDataValid(false);
				ele.setErrorMsg(Utils.connectString("第",ele.getLine(),"行生源类别为空或不正确！"));
				continue ;
			}
		}
	}
	private void checkUnique(List<GraduateImportData> datas){
		Map<String,Boolean> cache = new HashMap<String,Boolean>();
		for(GraduateImportData ele:datas){
			if(!ele.isDataValid()){
				ele.setXbxhValid(false);
				continue ;
			}
			if(cache.get(ele.getXbxh())!=null){
				ele.setXbxhValid(false);
				continue ;
			}
			cache.put(ele.getXbxh(), true);
			User user=userDao.queryByUnique(ele.getXbxh());
			if(user!=null){
				ele.setXbxhValid(false);
				continue ;
			}
		}
	}
	private String createMsg(int success,int failed,List<Integer> repeats,List<String> errorMsgs){
		int len = 5;
		if(repeats.size()>0){
			len += 4;
		}
		len += errorMsgs.size()*2;
		Object[] strs = new Object[len];
		int index = 0;
		strs[index++] = "导入结果如下：<br>成功";
		strs[index++] = success;
		strs[index++] = "条；<br>失败";
		strs[index++] = failed;
		if(repeats.size()>0){
			strs[index++] = "条，其中第";
			strs[index++] = ExcelUtils.list2StringLine(repeats);
			strs[index++] = "行共";
			strs[index++] = repeats.size();
			strs[index++] = "行校编学号已存在！";
		}else{
			strs[index++] = "条！";
		}
		for(String ele:errorMsgs){
			strs[index++] = "<br>";;
			strs[index++] = ele;
		}
		return Utils.connectString(strs);
	}
	
	@Override
	public void exportService(MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		if(file==null||file.getSize()==0){
			return ;
		}
		Workbook wbBase = ExcelUtils.getWorkbook(file);
		if(wbBase==null){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("导入失败，请选择正确的文件！");
			return ;
		}
		Sheet sheetBase = wbBase.getSheetAt(0);

		ServletOutputStream fos = null;
		HSSFWorkbook wbOut = new HSSFWorkbook();
		try{
			String filename = "毕业生信息核对表"+DateUtils.timeToString(System.currentTimeMillis(),"yyyyMMdd");
			boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
			if(isIE6){
				response.setHeader( "Content-Disposition", "attachment;filename=" + 
					     new String( filename.getBytes("gb2312"), "ISO8859-1" )+".xls" );
			}else{
				String indexName = new String(filename.getBytes(),"ISO-8859-1")+".xls";//URLEncoder.encode(document.getName(), "UTF-8");
				response.setHeader("Content-disposition", "attachment;filename="+ indexName);
			}
			fos = response.getOutputStream();
			HSSFSheet sheetOut = wbOut.createSheet("入学登记信息");
			Map<String,Integer> username_indexMap = new HashMap<String,Integer>();
			List<String> usernameList = this.copyBaseData(sheetBase, sheetOut,username_indexMap);
			
			this.doExportApply(sheetOut, usernameList, username_indexMap);
			
			wbOut.write(fos);
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
	private List<String> copyBaseData(Sheet sheetBase,HSSFSheet sheetOut,Map<String,Integer> username_indexMap){
		List<String> usernameList = new ArrayList<String>();
		Row rowBase = null;
		HSSFRow rowOut = null;
		String _username = null;
		for(int i=0;i<=sheetBase.getLastRowNum();i++){
			rowBase = sheetBase.getRow(i);
			if(rowBase==null){// 过滤掉Excel的空行
				continue;
			}
			rowOut = sheetOut.createRow(i);
			for(int j=0;j<=rowBase.getLastCellNum();j++){
				this.writeDataToCell(rowOut, j, ExcelUtils.getStringCellValue(rowBase.getCell(j)));
				logger.info(ExcelUtils.getStringCellValue(rowBase.getCell(j)));
			}
			if(i==0){
				continue ;
			}
			_username = Utils.clearBlank(ExcelUtils.getStringCellValue(rowBase.getCell(3)));
			if(_username!=null){
				usernameList.add(_username);
				username_indexMap.put(_username, i);
			}
		}
		return usernameList;
	}
	private void doExportApply(HSSFSheet s,List<String> usernameList,Map<String,Integer> username_indexMap){
		if(usernameList.size()==0){
			return ;
		}
		List<GraStuInfo> infos = stuGraInfoDAO.queryByUsernames(usernameList);
		Integer index = null;
		for(GraStuInfo ele:infos){
			usernameList.remove(ele.getXbxh());
			index = username_indexMap.get(ele.getXbxh());
			if(index==null){
				continue ;
			}
			
			this.writeDataToRow(s.getRow(index), ele);
		}
		for(String ele:usernameList){
			index = username_indexMap.get(ele);
			if(index==null){
				continue ;
			}
			
			this.writeDataToRow(s.getRow(index), new GraStuInfo());
		}
	}
	private void writeDataToRow(HSSFRow row,GraStuInfo info){
		int cellIndex = 4;
		this.writeDataToCell(row, cellIndex++, info.getBj());
		this.writeDataToCell(row, cellIndex++, info.getXm());
		if(info.getXb()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else if(info.getXb()==BooleanEnum.TRUE.val()){
			this.writeDataToCell(row, cellIndex++, "男");
		}else{
			this.writeDataToCell(row, cellIndex++, "女");
		}
		cellIndex += 2;//出生年月、入学年月
		this.writeDataToCell(row, cellIndex++, info.getJg1());
		this.writeDataToCell(row, cellIndex++, info.getJg2());
		this.writeDataToCell(row, cellIndex++, info.getCsd());
		this.writeDataToCell(row, cellIndex++, info.getMz());
		this.writeDataToCell(row, cellIndex++, info.getHkxz());
		this.writeDataToCell(row, cellIndex++, info.getSfzh());
		cellIndex++;//少先队员
		if(info.getDz()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else if(info.getDz()==BooleanEnum.TRUE.val()){
			this.writeDataToCell(row, cellIndex++, "是");
		}else{
			this.writeDataToCell(row, cellIndex++, "否");
		}
		cellIndex++;//进城务工
		cellIndex++;//留守儿童
		if(info.getSl()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else if(info.getSl()==BooleanEnum.TRUE.val()){
			this.writeDataToCell(row, cellIndex++, "是");
		}else{
			this.writeDataToCell(row, cellIndex++, "否");
		}
		if(info.getTl()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else if(info.getTl()==BooleanEnum.TRUE.val()){
			this.writeDataToCell(row, cellIndex++, "是");
		}else{
			this.writeDataToCell(row, cellIndex++, "否");
		}
		if(info.getZl()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else if(info.getZl()==BooleanEnum.TRUE.val()){
			this.writeDataToCell(row, cellIndex++, "是");
		}else{
			this.writeDataToCell(row, cellIndex++, "否");
		}
		cellIndex++;//其它随班
		this.writeDataToCell(row, cellIndex++, info.getJzxz());
		cellIndex++;//地址代码
		this.writeDataToCell(row, cellIndex++, info.getJtzz());
		this.writeDataToCell(row, cellIndex++, info.getHkszd());
		if(info.getHkdz1()==null&&info.getHkdz2()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else{
			this.writeDataToCell(row, cellIndex++, info.getHkdz1()+"，"+info.getHkdz2());
		}
		this.writeDataToCell(row, cellIndex++, info.getFuxm());
		this.writeDataToCell(row, cellIndex++, info.getFudw());
		this.writeDataToCell(row, cellIndex++, info.getFudh());
		this.writeDataToCell(row, cellIndex++, info.getMuxm());
		this.writeDataToCell(row, cellIndex++, info.getMudw());
		this.writeDataToCell(row, cellIndex++, info.getMudh());
		if(info.getSylbStr()==null){
			this.writeDataToCell(row, cellIndex++, null);
		}else{
			this.writeDataToCell(row, cellIndex++, info.getSylbStr().graAlias());
		}
		cellIndex += 4;//语文、数学、德育考核、体育达标
		this.writeDataToCell(row, cellIndex++, info.getZw());
		this.writeDataToCell(row, cellIndex++, info.getTc());
		this.writeDataToCell(row, cellIndex++, info.getShs());
		this.writeDataToCell(row, cellIndex++, info.getJl());
	}
	private void writeDataToCell(HSSFRow row,int index ,String value){
		HSSFCell cell = row.createCell(index);
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(new HSSFRichTextString(value));
	}
	
	@Override
	public Map<String, Object> createQueryParams(String keyword,String sylb,String xb,String bj,String status,String year) {
		Map<String,Object> params = new HashMap<String,Object>();
		sylb = Utils.emptyToNull(sylb);
		xb = Utils.emptyToNull(xb);
		bj = Utils.emptyToNull(bj);
		status = Utils.emptyToNull(status);
		year = Utils.emptyToNull(year);
		params.put("keyword", Utils.emptyToNull(keyword));
		if(sylb!=null){
			params.put("sylb", StuType.valueOf(sylb).val());
		}
		if(xb!=null){
			params.put("xb", BooleanEnum.valueOf(xb).val());
		}
		if(bj!=null){
			params.put("bj", bj);
		}
		if(status!=null){
			params.put("status", StuApplyStatus.valueOf(status).val());
		}
		if(year!=null){
			try {
				params.put("year", Integer.valueOf(year));
			} catch (Exception e) {}
		}
		
		if(Utils.checkCurrentUserType(UserType.GRADUATE_TEACHER)){
			CurrentUser cuser = ThreadLocalUtils.getCurrentUser();
			params.put("bj",cuser.getBj());
			params.put("year", cuser.getYear());
		}
		
		return params;
	}
}
