package by.tilalis.servlets.data.orders;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import by.tilalis.db.records.DataRecord;
import by.tilalis.db.records.OrderRecord;
import by.tilalis.db.records.UserRecord;

@WebServlet("/add_order")
public class AddOrderServlet extends OrderManagerServlet {
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
		final PrintWriter writer = response.getWriter();
		final String insertedJson = request.getParameter("inserted");
		final String email = request.getParameter("email");

		try {
			final OrderRecord inserted = mapper.readValue(insertedJson, OrderRecord.class);
			final UserRecord user = (UserRecord) request.getSession().getAttribute("user");
			final DataRecord data = inserted.getData();
			inserted.setUser(user);
			orderManager.addOrder(inserted);

			final Connection connection = connectionFactory.createConnection();
			connection.start();

			final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			final MessageProducer producer = session.createProducer(queue);
			
			if (email == null || "".equals(email) || !email.contains("@")) {
				writer.write("{\"status\": \"fail\"}");
				return;
			}
			
			producer.send(session.createTextMessage(
				MessageFormat.format(
					"Hello, {0}! Here are your order details:\n"
					+ "Factory Id: {1}\n"
					+ "Brand: {2}\n"
					+ "Category: {3}\n"
					+ "Model: {4}\n"
					+ "Price: {5}",
					user.getUsername(), 
					Integer.toString(data.getFactoryId()), 
					data.getBrand().getName(),
					data.getCategory().getName(),
					data.getModel(),
					data.getPrice()
				))
			);

			writer.write("{\"status\": \"success\"}");
		} catch (JsonParseException | JsonMappingException | SQLException | JMSException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}
}
