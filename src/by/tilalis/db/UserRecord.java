package by.tilalis.db;

import by.tilalis.db.interfaces.Record;
import by.tilalis.utils.SHA256;

public class UserRecord implements Record {
	private int id;
	private String username;
	private String hash;
	private String role;

	public UserRecord(final int id, final String username, final String password, final String role, boolean calculateHash) {
		this.id = id;
		this.username = username;
		if (calculateHash) {
			this.hash = new SHA256(password).toString();
		} else {
			this.hash = password;
		}
		this.role = role;
	}
	
	public UserRecord(final int id, final String username, final String password, final String role) {
		this(id, username, password, role, true);
	}
	
	public UserRecord(final int id, final String username, final String role) {
		this(id, username, "", role);
	}
	
	public UserRecord(final String username) {
		this(-1, username, "");
	}
	
	public UserRecord(final String username, final String password) {
		this(-1, username, password, "User");
	}
	
	public UserRecord(final int id) {
		this(id, "", "", "User");
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
