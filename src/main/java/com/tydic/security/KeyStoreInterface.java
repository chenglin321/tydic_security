package com.tydic.security;

import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;

/**
 * keystore接口，描述导入导出key以及获取keystore实例
 * @author leo
 *
 */
public interface KeyStoreInterface {
	
	/**
	 * 根据配置文件中指定的密钥库信息初始密钥库实例
	 * 
	 * @return 返回密钥库实例
	 * @throws KeyStoreException
	 */
	public KeyStore getKeyStore() throws KeyStoreException;
	
	/**
	 * 向默认keystore导入密钥
	 * @param alias key对应的别名 
	 * @param pwd key对应的密码
	 * @param key 密钥
	 * @throws IOException 
	 */
	public void importKey(String alias,String pwd,Key key) throws IOException;
	
	/**
	 * 从默认keystore导出密钥
	 * @param alias key对应的别名 
	 * @param pwd key对应的密码
	 * @param path 密钥导出后导入文件路径 
	 * @throws Exception 
	 */
	public void exportKey(String alias,String pwd,String path) throws IOException;
}
