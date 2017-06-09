<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="pages/static/head-content.html"%>
</head>
<body>
	<%@ include file="pages/menu.jspf"%>
	<div id="wrapper">
		<c:choose>
			<c:when test="${param.content == '/manage'}">
				<%@ include file="pages/manage.jspf"%>
			</c:when>
			<c:when test="${param.content == '/lookup'}">
				<%@ include file="pages/lookup.jspf"%>
			</c:when>
			<c:when test="${param.content == '/add'}">
				<%@ include file="pages/add.jspf"%>
			</c:when>
			<c:when test="${param.content == '/search'}">
				<%@ include file="pages/search.jspf"%>
			</c:when>
			<c:otherwise>
				<%@ include file="pages/lookup.jspf"%>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>