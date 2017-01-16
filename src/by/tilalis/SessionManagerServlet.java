package by.tilalis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tilalis.db.PGDatabaseManager;
import by.tilalis.db.UserManager;
import by.tilalis.utils.SHA256;

/**
 * Servlet implementation class SessionManagerServlet
 */
@WebServlet({ "/login", "/logout" })
public class SessionManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN  = "/login";
	private static final String LOGOUT = "/logout";
	
	private static final UserManager userManager = PGDatabaseManager.getInstance();

    public SessionManagerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String path = request.getServletPath();
		
	    switch (path) {
	      case LOGIN:
	        login(request.getParameter("username"), request.getParameter("password"), request.getSession());
	        break;
	      case LOGOUT:
	        logout(request.getSession());
	        break;
	      default:
	        break;
	    }
	    
	    response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void login(final String username, final String password, final HttpSession session) {
		if (username == null || password == null) {
			throw new IllegalArgumentException(username + " " + password);
		}
		
		session.removeAttribute("badLogin");
		
		try {
			if (new SHA256(password).toString().equals(userManager.getUserPasswordHash(username))) {
				session.setAttribute("username", username);
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
}
