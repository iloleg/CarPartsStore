package by.tilalis.db.interfaces;

import java.sql.SQLException;

import javax.ejb.Local;

import by.tilalis.db.records.UserRecord;

@Local
public interface UserManager {
	UserRecord getUser(String username);

	void addUser(UserRecord inserted) throws SQLException;

	void deleteUser(UserRecord deleted) throws SQLException;

	void deleteUserById(UserRecord deleted) throws SQLException;
}
