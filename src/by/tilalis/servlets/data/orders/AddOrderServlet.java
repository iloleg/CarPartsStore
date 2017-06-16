package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.records.OrderRecord;
import by.tilalis.db.records.UserRecord;

@WebServlet("/add_to_basket")
public class AddOrderServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final BasketManager basketManager = (BasketManager) getBasketManager(request.getSession());
		
		final PrintWriter writer = response.getWriter();
		final String insertedJson = request.getParameter("inserted");

		try {
			final OrderRecord inserted = mapper.readValue(insertedJson, OrderRecord.class);
			final UserRecord user = (UserRecord) request.getSession().getAttribute("user");
			inserted.setUser(user);
			basketManager.addOrderToBusket(inserted);
			writer.write("{\"status\": \"success\"}");
		} catch (JsonParseException | JsonMappingException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
