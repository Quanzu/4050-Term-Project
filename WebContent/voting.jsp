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
	<head>
  <title>eVote Homepage</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <style>
    body{
        position: relative;
    }
    .modal {
      z-index: 9999 !important;
    }
    .affix {
        top:0;
        width: 100%;
        z-index: 9999 !important;
    }
    .navbar {
        margin-bottom: 0px;
    }

    .affix ~ .container-fluid {
       position: relative;
       top: 50px;
    }

    .affix ~ .container {
       position: relative;
       top: 50px;
    }
    #Current {padding-top:50px;height:100vh;}
    #Election {padding-top:50px;height:100vh;}
    #Issue {padding-top:50px;height:100vh;}
  </style>
</head>



<body data-spy="scroll" data-target=".navbar" data-offset="50">

<div class="container-fluid">
  <div class="jumbotron">
    <h1 class="text-center">eVote</h1>
    <p>Welcome Voter!</p>
    <form action = "Logout" method="post">
    	<input type="submit" value="Logout">
    </form>
  </div>
</div>

<nav class="navbar navbar-default" data-spy="affix" data-offset-top="270">
  <div class="container">
    <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    </div>

    <div>
      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav">
          <li><a href="#Current">Ballot <%= session.getAttribute("choseBallot")%></a></li>
          
         
          <li><a href="#" data-toggle="modal" data-target="#accountModal">Account</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>


<% 
Object x = session.getAttribute("choseBallot");

Ballot theBallot;

String str = x.toString();
int temp = Integer.parseInt(str);
theBallot = logicLayer.findBallot(temp);




%>



  <!-- CURRENT -->
  <div id="Current" class="container">
    <h3>Ballot <%= session.getAttribute("choseBallot")%></h3>
      
      	 <br>

	<%
	List<BallotItem> items = logicLayer.findBallotItems(theBallot);
	
	%>
	<form action = "Vote" method = "post">
	<% 
		for (int i = 0; i < items.size();i++)
		{
			if(items.get(i) instanceof Issue )
			{
				Issue issue = (Issue)items.get(i);
				out.println("Issue " + i + ":" + issue.getQuestion());
				
				
				%>
				
				<br>
				<input type = "hidden" name = "voteCount" value = "<%=issue.getVoteCount() %>">
				<input type = "hidden" name = "issueId" value = "<%=issue.getId() %>">
				<input type = "hidden" name = "question" value = "<%=issue.getQuestion() %>">
				<input type = "radio" name = "issueVote" value = "yes">Yes<br>
				<input type = "radio" name = "issueVote" value = "no">No<br>
				<input type = "hidden" name = "todo" value = "issue">
				<% 
			}
			%>
			<button type = "submit" name = "submit">Vote</button>
			</form>
			<form action = "Vote" method = "post">
			<% 
			if(items.get(i) instanceof Election )
			{
				Election election = (Election)items.get(i);
				out.println("Election Office " + i + ":" + election.getOffice());
				List<Candidate> candidates = election.getCandidates();
				if (candidates.size() == 0)
				{
					%> <br>No Candidates Listed<br> <% 
				}
				else
				{
					for (int j = 0; j<candidates.size(); j++)
					{	
					%>
				
					<br>
					<input type = "radio" name = "electionVote" value = "<%=candidates.get(i).getName() %>"> <%=candidates.get(i).getName() %><br>
					<input type = "hidden" name = "todo" value = "election">
				<% 
					}
				}
				%><button type = "submit" name = "submit">Vote</button><% 
			}
		}
	%>
	
	</form>
	
      
      
      
  </div>


	<div>
	

	
	</div>
</body>



</body>
</html>