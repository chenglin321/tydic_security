package com.tydic.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息摘要之md5
 * 
 * @author leo
 * */
public final class MD5 {

	private static final Log log = LogFactory.getLog(MD5.class);

	/** 算法 */
	private static final String ALGORITHM = "MD5";

	/**
	 * 32位的md5
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String hash(String text) {
		String ptext = Hash.hash(text, ALGORITHM);
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * 16位的md5
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String hash16(String text) {
		String ptext = Hash.hash(text, ALGORITHM).substring(8, 24);;
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
}