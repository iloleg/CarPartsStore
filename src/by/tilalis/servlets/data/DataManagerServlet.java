package by.tilalis.servlets.data;

import java.io.InputStream;
import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.PGDataManager;
import by.tilalis.db.interfaces.DataManager;

public abstract class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected DataManager dataManager;
	protected ObjectMapper mapper;

	@Override
	public void init() {
		InputStream file = getServletContext().getResourceAsStream("/WEB-INF/config/sql.xml");
		this.dataManager = new PGDataManager(file);
		this.mapper = new ObjectMapper();
	}
}
