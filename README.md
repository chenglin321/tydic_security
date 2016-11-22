# tydic_security
tydic_security
security提供内容过滤、ip黑白名单、MD5、SHA、AES、DES、DESede、RSA等安全算法,以及X509操作,该组件要求JDK版本在1.5及以上。
支持Sun JDK以及JRockit JDK,IBM JDK支持不完善

配置依赖以下：
servlet-2.x.jar (仅对使用IPFilter类似的过滤器时需要）
commons-logging-1.1.1.jar 提供日志输出插件（具体可用log4j,jdk log等）
commons-codec-1.5.jar 提供base64编码
dom4j-1.6.jar 
common-0.0.2.jar --自身提供的组件
