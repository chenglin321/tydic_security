package com.tydic.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;

import com.tydic.BaseTests;
import com.tydic.util.IOUtil;

public class KeyStoreUtilTests extends BaseTests {
	
	KeyStoreHelper keyStoreUtil = new KeyStoreHelper();
	public void testKeyStore() throws Exception{
		KeyStore ks = keyStoreUtil.getKeyStore();
		assertNotNull(ks);
	}
	
	/**
	 * 从密钥库中导出密钥至指定文件
	 */
	public void testExportKey() {
		String path = baseConfPath+"/export/testAES.dat";
		String alias = "testaes";
		String pwd = "testaes";
		try{
			keyStoreUtil.exportKey(alias, pwd, path);
		}catch(Exception e){
			e.printStackTrace();
			fail("export key error! ");
		}
	}
	
	/***
	 * 导入密钥进密钥库
	 * @throws IOException
	 */
	public void testImportKey() throws IOException{
		KeyFactory kf = KeyFactory.getKeyFactory();
		String path = baseConfPath+"/import/testAES.dat";
		InputStream in = IOUtil.getInputStream(path);
		String algorithm = "AES";
		Key key = kf.getKey(in, algorithm);
		String alias = "testaes";
		String pwd = "testaes";
		try{
			keyStoreUtil.importKey(alias, pwd, key);
		}catch(Exception e){
			e.printStackTrace();
			fail("import key error! ");
		}
	}
}
