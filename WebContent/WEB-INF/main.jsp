<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="pages/static/head-content.html"%>
</head>
<body>
	<%@ include file="pages/menu.jsp" %>
	<div id="wrapper">
		<%
			final String content = request.getParameter("content");

			switch (content == null ? "" : content) {
			case "/manage":
		%>
		<%@ include file="pages/manage.jsp"%>
		<%
			break;
			case "/lookup":
		%>
		<%@ include file="pages/data.jsp"%>
		<%
			break;
			case "/add":
		%>
		<%@ include file="pages/add.jsp" %>
		<% 
			break;
			case "/search":
			default:
		%>
		<%@ include file="pages/data.jsp"%>
		<%
			break;
			}
		%>

	</div>
</body>
</html>