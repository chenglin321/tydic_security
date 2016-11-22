package com.tydic.security;

/**加密包中一些常量定义*/
public class SecurityConst {
	/** 密钥库常量key类 */
	public class KeyStore {
		/** 密钥库类型key */
		public static final String KEYSTORE_TYPE_KEY = "keystore.type";
		/** 密钥库路径key */
		public static final String KEYSTORE_PATH_KEY = "keystore.path";
		/** 密钥库密码key */
		public static final String KEYSTORE_PWD_KEY = "keystore.pwd";
	}

	/** 算法常量key类 */
	public class Algorithm {
		/**aes别名key*/
		public static final String AES_ALIAS_KEY = "aes.alias";
		/**aes密码key*/
		public static final String AES_PWD_KEY = "aes.pwd";
		/**aes算法标识*/
		public static final String AES = "AES";
		/**aes密钥强度*/
		public static final int AES_SIZE = 128;

		/**des别名key*/
		public static final String DES_ALIAS_KEY = "des.alias";
		/**des密码key*/
		public static final String DES_PWD_KEY = "des.pwd";
		/**des算法标识*/
		public static final String DES = "DES";
		/**des密钥强度*/
		public static final int DES_SIZE = 56;

		/**3des别名key*/
		public static final String DESEDE_ALIAS_KEY = "3des.alias";
		/**3des密码key*/
		public static final String DESEDE_PWD_KEY = "3des.pwd";
		/**3des算法标识*/
		public static final String DESEDE = "DESede";
		/**3des密钥强度*/
		public static final int DESEDE_SIZE = 112;

		/**rsa别名key*/
		public static final String RSA_ALIAS_KEY = "rsa.alias";
		/**rsa密码key*/
		public static final String RSA_PWD_KEY = "rsa.pwd";
		/**rsa算法标识*/
		public static final String RSA = "RSA";
		/**rsa密钥强度*/
		public static final int RSA_SIZE = 1024;

		/**dsa别名key*/
		public static final String DSA_ALIAS_KEY = "dsa.alias";
		/**dsa密码key*/
		public static final String DSA_PWD_KEY = "dsa.pwd";
		/**dsa算法标识*/
		public static final String DSA = "DSA";
		/**dsa密钥强度*/
		public static final int DSA_SIZE = 1024;
		
		/**hashmack别名key*/
		public static final String HASHMAC_ALIAS_KEY = "hashmac.alias";
		/**hashmack密码key*/
		public static final String HASHMAC_PWD_KEY = "hashmac.pwd";
		/**hashmac算法标识*/
		public static final String HASHMAC = "HmacSHA256";
		/**hashmac密钥强度*/
		public static final int HASHMAC_SIZE = 256;
	}
}
