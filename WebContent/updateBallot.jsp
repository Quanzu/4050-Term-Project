<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8" import="java.sql.*"%>
<%ResultSet resultset = null; %>
    <%@ page import="edu.uga.cs.evote.session.Session" %>
    <%@ page import="edu.uga.cs.evote.session.SessionManager" %>
    <%@ page import="edu.uga.cs.evote.logic.LogicLayer" %>
    <%@ page import="java.util.List" %>
    <%@ page import="edu.uga.cs.evote.entity.*" %>
    

 <%
	String ssid = (String)session.getAttribute("ssid");
    Session hpSession = SessionManager.getSessionById(ssid);
    LogicLayer logicLayer = hpSession.getLogicLayer();
    int i;
 %>
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html> 
<head> 
<title>eVote</title>
  <meta charset="utf-8"> 
  <meta name="viewport" content="width=device-width, initial-scale=1"> 
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"> 
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script> 
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head> 
<body> 
 
<div class="container-fluid"> 
  <div class="jumbotron"> 
    <h2 class="text-center">Updating Election</h2> 
  </div> 
</div> 
 
<div class="container"> 
	<% 
	int ballotId = Integer.parseInt(request.getParameter("ballotId"));
	Ballot modelBallot = logicLayer.getObjectLayer().createBallot();
	List<Election> allElectionList = logicLayer.findAllElection();
	i=0;
	modelBallot.setId(ballotId);
	Ballot currentBallot = logicLayer.getObjectLayer().findBallot(modelBallot).get(0);
	%>
	
	<form class="form-signin" action = "Ballot" method = "post">
	
	<input type = "hidden" name = "ballotId" value = "<%=ballotId %>">
	
	<label for="newOpenDate">New Open Date: </label>
    <input name ="newOpenDate" type="date" class="form-control" placeholder="yyyy-mm-dd">
    <br>
    <label for="newCloseDate">New Close Date: </label>
	<input name ="newCloseDate" type="date" class="form-control" placeholder="yyyy-mm-dd">
	<br>	
	<br>
	
	<label for="addElectionToBallot">Add Election: </label> <br>
	<%
	List<Election> toShowAddElection = logicLayer.findAllElection();
	List<BallotItem> electionList = logicLayer.getObjectLayer().getPersistence().restoreBallotIncludesBallotItem(currentBallot);
	toShowAddElection.remove(electionList);
	Election tempElection;
	PoliticalParty tempParty;
	i=0;
	while(i < toShowAddElection.size()){
		tempParty = logicLayer.getPoliticalPartyFromCandidate(toShowAddCandidates.get(i));
		tempElection = logicLayer.getElectionFromCandidate(toShowAddCandidates.get(i));
		if(currentElection.getIsPartisan()){
			if(tempParty != null && tempElection == null){
				%><input type="checkbox" name="addElectionToBallot" value = "<%=toShowAddCandidates.get(i).getName()%>"> <%=toShowAddCandidates.get(i).getName()%> <br>
				<%
			}
		}else{
			if(tempParty == null & tempElection == null){
				%><input type="checkbox" name="addElectionToBallot" value = "<%=candidateList.get(i).getName()%>"> <%=candidateList.get(i).getName()%> <br>
				<%
			}
		}
		i++;
						
	}
	%>
	
	<br>
	<label for="removeCandidatesToElection">Remove Candidate: </label> <br>
   	<%
	i=0;
	while(i<candidateList.size()){
		%><input type="checkbox" name="addCandidatesToElection" value = "<%=candidateList.get(i).getName()%>"> <%=candidateList.get(i).getName()%> <br>
		<%
		i++;			
	}
	%>
	
	<input type = "hidden" name = "todo" value = "update">
	<br>
	<br>
	<button class="btn btn-lg btn-primary" type="submit">Submit</button>
	<a href="eoHomepage.jsp" class="btn btn-lg btn-primary">Close</button>
	
	</form>
</div>