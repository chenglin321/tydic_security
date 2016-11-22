package com.tydic.security;

/**
 * 弱口令接口，定义弱口令等相关行为
 * @author leo
 *
 */
public interface WeakPasswords {
	/**
	 * 检查弱口令字典表或基本正则要求
	 * 
	 * @param pwd
	 *            需要检查的口令
	 * @return 是则返回true，否则返回false
	 */
	public boolean isWeakPasswords(String pwd);
	
	/**
	 * 检查是否超过最大出现次数范围限制
	 * <p>
	 * 口令中允许同一字符出现的最大次数可配置，取值范围：0-9，0为不限制，默认为 3。 适用于：操作员帐户、程序帐号、最终用户帐户的口令。 说明：如果采用默认值
	 * 3，表示任何一个字符 在同一个口令中最多出现 3 次（如：AbAbAcAc，A 出现了四次，不满足要求）。
	 * </p>
	 * 
	 * @param pwd
	 *            要检查的口令
	 * @return 超过限制则返回true,否则返回false
	 * @throws NullPointerException
	 *             当pwd为null时
	 */
	public boolean isOvertopOccurrenceNumber(String pwd);
	
	/**
	 * 检查是否超过连续出现次数范围限制
	 * <p>
	 * 口令中允许同一字符连续出现的最大次数可配置，取值范围：0-9，0为不限制，默认为 1。 适用于：操作员帐户、程序帐号、最终用户帐户的口令。 如果采用默认值
	 * 1，表示口令中相邻的字符都不应该相同（如 aabcdef，aa 连续出现了 2 次，不满足要求）。
	 * </p>
	 * @param pwd
	 *            要检查的口令
	 * @return 超过限制则返回true,否则返回false
	 * @throws NullPointerException 当pwd为null时
	 */
	public boolean isOvertopContinuousOccurrenceNumber(String pwd) ;
}
