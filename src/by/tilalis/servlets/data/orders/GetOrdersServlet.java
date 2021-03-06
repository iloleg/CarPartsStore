package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.entities.Order;

@WebServlet("/get_orders")
public class GetOrdersServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//final BasketManager orderManager = (BasketManager) getBasketManager(request.getSession());
		
		final PrintWriter writer = response.getWriter();
		int linesPerPage = -1;
		int numberOfPage = 0;
		try {
			linesPerPage = Integer.valueOf(request.getParameter("lines"));
			numberOfPage = Integer.valueOf(request.getParameter("page"));
		} catch (NumberFormatException nfe) {
		}
		
		final List<Order> dataRecords = orderManager.getOrders(linesPerPage, numberOfPage);
		mapper.writeValue(writer, dataRecords);
	}
}
