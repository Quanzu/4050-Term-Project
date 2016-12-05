<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="edu.uga.cs.evote.session.Session" %>
    <%@ page import="edu.uga.cs.evote.session.SessionManager" %>
    <%@ page import="edu.uga.cs.evote.logic.LogicLayer" %>
    <%@ page import="java.util.List" %>
    <%@ page import="java.text.DateFormat" %>
    <%@ page import="java.text.SimpleDateFormat" %>
    <%@ page import="java.util.Calendar" %>
    <%@ page import="java.util.Date" %>
    <%@ page import="edu.uga.cs.evote.entity.*" %>
    <%String ssid = (String)session.getAttribute("ssid");
    Session hpSession = SessionManager.getSessionById(ssid);
    LogicLayer logicLayer = hpSession.getLogicLayer();
    int i;
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
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
          <li><a href="#Current">Ballots</a></li>
          <li><a href="#Results">Results</a></li>
         
          <li><a href="#" data-toggle="modal" data-target="#accountModal">Account</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>

  <!-- CURRENT -->
  <div id="Current" class="container">
     <h3>Current</h3>
    <%
        List<Ballot> ballots = logicLayer.findAllBallot();
        Voter currentVoter = (Voter)hpSession.getUser();
       i = 0;
       String candName = "";
       String candParty = "";
       String userED = "";
       String ballotED = "";
       ElectoralDistrict ballotEDist = null;
       while(i < ballots.size()){
     	   userED = currentVoter.getElectoralDistrict().getName();
    	   ballotEDist = logicLayer.findED(ballots.get(i));
    	   ballotED = ballotEDist.getName();
    	   if(userED.equals(ballotED){  
    		   %>
      <table class="table table-hover">
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          	<td><%= "Ballot ID: " + ballots.get(i).getId() %></td>
          	<td><%= "Open Date: " + ballots.get(i).getOpenDate() %></td>
          	<td><%= "Close Date: " + ballots.get(i).getCloseDate() %></td>
        </tr>
        <%List<BallotItem> items = logicLayer.findBallotItems(ballots.get(i));
          	  for (int j = 0; j < items.size();j++){
          		  String balItemName = "";
          		  if(items.get(j) instanceof Issue){
          			  Issue issue = (Issue)items.get(j);
          			  %>
          		<tr data-toggle="modal" data-target="#tableElement">
          	<td><%= "Issue: " + issue.getQuestion() %></td>
        				</tr>
        <% 
          		  }
          		  else if (items.get(j) instanceof Election){
          			  Election election = (Election)items.get(j);
          			  %>	   
          			  <tr data-toggle="modal" data-target="#tableElement">
          			  <td><%= "Election Office: " + election.getOffice() %></td>
          		      <% 
          		      	List<Candidate> candidates = election.getCandidates();
          		      	for (int k = 0; k < candidates.size(); k++){
          		      		candName = candidates.get(k).getName();
          		      		candParty = candidates.get(k).getPoliticalParty().getName();
          		      %>
          		      <td><%= "Candidate " + k + ": " + candName + "\n" + candParty %></td>
          		      <% 
          		      	}
          		      %>
          		    
        				</tr>
       			 <% 
          			  
          		  }
          		  
          	  }   	
          	i++;
          		%>
      </tbody>
    </table>
    <%	   }
       }
      %>
  </div>

<button type = "button" data-toggle="modal" data-target= "#vote" >Vote</button>

<div id="vote" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Add Election</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action = "Vote">
            <label for="ballotId" class="sr-only">Ballot Id</label>
          <br>  Select Ballot<br>
            <%
            List<Ballot> ballots = logicLayer.findAllBallot();
        i=0;
      %>
      
      <% while(i < ballots.size()) {%>
          	<input type = "radio" name = "choseBallot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%} %>
           
			<input type = "hidden" name = "todo" value = "addElection">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">vote</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>


  <!-- Election -->
  <div id="Results" class="container">
    <h3>Results</h3>
    <%
        List<Ballot> ballotList = logicLayer.findAllBallot();
        Voter currentVoter2 = (Voter)hpSession.getUser();
       int  ii=0;
       int candVoteCount = 0;
       String candName2 = "";
       Date closeDate;
       Date dateCurrent = new Date();
       DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
       df.format(dateCurrent);
       String userED2 = "";
       String ballotED2 = "";
       ElectoralDistrict ballotEDist2 = null;
       while(ii < ballotList.size()){
       	   userED2 = currentVoter2.getElectoralDistrict().getName();
    	   ballotEDist2 = logicLayer.findED(ballotList.get(ii));
    	   ballotED2 = ballotEDist2.getName();
    	   if(userED2.equals(ballotED2){
    		 closeDate = ballotList.get(ii).getCloseDate();
    		 if(closeDate.before(dateCurrent)){
    		   %>
      <table class="table table-hover">
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          	<td><%= "Ballot ID: " + ballotList.get(ii).getId() %></td>
          	<td><%= "Open Date: " + ballotList.get(ii).getOpenDate() %></td>
          	<td><%= "Close Date: " + ballotList.get(ii).getCloseDate() %></td>
        </tr>
        <%List<BallotItem> items = logicLayer.findBallotItems(ballotList.get(ii));
          	  for (int j = 0; j < items.size();j++){
          		  String balItemName = "";
          		  if(items.get(j) instanceof Issue){
          			  Issue issue = (Issue)items.get(j);
          			  %>
          		<tr data-toggle="modal" data-target="#tableElement">
          	<td><%= "Issue: " + issue.getQuestion() %></td>
          	<td><%= "Vote Count: " + issue.getYesCount() %></td>
        				</tr>
        <% 
          		  }
          		  else if (items.get(j) instanceof Election){
          			  Election election = (Election)items.get(j);
          			  %>	   
          			  <tr data-toggle="modal" data-target="#tableElement">
          			  <td><%= "Election Office: " + election.getOffice() %></td>
          		      <% 
          		      	List<Candidate> candidates = election.getCandidates();
          		      	for (int k = 0; k < candidates.size(); k++){
          		      		candName2 = candidates.get(k).getName();
          		      		candVoteCount = candidates.get(k).getVoteCount();
          		      %>
          		      <td><%= candName2 +" VoteCount: " + candVoteCount %></td>
          		      <% 
          		      	}
          		      %>
          		    
        				</tr>
       			 <% 
          			  
          		  }
          		  
          	  }   	
          	ii++;
          		%>
      </tbody>
    </table>
    <%	 }  }
       }
      %>
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

  <div id="tableElement" class="modal fade" role="dialog">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Table Element</h1>
        </div>
        <div class="modal-body">
          Details Here
        </div>
      </div>
    </div>
  </div>

</body>
</html>
