package by.tilalis.tags;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public abstract class Info extends TagSupport {
	private static final long serialVersionUID = 1L;
	private String attribute;

	public void setAttribute(final String attribute) {
		this.attribute = attribute;
	}

	@Override
	public int doStartTag() throws JspException {
		HttpSession session =  ((HttpServletRequest) pageContext.getRequest()).getSession();
		final String type = (String) session.getAttribute(attribute);

		if (type != null && !type.equals("")) {
			try {
				pageContext.getOut().write(output(message(type)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			session.removeAttribute(attribute);
		}

		return SKIP_BODY;
	}
	
	public abstract String output(final String message);
	
	public abstract String message(final String type);

}
