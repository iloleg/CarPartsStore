package by.tilalis.db;

import by.tilalis.db.interfaces.Record;

public class DataRecord implements Record {
	private int id;
	private int factoryId;
	private Brand brand;
	private String model;
	private double price;
	
	public static class Brand {
		private int id;
		private String name;
		
		public Brand(final int id, final String name) {
			this.id = id;
			this.name = name;
		}
		
		public Brand(final String name) {
			this(-1, name);
		}
		
		public Brand() {
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
	
	public DataRecord() {
		this.id = -1;
	}
	
	public DataRecord(final int id) {
		this.id = id;
	}
	
	public DataRecord(final int id, final int factory_id, final Brand brand, final String model, final double price) {
		this.id = id;
		this.factoryId = factory_id;
		this.brand = brand;
		this.model = model;
		this.price = price;
	}
	
	public DataRecord(final int factory_id, final Brand brand, final String model, final double price) {
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

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
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
