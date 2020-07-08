package com.fz.enroll.student.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.security.CurrentUser;
import com.fz.common.security.ThreadLocalUtils;
import com.fz.common.util.DateUtils;
import com.fz.common.util.Utils;
import com.fz.enroll.config.service.TimeConfigService;
import com.fz.enroll.entity.student.GraStuInfo;
import com.fz.enroll.entity.student.GraStuInfoHistory;
import com.fz.enroll.enum0.BooleanEnum;
import com.fz.enroll.enum0.StuApplyStatus;
import com.fz.enroll.enum0.TimeConfigType;
import com.fz.enroll.enum0.UserType;
import com.fz.enroll.student.dao.StuGraInfoDAO;


@Service("stuGraInfoService")
public class StuGraInfoServiceImpl implements StuGraInfoService{
	
	@Autowired
	private StuGraInfoDAO stuGraInfoDao;
	@Autowired
	private TimeConfigService timeConfigService;
	@Autowired
	private StuApplyService stuApplyService;
	
	@Override
	public Response loadService(String stuIdStr) {
		int stuId = 0;
		if(stuIdStr!=null){
			try{
				stuId = Integer.valueOf(stuIdStr);
			}catch(Exception e){}
		}
		GraStuInfo gi=null;
		if(stuId!=0){
			gi = stuGraInfoDao.queryById(stuId);
		}else{
			gi = stuGraInfoDao.queryByUid(Utils.getCurrentUid());
		}
		if(gi==null){
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("未知的错误！");
			return res;
		}
		
		Response checkRes = this.checkOpAuth(gi);
		if(checkRes.getRetCode()!=ReturnCode.SUCCESS){
			gi.setLocked(true);
		}
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(gi);
		return res;
	}
	
