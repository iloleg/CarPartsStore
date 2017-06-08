package by.tilalis.db.interfaces;

import java.sql.SQLException;
import java.util.List;

import by.tilalis.db.DataRecord;
import by.tilalis.db.UserRecord;

public interface DataManager {
	String getPage(int linesPerPage, int numberOfPage);
	
	String getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery);
	
	int getRowsCount();

	void editRecord(DataRecord updated) throws SQLException;

	void addRecord(DataRecord inserted) throws SQLException;
	
	void deleteRecord(DataRecord deleted) throws SQLException;
	
	List<UserRecord> getUsersTable();
}
