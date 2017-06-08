package by.tilalis.servlets.data;

import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.PGDataManager;
import by.tilalis.db.interfaces.DataManager;


public abstract class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected final DataManager dataManager;
	protected final ObjectMapper mapper;
       
    public DataManagerServlet() {
        super();
        this.dataManager = PGDataManager.getInstance();
        this.mapper = new ObjectMapper();
    }
}
