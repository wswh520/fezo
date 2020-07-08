package com.fz.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static String timeToString(long time, String format) {
		SimpleDateFormat sp = new SimpleDateFormat(format);
		return sp.format(new Date(time));
	}
	
	public static int getCurrentYear(){
		SimpleDateFormat sp = new SimpleDateFormat("yyyy");
		return Integer.valueOf(sp.format(new Date()));
	}
	
	public static long getTimeByYear0831(int year){
		Calendar cal =  Calendar.getInstance();
		cal.set(year, 7, 31);//8月31日
		return cal.getTime().getTime();
	}
	
	/***************************************************************************
	 * 匹配日期格式 yyyyMMdd 并验证日期是否合法
	 * 
	 * @param date
	 *            日期字符串
	 * @return true 日期合法 false 日期非法
	 */
	public static boolean isValidDate(String date) {
		SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
		try {
			if (date.equals(sp.format(sp.parse(date)))) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 功能：验证出生年月是否符合范围
	 * @params birthStr 20150101 ,age
	 * @return boolean类型
	 */
	public static boolean checkBirthday(String birthStr, int age) {
		int tYear = DateUtils.getCurrentYear();
		int stuBirthday = Integer.parseInt(birthStr);
		int end = Integer.parseInt((tYear - age) + "0831");
		if (end >= stuBirthday) {
			return true;
		}
		return false;
	}
	public static boolean checkGreaterThanBirthday(String birthStr) {
		int tYear = DateUtils.getCurrentYear();
		int stuBirthday = Integer.parseInt(birthStr);
		int end = Integer.parseInt((tYear - 12) + "0831");
		if(stuBirthday>=end){  
	        return true;  
	    }  
	    return false;
	}

	/*
	 * 将年度转成中文的年度//2016-6-23
	 * year 2016
	 * return 二〇一六
	 */
	public static String convertCharCnNumber(Integer year) {
		year = year == null? DateUtils.getCurrentYear():year;
		String ALL_CN_NUMBER = "〇一二三四五六七八九";
		String ALL_NUMBER = "0123456789";
		StringBuffer buf = new StringBuffer();
		String numberStr = String.valueOf(year);
		for (int i = 0; i < numberStr.length(); i++) {
			char c = numberStr.charAt(i);
			int index = ALL_NUMBER.indexOf(c);
			if (index != -1) {
				buf.append(ALL_CN_NUMBER.charAt(index));
			} else {
				buf.append(numberStr.charAt(i));
			}
		}
		return buf.toString();
	}
}
