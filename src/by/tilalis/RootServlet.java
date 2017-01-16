package by.tilalis;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RootServlet
 */
@WebServlet("/")
public class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RootServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		final String username = (String) session.getAttribute("username");

		if (username == null) {
			getServletContext().getRequestDispatcher("/WEB-INF/singin.jsp").forward(request, response);
		} else {
			response.getWriter().append("LOGINED! Hello, " + username + ". Served at: ")
					.append(request.getContextPath());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
