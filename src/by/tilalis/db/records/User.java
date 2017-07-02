package by.tilalis.db.records;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import by.tilalis.utils.SHA256;

@Entity
@Table(name = "users")
@NamedQueries({
	@NamedQuery(name="User.findByUsername", query= "SELECT u FROM User u WHERE u.username = :username"),
	@NamedQuery(name="User.getAll", query = "SELECT u FROM User u")
})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String hash;
	
	@ManyToOne
	@JoinColumn(name="role")
	private Role role;

	public User() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String password) {
		this.hash = new SHA256(password).toString();
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
