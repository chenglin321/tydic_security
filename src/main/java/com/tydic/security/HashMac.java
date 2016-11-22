package com.tydic.security;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.ConfigUtil;
import com.tydic.util.HexUtil;

/**
 * hash消息验证码，默认实现HmacSHA256的消息验证码
 * 
 * @author leo
 * */
public final class HashMac {

	private static final Log log = LogFactory.getLog(HashMac.class);

	/** 数据编码方式 */
	private static final String ENCODE = "UTF-8";
	
	/**默认算法*/
	private String algorithm = "HmacSHA256";
	
	/**默认指定算法为HmacSHA256*/
	public HashMac(){}
	
	/**
	 * @param algorithm hashmac算法
	 */
	public HashMac(String algorithm){
		this.algorithm = algorithm;
	}
	
	/**
	 * 根据密钥库中默认密钥key进行HashMac，并以字节形式返回
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public static final byte[] hash2byte(String text) {
		byte[] ptext = null;
		Key key = null;
		try {
			key = KeyFactory.getKeyFactory().getKey(ConfigUtil.getProperty(SecurityConst.Algorithm.HASHMAC_ALIAS_KEY),
					ConfigUtil.getProperty(SecurityConst.Algorithm.HASHMAC_PWD_KEY));
			byte[] strTemp = text.getBytes(ENCODE);
			Mac mc = Mac.getInstance(key.getAlgorithm());
			if(log.isDebugEnabled()){
				log.debug("provider:"+mc.getProvider());
				log.debug("MacLength:"+mc.getMacLength());
			}
			mc.init(key);
			byte[] md = mc.doFinal(strTemp);
			ptext = md;
		} catch (NoSuchAlgorithmException e) {
			log.error("HashMac算法[" + key.getAlgorithm() + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * 根据密钥库中默认密钥key进行HashMac,并以16进制字符形式返回
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public static final String hash(String text) {
		return HexUtil.toHexString(hash2byte(text));
	}
	
	/**
	 * 根据指定密钥key进行HashMac，并以字节形式返回
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public final byte[] hash2byte(String text,Key key) {
		byte[] ptext = null;
		try {
			byte[] strTemp = text.getBytes(ENCODE);
			Mac mc = Mac.getInstance(key.getAlgorithm());
			if(log.isDebugEnabled()){
				log.debug("provider:"+mc.getProvider());
				log.debug("MacLength:"+mc.getMacLength());
			}
			mc.init(key);
			byte[] md = mc.doFinal(strTemp);
			ptext = md;
		} catch (NoSuchAlgorithmException e) {
			log.error("HashMac算法[" + key.getAlgorithm() + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}
		if(log.isDebugEnabled()){
			log.debug("密文："+ptext+" 明文："+text);
		}
		
		return ptext;
	}
	
	/**
	 * 根据指定密钥key进行HashMac,并以16进制字符形式返回
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * @throws NullPointerException 当text为null时抛出
	 * */
	public final String hash(String text,Key key) {
		return HexUtil.toHexString(hash2byte(text, key));
	}
	
	/**
	 * 根据key对text进行hash mac，算法：BASE64(hashMac(text,key))
	 * 
	 * @param text 要摘要的明文
	 * @param key 密文
	 * @return hashMac摘要数据
	 */
	public final String hash(String text,String key){
		String secretStr = null;
		try {
			Key secretKey = KeyFactory.getKeyFactory().getKey(key.getBytes(ENCODE), algorithm);
			byte[] secret = hash2byte(text,secretKey);
			secretStr = Base64.encodeBase64String(secret);
		} catch (UnsupportedEncodingException e) {
		}
		
		return secretStr;
	}
}