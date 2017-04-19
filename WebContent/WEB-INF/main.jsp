<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%@ include file="pages/static/head.html" %>
<body>
	<div id="wrapper">
	<%@ include file="pages/menu.jsp" %>
	<%
	String content = request.getParameter("content");
	if (content == null) content = "";
	switch (content) {
	case "/manage":
	%>
		<%@ include file="pages/manage.jsp" %>
	<% 
		break;
	case "/lookup":
	case "/add":
	case "/search":
	default:
	%>
		<%@ include file="pages/static/example-content.html"%>
	<%
		break;
	}
	%>
	</div>
</body>
<%@ include file="pages/static/foot.html" %>
<!-- <%= content %> -->
</html>