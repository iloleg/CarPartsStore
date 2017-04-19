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
		final String path = request.getServletPath();

		if (username == null) {
			if (path.equals("/registration")) {
				getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
			} else {
				getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);	
			}
		} else {
			System.out.println("/WEB-INF/main.jsp?content=" + request.getServletPath());
			
			getServletContext()
			.getRequestDispatcher("/WEB-INF/main.jsp?content=" + request.getServletPath())
			.forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
