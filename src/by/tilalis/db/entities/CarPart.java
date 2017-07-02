package by.tilalis.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "parts")
@NamedQueries({
	@NamedQuery(name = "CarPart.getParts", query = "SELECT part FROM CarPart part WHERE part.trash = false ORDER BY part.id"),
	@NamedQuery(name = "CarPart.getTrash", query = "SELECT part FROM CarPart part WHERE part.trash = true ORDER BY part.id")
})
public class CarPart  {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "factory_id")
	private int factoryId;
	
	@ManyToOne
	@JoinColumn(name = "brand")
	private Brand brand;
	
	@ManyToOne
	@JoinColumn(name = "category")
	private Category category;
	private String model;
	private double price;
	private boolean trash;
	
	public CarPart() {
		super();
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public boolean isTrash() {
		return trash;
	}

	public void setTrash(boolean trash) {
		this.trash = trash;
	}

}
