package com.tydic.filter.keywords.impl;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.tydic.filter.keywords.Scanner;
import com.tydic.util.NullUtil;

/**
 * 扫描请求报头信息 
 */
public class HeaderScanner extends Scanner {
	
	@SuppressWarnings("rawtypes")
	@Override
	public boolean scan(HttpServletRequest request) {
		Enumeration headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			/** 每一个报头*/
			String headerName = (String)headerNames.nextElement();
			String value = request.getHeader(headerName);
			if (!NullUtil.isNull(value) && findKeyWords(value)) {
				warnLog(request,value);
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}

}
