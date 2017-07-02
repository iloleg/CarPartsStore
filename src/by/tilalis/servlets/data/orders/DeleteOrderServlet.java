package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.records.Order;

@WebServlet("/delete_order")
@EJB(name="OrderManagerBean", beanInterface = BasketManager.class)
public class DeleteOrderServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//final BasketManager orderManager = (BasketManager) getOrderManager(request.getSession());
		
		final PrintWriter writer = response.getWriter();
		final String deletedJson = request.getParameter("deleted");
		
		try {
			final Order deleted = mapper.readValue(deletedJson, Order.class);
			orderManager.deleteOrder(deleted);
			writer.write("{\"status\": \"success\"}");
		} catch (Exception e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
