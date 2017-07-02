package by.tilalis.servlets.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tilalis.db.entities.User;

@WebServlet("/register")
public class RegisterServlet extends SessionServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final HttpSession session = request.getSession();
		final String username = request.getParameter("username");
		final String password = request.getParameter("password");
		final User inserted = new User();
		
		inserted.setUsername(username);
		inserted.setHash(password);
		
		try {
			userManager.addUser(inserted);
			session.setAttribute("registration", "success");
		} catch (Exception e) {
			session.setAttribute("registration", "fail");
			e.printStackTrace();
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}
}
