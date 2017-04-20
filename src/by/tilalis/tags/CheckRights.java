package by.tilalis.tags;

import java.io.IOException;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class CheckRights extends BodyTagSupport {
	private static final long serialVersionUID = 1L;

	private static String ADMINISTRATOR = "administrator";
	
	@Override
	public int doAfterBody() {
		final HttpSession session = pageContext.getSession();
		final String role = (String) session.getAttribute("role");

		if ((role != null && role.equalsIgnoreCase(ADMINISTRATOR))) {
			try {
				bodyContent.writeOut(bodyContent.getEnclosingWriter());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return SKIP_BODY;
	}
}
