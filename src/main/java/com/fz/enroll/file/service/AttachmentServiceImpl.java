package com.fz.enroll.file.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.entity.file.Attachment;
import com.fz.enroll.entity.file.FileMeta;
import com.fz.enroll.enum0.AttOtype;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.file.dao.AttachmentDAO;

@Service("attachmentService")
public  class AttachmentServiceImpl implements AttachmentService {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentServiceImpl.class);

	@Autowired
	private FileService fileService;
	@Autowired
	private AttachmentDAO attachmentDao;

	@Override
	public Response loadService(Integer oid,AttOtype otype){
		if(oid==null||otype==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("oid", oid);
		params.put("otype", otype.val());
		
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(attachmentDao.query(params));
		return res;
	}
	@Override
	public List<Attachment> loadService(Map<String, Object> params){
		if(params==null){
			return null;
		}
		CurrentUser user = ThreadLocalUtils.getCurrentUser();
		if (user== null || (user.getType() != UserType.ADMIN
				&& user.getType() != UserType.RECRUIT_TEACHER
				&& user.getType() != UserType.PATRIARCH)) {
			return null;
		}
		return attachmentDao.queryApplyAtt(params);
	}
	@Override
	public Response delService(Integer id){
		Attachment att = id==null?null:attachmentDao.queryById(id);
		if(att==null||att.getUid()!=Utils.getCurrentUid()){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		int uc = attachmentDao.delete(att);
		if(uc>0){
			this.fileService.decRcService(att.getHash());
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public Response uploadService(MultipartFile file,String prefix,Integer oid,Integer otype){
		Response res = this.fileService.uploadService(file);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		String name = file.getOriginalFilename();
		res = this.saveOrUpdate((FileMeta)res.getData(), Utils.connectString(prefix,name));
		if(res.getRetCode()==ReturnCode.SUCCESS
				&&oid!=null&&otype!=null){
			List<Integer> ids = new ArrayList<Integer>();
			ids.add(((Attachment)res.getData()).getId());
			this.batchUpdateService(ids, otype, oid);
		}
		return res;
	}
	private Response saveOrUpdate(FileMeta fileMeta,String name){
		name = Utils.emptyToNull(name);
		
		Attachment attachment = new Attachment();
		attachment.setUid(Utils.getCurrentUid());
		attachment.setHash(fileMeta.getHash());
		attachment.setName(name);
		int rc = attachmentDao.save(attachment);
		if(rc==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		this.fileService.incRcService(fileMeta.getHash());
		
		Response res = new Response(ReturnCode.SUCCESS);
		attachment.setSize(fileMeta.getSize());
		res.setData(attachment);
		return res;
	}
	
	@Override
	public void downloadService(Integer id,String hash,
			HttpServletRequest request, HttpServletResponse response) {
		Attachment att = id==null?null:attachmentDao.queryById(id);
		if(att==null
				||(!att.getHash().equals(hash)
						&&att.getUid()!=Utils.getCurrentUid())){//访问自己的附件不需要校验hash，IM发送消息时可能没有附件的hash.
			logger.info("The attachment("+(id)+":"+hash+") is not exist!");
			response.setStatus(404);
			return ;
		}
		String name = att.getName();
		name = name==null?"未命名":name;
		fileService.downloadService(false,att.getHash(), name, request, response);
	}

	@Override
	public Response batchUpdateService(List<Integer> ids,int otype,int oid) {
		List<Integer> attIds = new ArrayList<Integer>();
		if(ids!=null){
			Map<Integer,Boolean> _map = new HashMap<Integer,Boolean>();
			for(Integer ele:ids){
				if(ele==null||_map.get(ele)!=null){//去空、去重
					continue ;
				}
				_map.put(ele, true);
				attIds.add(ele);
			}
		}
		
		Response res = new Response(ReturnCode.SUCCESS);
		if(attIds.size()>0){//关联附件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("ids", attIds);
			params.put("uid", Utils.getCurrentUid());
			params.put("otype", otype);
			params.put("oid", oid);

			List<Attachment> atts = attachmentDao.queryUsable(params);//batchUpdateOwner执行后便取不到想要的结果了
			if(atts.size()!=attIds.size()){
//				BaseServiceUtils.setRollbackOnly();
				
				for(Attachment ele:atts){
					attIds.remove(Integer.valueOf(ele.getId()));
				}
				
				res.setRetCode(ReturnCode.FILE_ATT_UNUSABLE);
				res.setData(attIds);
				return res;
			}
			
			int uc = attachmentDao.batchUpdateOwner(params);
			if(uc!=attIds.size()){
//				BaseServiceUtils.setRollbackOnly();
				
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);//并发操作导致失败
				return res;
			}
		}
		return res;
	}

	@Override
	public boolean batchDelService(int otype,int oid) {
		Map<String, Object> attMap = new HashMap<String, Object>();
		attMap.put("otype", otype);
		attMap.put("oid", oid);
		List<Attachment> atts = attachmentDao.queryByOid(attMap);
		if(atts.size()==0){
			return true;
		}
		int uc = this.attachmentDao.batchDelete(atts);
		if (uc == atts.size()) {
			for (int i = 0; i < atts.size(); i++) {
				this.fileService.decRcService(atts.get(i).getHash());
			}
		}
		return uc == atts.size();
	}
}
