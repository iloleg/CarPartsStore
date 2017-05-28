package by.tilalis;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.DataManager;
import by.tilalis.db.PGDatabaseManager;

@WebServlet({ "/get_users", "/get_page", "/get_items_count", "/add_record", "/delete_record", "/update_record" })
public class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static enum Paths {
		GET_USERS, GET_PAGE, GET_ITEMS_COUNT, DELETE_RECORD, ADD_RECORD, UPDATE_RECORD;

		public static Paths fromString(final String path) {
			System.out.println("CALLED WITH PATH: " + path + " AND " + path.substring(1).toUpperCase());
			return Paths.valueOf(path.substring(1).toUpperCase());
		}
	}

	private static final DataManager dataManager = PGDatabaseManager.getInstance();

	public DataManagerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		final Paths path = Paths.fromString(request.getServletPath());

		switch (path) {
		case GET_PAGE:
			getPage(response.getWriter(), request);
			break;
		case GET_ITEMS_COUNT:
			getCount(response.getWriter());
			break;
		default:
			break;
		}

		final String role = (String) request.getSession().getAttribute("role");
		if (role != null && role.equals("Administrator")) {
			switch (path) {
			case GET_USERS:
				getUsers(response.getWriter());
				break;
			case ADD_RECORD:
				addRecord(response.getWriter(), request);
				break;
			case DELETE_RECORD:
				deleteRecord(response.getWriter(), request);
				break;
			case UPDATE_RECORD:
				updateRecord(response.getWriter(), request);
			default:
				break;
			}
		}
	}
	
	private void updateRecord(final PrintWriter writer, HttpServletRequest request) {
		final String success = "{\"status\": \"success\"}";
		final String fail    = "{\"status\": \"fail\"}";
		
		final String[] fields = request.getParameterValues("fields[]");
		final String[] values = request.getParameterValues("values[]");
		try {
			if (dataManager.editRecord(Integer.valueOf(request.getParameter("id")), fields, values)) {
				writer.write(success);
			} else {
				writer.write(fail);
			}
			
		} catch (NumberFormatException | SQLException e) {
			writer.write(fail);
		}
	}

	private void deleteRecord(final PrintWriter writer, HttpServletRequest request) {
		try {
			dataManager.deleteRecord(Integer.valueOf(request.getParameter("id")));
			writer.write("{\"status\": \"success\"}");
		} catch (NumberFormatException | SQLException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}

	private void addRecord(final PrintWriter writer, HttpServletRequest request) {
		final String[] fields = request.getParameterValues("fields[]");
		final String[] values = request.getParameterValues("values[]");
		try {
			dataManager.addRecord(fields, values);
			writer.write("{\"status\": \"success\"}");
		} catch (SQLException e) {
			writer.write("{\"status\": \"fail\"}");
			e.printStackTrace();
		}
	}

	private void getPage(final PrintWriter writer, final HttpServletRequest request) {
		int linesPerPage = 10;
		int numberOfPage = 0;
		String searchField = request.getParameter("search_field");
		String searchQuery = request.getParameter("search_query");

		try {
			linesPerPage = Integer.valueOf(request.getParameter("lines"));
			numberOfPage = Integer.valueOf(request.getParameter("page"));
		} catch (NumberFormatException nfe) {
		}
		System.out.printf("%d %d %s %s", linesPerPage, numberOfPage, searchField, searchQuery);
		
		writer.write(dataManager.getPage(linesPerPage, numberOfPage, searchField, searchQuery));
	}

	private void getCount(final PrintWriter writer) {
		writer.write(Integer.toString(dataManager.getRowsCount()));
	}

	private void getUsers(final PrintWriter writer) {
		writer.write(dataManager.getUsersTable());
	}
}
