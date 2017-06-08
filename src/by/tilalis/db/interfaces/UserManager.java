package by.tilalis.db.interfaces;

import java.sql.SQLException;

import by.tilalis.db.UserRecord;

public interface UserManager {
	UserRecord getUser(String username);
	
	void addUser(UserRecord inserted) throws SQLException;

	void deleteUser(UserRecord deleted) throws SQLException;
	
	void deleteUserById(UserRecord deleted) throws SQLException;
}
