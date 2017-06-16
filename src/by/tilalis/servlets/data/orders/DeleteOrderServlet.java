package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.records.OrderRecord;

@WebServlet("/delete_order")
public class DeleteOrderServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final PrintWriter writer = response.getWriter();
		final String deletedJson = request.getParameter("deleted");
		
		try {
			final OrderRecord deleted = mapper.readValue(deletedJson, OrderRecord.class);
			orderManager.deleteOrder(deleted);
			writer.write("{\"status\": \"success\"}");
		} catch (NumberFormatException | SQLException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
