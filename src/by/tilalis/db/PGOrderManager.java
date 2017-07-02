package by.tilalis.db;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import by.tilalis.db.entities.Order;
import by.tilalis.db.interfaces.OrderManager;

@Stateless
public class PGOrderManager extends PGManager implements OrderManager {	
	public PGOrderManager() {
		super();
	}

	@Override
	public List<Order> getOrders(int linesPerPage, int numberOfPage) {
		final TypedQuery<Order> query = em.createNamedQuery("Order.get", Order.class);
		if (linesPerPage > 0) {
			int offset = linesPerPage * numberOfPage;
			query.setFirstResult(offset).setMaxResults(linesPerPage);
		}
		return query.getResultList();
	}

	@Override
	public void deleteOrder(Order deleted) {
		deleted = em.merge(deleted);
		em.remove(deleted);
	}

	@Override
	public void sendOrders(List<Order> basket) {
		for (final Order order : basket) {
			em.persist(order);
		}
	}

}
