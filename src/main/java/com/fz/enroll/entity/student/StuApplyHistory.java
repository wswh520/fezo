package com.fz.enroll.entity.student;

import com.fz.common.util.DateUtils;
import com.fz.enroll.entity.BaseEntity;

public class StuApplyHistory extends BaseEntity {

	private int mid;//学生报名表表ID
	private int uid;//操作人ID
	private String data;//数据内容JSON格式
	private long time;//存档时间
	
	private String name;//操作人姓名
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**************************************************/
	public String getTimeStr(){
		if(this.getTime()==0){
			return null;
		}
		return DateUtils.timeToString(this.getTime(), "yyyy-MM-dd HH:mm");
	}
}
