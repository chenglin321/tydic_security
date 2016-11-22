package com.tydic.filter.keywords;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tydic.filter.AbstractWebFilter;
import com.tydic.util.NullUtil;

/**
 * 关键字过滤器
 * 
 * @author leo
 * 
 */
public class KeyWordsFilter extends AbstractWebFilter {

	/** 关键字过滤处理类 */
	private Scanner scanner = new ScannerHelper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!scanner.scan((HttpServletRequest) request)) {
			chain.doFilter(request, response);
		} else if (!NullUtil.isNull(getErrorRedirectUrl())) {
			((HttpServletResponse) response)
					.sendRedirect(getErrorRedirectUrl());
		}

	}

}
