package com.tydic.security;

import com.tydic.util.ConfigListener;
import com.tydic.util.ConfigUtil;

/**
 * 弱口令配置信息监听器
 * 
 * @author leo
 * 
 */
public class WeakPasswordsConfigListener extends ConfigListener {

	private static final String WEAK_PASSWORDS_SEED = "weak.passwords.seed";

	/** 最大出现次数key */
	private static final String MAX_OCCURRENCE_NUMBER = "max.occurrence.number";

	/** 最大连续出现次数key */
	private static final String MAX_CONTINUOUS_OCCURRENCE_NUMBER = "max.continuous.occurrence.number";

	/** 最大字符出现次数 */
	private static int maxOccurrenceNumber = 3;

	/** 最大连续出现的字符数 */
	private static int maxContinuousOccurrenceNumber = 1;

	/** 最大值 */
	public static int MAX = 9;
	/** 最小值 */
	public static int MIN = 0;

	/** 弱口令检测规则 正则表达式 */
	private static String seed = null;

	@Override
	public void refresh() {
		log.debug("@@weak passwords config load info...");
		seed = ConfigUtil.getProperty(WEAK_PASSWORDS_SEED);
		maxOccurrenceNumber = Integer.parseInt(ConfigUtil.getProperty(
				MAX_OCCURRENCE_NUMBER, "3"));
		maxContinuousOccurrenceNumber = Integer.parseInt(ConfigUtil
				.getProperty(MAX_CONTINUOUS_OCCURRENCE_NUMBER, "1"));
		if (maxOccurrenceNumber < MIN || maxOccurrenceNumber > MAX) {
			maxOccurrenceNumber = 3;
		}
		if (maxContinuousOccurrenceNumber < MIN
				|| maxContinuousOccurrenceNumber > MAX) {
			maxContinuousOccurrenceNumber = 1;
		}
	}

	public static int getMaxOccurrenceNumber() {
		return maxOccurrenceNumber;
	}

	public static int getMaxContinuousOccurrenceNumber() {
		return maxContinuousOccurrenceNumber;
	}

	public static String getSeed() {
		return seed;
	}

}
