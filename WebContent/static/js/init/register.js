$(document).ready(function() {
	$("#register-form").submit(function(event) {
		if ($("[name=\"password\"]").val() != $("[name=\"password2\"]").val()) {
			alert("Passwords must be equal.");
			event.preventDefault();
		}
		if ($("[name=\"username\"]").val() == "") {
			alert("Username cannot be empty.");
			event.preventDefault();
		}
	});
});