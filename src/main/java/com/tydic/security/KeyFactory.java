package com.tydic.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.X509Certificate;

import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 密钥key工厂
 * 
 * @author leo
 * 
 */
public class KeyFactory {

	private static final Log log = LogFactory.getLog(KeyFactory.class);

	private static KeyStoreHelper keyStoreUtil = new KeyStoreHelper();

	private static X509CertUtil x509CertUtil = new X509CertUtil();

	private static KeyFactory keyFactory = null;

	private KeyFactory() {
	}

	private KeyFactory(KeyStoreHelper keyStoreUtil) {
		KeyFactory.keyStoreUtil = keyStoreUtil;
	}

	/**
	 * 
	 * @return 返回默认keystore的keyFactory
	 */
	public static KeyFactory getKeyFactory() {
		if (keyFactory == null) {
			keyFactory = new KeyFactory();
		}
		
		return keyFactory;
	}

	/**
	 * 
	 * @param keyStoreUtil keystore加载工具
	 * @return 返回指定keystoreutil的keyfactory
	 */
	public static KeyFactory getKeyFactory(KeyStoreHelper keyStoreUtil) {
		if (keyFactory == null) {
			keyFactory = new KeyFactory(keyStoreUtil);
		}
		
		return keyFactory;
	}
	
	/**
	 * 从密钥库中返回指定别名的密钥
	 * 
	 * @param alias
	 *            指定算法
	 * @param keyPwd
	 *            密码
	 * @return 密钥
	 */
	public Key getKey(String alias, String keyPwd) {
		char[] kp = keyPwd.toCharArray();
		Key key = null;
		try {
			key = getKeyStore().getKey(alias, kp);
		} catch (Exception e) {
			log.error(e);
		}

		return key;
	}

	/**
	 * @see #getKey(String, String)
	 */
	public PrivateKey getPrivKey(String alias, String keyPwd) {
		return (PrivateKey) getKey(alias, keyPwd);
	}

	/**
	 * 从密钥库中返回指定别名公钥
	 * 
	 * @param alias
	 *            别名
	 * @return 公钥
	 */
	public PublicKey getPublKey(String alias) {
		PublicKey key = null;
		try {
			X509Certificate cert = (X509Certificate) getKeyStore()
					.getCertificate(alias);
			key = cert.getPublicKey();
			cert.checkValidity();// 检查证书是否过期
		} catch (CertificateExpiredException e) {
			log.warn("********警告：证书已经过期，请及时更换！*******");
		} catch (Exception e) {
			log.error(e);
		}

		return key;
	}

	/**
	 * 从x509证书中返回公钥
	 * 
	 * @param in
	 *            证书文件流
	 * @return 公钥
	 */
	public PublicKey getPublKey(InputStream in) {
		PublicKey key = null;
		try {
			X509Certificate cert = x509CertUtil.getX509Cert(in);
			key = cert.getPublicKey();
			cert.checkValidity();// 检查证书是否过期
		} catch (CertificateExpiredException e) {
			log.warn("********警告：证书已经过期，请及时更换！*******");
		} catch (Exception e) {
			log.error(e);
		}

		return key;
	}

	/**
	 * @return 返回加载好的keystore实例
	 * @throws Exception
	 */
	KeyStore getKeyStore() throws Exception {
		return keyStoreUtil.getKeyStore();
	}
	
	/**
	 * 根据输入流返回密钥key，会关闭输入流
	 * 
	 * @param in
	 *            密钥输入流,是指以二进制格式存放的文件输入流
	 * @param algorithm
	 *            算法
	 * @return 密钥
	 * @throws IOException
	 * */
	public Key getKey(InputStream in, String algorithm) throws IOException {
		byte[] secret = null;
		try {
			secret = new byte[in.available()];
			// 读出密文
			in.read(secret);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				log.error("输入流关闭异常！", e);
			}
		}

		// 返回密钥
		return getKey(secret, algorithm);
	}

	/**
	 * 
	 * @param secret
	 *            以byte存放的密钥
	 * @param algorithm
	 *            算法
	 * @return 密钥
	 */
	public Key getKey(byte[] secret, String algorithm) {
		Key key = (Key) new SecretKeySpec(secret, algorithm);
		return key;
	}
	
	/**
	 * 根据 指定算法和密钥强度生成密钥
	 * 
	 * @param keySize
	 *            初始化密钥key大小
	 * @param algorithm
	 *            密钥的生成算法
	 * @return 密钥
	 * @throws NoSuchAlgorithmException
	 */
	public Key getKey(int keySize, String algorithm)
			throws NoSuchAlgorithmException {
		KeyGenerator kg = KeyGenerator.getInstance(algorithm);
		kg.init(keySize);
		return kg.generateKey();
	}

}
