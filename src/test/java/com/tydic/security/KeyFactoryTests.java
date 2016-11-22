package com.tydic.security;

import java.security.Key;

import com.tydic.BaseTests;

public class KeyFactoryTests extends BaseTests {

	private KeyFactory keyFactory = KeyFactory.getKeyFactory();
	
	public void testGetKeyStringString() {
		String alias = "testaes";
		String keyPwd = "testaes";
		Key key = keyFactory.getKey(alias, keyPwd);
		assertNotNull(key);
	}

}
