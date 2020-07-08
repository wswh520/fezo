package com.fz.enroll.file.dao;

import com.fz.enroll.entity.file.FileMeta;


public interface FileDAO {

	public int save(FileMeta entity);
	
	public FileMeta queryByHash(String hash);
	public FileMeta queryDataByHash(String hash);
	public void incRc(String hash);
	public void decRc(String hash);
}
