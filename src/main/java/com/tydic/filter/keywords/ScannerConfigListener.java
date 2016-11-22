package com.tydic.filter.keywords;

import java.util.ArrayList;
import java.util.List;

import com.tydic.util.ConfigListener;
import com.tydic.util.ConfigUtil;

/**
 * 关键字扫描器配置监听器
 * 
 * @author leo
 * 
 */
public class ScannerConfigListener extends ConfigListener {

	/** 扫描的渠道类型列表 */
	private final static List<Scanner> scanners = new ArrayList<Scanner>();

	/**
	 * 加载配置信息
	 */
	public void refresh() {
		log.debug("@@keywords config load info...");
		scanners.clear();
		boolean flag = false;
		String seed = null;
		String clazz = null;
		// 1.get scanner
		flag = Boolean.valueOf(ConfigUtil.getProperty(
				KeyWordsConst.GET_SCANNER_SWITCH, "false"));
		seed = ConfigUtil.getProperty(KeyWordsConst.GET_SCANNER_SEED);
		clazz = ConfigUtil.getProperty(KeyWordsConst.GET_SCANNER);
		scanners.add(buildScanner(flag, seed, clazz));

		// 2.post scanner
		flag = Boolean.valueOf(ConfigUtil.getProperty(
				KeyWordsConst.POST_SCANNER_SWITCH, "false"));
		seed = ConfigUtil.getProperty(KeyWordsConst.POST_SCANNER_SEED);
		clazz = ConfigUtil.getProperty(KeyWordsConst.POST_SCANNER);
		scanners.add(buildScanner(flag, seed, clazz));

		// 3.header scanner
		flag = Boolean.valueOf(ConfigUtil.getProperty(
				KeyWordsConst.HEADER_SCANNER_SWITCH, "false"));
		seed = ConfigUtil.getProperty(KeyWordsConst.HEADER_SCANNER_SEED);
		clazz = ConfigUtil.getProperty(KeyWordsConst.HEADER_SCANNER);
		scanners.add(buildScanner(flag, seed, clazz));

		// 4.cookie scanner
		flag = Boolean.valueOf(ConfigUtil.getProperty(
				KeyWordsConst.COOKIE_SCANNER_SWITCH, "false"));
		seed = ConfigUtil.getProperty(KeyWordsConst.COOKIE_SCANNER_SEED);
		clazz = ConfigUtil.getProperty(KeyWordsConst.COOKIE_SCANNER);
		scanners.add(buildScanner(flag, seed, clazz));
	}

	/**
	 * @param flag
	 *            开关
	 * @param seed
	 *            扫描表达式
	 * @param clazz
	 *            类路径
	 * @return 扫描器
	 */
	private Scanner buildScanner(boolean flag, String seed, String clazz) {
		Scanner scanner = null;
		try {
			scanner = (Scanner) Class.forName(clazz).newInstance();
			scanner.setFlag(flag);
			scanner.setSeed(seed);
		} catch (Exception e) {
			throw new RuntimeException("keyWords scanner load error!", e);
		}

		return scanner;
	}

	static List<Scanner> getScanners() {
		return scanners;
	}
}
