package com.tydic.security;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;

import javax.crypto.SecretKey;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.ConfigUtil;
import com.tydic.util.IOUtil;

/**
 * keystore相关操作助手工具类
 * 
 * @author leo
 * 
 */
public class KeyStoreHelper implements KeyStoreInterface {

	private Log log = LogFactory.getLog(KeyStoreHelper.class);

	public synchronized KeyStore getKeyStore() throws KeyStoreException {
		KeyStore ks = null;
		InputStream ksfin = null;
		BufferedInputStream ksbufin = null;
		try {
			String path = ConfigUtil
					.getProperty(SecurityConst.KeyStore.KEYSTORE_PATH_KEY);
			String type = ConfigUtil
					.getProperty(SecurityConst.KeyStore.KEYSTORE_TYPE_KEY);
			String pwd = ConfigUtil
					.getProperty(SecurityConst.KeyStore.KEYSTORE_PWD_KEY);
			char[] kspwd = pwd.toCharArray();
			ks = KeyStore.getInstance(type);
			ksfin = IOUtil.getInputStream(path);
			ksbufin = new BufferedInputStream(ksfin);
			ks.load(ksbufin, kspwd);
		} catch (Exception e) {
			throw new KeyStoreException("load keyStore exception!",e);
		} finally {
			try {
				ksbufin.close();
			} catch (IOException e) {
				log.error(e);
			}
		}

		return ks;
	}

	public synchronized void importKey(String alias, String pwd, Key key)
			throws IOException {
		OutputStream out = null;
		try {
			//备份
			backup();
			KeyStore ks = getKeyStore();
			if(ks.containsAlias(alias)){
				log.warn("别名:"+alias+" 已经存在密钥库，禁止导入!");
				return ;
			}
			KeyStore.SecretKeyEntry skEntry = new KeyStore.SecretKeyEntry(
					(SecretKey) key);
			KeyStore.PasswordProtection ksPwd = new KeyStore.PasswordProtection(
					pwd.toCharArray());
			ks.setEntry(alias, skEntry, ksPwd);
			out = IOUtil.getOutputStream(ConfigUtil
					.getProperty(SecurityConst.KeyStore.KEYSTORE_PATH_KEY));
			ks.store(
					out,
					ConfigUtil.getProperty(
							SecurityConst.KeyStore.KEYSTORE_PWD_KEY)
							.toCharArray());
		} catch (Exception e) {
			log.warn(e);
			log.info("try restore...");
			//恢复
			restore();
		} finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}

	}

	/**
	 * 备份
	 * 
	 * @throws FileNotFoundException
	 */
	private void backup()throws IOException {
		log.info("开始备份密钥库...");
		File keyStroeFile = IOUtil.getFileWithPath(ConfigUtil
				.getProperty(SecurityConst.KeyStore.KEYSTORE_PATH_KEY));
		File bakFile = new File(keyStroeFile.getAbsolutePath()+".bak");
		IOUtil.copyFile(keyStroeFile, bakFile);
		log.info("备份成功["+bakFile.getAbsolutePath()+"]!");
	}

	/** 当出现密钥库操作失败时进行恢复操作 
	 * @throws IOException */
	private void restore() throws IOException {
		log.info("开始恢复...");
		File keyStroeFile = IOUtil.getFileWithPath(ConfigUtil
				.getProperty(SecurityConst.KeyStore.KEYSTORE_PATH_KEY));
		File bakFile = new File(keyStroeFile.getAbsolutePath()+".bak");
		IOUtil.copyFile(bakFile, keyStroeFile);
		log.info("成功恢复...");
	}

	public synchronized void exportKey(String alias, String pwd, String path)
			throws IOException {
		OutputStream out = null;
		try {
			Key key = getKeyStore().getKey(alias, pwd.toCharArray());
			out = IOUtil.getOutputStream(path);
			byte[] b = key.getEncoded();
			out.write(b);
		} catch(Exception e){
			throw new IOException("export key exception!"+e.getMessage());
		}finally {
			if (out != null) {
				out.close();
				out = null;
			}
		}
	}

}
