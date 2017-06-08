package by.tilalis.db.interfaces;

import java.sql.SQLException;

public interface DataManager {
	String getPage(int linesPerPage, int numberOfPage);
	
	String getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery);
	
	int getRowsCount();

	boolean editRecord(int recordId, String[] fieldNames, String[] values) throws SQLException;

	boolean addRecord(String[] fields, String[] values) throws SQLException;
	
	boolean deleteRecord(int recordId) throws SQLException;
	
	String getUsersTable();
}
