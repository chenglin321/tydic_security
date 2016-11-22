package com.tydic.filter.ip;

import com.tydic.util.NullUtil;

/***
 * ip filter 助手 ，实现对黑白名单的拦截逻辑
 * 
 * @author leo
 * 
 */
public class IPFilterHelper implements IPBlackWhiteList {

	public int ipFilter(String ip) {
		if (!NullUtil.isNull(IPConfigListener.getBlackips())
				&& IPConfigListener.getBlackips().contains(ip)) {
			return BLACK_LIST_NOPASS;
		} else if (!NullUtil.isNull(IPConfigListener.getWhiteips())
				&& !IPConfigListener.getWhiteips().contains(ip)) {
			return WHITE_LIST_NOPASS;
		} else {
			return PASS;
		}
	}
}