	@Override
	public Response verifyServiceT(GraStuInfo entity) {
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		GraStuInfo exist = stuGraInfoDao.queryById(entity.getId());
		Response res = this.checkOpAuth(exist);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		{
			//保存存档
			GraStuInfoHistory history = new GraStuInfoHistory();
			history.setMid(exist.getId());
			history.setUid(Utils.getCurrentUid());
			history.setData(JSON.toJSONString(exist));
			history.setTime(System.currentTimeMillis());
			int uc = stuGraInfoDao.saveHistory(history);
			if(uc==0){
				return new Response(ReturnCode.SERVER_INNER_ERROR);
			}
		}

		boolean hasAlter = this.checkAlter(entity, exist);
		if(hasAlter){
			UserType utype = ThreadLocalUtils.getCurrentUser().getType();
			if(utype==UserType.GRADUATE){//毕业生
				entity.setStatus(StuApplyStatus.SUBMIT_ONCE.val());//待复核
			}else if(utype.equals(UserType.ADMIN)){//招生老师
				entity.setStatus(StuApplyStatus.REVIEW_PASS.val());//需要二次审核
			}
		}else{
			entity.setStatus(StuApplyStatus.SUBMIT_TWICE.val());//复核无误
		}
		
		int uc = stuGraInfoDao.verify(entity);
		if(uc==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public Response reviewServiceT(GraStuInfo entity) {
		if(entity==null){//entity不为null则认为entity中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		GraStuInfo exist = stuGraInfoDao.queryById(entity.getId());
		Response res = this.checkOpAuth(exist);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		{
			//保存存档
			GraStuInfoHistory history = new GraStuInfoHistory();
			history.setMid(exist.getId());
			history.setUid(Utils.getCurrentUid());
			history.setData(JSON.toJSONString(exist));
			history.setTime(System.currentTimeMillis());
			int uc = stuGraInfoDao.saveHistory(history);
			if(uc==0){
				return new Response(ReturnCode.SERVER_INNER_ERROR);
			}
		}

		this.checkAlter(entity, exist);
		entity.setStatus(StuApplyStatus.SUBMIT_TWICE.val());//复核无误
		if(entity.getXmN()!=null){
			entity.setXm(entity.getXmN());
		}
		int uc = stuGraInfoDao.review(entity);
		if(uc==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	
	@Override
	public Response submitServiceT(String idStr){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		GraStuInfo exist = stuGraInfoDao.queryById(id);
		Response res = this.checkOpAuth(exist);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		{
			//保存存档
			GraStuInfoHistory history = new GraStuInfoHistory();
			history.setMid(exist.getId());
			history.setUid(Utils.getCurrentUid());
			history.setData(JSON.toJSONString(exist));
			history.setTime(System.currentTimeMillis());
			int uc = stuGraInfoDao.saveHistory(history);
			if(uc==0){
				return new Response(ReturnCode.SERVER_INNER_ERROR);
			}
		}

		StuApplyStatus targetStatus = null;
		boolean hasAlter = this.checkAlter(exist, exist);
		if(hasAlter){
			targetStatus = StuApplyStatus.SUBMIT_ONCE;//待复核
		}else{
			targetStatus = StuApplyStatus.SUBMIT_TWICE;//复核无误
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("uid", Utils.getCurrentUid());
		params.put("currentStatus", exist.getStatus());
		params.put("targetStatus", targetStatus.val());
		
		int uc = stuGraInfoDao.submit(params);
		if(uc==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	@Override
	public Response refuseServiceT(String idStr){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		
		GraStuInfo exist = stuGraInfoDao.queryById(id);
		Response res = this.checkOpAuth(exist);
		if(res.getRetCode()!=ReturnCode.SUCCESS){
			return res;
		}
		{
			//保存存档
			GraStuInfoHistory history = new GraStuInfoHistory();
			history.setMid(exist.getId());
			history.setUid(Utils.getCurrentUid());
			history.setData(JSON.toJSONString(exist));
			history.setTime(System.currentTimeMillis());
			int uc = stuGraInfoDao.saveHistory(history);
			if(uc==0){
				return new Response(ReturnCode.SERVER_INNER_ERROR);
			}
		}

		Map<String,Object> params = new HashMap<String,Object>();
		params.put("id", id);
		params.put("currentStatus", exist.getStatus());
		params.put("targetStatus",  StuApplyStatus.REVIEW_REFUSE.val());
		
		int uc = stuGraInfoDao.refuse(params);
		if(uc==0){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return new Response(uc>0?ReturnCode.SUCCESS:ReturnCode.SERVER_INNER_ERROR);
	}
	
	private Response checkOpAuth(GraStuInfo exist){
		Response res = new Response();
		UserType utype = ThreadLocalUtils.getCurrentUser().getType();
		if(exist==null){//表不存在
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			return res;
		}else if(exist.getYear()!=DateUtils.getCurrentYear()){
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg(Utils.connectString("该数据为",exist.getYear(),"年的数据，当前不可操作！"));
			return res;
		}else if(utype==UserType.GRADUATE){//毕业生
			if(exist.getUid()!=0
					&&exist.getUid()!=Utils.getCurrentUid()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("没有权限");
				return res;
			}
			if(exist.getStatus()==StuApplyStatus.SUBMIT_NONE.val()
					||exist.getStatus()==StuApplyStatus.REVIEW_REFUSE.val()){//待核对
				res = timeConfigService.checkServeTime(TimeConfigType.GRA_INFO_INPUT);
				if(res.getRetCode()!=ReturnCode.SUCCESS){
					res.setErrorMsg("当前不能进行此操作");
					return res;
				}
			}else{
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("当前不能进行此操作");
				return res;
			}
		}else if(utype.equals(UserType.ADMIN)){//招生老师
			if(exist.getStatus()!=StuApplyStatus.SUBMIT_ONCE.val()
					&&exist.getStatus()!=StuApplyStatus.REVIEW_PASS.val()){
				res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
				res.setErrorMsg("表单状态已变，不能修改！");
				return res;
			}
		}else{
			res.setRetCode(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("没有权限");
			return res;
		}
		res.setRetCode(ReturnCode.SUCCESS);
		return res;
	}

	@Override
	public Response getHistoryListService(String idStr) {
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		List<GraStuInfoHistory> historys = stuGraInfoDao.queryHistory(id);
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(historys);
		return res;
	}

	@Override
	public Response getHistoryInfoService(String idStr) {
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		if(id==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		GraStuInfoHistory history = stuGraInfoDao.queryHistoryById(id);
		if(history==null||history.getData()==null){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(JSON.parseObject(history.getData(), GraStuInfo.class));
		return res;
	}
	
	@Override
	public void downloadStuInfoService(boolean pdf,String idStr,HttpServletRequest request,HttpServletResponse response){
		int id = 0;
		try{
			id = Integer.valueOf(idStr);
		}catch(Exception e){}
		GraStuInfo graStuInfo = null;
		if(id!=0){
			graStuInfo = stuGraInfoDao.queryById(id);
		}else{
			graStuInfo = stuGraInfoDao.queryByUid(Utils.getCurrentUid());
		}
		CurrentUser cuser = ThreadLocalUtils.getCurrentUser();
		if(graStuInfo==null
				||(cuser.getType()==UserType.GRADUATE&&graStuInfo.getUid()!=cuser.getUid())){
			response.setStatus(404);
			return ;
		}

		String docName = graStuInfo.getXm()+"的信息核对表.pdf";
		String xmlName = "gra_stu_info.xml";
		
		Map<String,String> dataMap = new HashMap<String,String>();
		dataMap.put("bj", graStuInfo.getBj());
		StringBuilder jg = new StringBuilder();
		if(!StringUtils.isEmpty(graStuInfo.getJg1())){
			jg.append(graStuInfo.getJg1()).append("省");
		}
		if(!StringUtils.isEmpty(graStuInfo.getJg2())){
			jg.append(graStuInfo.getJg2()).append("市（县）");
		}
		dataMap.put("jg", jg.toString());
		dataMap.put("xbxh", graStuInfo.getXbxh());
		dataMap.put("xm", graStuInfo.getXm());
		if(graStuInfo.getXb()==BooleanEnum.TRUE.val()){
			dataMap.put("xb", "男");
		}else{
			dataMap.put("xb", "女");
		}
		dataMap.put("sfzh", graStuInfo.getSfzh());
		dataMap.put("hkszd", graStuInfo.getHkszd());
		dataMap.put("hkdz1", graStuInfo.getHkdz1());
		dataMap.put("hkdz2", graStuInfo.getHkdz2());
		dataMap.put("jzxz", graStuInfo.getJzxz());
		dataMap.put("jtzz", graStuInfo.getJtzz());
		dataMap.put("fuxm", graStuInfo.getFuxm());
		dataMap.put("fudh", graStuInfo.getFudh());
		dataMap.put("fudw", graStuInfo.getFudw());
		dataMap.put("muxm", graStuInfo.getMuxm());
		dataMap.put("mudh", graStuInfo.getMudh());
		dataMap.put("mudw", graStuInfo.getMudw());
		dataMap.put("sylb1", "□");
		dataMap.put("sylb2", "□");
		dataMap.put("sylb3", "□");
		dataMap.put("sylb"+graStuInfo.getSylb(), "√");
		SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
		int date = Integer.valueOf(sp.format(new Date()));
		dataMap.put("year", String.valueOf(date/10000));
		dataMap.put("month", String.valueOf(date%10000/100));
		dataMap.put("day", String.valueOf(date%100));
		
		stuApplyService.doDownload(pdf,docName, xmlName, dataMap,request,response);
	}
	
	private boolean checkAlter(GraStuInfo newData,GraStuInfo oldData){
		boolean hasAlter = false;
		if(newData.getXmN()!=null&&!newData.getXmN().equals(oldData.getXm())){
			hasAlter = true;
		}else{
			newData.setXmN(null);
		}
		if(newData.getJg1N()!=null&&!newData.getJg1N().equals(oldData.getJg1())){
			hasAlter = true;
		}else{
			newData.setJg1N(null);
		}
		if(newData.getJg2N()!=null&&!newData.getJg2N().equals(oldData.getJg2())){
			hasAlter = true;
		}else{
			newData.setJg2N(null);
		}
		if(newData.getCsdN()!=null&&!newData.getCsdN().equals(oldData.getCsd())){
			hasAlter = true;
		}else{
			newData.setCsdN(null);
		}
		if(newData.getMzN()!=null&&!newData.getMzN().equals(oldData.getMz())){
			hasAlter = true;
		}else{
			newData.setMzN(null);
		}
		if(newData.getHkxzN()!=null&&!newData.getHkxzN().equals(oldData.getHkxz())){
			hasAlter = true;
		}else{
			newData.setHkxzN(null);
		}
		if(newData.getSfzhN()!=null&&!newData.getSfzhN().equals(oldData.getSfzh())){
			hasAlter = true;
		}else{
			newData.setSfzhN(null);
		}
		if(newData.getDzN()!=null&&newData.getDzN().intValue()!=oldData.getDz().intValue()){
			hasAlter = true;
		}else{
			newData.setDzN(null);
		}
		if(newData.getSlN()!=null&&newData.getSlN().intValue()!=oldData.getSl().intValue()){
			hasAlter = true;
		}else{
			newData.setSlN(null);
		}
		if(newData.getTlN()!=null&&newData.getTlN().intValue()!=oldData.getTl().intValue()){
			hasAlter = true;
		}else{
			newData.setTlN(null);
		}
		if(newData.getZlN()!=null&&newData.getZlN().intValue()!=oldData.getZl().intValue()){
			hasAlter = true;
		}else{
			newData.setZlN(null);
		}
		if(newData.getJzxzN()!=null&&!newData.getJzxzN().equals(oldData.getJzxz())){
			hasAlter = true;
		}else{
			newData.setJzxzN(null);
		}
		if(newData.getJtzzN()!=null&&!newData.getJtzzN().equals(oldData.getJtzz())){
			hasAlter = true;
		}else{
			newData.setJtzzN(null);
		}
		if(newData.getHkszdN()!=null&&!newData.getHkszdN().equals(oldData.getHkszd())){
			hasAlter = true;
		}else{
			newData.setHkszdN(null);
		}
		if(newData.getHkdz1N()!=null&&!newData.getHkdz1N().equals(oldData.getHkdz1())){
			hasAlter = true;
		}else{
			newData.setHkdz1N(null);
		}
		if(newData.getHkdz2N()!=null&&!newData.getHkdz2N().equals(oldData.getHkdz2())){
			hasAlter = true;
		}else{
			newData.setHkdz2N(null);
		}
		if(newData.getFuxmN()!=null&&!newData.getFuxmN().equals(oldData.getFuxm())){
			hasAlter = true;
		}else{
			newData.setFuxmN(null);
		}
		if(newData.getFudwN()!=null&&!newData.getFudwN().equals(oldData.getFudw())){
			hasAlter = true;
		}else{
			newData.setFudwN(null);
		}
		if(newData.getFudhN()!=null&&!newData.getFudhN().equals(oldData.getFudh())){
			hasAlter = true;
		}else{
			newData.setFudhN(null);
		}
		if(newData.getMuxmN()!=null&&!newData.getMuxmN().equals(oldData.getMuxm())){
			hasAlter = true;
		}else{
			newData.setMuxmN(null);
		}
		if(newData.getMudwN()!=null&&!newData.getMudwN().equals(oldData.getMudw())){
			hasAlter = true;
		}else{
			newData.setMudwN(null);
		}
		if(newData.getMudhN()!=null&&!newData.getMudhN().equals(oldData.getMudh())){
			hasAlter = true;
		}else{
			newData.setMudhN(null);
		}
		if(newData.getSylbN()!=null&&newData.getSylbN().intValue()!=oldData.getSylb().intValue()){
			hasAlter = true;
		}else{
			newData.setSylbN(null);
		}
		if(newData.getZw()!=null){
			hasAlter = true;
		}
		if(newData.getTc()!=null){
			hasAlter = true;
		}
		if(newData.getShs()!=null){
			hasAlter = true;
		}
		if(newData.getJl()!=null){
			hasAlter = true;
		}
		return hasAlter;
	}
}
