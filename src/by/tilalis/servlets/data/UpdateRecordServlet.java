package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/update_record")
public class UpdateRecordServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		final String success = "{\"status\": \"success\"}";
		final String fail    = "{\"status\": \"fail\"}";
		
		final String[] fields = request.getParameterValues("fields[]");
		final String[] values = request.getParameterValues("values[]");
		try {
			if (dataManager.editRecord(Integer.valueOf(request.getParameter("id")), fields, values)) {
				writer.write(success);
			} else {
				writer.write(fail);
			}
			
		} catch (NumberFormatException | SQLException e) {
			writer.write(fail);
		}
	}
}
