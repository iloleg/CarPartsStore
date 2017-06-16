package by.tilalis.db.records;

import by.tilalis.db.interfaces.Record;

public class OrderRecord implements Record {
	private int id;
	private UserRecord user;
	private DataRecord data;
	
	public OrderRecord(final int id, final UserRecord user, final DataRecord data) {
		this.id = id;
		this.user = user;
		this.data = data;
	}
	
	public OrderRecord(final int id) {
		this.id = id;
	}
	
	public OrderRecord() {
		this.id = -1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public UserRecord getUser() {
		return user;
	}
	
	public void setUser(UserRecord user) {
		this.user = user;
	}
	
	public DataRecord getData() {
		return data;
	}
	
	public void setData(DataRecord data) {
		this.data = data;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != this.getClass()) {
			return false;
		}
		final OrderRecord record = (OrderRecord) other;
		return record.id == this.id;
	}
}
