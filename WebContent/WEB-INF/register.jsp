<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="pages/static/head-content.html"%>
</head>
<body>
	<script src="static/js/init/register.js"></script>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-4">
				<div class="login-panel panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">Registration</h3>
					</div>
					<div class="panel-body">
						<form id="register-form" role="form"
							action="<%=request.getContextPath() + "/register"%>">
							<fieldset>
								<div class="form-group">
									<input class="form-control" placeholder="Username"
										name="username" type="text" autofocus>
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password"
										name="password" type="password" value="">
								</div>
								<div class="form-group">
									<input class="form-control" placeholder="Password Again"
										name="password2" type="password" value="">
								</div>
								<button type="submit" class="btn btn-lg btn-success btn-block">Register</button>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="pages/static/foot.html"%>
</body>
</html>
