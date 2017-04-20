package by.tilalis.db;

import java.sql.SQLException;

public interface DataManager {
	String getPage(int linesPerPage, int numberOfPage);
	
	int getRowsCount();

	boolean editRecord(int recordId, String[] fieldNames, String[] values);

	boolean addRecord(String[] fields, String[] values) throws SQLException;
	
	boolean deleteRecord(int recordId) throws SQLException;

	String findRecord(String fieldName, String query);

	String findRecords(String fieldName, String query);
	
	String getUsersTable();
}
