package com.fz.enroll.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.enroll.config.dao.TimeConfigDAO;
import com.fz.enroll.entity.config.TimeConfig;
import com.fz.enroll.enum0.TimeConfigType;

@Service("timeConfigService")
public class TimeConfigServiceImpl implements TimeConfigService {
	
	@Autowired
	private TimeConfigDAO timeConfigDao;

	@Override
	public Response getService(String type) {
		int typeInt = 0;
		try {
			typeInt = TimeConfigType.valueOf(type).val();
		} catch (Exception e) {}
		if(typeInt==0){
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}

		TimeConfig config = timeConfigDao.queryByType(typeInt);
		if(config==null){
			config = new TimeConfig();
			config.setType(typeInt);
		}
		Response res = new Response(ReturnCode.SUCCESS);
		res.setData(config);
		return res;
	}

	@Override
	public Response setService(TimeConfig config) {
		if(config==null){//config不为null则认为config中存在且只存在所需要参数
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		timeConfigDao.saveOrUpdate(config);
		return new Response(ReturnCode.SUCCESS);
	}
	
	@Override
	public Response checkServeTime(TimeConfigType type){
		TimeConfig config = timeConfigDao.queryByType(type.val());
		long ct = System.currentTimeMillis();
		if(config==null
				||ct<config.getStartTime()
				||ct>config.getEndTime()){
			return new Response(ReturnCode.NOT_IN_SERVE_TIME);
		}
		return new Response(ReturnCode.SUCCESS);
	}
	
	@Override
	public Response isTimeEnd(TimeConfigType type){
		TimeConfig config = timeConfigDao.queryByType(type.val());
		long ct = System.currentTimeMillis();
		if(config==null
				||ct<config.getEndTime()){
			return new Response(ReturnCode.NOT_IN_SERVE_TIME);
		}
		return new Response(ReturnCode.SUCCESS);
	}

}
