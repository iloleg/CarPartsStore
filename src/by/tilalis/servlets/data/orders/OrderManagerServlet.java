package by.tilalis.servlets.data.orders;

import javax.ejb.EJB;
import javax.ejb.NoSuchEJBException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.interfaces.OrderManager;

public abstract class OrderManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static BasketManager basketManager;
	
	@EJB
	protected OrderManager orderManager;
	protected ObjectMapper mapper;

	@Override
	public void init() {
		this.mapper = new ObjectMapper();
	}

	protected BasketManager getBasketManager(final HttpSession session) {
		BasketManager basketManager = (BasketManager) session.getAttribute("basketManager");
		if (basketManager == null) {
			basketManager = getNewBasketManager();
			session.setAttribute("basketManager", basketManager);
		}
		OrderManagerServlet.basketManager = basketManager;
		return basketManager;
	}

	private BasketManager getNewBasketManager() {
		BasketManager basket = null;
		try {
			basket = (BasketManager) new InitialContext().lookup("java:module/BasketManagerBean");
		} catch (NoSuchEJBException | NamingException e) {
			e.printStackTrace();
		}
		return basket;
	}
}
