package by.tilalis.db;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateful;

import by.tilalis.db.interfaces.BasketManager;
import by.tilalis.db.records.OrderRecord;

@Stateful(name="BasketManagerBean")
public class PGBasketManager implements BasketManager {
	private List<OrderRecord> busket;
	
	public PGBasketManager() {
		this.busket = new LinkedList<>();
	}

	@Override
	public List<OrderRecord> getBusket() {
		return busket;
	}

	@Override
	public void addOrderToBusket(OrderRecord inserted) {
		busket.add(inserted);
	}

	@Override
	public void deleteOrderFromBusket(OrderRecord deleted) {
		busket.remove(deleted);
	}
	
	@Override
	public void clear() {
		busket.clear();
	}
}
