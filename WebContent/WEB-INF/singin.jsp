<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>Signin</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">


</head>

<body id="singin">
	<div class="container">
		<%
			if (session.getAttribute("badLogin") != null) {
				final String message;

				switch (session.getAttribute("badLogin").toString()) {
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
		%>
		<div class="alert alert-danger" role="alert"><%=message%></div>
		<%
			}
		%>
		<form class="form-signin"
			action="<%=request.getContextPath() + "/login"%>" method="POST">
			<h2 class="form-signin-heading">Please sign in</h2>

			<label for="username" class="sr-only">Login</label> <input
				type="text" name="username" id="username" class="form-control"
				placeholder="Login" required autofocus> <label
				for="password" class="sr-only">Password</label> <input
				type="password" name="password" id="password" class="form-control"
				placeholder="Password" required>

			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
			<button class="btn btn-lg btn-default btn-block">Register</button>
		</form>

	</div>
	<!-- /container -->

</body>
</html>
