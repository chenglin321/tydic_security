package com.tydic.security;

import com.tydic.BaseTests;

public class WeakPasswordsTests extends BaseTests {
	
	WeakPasswords weakPasswords = new WeakPasswordsHelper();

	@Override
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testIsWeakPasswords() {
		String pwd = null;

		pwd = "123456";
		assertTrue(weakPasswords.isWeakPasswords(pwd));

		pwd = "123123";
		assertTrue(weakPasswords.isWeakPasswords(pwd));

		pwd = "000000";
		assertTrue(weakPasswords.isWeakPasswords(pwd));

		pwd = "aaaaaa";
		assertTrue(weakPasswords.isWeakPasswords(pwd));

		pwd = "bbbbbb";
		assertTrue(weakPasswords.isWeakPasswords(pwd));
	}

	public void testIsOccurrenceNumber() {
		String pwd = null;
		// b出现了6次
		pwd = "bbbddebf2b4b";
		assertTrue(weakPasswords.isOvertopOccurrenceNumber(pwd));

		// b出现了3次
		pwd = "baab343b";
		assertFalse(weakPasswords.isOvertopOccurrenceNumber(pwd));

	}
	
	public void testIsContinuousOccurrenceNumber() {
		String pwd = null;
		// b出现了6次,b连续出现3次,超过配置值1次
		pwd = "bbbddebf2b4b";
		assertTrue(weakPasswords.isOvertopContinuousOccurrenceNumber(pwd));

		// a连接出现了2次,超过配置值1次
		pwd = "baab343b";
		assertTrue(weakPasswords.isOvertopContinuousOccurrenceNumber(pwd));
		
		//所有字符连续次数为1满足要求
		pwd = "123456";
		assertFalse(weakPasswords.isOvertopContinuousOccurrenceNumber(pwd));
	}
}
