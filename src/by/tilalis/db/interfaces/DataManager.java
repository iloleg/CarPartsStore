package by.tilalis.db.interfaces;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.records.BrandRecord;
import by.tilalis.db.records.CategoryRecord;
import by.tilalis.db.records.DataRecord;
import by.tilalis.db.records.UserRecord;

@Local
public interface DataManager {
	List<DataRecord> getPage(int linesPerPage, int numberOfPage);
	
	List<DataRecord> getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery);
	
	List<DataRecord> getTrash(int linesPerPage, int numberOfPage);
	
	List<BrandRecord> getBrands();
	
	List<CategoryRecord> getCategories();
	
	int getRowsCount();

	void untrashRecord(DataRecord untrashed) throws SQLException;
	
	void editRecord(DataRecord updated) throws SQLException;

	void addRecord(DataRecord inserted) throws SQLException;
	
	void deleteRecord(DataRecord deleted) throws SQLException;
	
	void addBrand(BrandRecord inserted) throws SQLException;
	
	void addCategory(CategoryRecord inserted) throws SQLException;
	
	List<UserRecord> getUsersTable();
}
