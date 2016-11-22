package com.tydic.filter.keywords;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tydic.util.RegexUtil;
import com.tydic.util.RequestUtil;

/**
 * 内容扫描器,对请求内容进行扫描
 * 
 * 可对Bad KeyWords、XSS、SQLInjection...进行扫描
 * @author leo
 *
 */
public abstract class Scanner {
	
	protected Log log = LogFactory.getLog(this.getClass());
	
	/**开关标志*/
	private boolean flag;
	
	/** 配置文件信息*/
	private String seed;
	
	/**
	 * 扫描request信息，判断是否有敏感信息
	 * 
	 * @param request http request
	 * @return 有非法关键字返回true，否则返回false
	 */
	public abstract boolean scan(HttpServletRequest request);
	
	/**
	 * 记录扫描到的非法关键字信息
	 * 
	 * @param request http request
	 * @param key 非法关键字
	 */
	protected void warnLog(HttpServletRequest request,String key){
		StringBuilder s = new StringBuilder();
		s.append("Scanner:");
		s.append(this.getClass());
		s.append(" ");
		s.append("非法关键字:");
		s.append(key);
		s.append(" ");
		s.append("请求路径:");
		s.append(RequestUtil.getRequestFullPath(request));
		s.append(" ");
		s.append("来源地址:");
		s.append(RequestUtil.getClientIP(request));
		log.warn(s.toString());
	}
	
	/***
	 * 查找是否有非法关键字
	 * 
	 * @param value
	 *      被匹配的字符
	 * @return 有则返回true,否则返回false
	 */
	public boolean findKeyWords(final String value) {
		Boolean findIt = RegexUtil.findIgnoreCase(getSeed(), value);
		if(log.isDebugEnabled()){
			log.debug("Scanner:"+this.getClass()+" value:["+value+"] findIt:["+findIt+"]");
		}
		return findIt;
	}

	protected boolean isFlag() {
		return flag;
	}

	protected void setFlag(boolean flag) {
		this.flag = flag;
	}

	protected String getSeed() {
		return seed;
	}

	protected void setSeed(String seed) {
		this.seed = seed;
	}
	
}
