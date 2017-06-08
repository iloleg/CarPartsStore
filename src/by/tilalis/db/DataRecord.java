package by.tilalis.db;

import by.tilalis.db.interfaces.Record;

public class DataRecord implements Record {
	private int id;
	private int factoryId;
	private String brand;
	private String model;
	private double price;
	
	public DataRecord() {
		this.id = -1;
	}
	
	public DataRecord(final int id) {
		this.id = id;
	}
	
	public DataRecord(final int id, final int factory_id, final String brand, final String model, final double price) {
		this.id = id;
		this.factoryId = factory_id;
		this.brand = brand;
		this.model = model;
		this.price = price;
	}
	
	public DataRecord(final int factory_id, final String brand, final String model, final double price) {
		this(-1, factory_id, brand, model, price);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(int factory_id) {
		this.factoryId = factory_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

}
