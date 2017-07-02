package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.records.CarPart;

@WebServlet("/delete_record")
public class DeleteRecordServlet extends DataManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		final String deletedJson = request.getParameter("deleted");
		
		try {
			final CarPart deleted = mapper.readValue(deletedJson, CarPart.class);
			dataManager.deleteRecord(deleted);
			writer.write("{\"status\": \"success\"}");
		} catch (Exception e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
