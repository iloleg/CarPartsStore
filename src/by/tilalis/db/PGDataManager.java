package by.tilalis.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;

import com.sun.rowset.CachedRowSetImpl;

import by.tilalis.db.interfaces.DataManager;
import by.tilalis.utils.JSON;

public class PGDataManager implements DataManager {
	private static PGDataManager instance;
	private static Connection connection;
	private static Statement statement;

	private PGDataManager() {
		try {
			connection = DatabaseConnectionManager.getInstance().getConnection();
			statement = connection.createStatement();
		} catch (SQLException | NamingException e) {
			e.printStackTrace();
		}
	}

	public static PGDataManager getInstance() {
		if (instance != null) {
			return instance;
		}
		return new PGDataManager();
	}

	@Override
	public List<UserRecord> getUsersTable() {
		final ArrayList<UserRecord> users = new ArrayList<>();
		
		try (ResultSet rs = statement.executeQuery(
				"SELECT users.id, username, name AS role FROM public.users INNER JOIN public.\"userRoles\" ON role = \"userRoles\".id;")) {
			while (rs.next()) {
				users.add(new UserRecord(rs.getInt("id"), rs.getString("username"), rs.getString("role")));
			}
			return users;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
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
	public String getPage(int linesPerPage, int numberOfPage) {
		return getPage(linesPerPage, numberOfPage, null, null);
	}

	@Override
	public String getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery) {
		final String query;
		if (searchField == null || searchQuery == null || "".equals(searchField) || "".equals(searchQuery)) {
			query = String.format("SELECT id, factory_id, brand, model, price " + "FROM parts " + "ORDER BY id "
					+ "LIMIT %d OFFSET %d", linesPerPage, numberOfPage * linesPerPage);
		} else {
			query = String.format(
					"SELECT id, factory_id, brand, model, price FROM parts WHERE CAST(\"%s\" AS TEXT) ILIKE '%s%%' ORDER BY id LIMIT %d OFFSET %d",
					searchField, searchQuery, linesPerPage, numberOfPage * linesPerPage);
		}

		try (ResultSet rs = statement.executeQuery(query)) {
			CachedRowSetImpl crs = new CachedRowSetImpl();
			crs.populate(rs);
			return new JSON(crs).toString();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getRowsCount() {
		System.out.println(singleFieldQuery("COUNT(*)", "parts", "TRUE"));
		return Integer.valueOf(singleFieldQuery("COUNT(*)", "parts", "TRUE"));
	}

	@Override
	public void editRecord(final DataRecord updated) throws SQLException {
		final String updateQuery = MessageFormat.format(
				"UPDATE parts SET factory_id = {0}, brand = ''{1}'', model = ''{2}'', price = ''{3}'' WHERE id = {4}", 
				updated.getId(), updated.getBrand(), updated.getModel(), updated.getPrice(), updated.getId()
		);
		statement.executeUpdate(updateQuery);
	}

	@Override
	public void addRecord(final DataRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				"INSERT INTO parts (factory_id, brand, model, price) VALUES ({0}, {1}, {2}, {3})", 
				inserted.getFactoryId(), inserted.getBrand(), inserted.getModel(), inserted.getPrice()
		);
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void deleteRecord(DataRecord deleted) throws SQLException {
		final String deleteQuery = MessageFormat.format(
				"DELETE FROM PARTS WHERE id = {}", 
				deleted.getId()
		);
		statement.executeUpdate(deleteQuery);
	}

}
