package com.fz.enroll.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fz.common.res.Response;
import com.fz.common.res.ReturnCode;
import com.fz.common.util.MD5;
import com.fz.enroll.entity.file.FileMeta;
import com.fz.enroll.file.dao.FileDAO;

@Service("fileService")
public class FileServiceImpl implements FileService {
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	private static final long att_limit = 1024*1024L;//1M
	
	@Autowired
	private FileDAO fileDao;
	
	@Override
	public void incRcService(String hash){
		fileDao.incRc(hash);
	}
	@Override
	public void decRcService(String hash){
		fileDao.decRc(hash);
	}

	@Override
	public Response uploadService(MultipartFile file) {
		if(file==null||file.getSize()==0){
			logger.info("file is null or empty!!");
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		}
		long fileSize = file.getSize();
		if (fileSize > att_limit) {
			Response res = new Response(ReturnCode.SERVER_INNER_ERROR);
			res.setErrorMsg("文件过大，上传失败，上传文件大小不得大于1M！");
			return res;
		}
		String hash = this.getHash(file);
		FileMeta fileMeta = fileDao.queryByHash(hash);
		if(fileMeta!=null){
			return new Response(ReturnCode.SUCCESS,fileMeta);
		}

		byte[] data = null;
		InputStream ins = null;
		try {
			ins = file.getInputStream();
			data = new byte[ins.available()];
			ins.read(data);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return new Response(ReturnCode.SERVER_INNER_ERROR);
		} finally {
			if(ins!=null){
				try{
					ins.close();
				}catch(Exception e){}
			}
		}
		fileMeta = this.createFileMeta(hash, fileSize,data);
		return new Response(ReturnCode.SUCCESS,fileMeta);
	}
	private FileMeta createFileMeta(String hash, long fileSize, byte[] data) {
		FileMeta fileMeta = new FileMeta();
		fileMeta.setHash(hash);
		fileMeta.setSize(fileSize);
		fileMeta.setData(data);
		fileDao.save(fileMeta);
		return fileMeta;
	}
	
	@Override
	public boolean downloadService(boolean isOnline,String hash,String name,HttpServletRequest request, HttpServletResponse response){
		OutputStream out = null;
		name = name!=null?name:hash;
		try{
			if(!isOnline)//不设置“Content-Disposition”时手机浏览器下载文件时可据文件后缀调用相关的系统工具
			{
				boolean isIE6 = request.getHeader("User-Agent")==null?false:request.getHeader("User-Agent").indexOf("MSIE")>0;
				if(isIE6){
					response.setHeader( "Content-Disposition", "attachment;filename=" + 
						     new String( name.getBytes("gb2312"), "ISO8859-1" ) );
				}else{
					String indexName = new String(name.getBytes(),"ISO-8859-1");//URLEncoder.encode(document.getName(), "UTF-8");
					response.setHeader("Content-disposition", "attachment;filename="+ indexName);
				}
			}
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setDateHeader("Last-Modified", System.currentTimeMillis());
			out = response.getOutputStream();
			FileMeta fileMeta = fileDao.queryByHash(hash);
			if(fileMeta==null){
				logger.warn("FileMeta lost by id:"+hash);
				response.setStatus(404);
				return false;
			}
			String reqHash = request.getHeader("hash");
			if(reqHash!=null&&!reqHash.equals(hash)){//请求的文件已更新
				response.setHeader("hash", hash);
				response.setStatus(404);
				return false;
			}
			this.readData(out,hash);
			out.flush();
			return true;
		}catch(IOException e){
			logger.error("download ("+hash+") failed.-------"+e.getMessage());
			return false;
		}finally{
			if(out!=null){
				try{out.close();}catch(Exception e){}
			}
		}
	}
	private void readData(OutputStream bout,String hash) throws IOException{
		FileMeta fileMeta = fileDao.queryDataByHash(hash);
		if(fileMeta==null||fileMeta.getData()==null){
			logger.warn("read fileMeta "+fileMeta.getHash()+" failed!");
			return ;
		}
		FileChannel inChannel = null;
		try {
			bout.write(fileMeta.getData(), 0, fileMeta.getData().length);
		} catch (IOException e) {
			logger.error(e.getMessage());
			return ;
		} finally {
			if(inChannel!=null){
				try{
					inChannel.close();
				}catch(Exception e){}
			}
		}
	}

	private String getHash(MultipartFile file) {
		try {
			return MD5.bufferToHex(MD5.getFileMD5String(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
