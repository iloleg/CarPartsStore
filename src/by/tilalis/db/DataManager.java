package by.tilalis.db;

public interface DataManager {
	String getPage(int linesPerPage, int numberOfPage);

	boolean editRecord(int recordId, String[] fieldNames, String[] values);

	boolean addRecord(String[] values);

	String findRecord(String fieldName, String query);

	String findRecords(String fieldName, String query);
	
	String getUsersTable();
}
