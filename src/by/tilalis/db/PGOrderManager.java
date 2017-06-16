package by.tilalis.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import by.tilalis.db.interfaces.OrderManager;
import by.tilalis.db.records.DataRecord;
import by.tilalis.db.records.OrderRecord;
import by.tilalis.db.records.UserRecord;

@Stateless
public class PGOrderManager extends PGManager implements OrderManager {
	private String getQuery(String path) {
		final String base = "//Queries/OrderManager/";
		try {
			return (String) xpath.compile(base + path).evaluate(document, XPathConstants.STRING);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void sendOrders(List<OrderRecord> basket) throws SQLException  {
		final String query = getQuery("Insert");
		for (final OrderRecord record : basket) {
			final UserRecord user = record.getUser();
			final DataRecord data = record.getData();
			try {
				statement.executeQuery(
						MessageFormat.format(query, user.getId(), data.getId())
				);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<OrderRecord> getOrders(int linesPerPage, int numberOfPage) {
		final List<OrderRecord> list = new ArrayList<>();
		final String query;
		if (linesPerPage > 0) {
			 query = MessageFormat.format(
					getQuery("GetOrders"), 
					linesPerPage,
					numberOfPage * linesPerPage
			);
		} else {
			 query = MessageFormat.format(
				    getQuery("GetOrders"), 
					"ALL",
					0
			 );
		}
		try (ResultSet rs = statement.executeQuery(query)) {
			while (rs.next()) {
				list.add(
					new OrderRecord(
							rs.getInt("id"), 
							new UserRecord(rs.getInt("users_id"), rs.getString("username")),
							new DataRecord(rs.getInt("parts_id"), rs.getInt("factory_id"))
					)
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void deleteOrder(OrderRecord deleted) {
		final String query = MessageFormat.format(getQuery("Delete"), deleted.getId());
		try {
			statement.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
