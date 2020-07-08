package com.fz.common.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fz.enroll.login.dao.OnLineDAO;


public class Quartz {
	private static Logger log = LoggerFactory.getLogger(Quartz.class);

	private long timeout;
	
	@Autowired
	private  OnLineDAO onLineDao ;
	
	/**
	 * 维护在线表
	 */
	public void maintenanceOnLine(){
		log.info("maintenanceOnLine begin");
		onLineDao.clearTimeoutSession(System.currentTimeMillis()-timeout*1000);
		log.info("maintenanceOnLine end");
	}
	
	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
