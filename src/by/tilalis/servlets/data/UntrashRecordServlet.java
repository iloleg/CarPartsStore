package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.records.DataRecord;

@WebServlet("/untrash_record")
public class UntrashRecordServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		final String untrashedJson = request.getParameter("untrashed");
		
		try {
			final DataRecord untrashed = mapper.readValue(untrashedJson, DataRecord.class);
			dataManager.untrashRecord(untrashed);
			writer.write("{\"status\": \"success\"}");
		} catch (NumberFormatException | SQLException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
