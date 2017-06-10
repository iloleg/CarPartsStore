package by.tilalis.filters;

import java.io.IOException;
import java.util.stream.Stream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class UserRoleFilter implements Filter {
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;
		final HttpSession session = req.getSession();
		
		final String servletPath = req.getServletPath();
		final String path = req.getRequestURI();
		
		final String username = (String) session.getAttribute("username");
		final Stream<String> allowedPaths = Stream.of("/login", "/signin", "/register", "/registration");
		
		if (username != null || allowedPaths.anyMatch(servletPath::equals) || path.contains("static")) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/signin");
		}
	}
}
