package com.tydic.filter.keywords.impl;

import javax.servlet.http.HttpServletRequest;

import com.tydic.filter.keywords.Scanner;
import com.tydic.util.NullUtil;

/**
 * 对get请求进行关键字过滤验证
 * 
 */
public class GetScanner extends Scanner {

	@Override
	public boolean scan(HttpServletRequest request) {
		String value = request.getQueryString();
		if (!NullUtil.isNull(value) && findKeyWords(value)) {
			warnLog(request,value);
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

}
