package by.tilalis.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.naming.NamingException;

import by.tilalis.db.interfaces.UserManager;

public class PGUserManager implements UserManager {
	private static PGUserManager instance;
	private static Connection connection;
	private static Statement statement;

	private PGUserManager() {
		try {
			connection = DatabaseConnectionManager.getInstance().getConnection();
			statement = connection.createStatement();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}

	public static PGUserManager getInstance() {
		if (instance != null) {
			return instance;
		}
		return new PGUserManager();
	}

	@Override
	public void addUser(final UserRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				"INSERT INTO users (username, hash, role) VALUES (''{0}'', ''{1}'', (SELECT id FROM \"userRoles\" WHERE name = ''{2}''))", 
				inserted.getUsername(), inserted.getHash(), inserted.getRole()
		);
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void deleteUser(final UserRecord deleted) throws SQLException {
		statement.executeUpdate(String.format("DELETE FROM users WHERE username = '%s'", deleted.getUsername()));
	}

	@Override
	public void deleteUserById(final UserRecord deleted) throws SQLException {
		if (statement.executeUpdate(String.format(
				"DELETE FROM users WHERE id = '%s' AND role = (SELECT id FROM \"userRoles\" WHERE name = 'User')",
				deleted.getId())) == 0) {
			throw new SQLException();
		}
	}

	@Override
	public UserRecord getUser(String username) {
		final String query = MessageFormat.format(
				"SELECT users.id, username, hash, name AS role FROM users INNER JOIN \"userRoles\" ON role = \"userRoles\".id WHERE username = ''{0}''", 
				username
		);
		try (ResultSet rs = statement.executeQuery(query)) {
			if (rs.next()) {
				return new UserRecord(rs.getInt("id"), rs.getString("username"), rs.getString("hash"), rs.getString("role"), false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
