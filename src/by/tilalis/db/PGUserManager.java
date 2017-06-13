package by.tilalis.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;

import javax.ejb.Stateless;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import by.tilalis.db.interfaces.UserManager;

@Stateless
public class PGUserManager extends PGManager implements UserManager {
	public PGUserManager() {
		super();
	}

	private String getQuery(final String path) {
		final String base = "//Queries/UserManager/";
		try {
			return (String) xpath.compile(base + path).evaluate(document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void addUser(final UserRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				getQuery("Insert"), 
				inserted.getUsername(), 
				inserted.getHash(),
				inserted.getRole());
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void deleteUser(final UserRecord deleted) throws SQLException {
		statement.executeUpdate(MessageFormat.format(getQuery("DeleteByUsername"), deleted.getUsername()));
	}

	@Override
	public void deleteUserById(final UserRecord deleted) throws SQLException {
		final int deletedCount = statement.executeUpdate(MessageFormat.format(getQuery("DeleteById"), Integer.toString(deleted.getId())));
		if (deletedCount == 0) {
			throw new SQLException();
		}
	}

	@Override
	public UserRecord getUser(String username) {
		final String query = MessageFormat.format(getQuery("GetUser"), username);
		try (ResultSet rs = statement.executeQuery(query)) {
			if (rs.next()) {
				return new UserRecord(rs.getInt("id"), rs.getString("username"), rs.getString("hash"),
						rs.getString("role"), false);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
