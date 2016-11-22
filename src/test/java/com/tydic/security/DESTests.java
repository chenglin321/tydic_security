package com.tydic.security;

import java.security.Key;

import com.tydic.BaseTests;

public class DESTests extends BaseTests {

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testDES() {
		// 原文
		String text = "Hello1234Hello1234Hello1234Hello1234";
		Encrypt encrypt = DES.getInstance();

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);

	}

	public void testDESWithCMD() {
		// 原文
		String text = "嗨，鱼头，How are you?胡锦涛同志在纪念辛亥革命100周年大会的重要讲话中强调，“中国人民付出艰辛努力、作出巨大牺牲，终于找到了实现中华民族伟大复兴的正确道路和核心力量。这条正确道路就是中国特色社会主义道路，这个核心力量就是中国共产党。”学习贯彻胡锦涛同志重要讲话精神，最根本的就是高举中国特色社会主义伟大旗帜，坚持走这条正确道路，坚持这个核心力量的领导，在实现民族复兴的征程上不懈奋斗。";
		Encrypt encrypt = DESWithCMD.getInstance();

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);

	}

	/**
	 * 测试创建随机密钥
	 */
	public void testCreateDES() {
		// 密钥
		Key key = DES.getRandomKey();
		// 验证
		assertEquals(key.getAlgorithm(), SecurityConst.Algorithm.DES);

	}

	/**
	 * 测试DES随机密钥加密解密
	 */
	public void testRandomDESEncrypt() {
		// 密钥
		Key key = DES.getRandomKey();
		// 原文
		String text = "嗨，鱼头，How are you?胡锦涛同志在纪念辛亥革命100周年大会的重要讲话中强调，“中国人民付出艰辛努力、作出巨大牺牲，终于找到了实现中华民族伟大复兴的正确道路和核心力量。这条正确道路就是中国特色社会主义道路，这个核心力量就是中国共产党。”学习贯彻胡锦涛同志重要讲话精神，最根本的就是高举中国特色社会主义伟大旗帜，坚持走这条正确道路，坚持这个核心力量的领导，在实现民族复兴的征程上不懈奋斗。";
		Encrypt encrypt = DES.getInstance(key);

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);

	}

	public void testDESEncryptWithPWD() {
		// 密钥
		Key key = KeyFactory.getKeyFactory().getKey(
				"tydic-crm-66789".getBytes(), "DES");
		// 原文
		String text = "okoko中吗？";
		Encrypt encrypt = DES.getInstance(key);

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);
	}
}
