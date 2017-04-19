<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<%@ include file="pages/static/head.html" %>

<body>

    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
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
						<div class="alert alert-danger" role="alert"><%= message %></div>
					<%
						}
					%>
					<%
						if (session.getAttribute("registration") != null) {
							final String message;

							switch (session.getAttribute("registration").toString()) {
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
							session.removeAttribute("registration");
					%>
						<div class="alert alert-info" role="alert"><%= message %></div>
					<%
						}
					%>
                        <form role="form" action="<%= request.getContextPath() + "/login" %>">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <button type="submit" class="btn btn-lg btn-info btn-block">Login</button>
                                <a href="<%= request.getContextPath() + "/registration" %>" class="btn btn-lg btn-success btn-block">Register</a>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%@ include file="pages/static/foot.html" %>

</body>

</html>
