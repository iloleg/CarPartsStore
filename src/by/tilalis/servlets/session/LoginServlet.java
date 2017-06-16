package by.tilalis.servlets.session;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tilalis.db.records.UserRecord;
import by.tilalis.utils.SHA256;

@WebServlet("/login")
public class LoginServlet extends SessionServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final HttpSession session = request.getSession();

		if (username == null || password == null) {
			throw new IllegalArgumentException(username + " " + password);
		}

		session.removeAttribute("badLogin");
		final UserRecord user = userManager.getUser(username);
		
		if (user == null) {
			session.setAttribute("badLogin", "username");
		} else if (new SHA256(password).toString().equals(user.getHash())) {
			session.setAttribute("user", user);
		} else {
			session.setAttribute("badLogin", "password");
		}

		response.sendRedirect(request.getContextPath() + "/");
	}

}
