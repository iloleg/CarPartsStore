package by.tilalis.db.interfaces;

import java.util.List;

import javax.ejb.Local;

import by.tilalis.db.entities.Brand;
import by.tilalis.db.entities.CarPart;
import by.tilalis.db.entities.Category;
import by.tilalis.db.entities.User;

@Local
public interface CarPartManager {
	List<CarPart> getPage(int linesPerPage, int numberOfPage);
	
	List<CarPart> getPage(int linesPerPage, int numberOfPage, String searchField, String searchQuery);
	
	List<CarPart> getTrash(int linesPerPage, int numberOfPage);
	
	List<Brand> getBrands();
	
	List<Category> getCategories();
	
	int getRowsCount();

	void untrashRecord(CarPart untrashed);
	
	void editRecord(CarPart updated);

	void addRecord(CarPart inserted);
	
	void deleteRecord(CarPart deleted);
	
	void addBrand(Brand inserted);
	
	void addCategory(Category inserted);
	
	List<User> getUsersTable();
}
