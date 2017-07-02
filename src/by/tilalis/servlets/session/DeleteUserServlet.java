package by.tilalis.servlets.session;

import java.io.IOException;
import java.io.PrintWriter;

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
			userManager.deleteUserById(Integer.valueOf(id));
			writer.write("{\"status\": \"success\"}");
		} catch (Exception e) {
			e.printStackTrace();
			writer.write("{\"status\": \"fail\"}");
		}
	}

}
