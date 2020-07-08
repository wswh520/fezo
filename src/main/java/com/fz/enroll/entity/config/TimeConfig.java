package com.fz.enroll.entity.config;

import com.fz.common.util.DateUtils;
import com.fz.enroll.enum0.TimeConfigType;

public class TimeConfig {
	public static final String TIME_FORMAT = "yyyy年MM月dd日";

	/**
	 * {@link com.fz.enroll.enum0.TimeConfigType}
	 */
	private int type;
	private long startTime;
	private long endTime;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	/***************************************/
	public String getStartTimeStr(){
		if(this.getStartTime()>0){
			return DateUtils.timeToString(this.getStartTime(), TIME_FORMAT);
		}
		return null;
	}
	public String getEndTimeStr(){
		if(this.getEndTime()>0){
			return DateUtils.timeToString(this.getEndTime(), TIME_FORMAT);
		}
		return null;
	}
	public TimeConfigType getTypeStr(){
		return TimeConfigType.valueOf(this.getType());
	}
}
