package com.fz.common.util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtils{
	public static  Workbook getWorkbook(MultipartFile file){
		Workbook wb = null;
		try {
			wb = WorkbookFactory.create(file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return wb;
	}
	public static String getStringCellValue(Cell cell) {
		try {
			if (cell == null) {
				return null;
			}
			String value = null;
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				value = Utils.emptyToNull(cell.getRichStringCellValue().getString());
				break;
			case Cell.CELL_TYPE_NUMERIC:
//				value = new BigDecimal(cell.getNumericCellValue()).toString();
				if(org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					value = sdf.format(org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cell.getNumericCellValue()));
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
			e.printStackTrace();
			return null;
		}
	}
	public static String list2String(List<String> names){
		if(names==null||names.size()==0){
			return null;
		}
		StringBuilder sb = new StringBuilder("【");
		for(String name:names){
			sb.append(name).append("<br>");
		}
		sb.replace(sb.length()-4, sb.length(), "】<br>");
		return sb.toString();
	}
	public static String list2StringLine(List<Integer> datas){
		if(datas==null||datas.size()==0){
			return null;
		}
		StringBuilder sb = new StringBuilder("【");
		for(Integer ele:datas){
			sb.append(ele).append("、");
		}
		sb.replace(sb.length()-1, sb.length(), "】");
		return sb.toString();
	}
}
