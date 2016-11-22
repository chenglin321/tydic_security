package com.tydic.security;

import com.tydic.SpringTests;

public class PKITests extends SpringTests {
	
	private String mText = "是123456435*@&^@^7a中国asdf%dsaf%dsaf??saddsab";
	private String alias = "tydic-pcc";
	private String keyPwd = "tydic-pcc";
	
	/**操作同一别名的密钥所以申明一个pki实例并且初始化一次*/
	private PKI pki;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		pki = new PKI();
		pki.init(alias, keyPwd);
	}

	/**
	 * rsa私钥加密 公钥解密
	 * 
	 * @throws Exception
	 */
	public void testPKI1() throws Exception {
		String m = pki.getPrivatePKI().encrypt(mText);
		System.out.println("密文：" + m);
		String mw = pki.getPublicPKI().decrypt(m);
		System.out.println("解密得明文：" + mw);

		assertEquals(mText, mw);

	}

	/**
	 * rsa公 钥加密 私钥解密
	 * 
	 * @throws Exception
	 */
	public void testPKI2() throws Exception {
		String m = pki.getPublicPKI().encrypt(mText);
		System.out.println("密文：" + m);
		String mw = pki.getPrivatePKI().decrypt(m);
		System.out.println("解密得明文：" + mw);

		assertEquals(mText, mw);
	}
	
	/**
	 * 验证数字签名
	 * 1.对明文进行数字签名，签名算法是SHA1-DSA
	 * 2.对明文进行数字签名验证
	 * @throws Exception
	 */
	public void testSign()throws Exception{
		PKI pki = new PKI();
		pki.init("tydic-pcc-sign", "tydic-pcc-sign");
		String signStr = pki.sign(mText);
		System.out.println("原文:"+mText+" 签名:"+signStr);
		
		boolean flag = pki.verify(mText,signStr);
		assertTrue(flag);
	}
	
}
