package com.fz.common.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.springframework.web.util.HtmlUtils;

import com.fz.common.listener.PropertyHolder;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.enroll.enum0.UserType;


public class Utils {
	
	/**
	 * 只供已经登陆时调用
	 * @return
	 */
	public static int getCurrentUid(){
		CurrentUser user = ThreadLocalUtils.getCurrentUser();
		return user==null?-1:user.getUid();
	}
	/**
	 * 只供已经登陆时调用
	 * @return
	 */
	public static String getCurrentUsername(){
		CurrentUser user = ThreadLocalUtils.getCurrentUser();
		return user==null?null:user.getUsername();
	}
	/**
	 * 连接字符串
	 * @param strs
	 * @return
	 */
	public static String connectString(Object... strs){
		StringBuilder sb = new StringBuilder();
		for(Object str:strs){
			if(str==null){
				continue ;
			}
			sb.append(str);
		}
		return sb.toString();
	}
	
	public static String getRandomString(int length) {  
	    StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");  
	    StringBuffer sb = new StringBuffer();  
	    Random r = new Random();  
	    int range = buffer.length();  
	    for (int i = 0; i < length; i ++) {  
	        sb.append(buffer.charAt(r.nextInt(range)));  
	    }  
	    return sb.toString();  
	}
	
	public static String getNumRandomString(int length) {  
	    StringBuffer buffer = new StringBuffer("0123456789");  
	    StringBuffer sb = new StringBuffer();  
	    Random r = new Random();  
	    int range = buffer.length();  
	    for (int i = 0; i < length; i ++) {  
	        sb.append(buffer.charAt(r.nextInt(range)));  
	    }  
	    return sb.toString();  
	}
	
	public static String emptyToNull(String arg){
		if(arg==null||arg.trim().isEmpty()){
			return null;
		}else{
			return arg.trim();
		}
	}
	public static String clearBlank(String arg){
		if(arg==null){
			return null;
		}
		arg = arg.replaceAll(" ", "");
		return arg.isEmpty()?null:arg;
	}
	public static String removeLastChar(String source,String c){
		source = emptyToNull(source);
		if(source!=null&&source.endsWith(c)){
			return emptyToNull(source.substring(0, source.length()-1));
		}
		return source;
	}
	public static String htmlEscape(String arg){
		if(arg!=null){
			arg = arg.replaceAll("\t", " ");//当前json无法解析\t（谷歌解析出错）
			return HtmlUtils.htmlEscape(arg);
		}
		return arg;
	}
	/**
	 * 将汉字转化成全拼
	 * 
	 * @param src
	 * @return
	 */
	public static String getPinYin(String src) {
		if (src==null||src.trim().isEmpty()) {
			return "";
		}
		char[] t1 = null;
		t1 = src.toCharArray();
		String[] t2 = new String[t1.length];
		HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
		t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		t3.setVCharType(HanyuPinyinVCharType.WITH_V);
		StringBuilder t4 = new StringBuilder();
		int t0 = t1.length;
		try {
			for (int i = 0; i < t0; i++) {
				// 判断是否为汉字字符
				if (java.lang.Character.toString(t1[i]).matches(
						"[\\u4E00-\\u9FA5]+")) {
					if(i==0&&PropertyHolder.get(t1[i]+"")!=null){
						t4.append(PropertyHolder.get(t1[i]+""));
						continue;
					}
					t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
					if(t2==null||t2.length==0)
						continue;
					t4.append(t2[0]);
				} else
					t4.append(java.lang.Character.toString(t1[i]));
			}
			// System.out.println(t4);
			return t4.toString().toLowerCase();
		} catch (BadHanyuPinyinOutputFormatCombination e1) {
			e1.printStackTrace();
		}catch (Exception e2) {
			e2.printStackTrace();
		}
		return t4.toString().toLowerCase();
	}
	public static boolean checkCurrentUserType(UserType userType){
		CurrentUser user = ThreadLocalUtils.getCurrentUser();
		return user==null?false:userType==user.getType();
	}
	/*public  static byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
			byte[] buff = new byte[100]; //buff用于存放循环读取的临时数据
			int rc = 0;
			while ((rc = input.read(buff, 0, 100)) > 0) {
			swapStream.write(buff, 0, rc);
			}
			byte[] in_b = swapStream.toByteArray();
			return in_b;
		}*/
}