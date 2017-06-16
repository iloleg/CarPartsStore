package by.tilalis.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import by.tilalis.db.interfaces.DataManager;
import by.tilalis.db.records.BrandRecord;
import by.tilalis.db.records.CategoryRecord;
import by.tilalis.db.records.DataRecord;
import by.tilalis.db.records.UserRecord;

@Stateless
public class PGDataManager extends PGManager implements DataManager {
	public PGDataManager() {
		super();
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
			if (linesPerPage > 0) {
				query = MessageFormat.format(
						getQuery("GetPage"), 
						linesPerPage,
						numberOfPage * linesPerPage
				);
			} else {
				query = MessageFormat.format(
						getQuery("GetPage"), 
						"ALL",
						0
				);
			}

		} else {
			query = MessageFormat.format(
					getQuery("GetPageSearch"), 
					searchField, searchQuery, linesPerPage, numberOfPage * linesPerPage
			);
			System.out.println(query);
		}
		
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(
					new DataRecord(
						rs.getInt("id"), 
						rs.getInt("factory_id"), 
						new BrandRecord(rs.getInt("brand_id"), rs.getString("brand_name")), 
						new CategoryRecord(rs.getInt("category_id"), rs.getString("category_name")),
						rs.getString("model"), 
						rs.getDouble("price")
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	@Override
	public List<DataRecord> getTrash(int linesPerPage, int numberOfPage) {
		final List<DataRecord> list = new ArrayList<>();
		final String query;
		if (linesPerPage > 0) {
			 query = MessageFormat.format(
					getQuery("GetTrash"), 
					linesPerPage,
					numberOfPage * linesPerPage
			);
		} else {
			 query = MessageFormat.format(
						getQuery("GetTrash"), 
						"ALL",
						0
			 );
		}

		
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(
						new DataRecord(
							rs.getInt("id"), 
							rs.getInt("factory_id"), 
							new BrandRecord(rs.getInt("brand_id"), rs.getString("brand_name")), 
							new CategoryRecord(rs.getInt("category_id"), rs.getString("category_name")),
							rs.getString("model"), 
							rs.getDouble("price")
						)
					);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<BrandRecord> getBrands() {
		final String query = getQuery("GetBrands");
		final List<BrandRecord> list = new ArrayList<>();
		
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(new BrandRecord(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public List<CategoryRecord> getCategories() {
		final String query = getQuery("GetCategories");
		final List<CategoryRecord> list = new ArrayList<>();
		
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(new CategoryRecord(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public int getRowsCount() {
		final String query = getQuery("ItemsCount");
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
				updated.getBrand().getId(), 
				updated.getCategory().getId(),
				updated.getModel(), 
				Double.toString(updated.getPrice()), 
				Integer.toString(updated.getId())
		);
		statement.executeUpdate(updateQuery);
	}

	@Override
	public void addRecord(final DataRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				getQuery("Insert"), 
				Integer.toString(inserted.getFactoryId()), 
				inserted.getBrand().getId(), 
				inserted.getCategory().getId(),
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
	
	@Override
	public void untrashRecord(DataRecord untrashed) throws SQLException {
		final String untrashQuery = MessageFormat.format(
				getQuery("Untrash"), 
				untrashed.getId()
		);
		statement.executeUpdate(untrashQuery);
	}

	@Override
	public void addBrand(BrandRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				getQuery("InsertBrand"),
				inserted.getName()
		);
		statement.executeUpdate(insertQuery);
	}

	@Override
	public void addCategory(CategoryRecord inserted) throws SQLException {
		final String insertQuery = MessageFormat.format(
				getQuery("InsertCategory"),
				inserted.getName()
		);
		statement.executeUpdate(insertQuery);
	}

}
