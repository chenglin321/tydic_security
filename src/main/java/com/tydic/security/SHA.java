package com.tydic.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 消息摘要之sha
 * 提供sha256,384,512三种强度的hash
 * @author leo
 * */
public final class SHA {

	private static final Log log = LogFactory.getLog(SHA.class);

	/** sha-256算法 */
	private static final String SHA256 = "SHA-256";
	
	/** sha-384算法 */
	private static final String SHA384 = "SHA-384";
	
	/** sha-512算法 */
	private static final String SHA512 = "SHA-512";
	
	private SHA(){}

	/**
	 * sha-256消息摘要计算
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String hash(String text) {
		String ptext = Hash.hash(text, SHA256);
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * sha-384消息摘要计算
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String hash384(String text) {
		String ptext = Hash.hash(text, SHA384);
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * sha-512消息摘要计算
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws 当text为null时抛出NullPointerException
	 * */
	public static final String hash512(String text) {
		String ptext = Hash.hash(text, SHA512);
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
}