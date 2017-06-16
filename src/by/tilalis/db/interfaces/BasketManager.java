package by.tilalis.db.interfaces;

import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.records.OrderRecord;

@Local
public interface BasketManager {
	List<OrderRecord> getBusket();
	
	void addOrderToBusket(OrderRecord inserted);
	
	void deleteOrderFromBusket(OrderRecord deleted);
	
	void clear();
}
