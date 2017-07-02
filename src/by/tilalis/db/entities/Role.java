package by.tilalis.db.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	public Role() {
		super();
	}
	
	public void setId(final int id) {
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setName(final String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
}
