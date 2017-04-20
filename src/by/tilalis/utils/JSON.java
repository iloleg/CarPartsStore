package by.tilalis.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utility for converting ResultSets into JSON
 */
public class JSON {
	private String result;

	public JSON(final ResultSet resultSet) {
		JSONArray jsonArray = new JSONArray();
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			while (resultSet.next()) {
				int total_rows = metaData.getColumnCount();
				JSONObject obj = new JSONObject();
				for (int i = 0; i < total_rows; i++) {
					obj.put(metaData.getColumnLabel(i + 1).toLowerCase(), resultSet.getObject(i + 1));
				}
				jsonArray.put(obj);
			}
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		} finally {
			this.result = jsonArray.toString();
		}
	}

	@Override
	public String toString() {
		return this.result;
	}
}