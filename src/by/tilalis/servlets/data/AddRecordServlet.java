package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add_record")
public class AddRecordServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		final String[] fields = request.getParameterValues("fields[]");
		final String[] values = request.getParameterValues("values[]");
		
		try {
			dataManager.addRecord(fields, values);
			writer.write("{\"status\": \"success\"}");
		} catch (SQLException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
