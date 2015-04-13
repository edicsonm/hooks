package au.com.billingbuddy.hook;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class RestrictAccess implements Filter {

	@Override
	public void destroy() {}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		String clientIp = servletRequest.getRemoteAddr();
		System.err.println("clientIp: " + clientIp);
		String url = ((HttpServletRequest)servletRequest).getRequestURL().toString();
		String queryString = ((HttpServletRequest)servletRequest).getQueryString();
		System.out.println("url: " + url);
		System.out.println("queryString: " + queryString);
		chain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
