package by.tilalis.db.interfaces;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.records.OrderRecord;

@Local
public interface OrderManager {
	List<OrderRecord> getOrders(int linesPerPage, int numberOfPage);
	
	void deleteOrder(OrderRecord deleted) throws SQLException;
	
	void sendOrders(List<OrderRecord> basket) throws SQLException;
}
