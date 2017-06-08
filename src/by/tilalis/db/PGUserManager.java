package by.tilalis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import by.tilalis.db.interfaces.UserManager;
import by.tilalis.utils.SHA256;

public class PGUserManager implements UserManager {
	private static PGUserManager instance;
	private static Connection connection;
	private static Statement statement;

	private PGUserManager() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CPS", "postgres", "290197");
			statement = connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
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
	public String getUserPasswordHash(String username) {
		return singleFieldQuery("hash", "users", String.format("username = \'%s\'", username));
	}

	@Override
	public String getUserRole(String username) {
		return singleFieldQuery("name", "userRoles",
				String.format("id = (SELECT role FROM users WHERE username = \'%s\')", username));
	}

	@Override
	public int getUserId(String username) {
		return Integer.valueOf(singleFieldQuery("id", "users", String.format("username = \'%s\'", username)));
	}

	@Override
	public boolean isRole(final String username, final String roleName) {
		try (ResultSet rs = statement.executeQuery(String.format(
				"SELECT id FROM users WHERE username = '%s' AND role = (SELECT id FROM \"userRoles\" WHERE name = '%s')",
				username, roleName))) {
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String singleFieldQuery(final String fieldName, final String tableName, final String whereClause) {
		final String query = String.format("SELECT %s FROM \"%s\" WHERE %s", fieldName, tableName, whereClause);
		System.out.println(query);
		try (ResultSet rs = statement.executeQuery(query)) {
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new IllegalArgumentException();
	}

	@Override
	public void addUser(String username, String password, String role) throws SQLException {
		statement.executeUpdate(String.format(
				"INSERT INTO users (username, hash, role) VALUES ('%s', '%s', (SELECT id FROM \"userRoles\" WHERE name = '%s'))",
				username, new SHA256(password).toString(), role));

	}

	@Override
	public void deleteUser(String username) throws SQLException {
		statement.executeUpdate(String.format("DELETE FROM users WHERE username = '%s'", username));
	}

	@Override
	public void deleteUserById(String id) throws SQLException {
		if (statement.executeUpdate(String.format(
				"DELETE FROM users WHERE id = '%s' AND role = (SELECT id FROM \"userRoles\" WHERE name = 'User')",
				id)) == 0) {
			throw new SQLException();
		}
	}
}
