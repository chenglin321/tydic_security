package com.tydic.security;

/**
 * 加解密器接口
 * @author leo
 *
 */
public interface Encrypt {
	
	/**
	 * 加密数据
	 * 
	 * @param text 明文数据
	 * @return 返回加密后的数据
	 * */
	public byte[] encrypt(byte[] text);
	
	
	/**
	 * 加密数据
	 * 
	 * @param text 明文数据
	 * @return 返回加密后的数据
	 * */
	public String encrypt(String text);
	
	/**
	 * 解密数据
	 * 
	 * @param ptext 密文数据
	 * @return 返回解密后的数据
	 * */
	public byte[] decrypt(byte[] ptext);
	
	/**
	 * 解密数据
	 * 
	 * @param ptext 密文数据
	 * @return 返回解密后的数据
	 * */
	public String decrypt(String ptext);
	
}
