<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>TEST</title>
	</head>
	<body>
		<h1>Voter Registration</h1>
		<form action="Register" method="POST">
			<input type=hidden name="option" value="addVoter" />
			First Name <input type="text" name="fname" value=""><br>
			Last Name <input type="text" name="lname" value=""><br>
			Username <input type="text" name="uname" value=""><br>
			Password <input type="text" name="pword" value=""><br>
			Retype Password <input type="text" name="retype" value=""><br>
			Email Address <input type="text" name="email" value=""><br>
			Age <input type="text" name="age" value=""><br>
			Street <input type="text" name="street" value=""><br>
			City <input type="text" name="city" value=""><br>
			State <input type="text" name="state" value=""><br>
			Zip Code <input type="text" name="zip" value=""><br>
			<input type="submit" value="register" name="register" />
		
		
		</form>
	</body>
</html>