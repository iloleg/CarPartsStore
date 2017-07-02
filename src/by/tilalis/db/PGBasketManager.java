package by.tilalis.db;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;

import by.tilalis.db.entities.Order;
import by.tilalis.db.interfaces.BasketManager;

@Stateful(name="BasketManagerBean")
public class PGBasketManager implements BasketManager {
	private List<Order> busket;
	
	public PGBasketManager() {
		this.busket = new LinkedList<>();
	}

	@Override
	public List<Order> getBusket() {
		return busket;
	}

	@Override
	public void addOrderToBusket(Order inserted) {
		busket.add(inserted);
	}

	@Override
	public void deleteOrderFromBusket(Order deleted) {
		busket.remove(deleted);
	}
	
	@Override
	public void clear() {
		busket.clear();
	}
}
