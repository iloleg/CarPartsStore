package by.tilalis.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseConnectionManager {
	private static DatabaseConnectionManager instance;
	private Connection connection;
	
	private DatabaseConnectionManager() throws SQLException, NamingException {
		DataSource source = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/cps");
		this.connection = source.getConnection();
	}
	
	public static DatabaseConnectionManager getInstance() throws SQLException, NamingException {
		if (instance == null) {
			return new DatabaseConnectionManager();
		} else {
			return instance;
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
}
