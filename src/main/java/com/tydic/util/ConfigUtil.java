package com.tydic.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.security.DESWithCMD;

/**
 * properties资源工具
 * 
 * @author leo
 * 
 */
public class ConfigUtil {

	private static final String CONFIG_LISTENER = "config.listener";

	private static final Log log = LogFactory.getLog(ConfigUtil.class);

	private static Properties properties;

	/** 配置文件路径 */
	private static String path;

	/** 刷新时间 */
	private static long refTime;

	/** 配置文件更新时间 */
	private static long updateTime;

	/** 以pwd结尾的key要进行加密存储处理 */
	private static final String SUFFIX = "pwd";

	/** 加密后的密文前缀 */
	private static final String PLACEHOLDER = "{SECRET}";

	private ConfigUtil() {
	}

	private static Properties getProperties() {
		return properties;
	}

	/**
	 * 初始化properties文件，同时会启动一个名为ConfigWatchdog的守护线程去监听和刷新配置文件，刷新时间为refTime值
	 * 
	 * @param path
	 *            配置文件路径,支持file和classpath前缀
	 * @param refTime
	 *            配置文件刷新时间
	 * @throws IOException
	 */
	public static void init(String path, long refTime) throws IOException {
		ConfigUtil.path = path;
		ConfigUtil.refTime = refTime;
		ConfigUtil.loadConfig();
		Thread configWatchdog = new Thread(new RefreshThread(),
				"ConfigWatchdog");
		configWatchdog.setDaemon(Boolean.TRUE);
		configWatchdog.start();
	}

	/**
	 * 根据指定的刷新时间 去检查配置文件可有更新，有更新则刷新
	 * 
	 * @throws IOException
	 */
	public synchronized static void loadConfig() throws IOException {
		if (getLastModifyTime() > updateTime) {// 如果配置文件有更新则加载
			log.debug("load properties config file start...");
			// 1.先加密存储
			encryptAndSave(path);
			updateTime = getLastModifyTime();
			// 2.再解密读出
			ConfigUtil.properties = loadAndDecrypt(path);
			// 3.刷新各个具体的配置信息
			String[] configListeners = ConfigUtil.getProperty(CONFIG_LISTENER)
					.split(",");
			for (int i = 0; !NullUtil.isNull(configListeners)
					&& i < configListeners.length; i++) {
				String configListener = configListeners[i];
				if (NullUtil.isNull(configListener)) {
					continue;
				}
				try {
					ConfigListener cl = (ConfigListener) Class.forName(
							configListener).newInstance();
					cl.refresh();
				} catch (Exception e) {
					log.error("配置监听器[" + configListener + "]实例化失败", e);
				}
			}
			log.debug("load properties config file end...");
		}
	}

	private static long getLastModifyTime() {
		return IOUtil.getFileWithPath(path).lastModified();
	}

	/*
	 * 对配置文件敏感数据进行加密并持久化到配置文件
	 */
	private static void encryptAndSave(String path) throws IOException{
		String newFilePath = path + ".tmp";
		boolean flag = false;
		BufferedReader reader = null;
		BufferedWriter writer = null;
		log.debug("配置文件:" + path + " 加密并存储开始...");
		try {
			InputStream in = IOUtil.getInputStream(path);
			OutputStream out = IOUtil.getOutputStream(newFilePath);
			String lineStr = null;
			reader = new BufferedReader(
					new InputStreamReader(in));
			writer = new BufferedWriter(new OutputStreamWriter(
					out, "UTF-8"));
			while ((lineStr = reader.readLine()) != null) {
				lineStr = new String(lineStr.getBytes(), "utf-8");
				if (!NullUtil.isNull(lineStr)) {
					if (!lineStr.startsWith("#")
							&& lineStr.indexOf(SUFFIX) != -1
							&& lineStr.indexOf(PLACEHOLDER) == -1) {// 如果key以pwd结尾并且未加密
						flag = true;
						String key = lineStr.substring(0, lineStr.indexOf("="));// 取key
						String value = lineStr
								.substring(lineStr.indexOf("=") + 1);// 取value
						value = PLACEHOLDER
								+ DESWithCMD.getInstance().encrypt(value);// 加密value
						writer.write(key + "=" + value);// 写入加密后的行
					} else {
						writer.write(lineStr);// 归原文档行写入缓冲区
					}
				}
				writer.newLine();
			}

			// 2.写入
			writer.flush();
			
			// 3.替换
			if (flag) {
				IOUtil.copyFile(IOUtil.getFileWithPath(newFilePath),
						IOUtil.getFileWithPath(path));
				log.debug("配置文件:" + path + " 加密并存储完成!");
			} else {
				log.debug("配置文件无明文数据,无须再次加密存储!");
			}
		}finally{
			if(writer!=null){
				writer.close();
				writer = null;
			}
			IOUtil.getFileWithPath(newFilePath).delete();
			if(reader!=null){
				reader.close();
				reader = null;
			}
		}
	}

	/*
	 * 对配置文件敏感数据进行安全处理
	 */
	@SuppressWarnings("rawtypes")
	private static Properties loadAndDecrypt(String path) throws IOException {
		log.debug("配置文件:" + path + " 开始加载...");
		InputStream in = IOUtil.getInputStream(path);
		Properties prop = null;
		try {
			prop = new Properties();
			prop.load(in);
			Enumeration e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = e.nextElement().toString();
				String value = null;
				if (key.endsWith(SUFFIX)) {
					value = prop.getProperty(key);
					// 判断value是否以{DES}开头
					if (!NullUtil.isNull(value)
							&& value.startsWith(PLACEHOLDER)) {
						value = DESWithCMD.getInstance().decrypt(
								value.substring(PLACEHOLDER.length()));
						prop.setProperty(key, value);
					}
				}
			}// end while
			log.debug("配置文件:" + path + " 加载完成!");
		} finally {
			if (in != null) {
				in.close();
				in = null;
			}
		}

		return prop;
	}

	/**
	 * @see java.util.Properties#getProperty(String key)
	 * @throws NullPointerException
	 *             properties为空时返回
	 */
	public static String getProperty(String key) {
		return getProperties().getProperty(key);
	}

	/**
	 * @see java.util.Properties#getProperty(String key, String defaultValue)
	 * @throws NullPointerException
	 *             properties为空时返回
	 */
	public static String getProperty(String key, String defaultValue) {
		return getProperties().getProperty(key, defaultValue);
	}

	/**
	 * 刷新线程
	 */
	private static class RefreshThread implements Runnable {

		public void run() {
			while (true) {
				try {
					loadConfig();
					Thread.sleep(refTime);
				} catch (InterruptedException e) {
					log.error("Thread sleep error!", e);
				} catch (IOException e) {
					log.error("load config error!", e);
				}
			}
		}

	}
}
