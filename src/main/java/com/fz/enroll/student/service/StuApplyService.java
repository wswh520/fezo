package com.fz.enroll.student.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.StuType;

public interface StuApplyService {

	public Response loadService(String id);
	/**
	 * 修改入学登记信息
	 * 		1、家长只能在两个相应的时间段内修改对应状态的自己的信息
									SUBMIT_NONE(1),//未提交
									SUBMIT_ONCE(2),//初次提交
	 * 		2、招生老师任何时刻可以修改相应状态的信息
									SUBMIT_ONCE(2),//初次提交
									SUBMIT_TWICE(3),//再次提交
	 * @param entity  isForwardGrade 是否是转级
	 * 
	 * @return
	 */
	public Response saveService(StuApply entity);
	
	public Response submit2ReviewService(String id,StuApplyStatus targetStatus);
	
	public void downloadStuApplyService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response);
	public void doDownload(boolean pdf,String docName,String xmlName,Map<String,String> dataMap,
			HttpServletRequest request,HttpServletResponse response);
	
	/**
	 * 获取历史版本列表
	 * @param id
	 * @return
	 */
	public Response getHistoryListService(String idStr);
	public Response getHistoryInfoService(String idStr);
	public Response initPinyinService();
	/**
	 * 查询附件列表，目前仅供新生入学登记使用(包括老师账号)
	 * @param params {oid、otypes:类型列表, type}
	 * @return 
	 */
	public Response loadAttService(Integer oid, AttOtype otype, StuType type);
	/**
	 * 生成查询条件，限定查询范围
	 * 
	 * @param uid
	 * @param oid
	 * @param otype
	 * @return
	 */
	public Map<String, Object> createQueryParams(Integer oid, AttOtype otype, StuType type);
	/**
	 * 上传附件
	 * @param file
	 * @param type
	 * @param otype
	 * @return data:Attachment
	 */
	public Response uploadService(MultipartFile file, String prefix, Integer oid, AttOtype otype, StuType type);
	/**
	 * 获取对应的图片字符串
	 * @param oid
	 * @param otype
	 * @return 
	 */
	public String getPhotoStr(Integer oid, AttOtype otype, HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 获取对应的报名号
	 * @return 
	 */
	public String getNo(String name);
	public Response forwardGradeServiceT(Integer id);

	public void downloadStuApplyService2(boolean b, String id, HttpServletRequest request, HttpServletResponse response);
}
