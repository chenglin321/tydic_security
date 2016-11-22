package com.tydic.filter.keywords.impl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.tydic.filter.keywords.Scanner;
import com.tydic.util.NullUtil;

/***
 * cookie过滤
 * 
 */
public class CookieScanner extends Scanner {

	@Override
	public boolean scan(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (NullUtil.isNull(cookies)) {
			return Boolean.FALSE;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cook = cookies[i];
			String value = cook.getValue();
			if (!NullUtil.isNull(value) && findKeyWords(value)) {
				warnLog(request,value);
				return Boolean.TRUE;
			}
		}

		return Boolean.FALSE;
	}

}
