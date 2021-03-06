package by.tilalis;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.servlets.data.DataManagerServlet;

@WebServlet("/")
public class RootServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final String path = request.getServletPath();
		final ServletContext context = request.getServletContext();

		if (path.equals("/registration")) {
			context.getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
		} else if (path.equals("/signin")) {
			context.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
		} else {
			request.setAttribute("dataManager", dataManager);
			request.setAttribute("content", request.getServletPath());;
			context.getRequestDispatcher("/WEB-INF/main.jsp?content=/add").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
