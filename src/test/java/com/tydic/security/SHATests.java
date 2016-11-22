package com.tydic.security;

import com.tydic.BaseTests;
import com.tydic.security.SHA;

public class SHATests extends BaseTests {
	
	/***
	 * 验证sha hash值 
	 */
	public void testSHA(){
		//原文
		String text = "admin123";
		//期望值
		String exp = "240BE518FABD2724DDB6F04EEB1DA5967448D7E831C08C8FA822809F74C720A9";
		String result = SHA.hash(text);
		assertEquals(exp, result);
	}
	
	/***
	 * 验证sha hash值 
	 */
	public void testSHA384(){
		//原文
		String text = "admin123";
		//期望值
		String exp = "3F75AA9266C066B106318AEB0301226EBBA5399D4DA3C9E5264E2F7B2F06ECC16653DE49816B7F767B41DD138336F613";
		String result = SHA.hash384(text);
		assertEquals(exp, result);
	}
	
	/***
	 * 验证sha hash值 
	 */
	public void testSHA512(){
		//原文
		String text = "admin123";
		//期望值
		String exp = "7FCF4BA391C48784EDDE599889D6E3F1E47A27DB36ECC050CC92F259BFAC38AFAD2C68A1AE804D77075E8FB722503F3ECA2B2C1006EE6F6C7B7628CB45FFFD1D";
		String result = SHA.hash512(text);
		assertEquals(exp, result);
	}
}
