<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Log In Error</title>
</head>
<body>
<br><br>
<center><h3><p style="color:red">Sorry, either the username or password entered is incorrect, please try again.</p></h3></center>
<%
getServletContext().getRequestDispatcher("/index.jsp").include(request, response);
%></body>
</html>