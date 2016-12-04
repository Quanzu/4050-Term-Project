<%@page import="java.sql.*,edu.uga.cs.evote.persistence.impl.DbUtils"%>

<html>
	<head>
		<title>Forgot your username/password?</title>
	</head>
	<%
		String id = request.getParameter("userId");
		String driverName = "com.mysql.jdbc.Driver";
		String connectionUrl = "jdbc:mysql://localhost:3306/";
		String dbName = "evote";
		String userId = "root";
		String password = "Ihave0ideas!";

		try {
			Class.forName(driverName);
		}

		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
	%>
	<h2 align="left"><font><strong>Here are your login credentials!</strong></font></h2>

	<%
		try{ 
			connection = DbUtils.connect();
			statement=connection.createStatement();
			String email = request.getParameter("email");
			String sql ="SELECT * FROM user where email = '" + email + "'";
			resultSet = statement.executeQuery(sql);
			while(resultSet.next()){
	%>

	Username: <%=resultSet.getString("userName") %><br>
	Password: <%=resultSet.getString("password") %><br>

	<br> <a href = "index.jsp">Home</a>

	<% 
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	%>
