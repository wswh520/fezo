package com.fz.enroll.entity.file;

public class FileMeta {

	private String hash;//文件hash
	private long size;//文件大小
	private int rc;//文件被引用次数
	private byte[] data;//文件数据
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public int getRc() {
		return rc;
	}
	public void setRc(int rc) {
		this.rc = rc;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
