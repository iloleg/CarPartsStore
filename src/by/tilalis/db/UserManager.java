package by.tilalis.db;

import java.sql.SQLException;

public interface UserManager {
	String getUserPasswordHash(String username);

	String getUserRole(String username);

	int getUserId(String username);

	boolean isRole(String username, String roleName);

	void addUser(String username, String password, String role) throws SQLException;

	void deleteUser(String username) throws SQLException;
}
