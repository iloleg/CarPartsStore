package by.tilalis.servlets.data;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.interfaces.DataManager;

public abstract class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@EJB
	protected DataManager dataManager;
	protected ObjectMapper mapper;

	@Override
	public void init() {
		this.mapper = new ObjectMapper();
	}
}
