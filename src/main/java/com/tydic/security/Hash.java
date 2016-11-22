package com.tydic.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.HexUtil;

/**
 * 消息摘要
 * 
 * @author leo
 * */
public final class Hash {

	private static final Log log = LogFactory.getLog(Hash.class);

	/** 数据编码方式 */
	private static final String ENCODE = "UTF-8";

	/**
	 * 以字节方式返回指定算法的hash摘要
	 * 
	 * @param text
	 *            明文
	 * @param algorithm 摘要算法
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public static final byte[] hash2byte(String text,String algorithm) {
		byte[] ptext = null;
		try {
			byte[] strTemp = text.getBytes(ENCODE);
			MessageDigest mdt = MessageDigest.getInstance(algorithm);
			if(log.isDebugEnabled()){
				log.debug("provider:"+mdt.getProvider());
				log.debug("DigestLength:"+mdt.getDigestLength());
			}
			mdt.update(strTemp);
			byte[] md = mdt.digest();
			ptext =md;
		} catch (NoSuchAlgorithmException e) {
			log.error("摘要算法[" + algorithm + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * 根据指定算法的hash摘要,以16进制字符方式返回
	 * 
	 * @param text
	 *            明文
	 * @param algorithm 摘要算法
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public static final String hash(String text,String algorithm) {
		return HexUtil.toHexString(hash2byte(text, algorithm));
	}
}