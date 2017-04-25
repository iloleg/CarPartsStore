<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="info" uri="CPS.TLD" %>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="pages/static/head-content.html" %>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Please Sign In</h3>
                    </div>
                    <div class="panel-body">
                    	<%-- custom tags --%>
						<info:login-alert attribute="badLogin"/>
						<info:registration-info attribute="registration"/>
                        <form role="form" action="${pageContext.request.contextPath}/login">
                            <fieldset>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Username" name="username" type="text" autofocus>
                                </div>
                                <div class="form-group">
                                    <input class="form-control" placeholder="Password" name="password" type="password" value="">
                                </div>
                                <button type="submit" class="btn btn-lg btn-info btn-block">Login</button>
                                <a href="${pageContext.request.contextPath}/registration" class="btn btn-lg btn-success btn-block">Register</a>
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
