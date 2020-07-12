package com.fz.enroll.student.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.student.StuApply;
import com.fz.enroll.entity.student.StuVaccine;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.dao.StuApplyDAO;
import com.fz.enroll.student.dao.StuVaccineDAO;

@Service("stuVaccineService")
public class StuVaccineServiceImpl implements StuVaccineService {

	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private StuApplyDAO stuApplyDao;
	@Autowired
	private StuVaccineDAO stuVaccineDao;
	
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
			res.setErrorMsg("学生报名表表尚未审核通过！");
			return res;
		}
		
		StuVaccine stuVaccine = stuVaccineDao.queryByStuId(stuApply.getId());
		stuVaccine = stuVaccine!=null?stuVaccine:new StuVaccine();
		stuVaccine.setStuId(stuApply.getId());
		stuVaccine.setStuApply(stuApply);
		
		Response checkRes = this.checkOpAuth(stuVaccine);
		if(checkRes.getRetCode()!=ReturnCode.SUCCESS){
			stuVaccine.setLocked(true);
		}
		
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(stuVaccine);
		return res;
	}
	
	@Override
	public Response saveService(StuVaccine entity){
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
				res.setErrorMsg("学生报名表表尚未填写！");//审核通过
				return res;
			}
			entity.setStuId(stuApply.getId());
			
			StuVaccine exist = stuVaccineDao.queryByStuId(stuApply.getId());
			int uc = 0;
			if(exist==null){
				uc = stuVaccineDao.save(entity);
			}else{
				uc = stuVaccineDao.update(entity);
			}
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}else{//修改
			StuVaccine exist = stuVaccineDao.queryById(entity.getId());
			res = this.checkOpAuth(exist);
			if(res.getRetCode()!=ReturnCode.SUCCESS){
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_ONCE.val()){
				//保存存档
				
			}
			
			entity.setStatus(exist.getStatus());
			int uc = stuVaccineDao.update(entity);
			return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
		}
	}
	private Response checkOpAuth(StuVaccine exist){
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
		
		int uc = stuVaccineDao.submit(params);
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}

}
