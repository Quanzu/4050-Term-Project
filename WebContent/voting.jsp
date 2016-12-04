<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@     page import="edu.uga.cs.evote.session.Session" %>
    <%@ page import="edu.uga.cs.evote.session.SessionManager" %>
    <%@ page import="edu.uga.cs.evote.logic.LogicLayer" %>
    <%@ page import="java.util.List" %>
    <%@ page import="edu.uga.cs.evote.entity.*" %>
    <%
	String ssid = (String)session.getAttribute("ssid");
    Session hpSession = SessionManager.getSessionById(ssid);
    LogicLayer logicLayer = hpSession.getLogicLayer();

    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%= session.getAttribute("choseBallot")%>
<% 
Object x = session.getAttribute("choseBallot");

Ballot theBallot;

String str = x.toString();
int temp = Integer.parseInt(str);
theBallot = logicLayer.findBallot(temp);



out.print(theBallot.getCloseDate());

%>

	<div>
	
	Ballot ID: <%out.print( theBallot.getId()); %> <br>
	<% out.println(theBallot.getOpenDate());%>
	<%
	List<BallotItem> items = logicLayer.findBallotItems(theBallot);
	out.println(items.size());
		for (int i = 0; i < items.size();i++)
		{
			if(items.get(i) instanceof Issue )
			{
				Issue issue = (Issue)items.get(i);
				out.println("Issue " + i + ":" + issue.getQuestion());
				
				
				%>
				<input type = "radio" name = "issueVote" value = "yes">Yes<br>
				<input type = "radio" name = "issueVote" value = "yes">No<br>
				<% 
			}
			
			if(items.get(i) instanceof Election )
			{
				Election election = (Election)items.get(i);
				out.println("Election Office " + i + ":" + election.getOffice());
				List<Candidate> candidates = election.getCandidates();
				for (int j = 0; j<candidates.size(); j++)
				{	
				%>
					<input type = "radio" name = "electionVote" value = "<%=candidates.get(i).getName() %>"> <%=candidates.get(i).getName() %><br>
				<% 
				}
			}
		}
	%>
	<button type = "submit" name = "submit">Vote</button>
	
	</div>




</body>
</html>