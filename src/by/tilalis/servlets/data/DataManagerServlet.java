package by.tilalis.servlets.data;

import javax.servlet.http.HttpServlet;
import by.tilalis.db.PGDataManager;
import by.tilalis.db.interfaces.DataManager;


public abstract class DataManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected DataManager dataManager;
       
    public DataManagerServlet() {
        super();
        this.dataManager = PGDataManager.getInstance();
    }
}
