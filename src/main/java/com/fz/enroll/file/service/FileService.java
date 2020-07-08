package com.fz.enroll.file.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;

public interface FileService {

	/**
	 * 文件上传
	 * @param file
	 * @return
	 */
	public Response uploadService(MultipartFile file);
	
	/**
	 * 文件下载
	 * @param hash
	 */
	public boolean downloadService(boolean isOnline,String hash,String name,HttpServletRequest request, HttpServletResponse response);

	public void incRcService(String hash);
	public void decRcService(String hash);
}
