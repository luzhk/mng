package com.platform.universally.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class SignatureUtil {
	
	public final static String SHA1 = "SHA-1";
	 
	public final static String MD5 = "MD5";
	
	/**
	 * 获取字符串签名，支持sha1和md5
	 * @param signType 签名类型
	 * @return 字符串的签名串
	 * @throws NoSuchAlgorithmException 
	 */
	public static String getSign(String signS, String signType) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance(signType);
		digest.update(signS.getBytes());
		byte[] digByte = digest.digest();
		Formatter formatter = new Formatter();
		for (byte bys : digByte) {
			formatter.format("%02x", bys);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
