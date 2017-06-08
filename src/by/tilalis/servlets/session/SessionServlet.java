package by.tilalis.servlets.session;

import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.PGUserManager;
import by.tilalis.db.interfaces.UserManager;

public abstract class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected UserManager userManager;
	protected final ObjectMapper mapper;
       
    public SessionServlet() {
        super();
        this.userManager = PGUserManager.getInstance();
        this.mapper = new ObjectMapper();
    }
}
