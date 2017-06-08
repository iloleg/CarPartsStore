package by.tilalis.servlets.data;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import by.tilalis.db.DataRecord;

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
		
		/*final String[] fields = request.getParameterValues("fields[]");
		final String[] values = request.getParameterValues("values[]");*/
		final String updatedJson = request.getParameter("updated");
		try {
			final DataRecord updated = mapper.readValue(updatedJson, DataRecord.class);
			dataManager.editRecord(updated);
			writer.write(success);
		} catch (JsonParseException | JsonMappingException | SQLException e) {
			writer.write(fail);
			e.printStackTrace();
		}
	}
}
