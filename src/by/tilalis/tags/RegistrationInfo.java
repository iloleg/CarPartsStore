package by.tilalis.tags;

public class RegistrationInfo extends Info {
	private static final long serialVersionUID = 1L;

	@Override
	public String output(String message) {
		return String.format("<div class=\"alert alert-info\" role=\"alert\">%s</div>", message);
	}

	@Override
	public String message(String type) {
		String message = "";
		switch (type) {
		case "success":
			message = "Successfully registered!";
			break;
		case "fail":
			message = "Unsuccessfull registration";
			break;
		default:
			message = "Something strange happened during registration process";
			break;
		}
		return message;
	}

}
