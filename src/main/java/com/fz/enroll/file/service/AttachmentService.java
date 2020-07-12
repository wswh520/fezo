package com.fz.enroll.file.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.enroll.entity.file.Attachment;
import com.fz.enroll.enum0.AttOtype;

public interface AttachmentService {

	public Response loadService(Integer oid,AttOtype otype);
	/**
	 * 查询附件列表，目前仅供新生学生报名表使用(包括老师账号)
	 * @param params {oid/uid、oid、otypes:类型列表}
	 * @return List<Attachment>
	 */
	public List<Attachment> loadService(Map<String, Object> params);
	
	/**
	 * 批量设置附件所属
	 * @param params {ids:ID列表,otype、oid}
	 * @return
	 */
	public Response batchUpdateService(List<Integer> ids,int otype,int oid);
	/**
	 * 批量删除附件
	 * @param params {ids:ID列表}
	 * @return
	 */
	public boolean batchDelService(int otype,int oid);
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	public Response delService(Integer id);
	/**
	 * 上传附件
	 * @param file
	 * @param type
	 * @param otype
	 * @return data:Attachment
	 */
	public Response uploadService(MultipartFile file,String prefix,Integer oid,Integer otype);
	/**
	 * 下载附件
	 * @param id
	 * @param hash	HASH值用作校验
	 */
	public void downloadService(Integer id,String hash,HttpServletRequest request, HttpServletResponse response);
	
}
