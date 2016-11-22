package com.tydic.security;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 对称加密解密抽象实现，依赖commons-code#base64
 */
public abstract class AbstractEncrypt implements Encrypt {

	protected final Log log = LogFactory.getLog(this.getClass());

	/** 数据编码方式 */
	private static final String ENCODE = "UTF-8";
	
	/**密钥工厂*/
	protected static KeyFactory keyFactory = KeyFactory.getKeyFactory();
	
	/** 密钥key */
	private Key key;
	
	protected AbstractEncrypt(){}

	/**
	 * 
	 * @param key
	 *            密钥key
	 */
	protected AbstractEncrypt(Key key) {
		this.key = key;
	}
	
	/**
	 * 加密
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * */
	public byte[] encrypt(byte[] text) {
		byte[] result = null;
		byte[] ptext = null;// 加密前的字节数组
		byte[] ctext = null;// 加密后的字节数组
		try {
			Cipher cp = Cipher.getInstance(key.getAlgorithm());
			cp.init(Cipher.ENCRYPT_MODE, key);// 加密
			ptext = text;
			ctext = cp.doFinal(ptext);
		} catch (NoSuchAlgorithmException e) {
			log.error("执行加密算[" + key.getAlgorithm() + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}
		result = ctext;

		return result;
	}

	/**
	 * 解密
	 * 
	 * @param ptext
	 *            经过加密后的密文
	 * @return 返回明文
	 * */
	public byte[] decrypt(byte[] ptext) {
		byte[] ctext = null;// 密文
		byte[] result = null;// 解密后的明文
		try {
			ctext = ptext;
			Cipher cp = null;
			cp = Cipher.getInstance(key.getAlgorithm());
			cp.init(Cipher.DECRYPT_MODE, key);// 解密
			ctext = cp.doFinal(ctext);
			result = ctext;
		} catch (NoSuchAlgorithmException e) {
			log.error("执行加密算[" + key.getAlgorithm() + "]法异常!", e);
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}

	/**
	 * 加密
	 * 
	 * @param text
	 *            明文
	 * @return 返回密文
	 * */
	public String encrypt(String text) {
		String result = null;
		byte[] ptext = null;// 加密前的字节数组
		byte[] ctext = null;// 加密后的字节数组
		try {
			ptext = text.getBytes(ENCODE);
			ctext = this.encrypt(ptext);
			result = Base64.encodeBase64String(ctext);
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}

	/**
	 * 解密
	 * 
	 * @param ptext
	 *            经过加密后的密文
	 * @return 返回明文
	 * */
	public String decrypt(String ptext) {
		byte[] ctext = null;// 密文
		String result = null;// 解密后的明文
		try {
			ctext = Base64.decodeBase64(ptext);
			ctext = this.decrypt(ctext);
			result = new String(ctext, ENCODE);
		} catch (Exception e) {
			log.error(e);
		}

		return result;
	}
}
