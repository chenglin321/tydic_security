package com.tydic.security;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.cert.X509Certificate;

import com.tydic.BaseTests;
import com.tydic.util.IOUtil;

public class X509CertUtilTests extends BaseTests {
	
	/**
	 * 测试x509证书的加载
	 * @throws FileNotFoundException 
	 */
	public void testGetX509Cert() throws FileNotFoundException{
		X509CertUtil x509CertUtil = new X509CertUtil();
		String path = baseConfPath+"tydic-ca.cer";
		InputStream in = IOUtil.getInputStream(path);
		X509Certificate cert = x509CertUtil.getX509Cert(in);
		assertNotNull(cert);
		assertNotNull(cert.getPublicKey());
	}
}
