package by.tilalis.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter({"/add_record", "/delete_record", "/add_brand", "/update_record", "/get_users", "/manage", "/add"})
public class AdminRoleFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;
		final HttpSession session = req.getSession();
		final String role = (String) session.getAttribute("role");
		
		if (role != null && role.equals("Administrator")) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/signin");
		}
	}
}
