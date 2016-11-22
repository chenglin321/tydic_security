package com.tydic.security;

/**
 * 该类不作任何数据加密解密动作，使用时请根据实践情况考虑
 */
public class UnEncrypt implements Encrypt {

	public byte[] encrypt(byte[] text) {
		return text;
	}

	public byte[] decrypt(byte[] ptext) {
		return ptext;
	}

	public String encrypt(String text) {
		return text;
	}

	public String decrypt(String ptext) {
		return ptext;
	}
	
}
