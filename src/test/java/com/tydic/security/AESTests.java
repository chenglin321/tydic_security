package com.tydic.security;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;

import com.tydic.BaseTests;
import com.tydic.util.IOUtil;

public class AESTests extends BaseTests {

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * 测试AES加密解
	 */
	public void testAESEncrypt() {
		// 原文
		String text = "嗨，鱼头，How are you?胡锦涛同志在纪念辛亥革命100周年大会的重要讲话中强调，“中国人民付出艰辛努力、作出巨大牺牲，终于找到了实现中华民族伟大复兴的正确道路和核心力量。这条正确道路就是中国特色社会主义道路，这个核心力量就是中国共产党。”学习贯彻胡锦涛同志重要讲话精神，最根本的就是高举中国特色社会主义伟大旗帜，坚持走这条正确道路，坚持这个核心力量的领导，在实现民族复兴的征程上不懈奋斗。";
		Encrypt encrypt = AES.getInstance();

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
	public void testCreateAES() {
		// 密钥
		Key key = AES.getRandomKey();
		// 验证
		assertEquals(key.getAlgorithm(), SecurityConst.Algorithm.AES);

	}

	/**
	 * 测试AES随机密钥加密解密
	 */
	public void testRandomAESEncrypt() {
		// 密钥
		Key key = AES.getRandomKey();
		// 原文
		String text = "嗨，鱼头，How are you?胡锦涛同志在纪念辛亥革命100周年大会的重要讲话中强调，“中国人民付出艰辛努力、作出巨大牺牲，终于找到了实现中华民族伟大复兴的正确道路和核心力量。这条正确道路就是中国特色社会主义道路，这个核心力量就是中国共产党。”学习贯彻胡锦涛同志重要讲话精神，最根本的就是高举中国特色社会主义伟大旗帜，坚持走这条正确道路，坚持这个核心力量的领导，在实现民族复兴的征程上不懈奋斗。";
		Encrypt encrypt = AES.getInstance(key);

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);

	}

	/**
	 * 根据 外部aes key加密
	 * 
	 * @throws FileNotFoundException
	 */
	public void testOuterKeyAESEncrypt() throws IOException {
		KeyFactory kf = KeyFactory.getKeyFactory();
		String path = baseConfPath+"/export/testAES.dat";
		InputStream in = IOUtil.getInputStream(path);
		String algorithm = "AES";
		Key key = kf.getKey(in, algorithm);
		String text = "哈哈，测试一下撒";
		Encrypt encrypt = AES.getInstance(key);

		// 加密
		String mtext = encrypt.encrypt(text);
		// 解密
		String ptext = encrypt.decrypt(mtext);

		// 验证
		assertEquals(text, ptext);
	}
}
