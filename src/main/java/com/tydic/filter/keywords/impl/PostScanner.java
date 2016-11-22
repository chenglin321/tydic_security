package com.tydic.filter.keywords.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.tydic.filter.keywords.Scanner;
import com.tydic.util.NullUtil;

/***
 * 对post请求进行关键字过滤验证
 * 
 */
public class PostScanner extends Scanner {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean scan(HttpServletRequest request) {
		Map requestParams = request.getParameterMap();
		Set postData = requestParams.entrySet();
		Iterator iter = postData.iterator();
		while (!NullUtil.isNull(iter) && iter.hasNext()) {
			Map.Entry<String,String[]> entry = (Entry<String, String[]>) iter.next();
			String key = entry.getKey();
			String[] values = entry.getValue();
			if(log.isDebugEnabled()){
				log.debug("The Post Scanner key:["+key+"]");
			}
			for(String value:values){
				if (!NullUtil.isNull(value) && findKeyWords(value)) {
					warnLog(request,value);
					return Boolean.TRUE;
				}
			}
		}

		return Boolean.FALSE;
	}

}
