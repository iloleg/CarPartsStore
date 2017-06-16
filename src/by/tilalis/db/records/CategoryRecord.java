package by.tilalis.db.records;

import by.tilalis.db.interfaces.Record;

public class CategoryRecord implements Record {
	private int id;
	private String name;
	
	public CategoryRecord() {
		this.id = -1;
	}
	
	public CategoryRecord(final int id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	public CategoryRecord(final String name) {
		this(-1, name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
