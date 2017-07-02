package by.tilalis.db.interfaces;

import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.entities.Order;

@Local
public interface BasketManager {
	List<Order> getBusket();
	
	void addOrderToBusket(Order inserted);
	
	void deleteOrderFromBusket(Order deleted);
	
	void clear();
}
