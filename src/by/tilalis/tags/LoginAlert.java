package by.tilalis.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class LoginAlert extends SimpleTagSupport {
	private String type;
	
	public void setType(final String type) {
		this.type = type;
	}
	
	@Override
	public void doTag() throws JspException, IOException {
		if (type != null) {
			final String message;

			switch (type) {
				case "username":
					message = "There's no such user.";
					break;
				case "password":
					message = "Wrong password.";
					break;
				default:
					message = "Bad login.";
					break;
			}
			
			getJspContext().getOut().write("<div class=\"alert alert-danger\" role=\"alert\">" +  message + "</div>");
		}
	}

}
