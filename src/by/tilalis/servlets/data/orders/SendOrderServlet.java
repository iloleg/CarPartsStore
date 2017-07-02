package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.records.CarPart;
import by.tilalis.db.records.Order;

@WebServlet("/send_order")
public class SendOrderServlet extends OrderManagerServlet {
	private static final long serialVersionUID = 1L;

	@Resource
	private ConnectionFactory connectionFactory;

	@Resource(name = "MailSender")
	private Queue queue;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final BasketManager basketManager = (BasketManager) getBasketManager(request.getSession());

		final PrintWriter writer = response.getWriter();
		final String email = request.getParameter("email");

		try {
			List<Order> busket = basketManager.getBusket();
			orderManager.sendOrders(busket);

			final Connection connection = connectionFactory.createConnection();
			connection.start();

			final Session jmsSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer producer = jmsSession.createProducer(queue);

			if (email == null || "".equals(email) || !email.contains("@")) {
				writer.write("{\"status\": \"fail\"}");
				return;
			}

			final StringBuilder messageText = new StringBuilder("Hello! Here are your order details:\n");
			for (final Order record : busket) {
				System.out.println(record.getId());
				final CarPart data = record.getData();
				messageText.append(MessageFormat.format(
						"\n---\nFactory Id: {0}\n" + "Brand: {1}\n"
								+ "Category: {2}\n" + "Model: {3}\n" + "Price: {4}",
						Integer.toString(data.getFactoryId()), data.getBrand().getName(),
						data.getCategory().getName(), data.getModel(), data.getPrice()));

			}
			
			producer.send(jmsSession.createTextMessage(messageText.toString()));
			basketManager.clear();
			writer.write("{\"status\": \"success\"}");
		} catch (Exception e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
