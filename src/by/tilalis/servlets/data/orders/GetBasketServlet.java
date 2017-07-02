package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.records.Order;

@WebServlet("/get_basket")
public class GetBasketServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		final BasketManager basketManager = (BasketManager) getBasketManager(request.getSession());
		
		final PrintWriter writer = response.getWriter();
		
		final List<Order> dataRecords = basketManager.getBusket();
		mapper.writeValue(writer, dataRecords);
	}
}
