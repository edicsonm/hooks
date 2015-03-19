import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SampleFilter implements Filter {
	@Override
	public void destroy() {
		System.out.println("Called SampleFilter.destroy()");
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		String uri = (String) servletRequest.getAttribute(WebKeys.INVOKER_FILTER_URI);
//		System.out.println("Called SampleFilter.doFilter(" + servletRequest
//				+ ", " + servletResponse + ", " + filterChain + ") for URI "
//				+ uri);
		System.out.println(" 111 *****************************************");
//		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(WebKeys.THEME_DISPLAY);
//        Long currentuser = themeDisplay.getUserId();
//        System.out.println("currentuser. " + currentuser);
//		User user = themeDisplay.getUser();
		HttpServletRequest request =    (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		System.out.println("request.getContextPath(): " + request.getContextPath());
		System.out.println("request.getServletPath(): " + request.getServletPath());
		System.out.println("request.getServletContext(): " + request.getServletContext());
		System.out.println("response.getHeaders(\"Location\"): " + response.getHeaders("Location"));
		System.out.println(" 222 *****************************************");

		
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) {
		System.out.println("Called SampleFilter.init(" + filterConfig + ") where hello=" + filterConfig.getInitParameter("hello"));
	}
}