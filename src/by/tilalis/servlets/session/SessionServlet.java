package by.tilalis.servlets.session;

import java.io.InputStream;

import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.PGUserManager;
import by.tilalis.db.interfaces.UserManager;

public abstract class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected UserManager userManager;
	protected ObjectMapper mapper;
    
	@Override
	public void init() {
		InputStream file = getServletContext().getResourceAsStream("/WEB-INF/config/sql.xml");
		this.userManager = new PGUserManager(file);
		this.mapper = new ObjectMapper();
	}
}
