package com.fz.common.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.web.multipart.MultipartFile;

public class MD5 {
	protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/*protected static MessageDigest messageDigest = null;
	static {
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
		}
	}*/
	
	private static MessageDigest getMessageDigest(){
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException nsaex) {
		}
		return null;
	}

	public static byte[] getFileMD5String(MultipartFile file) throws IOException {
		MessageDigest messageDigest = getMessageDigest();
		InputStream in = null;
		try {
			in = file.getInputStream();
			int maxSize = 64 * 1024;
			byte[] byteBuffer = new byte[maxSize];
			int n = 0;
			while ((n = in.read(byteBuffer)) > 0) {
				messageDigest.update(byteBuffer, 0, n);
			}
			byte[] result = messageDigest.digest();
			return result;
		} catch (IOException e) {
			throw e;
		} finally {
			if(in!=null){
				try{
					in.close();
				}catch(Exception e){}
			}
		}
	}

	public static byte[] getMD5FileChunk(FileInputStream in, int chunkSize)
			throws IOException {
		MessageDigest messageDigest = getMessageDigest();
		int bsize = 300 * 1024;
		byte[] buf = new byte[bsize];
		int n = 0;

		while (chunkSize != 0) {
			if (chunkSize > bsize)
				n = in.read(buf);
			else
				n = in.read(buf, 0, chunkSize);
			if (n == -1) {
				break;
			}
			messageDigest.update(buf, 0, n);
			chunkSize -= n;
		}
		return messageDigest.digest();
	}

	public static byte[] getMD5FileChunk(FileInputStream in, int offset,
			int trumbnum, int trumbsize) throws IOException {
		MessageDigest messageDigest = getMessageDigest();
		byte[] buf = new byte[trumbsize];
		int n = 0;
		in.skip(offset);
		for (int i = 0; i < trumbnum; ++i) {
			n = in.read(buf);
			if (n < trumbsize) {
				messageDigest.update(buf, 0, n);
				break;
			}
			messageDigest.update(buf);
		}
		return messageDigest.digest();
	}

	public static String getMD5String(String s) {
		return getMD5String(s.getBytes());
	}

	public static byte[] getMD5Str(String s) {
		MessageDigest messageDigest = getMessageDigest();
		messageDigest.update(s.getBytes());
		return messageDigest.digest();
	}

	public static String getMD5String(byte[] bytes) {
		MessageDigest messageDigest = getMessageDigest();
		messageDigest.update(bytes);
		return bufferToHex(messageDigest.digest());
	}

	public static String bufferToHex(byte bytes[]) {
		return bufferToHex(bytes, 0, bytes.length);
	}

	private static String bufferToHex(byte bytes[], int m, int n) {
		StringBuffer stringbuffer = new StringBuffer(2 * n);
		int k = m + n;
		for (int l = m; l < k; l++) {
			appendHexPair(bytes[l], stringbuffer);
		}
		return stringbuffer.toString();
	}

	private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
		char c0 = hexDigits[(bt & 0xf0) >> 4];
		char c1 = hexDigits[bt & 0xf];
		stringbuffer.append(c0);
		stringbuffer.append(c1);
	}

	public static boolean checkPassword(String password, String md5PwdStr) {
		String s = getMD5String(password);
		return s.equals(md5PwdStr);
	}
    
    public static String md5MatchToPHP(String  str){
        String result = "";
        try{ 
            MessageDigest m = MessageDigest.getInstance("MD5");                             
            m.update(str.getBytes("UTF-8")); 
            byte s[]= m.digest(); 
            for(int i=0;i<s.length;i++){ 
               result += Integer.toHexString((0x000000FF&s[i])|0xFFFFFF00).substring(6);
            } 
        }catch(Exception e){ 
         e.printStackTrace();
        } 
        return  result; 
    }

}
