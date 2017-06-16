package by.tilalis.db.records;

public class BrandRecord {
	private int id;
	private String name;
	
	public BrandRecord(final int id, final String name) {
		this.id = id;
		this.name = name;
	}
	
	public BrandRecord(final String name) {
		this(-1, name);
	}
	
	public BrandRecord() {
		this.id = -1;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
}