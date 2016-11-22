package com.tydic.filter.keywords;

import javax.servlet.http.HttpServletRequest;

public class ScannerHelper extends Scanner{
	/**
	 * 判断是否有敏感关键字
	 * 
	 * @param request
	 *            http request
	 * @return 有非法关键字返回true，否则返回false
	 */
	public boolean scan(HttpServletRequest request) {
		if (ScannerConfigListener.getScanners() == null || ScannerConfigListener.getScanners().size() == 0) {
			log.warn("无任何可用的扫描器...");
			return Boolean.FALSE;
		}
		/** 遍历各种维度的关键字过滤 */
		for (Scanner sca : ScannerConfigListener.getScanners()) {
			if (!sca.isFlag()) {
				log.debug("Scanner:" + sca.getClass() + " 未开启!");
				continue;
			}
			if (sca.scan(request)) {
				return Boolean.TRUE;
			} else {
				log.debug("##pass the Scanner:" + sca.getClass() + " check.##");
			}
		}

		return Boolean.FALSE;
	}
}
