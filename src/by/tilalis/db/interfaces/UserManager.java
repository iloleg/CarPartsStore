package by.tilalis.db.interfaces;

import javax.ejb.Local;

import by.tilalis.db.records.User;

@Local
public interface UserManager {
	User getUser(String username);

	void addUser(User inserted);

	void deleteUser(User deleted);

	void deleteUserById(int id);
}
