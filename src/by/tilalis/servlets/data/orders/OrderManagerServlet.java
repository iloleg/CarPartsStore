package by.tilalis.servlets.data.orders;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import by.tilalis.db.interfaces.OrderManager;

public abstract class OrderManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	protected OrderManager orderManager;
	protected ObjectMapper mapper;

	@Override
	public void init() {
		this.mapper = new ObjectMapper();
	}
}
