package com.tydic.security;

import java.io.InputStream;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * x509工具类
 * @author leo
 *
 */
public class X509CertUtil {
	
	private Log log = LogFactory.getLog(X509CertUtil.class);
	
	/** 证书类型 */
	private static final String CERT_TYPE = "X.509";
	
	/**
	 * 
	 * @param in x509证书文件输入流
	 * @return 返回java.security.cert.X509Certificate实例
	 */
	public X509Certificate getX509Cert(InputStream in) {
		CertificateFactory cf = null;
		X509Certificate x509 = null;
		try {
			cf = CertificateFactory.getInstance(CERT_TYPE);
			x509 = (X509Certificate) cf.generateCertificate(in);
		} catch (CertificateException e) {
			log.error(e);
		}
		
		return x509;
	}
}
