package by.tilalis.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;

import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import by.tilalis.db.interfaces.UserManager;

public class PGUserManager implements UserManager {
	private static Connection connection;
	private static Statement statement;
	private static Document document;
	private static XPath xpath;

	public PGUserManager(final InputStream file) {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			XPathFactory pathFactory = XPathFactory.newInstance();

			xpath = pathFactory.newXPath();
			document = documentBuilder.parse(file);

			connection = DatabaseConnectionManager.getInstance().getConnection();
			statement = connection.createStatement();
		} catch (SQLException | NamingException | ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
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
		System.out.println(insertQuery);
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void deleteUser(final UserRecord deleted) throws SQLException {
		statement.executeUpdate(MessageFormat.format(getQuery("DeleteByUsername"), deleted.getUsername()));
	}

	@Override
	public void deleteUserById(final UserRecord deleted) throws SQLException {
		final int deletedCount = statement.executeUpdate(MessageFormat.format(getQuery("DeleteById"), Integer.toString(deleted.getId())));
		System.out.println(getQuery("DeleteById"));
		System.out.println(deletedCount);
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
