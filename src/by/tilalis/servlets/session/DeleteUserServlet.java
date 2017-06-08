package by.tilalis.servlets.session;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/delete_user")
public class DeleteUserServlet extends SessionServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final String id = request.getParameter("id");
		final PrintWriter writer = response.getWriter();
		
		try {
			userManager.deleteUserById(id);
			writer.write("{\"status\": \"success\"}");
		} catch (SQLException e) {
			writer.write("{\"status\": \"fail\"}");
		}
	}

}
