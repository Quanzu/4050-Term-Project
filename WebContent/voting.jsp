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
  <title>eVote</title>
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
          <li><a href="voterHomepage.jsp">Cancel</a></li>
          
         
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
	List<Integer> votedElect = null;
	List<Integer> votedIssue = null;
	int issueCount = 0; 
	int electionCount = 0;
	//Object y = session.getAttribute("issueDelete");
	//String temporary = y.toString();
	//int addIssue = Integer.parseInt(temporary);
	//votedIssue.add(addIssue);
	
	//Gets count of how many items and elections
	for (int i = 0; i < items.size();i++)
	{
		if(items.get(i) instanceof Issue )
		{
			issueCount++;
		}
		if (items.get(i) instanceof Election )
		{
			electionCount++;
		}
	}
	
	//populates voted
//	for (int i = 0; i < items.size(); i++)
//	{
//		if (items.get(i) instanceof Issue)
//		{
//			Issue issue = (Issue)items.get(i);
//			votedIssue.add(issue.getId());
//		}
//		if (items.get(i) instanceof Election )
//		{
//			Election election = (Election)items.get(i);
//			votedElect.add(election.getId());
//		}
//	}
	
	
		for (int i = 0; i < items.size();i++)
		{
			if(items.get(i) instanceof Issue )
			{
				Issue issue = (Issue)items.get(i);
				
				//was gonna do something where if this list had the issue's id, then
				//you don't display the question
				//but i can't seem to pass through the id or anything...
				
				
					out.println("Issue " + i + ":" + issue.getQuestion());
					
					
					%>
					
					<form action = "SubmitVote" method = "post" target = "formresponse">
		
					<br>
					<input type = "hidden" name = "voteCount" value = "<%=issue.getVoteCount() %>">
					<input type = "hidden" name = "issueId" value = "<%=issue.getId() %>">
					<input type = "hidden" name = "question" value = "<%=issue.getQuestion() %>">
					<input type = "radio" name = "issueVote" value = "yes">Yes<br>
					<input type = "radio" name = "issueVote" value = "no">No<br>
					<input type = "hidden" name = "todo" value = "issue">
					<button type = "submit" name = "submit">Vote</button>
					</form>
				<!-- 	<iframe name='formresponse' width='300' height='200'></frame> -->
					<% 
					
						
	
				
				
				
			}
			%>
			<script>
           		function makeGone(){
           			
           		}
            </script>
			
			
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
					<form action = "Vote" method = "post">
					<br>
					<input type = "radio" name = "electionVote" value = "<%=candidates.get(i).getName() %>"> <%=candidates.get(i).getName() %><br>
					<input type = "hidden" name = "todo" value = "election">
				<% 
					}
					%><button type = "submit" name = "submit">Vote</button>
					</form><% 
							
				}
				
			}
		}
	%>
	
	
	
      
      
      
  </div>


	<div>
	

	
	</div>
	
	<div id="accountModal" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Account Information</h1>
        </div>
       
        
        <div class="modal-body">
          <form class="form-signin" action = "VoterUpdate" method = "post">
            
            <label for="age" class="sr-only">New Age</label>
            <input type="text" name="age" class="form-control" placeholder="New Age" required=false autofocus=true>
           
            <label for="fname" class="sr-only">New First Name</label>
            <input type="text" name="fName" class="form-control" placeholder="New First Name" required=false autofocus=true>
            
            <label for="lname" class="sr-only">New Last Name</label>
            <input type="text" name="lName" class="form-control" placeholder="New Last Name" required=false autofocus=true>
            
            <label for="username" class="sr-only">New Username</label>
            <input type="text" name="username" class="form-control" placeholder="New Username" required=false autofocus=true>
            
            <label for="password" class="sr-only">New Password</label>
            <input type="text" name="password" class="form-control" placeholder="New Password" required=false autofocus=true>
            
            <label for="email" class="sr-only">New Email</label>
            <input type="text" name="email" class="form-control" placeholder="New Email" required=false autofocus=true>
            
            <label for="street" class="sr-only">New Streete</label>
            <input type="text" name="street" class="form-control" placeholder="New Street" required=false autofocus=true>
			
			<label for="city" class="sr-only">New City</label>
            <input type="text" name="city" class="form-control" placeholder="New City" required=false autofocus=true>
            
            <label for="state" class="sr-only">New State</label>
            <input type="text" name="state" class="form-control" placeholder="New State" required=false autofocus=true>
            
            <label for="zip" class="sr-only">New Zip</label>
            <input type="text" name="zip" class="form-control" placeholder="New Zip" required=false autofocus=true>

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
          
          <form method = "post" action = "VoterUpdate">
<input type = "hidden" name = "todo" value = "delete">
<button type = "submit">Unregister</button> 
</form>
        </div>
      </div>
    </div>
  </div>
</body>



</body>
</html>