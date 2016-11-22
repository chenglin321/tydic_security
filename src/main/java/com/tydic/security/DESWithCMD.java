package com.tydic.security;



/**
 * 根据口令加密解密,系统内部使用。不能作为系统间传输加密使用
 */
public final class DESWithCMD  extends AbstractEncrypt {

	private static final byte[] SECRET = new byte[]{115, 122, 116, 121, 100, 105, 99, 46};
	
	private static final Encrypt o = new DESWithCMD();
	
	private DESWithCMD() {
		super(keyFactory.getKey(SECRET,DES.class.getSimpleName()));
	}
	
	/**
	 * 根据默认的内置密钥返回DES加密实例
	 * 
	 * @return 返回DES加密实例
	 */
	public static Encrypt getInstance() {
		return o;
	}
}
