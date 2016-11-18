<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eVote</title>
</head>
<body>

	<form method = "post" action = "Login">
		User name <input type = "text" name = "uname" value = ""/><br>
		Password <input type = "text" name = "pword" value = ""/><br>
		<input type = "submit" name = "submit" value = "submit">
		<input type = "hidden" name = "option" value = "addClient">
	</form>
	
	<% out.println(request.getAttribute("message") + "</br>");%>
	<% out.println(request.getAttribute("uname"));%>
	
</body>
</html>