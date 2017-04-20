package by.tilalis.tags;

public class LoginAlert extends Info {
	private static final long serialVersionUID = 1L;

	@Override
	public String message(final String type) {
		String message = "";
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
		return message;
	}
	
	@Override
	public String output(final String message) {
		return String.format("<div class=\"alert alert-danger\" role=\"alert\">%s</div>", message); 
	}

}
