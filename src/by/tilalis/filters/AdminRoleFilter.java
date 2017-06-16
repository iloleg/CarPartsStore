package by.tilalis.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tilalis.db.records.UserRecord;

@WebFilter({
	"/add_record", 
	"/delete_record",
	"/update_record", 
	"/untrash_record",
	"/add_brand", 
	"/add_category", 
	"/delete_order",
	"/delete_user",
	"/get_users", 
	"/manage", 
	"/add", 
	"/trash", 
	"/orders",
	"/get_orders",
	"/get_trash"
})
public class AdminRoleFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest req = (HttpServletRequest) request;
		final HttpServletResponse resp = (HttpServletResponse) response;
		final HttpSession session = req.getSession();
		final UserRecord user = (UserRecord) session.getAttribute("user");
		
		if (user != null && user.getRole().equals("Administrator")) {
			chain.doFilter(request, response);
		} else {
			resp.sendRedirect(req.getContextPath() + "/signin");
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}
