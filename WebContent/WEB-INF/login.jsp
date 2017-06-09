<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                    
						<c:if test="${not empty badLogin}">
							<div class="alert alert-danger" role="alert">
							<c:choose>
								<c:when test="${badLogin == 'username'}">
									There's no such user.
								</c:when>
								<c:when test="${badLogin == 'password'}">
									Wrong password.
								</c:when>
								<c:otherwise>
									Bad login.
								</c:otherwise>
							</c:choose>
							</div>
							<c:set var="badLogin" scope="session" value=""/>
						</c:if>
						
						<c:if test="${not empty registration}">
							<div class="alert alert-info" role="alert">
							<c:choose>
								<c:when test="${registration == 'success'}">
									Successfully registered!
								</c:when>
								<c:when test="${registration == 'fail'}">
									Unsuccessful registration.
								</c:when>
								<c:otherwise>
									Something strange happened during registration process.
								</c:otherwise>
							</c:choose>
							</div>
							<c:set var="registration" scope="session" value=""/>
						</c:if>
						
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
