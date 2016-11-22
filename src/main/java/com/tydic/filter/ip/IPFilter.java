package com.tydic.filter.ip;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.tydic.filter.AbstractWebFilter;

/***
 * 系统ip过滤器,过滤顺序为 黑名单 --> 白名单<br/>
 * 1.如果黑名单不为空则检查 黑名单,且请求ip在黑名单中找到则响应错误页面,否则继续执行白名单检查
 * 2.如果白名单不为空则检查白名单,且请求ip不在白名单则响应错误页面,否则正常通过
 * @author leo
 *
 */
public class IPFilter extends AbstractWebFilter implements Filter {
	
	private IPBlackWhiteList ipBlackWhiteList = new IPFilterHelper();

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String ip = request.getRemoteAddr();
		int flag = ipBlackWhiteList.ipFilter(ip);
		if (flag==IPBlackWhiteList.BLACK_LIST_NOPASS) {
			((HttpServletResponse) response)
					.sendRedirect(getErrorRedirectUrl());
		} else if (flag==IPBlackWhiteList.WHITE_LIST_NOPASS) {
			((HttpServletResponse) response)
					.sendRedirect(getErrorRedirectUrl());
		} else {
			chain.doFilter(request, response);
		}
	}
}
