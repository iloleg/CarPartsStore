package by.tilalis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.rowset.CachedRowSetImpl;

import by.tilalis.db.interfaces.DataManager;
import by.tilalis.utils.JSON;

public class PGDataManager implements DataManager {
	private static PGDataManager instance;
	private static Connection connection;
	private static Statement statement;

	private PGDataManager() {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/CPS", "postgres", "290197");
			statement = connection.createStatement();
		} catch (ClassNotFoundException | SQLException e) {
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
	public String getUsersTable() {
		try (ResultSet rs = statement.executeQuery(
				"SELECT users.id, username, name AS role FROM public.users INNER JOIN public.\"userRoles\" ON role = \"userRoles\".id;")) {
			return new JSON(rs).toString();
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
	public boolean editRecord(int recordId, String[] fieldNames, String[] values) throws SQLException {
		if (fieldNames.length != values.length) {
			return false;
		}
		StringBuilder query = new StringBuilder("UPDATE parts SET ");
		
		for (int i = 0; i < values.length - 1; ++i) {
			query.append(String.format("\"%s\" = '%s', ", fieldNames[i], values[i]));
		}
		query.append(String.format("\"%s\" = '%s'", fieldNames[values.length - 1], values[values.length - 1]));
		query.append(String.format(" WHERE id = %d", recordId));
		
		System.out.println(query.toString());
		statement.executeUpdate(query.toString());
		return true;
	}

	@Override
	public boolean addRecord(String[] fields, String[] values) throws SQLException {
		StringBuilder sb = new StringBuilder("INSERT INTO parts (");
		for (String filed : fields) {
			sb.append(filed + ",");
		}

		sb.setLength(Math.max(sb.length() - 1, 0));
		sb.append(") VALUES (");

		for (String value : values) {
			sb.append("'" + value + "',");
		}

		sb.setLength(Math.max(sb.length() - 1, 0));
		sb.append(")");

		statement.executeUpdate(sb.toString());
		return false;
	}

	@Override
	public boolean deleteRecord(int recordId) throws SQLException {
		statement.executeUpdate(String.format("DELETE FROM parts WHERE id = %d", recordId));
		return false;
	}

}
