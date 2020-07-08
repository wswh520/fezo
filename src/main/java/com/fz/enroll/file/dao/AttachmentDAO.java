package com.fz.enroll.file.dao;

import java.util.List;
import java.util.Map;

import com.fz.enroll.entity.file.Attachment;


//Attachment 数据库访问接口
public interface AttachmentDAO  {
	
	/**
	 * 根据oid与otype查询
	 * @param params
	 * @return
	 */
	public List<Attachment> query(Map<String,Object> params);
	/**
	 * 获取可用的附件列表
	 * 		附件匹配，且oid未指定值
	 * @param params {ids:ID列表,uid:用户ID}
	 * @return
	 */
	public List<Attachment> queryUsable(Map<String,Object> params);
	public Attachment queryById(int id);
	/**
	 * 获取相关附件列表用于删除
	 * @param params {oid:资讯id,otype:附件类型}
	 * @return
	 */
	public List<Attachment> queryByOid(Map<String, Object> attmap);

	/**
	 * 查询附件信息 
	 *  @param params {oid:资讯id,otypes:附件类型列表（此列表不能为空）}
	 * 	@return
	 */
	public List<Attachment> queryApplyAtt(Map<String, Object> params);

	/* 查询附件所有信息 */
	//public Attachment queryApplyByid(Map<String, Object> params);
	public int save(Attachment att);
	/**
	 * 批量设置附件所属
	 * @param params {ids:ID列表,uid:用户ID,otype、oid}
	 * @return
	 */
	public int batchUpdateOwner(Map<String,Object> params);
	/**
	 * 根据id删除，同时检验uid
	 * @param att
	 */
	public int delete(Attachment att);
	/**
	 * 批量删除附件所属
	 * @param params {ids:ID列表}
	 * @return
	 */
	public int batchDelete(List<Attachment> atts);
}