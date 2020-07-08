package com.fz.enroll.dto.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.fz.enroll.entity.config.TimeConfig;
import com.fz.enroll.enum0.TimeConfigType;

public class TimeConfigAdd {

	private String type;
	private String start;
	private String end;
	
	public TimeConfig convert2Entity(){
		TimeConfig entity = new TimeConfig();
		try {
			entity.setType(TimeConfigType.valueOf(this.getType()).val());
			entity.setStartTime(this.getTimeFromStr(this.getStart()));
			entity.setEndTime(this.getTimeFromStr(this.getEnd())+24*3600000-1);
		} catch (Exception e) {
			return null;
		}
		
		if(entity.getType()==0
				||entity.getStartTime()==0
				||entity.getStartTime()>entity.getEndTime()){
			return null;
		}
		
		return entity;
	}
	private long getTimeFromStr(String timeStr) throws ParseException{
		SimpleDateFormat sp = new SimpleDateFormat(TimeConfig.TIME_FORMAT);
		return sp.parse(timeStr).getTime();
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
}
