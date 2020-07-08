package com.fz.enroll.config.service;

import com.fz.common.res.Response;
import com.fz.enroll.entity.config.TimeConfig;
import com.fz.enroll.enum0.TimeConfigType;

public interface TimeConfigService {

	public Response getService(String type);
	public Response setService(TimeConfig config);
	
	public Response checkServeTime(TimeConfigType type);
	
	public Response isTimeEnd(TimeConfigType type);
}
