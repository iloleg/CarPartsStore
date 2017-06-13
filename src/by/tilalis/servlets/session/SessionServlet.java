package by.tilalis.servlets.session;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import by.tilalis.db.interfaces.UserManager;

public abstract class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@EJB
	protected UserManager userManager;
	protected ObjectMapper mapper;
    
	@Override
	public void init() {
		this.mapper = new ObjectMapper();
	}
}
