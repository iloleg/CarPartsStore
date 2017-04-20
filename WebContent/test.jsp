<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="cps" uri="CPS.TLD" %>

<?xml version="1.0" encoding="ISO-8859-1" ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
	<title>Jsp Custom Tag Example</title>
</head>
<body>
<%
	session.setAttribute("login", "password");
	session.setAttribute("reg", "succes");
%>
	<cps:check-rights role="user">
		hello
	</cps:check-rights>
	<cps:check-rights role="Administrator">
		worlds
	</cps:check-rights>
</body>
</html>