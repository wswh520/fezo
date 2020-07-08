package com.fz.enroll.entity.file;

import com.fz.enroll.entity.BaseEntity;
import com.fz.enroll.enum0.AttOtype;


public class Attachment extends BaseEntity {

	private int otype = AttOtype.TYPE_NONE.val();//所属实体类型，如：微博、公告
	private int oid;//所属实体ID，不设外键约束，手动维护级联删除

	private int uid;//附件所属用户ID
	private String hash;//附件数据索引
	private String name;//附件名称

	//非数据库字段
	private long size;//附件大小
	private String uname;//上传附件的人名
	public int getOtype() {
		return otype;
	}
	public void setOtype(int otype) {
		this.otype = otype;
	}
	public int getOid() {
		return oid;
	}
	public void setOid(int oid) {
		this.oid = oid;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
}
