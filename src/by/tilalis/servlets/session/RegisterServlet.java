package by.tilalis.servlets.session;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends SessionServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final HttpSession session = request.getSession();
		
		try {
			userManager.addUser(username, password, "User");
			session.setAttribute("registration", "success");
		} catch (SQLException e) {
			session.setAttribute("registration", "fail");
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}
}
