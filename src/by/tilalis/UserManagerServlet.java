package by.tilalis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tilalis.db.PGDatabaseManager;
import by.tilalis.db.UserManager;
import by.tilalis.utils.SHA256;

@WebServlet({ "/login", "/logout", "/register", "/delete_user"})
public class UserManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static enum Paths {
		LOGIN, 
		LOGOUT, 
		REGISTER, 
		DELETE_USER;
		
		public static Paths fromString(final String path) {
			System.out.println("CALLED WITH PATH:" + path + " AND " + path.substring(1).toUpperCase());
			return Paths.valueOf(path.substring(1).toUpperCase());
		}
	}

	private static final UserManager userManager = PGDatabaseManager.getInstance();

	public UserManagerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		final Paths path = Paths.fromString(request.getServletPath());

		switch (path) {
		case LOGIN:
			login(request.getParameter("username"), request.getParameter("password"), request.getSession());
			break;
		case LOGOUT:
			logout(request.getSession());
			break;
		case REGISTER:
			register(request.getParameter("username"), request.getParameter("password"), request.getSession());
			break;
		case DELETE_USER:
			deleteUser(request.getParameter("id"), response.getWriter());
			break;
		default:
			break;
		}

		switch (path) {
		case LOGIN:
		case LOGOUT:
		case REGISTER:
			response.sendRedirect(request.getContextPath() + "/");
			break;
		default:
			break;
		}
		
	}

	private void login(final String username, final String password, final HttpSession session) {
		if (username == null || password == null) {
			throw new IllegalArgumentException(username + " " + password);
		}

		session.removeAttribute("badLogin");

		try {
			if (new SHA256(password).toString().equals(userManager.getUserPasswordHash(username))) {
				session.setAttribute("username", username);
				session.setAttribute("role", userManager.getUserRole(username));
			} else {
				session.setAttribute("badLogin", "password");
			}
		} catch (IllegalArgumentException iae) {
			session.setAttribute("badLogin", "username");
		}
	}

	private void logout(final HttpSession session) {
		session.removeAttribute("username");
	}
	
	private void deleteUser(final String id, final PrintWriter writer) {
		try {
			userManager.deleteUserById(id);
			writer.write("{\"status\": \"success\"}");
		} catch (SQLException e) {
			writer.write("{\"status\": \"fail\"}");
		}
	}
	
	
	private void register(final String username, final String password, final HttpSession session) {
		try {
			userManager.addUser(username, password, "User");
			session.setAttribute("registration", "success");
		} catch (SQLException e) {
			session.setAttribute("registration", "fail");
		}
	}
}
