package by.tilalis.servlets.session;

import javax.servlet.http.HttpServlet;

import by.tilalis.db.PGUserManager;
import by.tilalis.db.interfaces.UserManager;

public abstract class SessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected UserManager userManager;
       
    public SessionServlet() {
        super();
        this.userManager = PGUserManager.getInstance();
    }
}
