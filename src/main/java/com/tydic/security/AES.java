package com.tydic.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import com.tydic.util.ConfigUtil;

/**
 * AES对称加密解密实现
 * 
 * @author leo
 * 
 */
public final class AES extends AbstractEncrypt {

	private static final Encrypt o = new AES();

	private AES() {
		//初始化密钥库中默认密钥key
		this(keyFactory.getKey(ConfigUtil.getProperty(SecurityConst.Algorithm.AES_ALIAS_KEY),
				ConfigUtil.getProperty(SecurityConst.Algorithm.AES_PWD_KEY)));
	}
	
	private AES(Key key){
		super(key);
	}
	
	/**
	 * 根据密钥库中默认的密钥返回加密实例（单例）
	 * @return 返回加密实例
	 */
	public static Encrypt getInstance() {
		return o;
	}
	
	/**
	 * 返回指定密钥的加密实例（多例）
	 * @param key 密钥
	 * @return 返回加密实例
	 */
	public static Encrypt getInstance(Key key) {
		return new AES(key);
	}
	
	/**
	 * 生成一个密钥，通常用于协商后的数据加密
	 * @return 返回一个该类的随机密钥
	 */
	public static Key getRandomKey(){
		try {
			return keyFactory.getKey(SecurityConst.Algorithm.AES_SIZE,SecurityConst.Algorithm.AES);
		} catch (NoSuchAlgorithmException e) {
		}
		return null;
	}
}
