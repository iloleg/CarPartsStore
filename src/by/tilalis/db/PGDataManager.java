package by.tilalis.db;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

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

import by.tilalis.db.interfaces.DataManager;

public class PGDataManager implements DataManager {
	private static Connection connection;
	private static Statement statement;
	private static Document document;
	private static XPath xpath;

	public PGDataManager(final InputStream file) {
		try {
			DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			XPathFactory pathFactory = XPathFactory.newInstance();
			
	        xpath = pathFactory.newXPath();
            document = documentBuilder.parse(file);
            
			connection = DatabaseConnectionManager.getInstance().getConnection();
			statement = connection.createStatement();
		} catch (SQLException | NamingException | IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
	
	private String getQuery(final String path) {
		final String base = "//Queries/DataManager/";
		try {
			return (String) xpath.compile(base + path).evaluate(document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserRecord> getUsersTable() {
		final ArrayList<UserRecord> users = new ArrayList<>();
		try (ResultSet rs = statement.executeQuery(getQuery("UsersTable"))) {
			while (rs.next()) {
				users.add(new UserRecord(rs.getInt("id"), rs.getString("username"), rs.getString("role")));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<DataRecord> getPage(int linesPerPage, int numberOfPage) {
		return getPage(linesPerPage, numberOfPage, null, null);
	}

	@Override
	public List<DataRecord> getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery) {
		final List<DataRecord> list = new ArrayList<>();
		final String query;
		if (searchField == null || searchQuery == null || "".equals(searchField) || "".equals(searchQuery)) {
			query = MessageFormat.format(
					getQuery("GetPage"), 
					linesPerPage,
					numberOfPage * linesPerPage
			);
		} else {
			query = MessageFormat.format(
					getQuery("GetPageSearch"), 
					searchField, searchQuery, linesPerPage, numberOfPage * linesPerPage
			);
		}

		System.out.println(query);
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(new DataRecord(rs.getInt("id"), rs.getInt("factory_id"), rs.getString("brand"), rs.getString("model"), rs.getDouble("price")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public int getRowsCount() {
		final String query = getQuery("ItemsCount");
		System.out.println(query);
		try (ResultSet rs = statement.executeQuery(query)){
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void editRecord(final DataRecord updated) throws SQLException {
		final String updateQuery = MessageFormat.format(
				getQuery("Update"), 
				Integer.toString(updated.getFactoryId()), 
				updated.getBrand(), 
				updated.getModel(), 
				Double.toString(updated.getPrice()), 
				Integer.toString(updated.getId())
		);
		System.out.println(updateQuery);
		statement.executeUpdate(updateQuery);
	}

	@Override
	public void addRecord(final DataRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				getQuery("Insert"), 
				Integer.toString(inserted.getFactoryId()), 
				inserted.getBrand(), 
				inserted.getModel(), 
				Double.toString(inserted.getPrice())
		);
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void deleteRecord(DataRecord deleted) throws SQLException {
		final String deleteQuery = MessageFormat.format(
				getQuery("DeleteById"), 
				deleted.getId()
		);
		statement.executeUpdate(deleteQuery);
	}

}
