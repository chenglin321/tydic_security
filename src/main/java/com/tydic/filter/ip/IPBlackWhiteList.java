package com.tydic.filter.ip;

/***
 * ip 黑白名单接口
 * @author leo
 *
 */
public interface IPBlackWhiteList {
	/** 黑名单未通过检查 */
	public static int BLACK_LIST_NOPASS = -1;

	/** 白名单未通过检查 */
	public static int WHITE_LIST_NOPASS = 0;

	/** 通过检查 */
	public static int PASS = 1;

	/**
	 * 对ip地址进行黑白名单过滤处理
	 * 
	 * @param ip
	 *            要过滤的ip地址
	 * @return 返回-1,0,1,分别对应黑名单未通过检查，白名单未通过检查，通过检查
	 */
	public int ipFilter(String ip);
}
