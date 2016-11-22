package com.tydic.security;

import com.tydic.BaseTests;
import com.tydic.security.MD5;

public class MD5Tests extends BaseTests {
	
	/***
	 * 验证32位md5 hash值 
	 */
	public void testMD5For32(){
		//原文
		String text = "admin123";
		//期望值
		String exp = "0192023A7BBD73250516F069DF18B500";
		String result = MD5.hash(text);
		assertEquals(exp, result);
	}
	
	/***
	 * 验证16位md5 hash值 
	 */
	public void testMD5For16(){
		//原文
		String text = "admin123";
		//期望值
		String exp = "7BBD73250516F069";
		String result = MD5.hash16(text);
		assertEquals(exp, result);
	}
}
