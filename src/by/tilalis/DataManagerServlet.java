package by.tilalis;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tilalis.db.DataManager;
import by.tilalis.db.PGDatabaseManager;

@WebServlet({"/getUsers", "/getPage"})
public class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String GET_USERS = "/getUsers";
	
	private static final DataManager dataManager = PGDatabaseManager.getInstance();

    public DataManagerServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String path = request.getServletPath();

		switch (path) {
		case GET_USERS:
			getUsers(response.getWriter());
			break;
		default:
			break;
		}

	}


	private void getUsers(final PrintWriter writer) {
		writer.write(dataManager.getUsersTable());
	}
}
