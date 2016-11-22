package com.tydic.security;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.NullUtil;
import com.tydic.util.RegexUtil;

/**
 * 弱口令助手，处理拦截或检查弱口令相关的功能
 * @author leo
 *
 */
public class WeakPasswordsHelper implements WeakPasswords {
	
	private static final Log log = LogFactory.getLog(WeakPasswordsHelper.class);

	/**
	 * 检查弱口令字典表或基本正则要求
	 * 
	 * @param pwd
	 *            需要检查的口令
	 * @return 是则返回true，否则返回false
	 */
	public boolean isWeakPasswords(String pwd) {
		return RegexUtil.check(WeakPasswordsConfigListener.getSeed(), pwd);
	}

	/**
	 * 检查是否超过最大出现次数范围限制
	 * <p>
	 * 口令中允许同一字符出现的最大次数可配置，取值范围：0-9，0为不限制，默认为 3。 适用于：操作员帐户、程序帐号、最终用户帐户的口令。
	 * 说明：如果采用默认值 3，表示任何一个字符 在同一个口令中最多出现 3 次（如：AbAbAcAc，A 出现了四次，不满足要求）。
	 * </p>
	 * 
	 * @param pwd
	 *            要检查的口令
	 * @return 超过限制则返回true,否则返回false
	 * @throws NullPointerException
	 *             当pwd为null时
	 */
	public boolean isOvertopOccurrenceNumber(String pwd) {
		if (WeakPasswordsConfigListener.getMaxOccurrenceNumber() <= WeakPasswordsConfigListener.MIN) {
			return false;
		}
		Map<Character, Integer> d = new HashMap<Character, Integer>();
		for (Character chr : pwd.toCharArray()) {
			Integer times = d.get(chr);
			if (NullUtil.isNull(times)) {
				times = 1;
			} else {
				times++;
			}
			if (times > WeakPasswordsConfigListener.getMaxOccurrenceNumber()) {
				if (log.isDebugEnabled()) {
					log.debug("char:[" + chr
							+ "] overtop the max occurrence number["
							+ WeakPasswordsConfigListener.getMaxOccurrenceNumber() + "]");
				}
				return true;
			}
			d.put(chr, times);
		}

		return false;
	}

	/**
	 * 检查是否超过连续出现次数范围限制
	 * <p>
	 * 口令中允许同一字符连续出现的最大次数可配置，取值范围：0-9，0为不限制，默认为 1。 适用于：操作员帐户、程序帐号、最终用户帐户的口令。
	 * 如果采用默认值 1，表示口令中相邻的字符都不应该相同（如 aabcdef，aa 连续出现了 2 次，不满足要求）。
	 * </p>
	 * 
	 * @param pwd
	 *            要检查的口令
	 * @return 超过限制则返回true,否则返回false
	 * @throws NullPointerException
	 *             当pwd为null时
	 */
	public boolean isOvertopContinuousOccurrenceNumber(String pwd) {
		if (WeakPasswordsConfigListener.getMaxContinuousOccurrenceNumber() <= WeakPasswordsConfigListener.MIN) {
			return false;
		}
		Integer times = 0;// 记数器
		LinkedList<Character> chars = new LinkedList<Character>();// 存放字符列表
		for (Character chr : pwd.toCharArray()) {
			if (chars.size() == 0 || !chr.equals(chars.getLast())) {
				times = 1;
			} else {
				times++;
			}
			if (times > WeakPasswordsConfigListener.getMaxContinuousOccurrenceNumber()) {
				if (log.isDebugEnabled()) {
					log.debug("char:[" + chr
							+ "] overtop the max continuous occurrence number["
							+ WeakPasswordsConfigListener.getMaxContinuousOccurrenceNumber() + "]");
				}
				return true;
			}
			chars.add(chr);
		}

		return false;
	}

}
