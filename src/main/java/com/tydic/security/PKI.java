package com.tydic.security;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.NullUtil;

/**
 * pki公钥基础设施，用来操作keystore中密钥对的加密解密</br>
 * 
 * @author leo
 * 
 */
public class PKI {

	private static final Log log = LogFactory.getLog(PKI.class);

	private PKIPair pkiPair;

	/** 签名算法 */
	private static final String SIGN_ALG = "SHA1withDSA";

	/**
	 * 初始化后获取x509证书中公钥加密实例[调用getPublicPKI()]
	 * 
	 * @param alias
	 *            密钥别名
	 */
	public void init(String alias) {
		pkiPair = new PKIPair(alias);
	}

	/**
	 * 初始化后不仅可调用getPublicPKI()，还可调用getPrivatePKI()来加解密
	 * 
	 * @param alias
	 *            密钥别名
	 * @param keyPwd
	 *            存取密钥所用的密码
	 */
	public void init(String alias, String keyPwd) {
		pkiPair = new PKIPair(alias, keyPwd);
	}

	/**
	 * 
	 * @return 返回公钥填充的加密实例
	 * @exception NullPointerException
	 *                当pkiPair为空时抛出
	 */
	public Encrypt getPublicPKI() {
		if (NullUtil.isNull(pkiPair)) {
			throw new NullPointerException("请先调用init()进行初始化...");
		}
		return pkiPair.getPubPKI();
	}

	/**
	 * 可根据别名返回非对称算法的私钥
	 * 
	 * @return 返回私钥填充的加密实例
	 * @exception NullPointerException
	 *                当pkiPair为空时抛出
	 */
	public Encrypt getPrivatePKI() {
		if (NullUtil.isNull(pkiPair)) {
			throw new NullPointerException("请先调用init()进行初始化...");
		}
		return pkiPair.getPrvPKI();
	}

	/**
	 * 数字签名 算法：SHA1withDSA
	 * 
	 * @param data
	 *            要签名的数据
	 * @return 返回字节数组的签名数据
	 */
	public byte[] sign(byte[] data) {
		byte[] signData = null;
		Signature s;
		try {
			s = Signature.getInstance(SIGN_ALG);
			s.initSign(pkiPair.getPrvKey());
			s.update(data);
			signData = s.sign();
			if (log.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();
				for (byte b : signData) {
					sb.append(b + ",");
				}
				log.debug("signData:" + sb);
			}
		} catch (Exception e) {
			log.error("签名异常", e);
		}

		return signData;
	}

	/**
	 * @see #sign(byte[])
	 */
	public String sign(String data) {
		return Base64.encodeBase64String(sign(StringUtils.getBytesUtf8(data)));
	}

	/**
	 * 验证签名是否正确 算法：SHA1withDSA
	 * 
	 * @param data
	 *            明文数据
	 * @param signData
	 *            签名数据
	 * @return 正确返回true,否则返回false
	 */
	public boolean verify(byte[] data, byte[] signData) {
		boolean flag = false;
		Signature s;
		try {
			s = Signature.getInstance(SIGN_ALG);
			s.initVerify(pkiPair.getPubKey());
			s.update(data);
			flag = s.verify(signData);
			if (log.isDebugEnabled()) {
				StringBuilder sb = new StringBuilder();
				for (byte b : signData) {
					sb.append(b + ",");
				}
				log.debug("signData:" + sb);
			}
		} catch (Exception e) {
			log.error("验证签名异常", e);
		}

		return flag;
	}

	/**
	 * @see #verify(byte[], byte[])
	 */
	public boolean verify(String data, String signData) {
		return verify(StringUtils.getBytesUtf8(data), Base64.decodeBase64(signData));
	}

	private class PKIPair extends AbstractEncrypt {

		/** 用公钥填充的pki加密器 */
		private Encrypt pubPKI = null;

		/** 用私钥填充的rsa加密器 */
		private Encrypt prvPKI = null;

		/** 密钥别名 */
		private String alias;

		/** 密钥密码 */
		private String keyPwd;

		protected PKIPair(Key key) {
			super(key);
		}

		/** 只获取x509公钥证书时调用该构造方法 */
		public PKIPair(String alias) {
			this.alias = alias;
			pubPKI = new PKIPair(keyFactory.getPublKey(alias));
		}

		/** 要获取密钥库中密钥时调用该构造方法 */
		public PKIPair(String alias, String keyPwd) {
			this(alias);
			this.keyPwd = keyPwd;
			prvPKI = new PKIPair(keyFactory.getPrivKey(alias, keyPwd));
		}

		public Encrypt getPubPKI() {
			return pubPKI;
		}

		public Encrypt getPrvPKI() {
			return prvPKI;
		}

		public PrivateKey getPrvKey() {
			return keyFactory.getPrivKey(alias, keyPwd);
		}

		public PublicKey getPubKey() {
			return keyFactory.getPublKey(alias);
		}
	}

}
