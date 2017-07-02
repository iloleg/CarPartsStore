package by.tilalis.db.interfaces;

import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.records.Order;

@Local
public interface OrderManager {
	List<Order> getOrders(int linesPerPage, int numberOfPage);
	
	void deleteOrder(Order deleted);
	
	void sendOrders(List<Order> basket);
}
