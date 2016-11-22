package com.tydic.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.tydic.util.NullUtil;

/***
 * 抽象的web过滤器(基于servlet filter)
 * @author leo
 *
 */
public abstract class AbstractWebFilter implements Filter {
	
	/**全局的错误重定义标识 */
	public static final String ERROR_REDIRECT_URL = "errorRedirectUrl";
	
	/**全局的错误重定义url*/
	private String errorRedirectUrl;
	
	/** 子filter中配置的错误重定义标识  */
	public static final String REDIRECT_URL = "redirectUrl";

	/**错误或失败的重定义路径 */
	protected String getErrorRedirectUrl() {
		return errorRedirectUrl;
	}

	public void init(FilterConfig config) throws ServletException {
		String redirectUrl = config.getInitParameter(REDIRECT_URL);
		if(!NullUtil.isNull(redirectUrl)){
			errorRedirectUrl = redirectUrl;
		}else{
			errorRedirectUrl = config.getServletContext().getInitParameter(ERROR_REDIRECT_URL);
		}
	}

	public abstract void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException ;

	public void destroy() {
	}

}
