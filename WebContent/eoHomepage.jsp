<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        z-index: 9998 !important;
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
    #Ballot {padding-top:50px;height:100vh;}    
    #Election {padding-top:50px;height:100vh;}
    #Issue {padding-top:50px;height:100vh;}
    #District {padding-top:50px;height:100vh;}
    #Party {padding-top:50px;height:100vh;}
    #Candidate {padding-top:50px;height:100vh;}

  </style>
</head>
<body data-spy="scroll" data-target=".navbar" data-offset="50">

<div class="container-fluid">
  <div class="jumbotron">
    <h1 class="text-center">eVote</h1>
    <p>Welcome Officer!</p>
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
    </button>
    </div>

    <div>
      <div class="collapse navbar-collapse" id="myNavbar">
        <ul class="nav navbar-nav">
          <li><a href="#Current">Current</a></li>
          <li><a href="#Election">Election</a></li>
          <li><a href="#Issue">Issue</a></li>
          <li><a href="#District">District</a></li>
          <li><a href="#Party">Party</a></li>
          <li><a href="#Candidate">Candidate</a></li>

          <li><a href="#" data-toggle="modal" data-target="#accountModal">Account</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>

  <!-- CURRENT -->
  <div id="Current" class="container">
    <h3>Current</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Election/Issue</th>
          <th>Name</th>
          <th>Date</th>
        </tr>
      </thead>
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>Election</td>
          <td>Trump VS Hillary</td>
          <td>11-11-2016</td>
        </tr>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>Issue</td>
          <td>Can we get a curve?</td>
          <td>10-18-2016</td>
        </tr>
      </tbody>
    </table>
  </div>

  <!-- BALLOT -->
  <div id="Ballot" class="container">
    <h3>Ballot</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Open date</th>
          <th>Close date</th>
        </tr>
      </thead>
      <tbody>

      </tbody>
    </table>
  </div>

  <!-- Election -->
  <div id="Election" class="container">
    <h3>Election</h3>
     
      <%
        List<Election> elections = logicLayer.findAllElection();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < elections.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= elections.get(i++).getOffice() %></td>
        </tr>
      <%} %>
      </tbody>
    </table>
     
     <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createElection"></span>
    <!--  	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateCand"></span> -->
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteElection"></span>
    </div>
  </div>

  <!-- Issue -->
  <div id="Issue" class="container">
    <h3>Issue</h3>

      <%
        List<Issue> issues = logicLayer.findAllIssue();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Question</th>
          <th>Yes Count</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < issues.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= issues.get(i).getId() %></td>
          	<td><%= issues.get(i).getQuestion() %></td>
          	<td><%= issues.get(i++).getYesCount() %></td>          	
        </tr>
      <%} %>
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateIssue"></span>
    </div>

  </div>

  <!-- District -->
  <div id="District" class="container">
    <h3>District</h3>

      <%
        List<ElectoralDistrict> districts = logicLayer.findAllElectoralDistrict();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < districts.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= districts.get(i++).getName() %></td>
        </tr>
      <%} %>
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createED"></span>
    	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateED"></span>
   	    <span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteED"></span>
    </div>
  </div>



  <!-- Party -->
  <div id="Party" class="container">
    <h3>Party</h3>
      <%
        List<PoliticalParty> parties = logicLayer.findAllPoliticalParty();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < parties.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= parties.get(i++).getName() %></td>
        </tr>
      <%} %>
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createPP"></span>
    	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updatePP"></span>
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deletePP"></span>
    </div>
  </div>

<!-- CANDIDATE -->
<div id="Candidate" class="container">
    <h3>Candidate</h3>
      <%
        List<Candidate> candidates = logicLayer.findAllCandidate();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < candidates.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= candidates.get(i++).getName() %></td>
        </tr>
      <%} %>
      
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createCand"></span>
    <!--  	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateCand"></span> -->
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteCand"></span>
    </div>
  </div>


  <div id="accountModal" class="modal fade" role="dialog">
    
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Account Information</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "ElectionsOfficerUpdate" method = "post">
            
            <label for="fname" class="sr-only">New First Name</label>
            <input type="text" name="fName" class="form-control" placeholder="New First Name" required=false autofocus=true>

            <label for="lname" class="sr-only">New Last Name</label>
            <input type="text" name="fName" class="form-control" placeholder="New Last Name" required=false autofocus=true> 
            
            <label for="username" class="sr-only">New Username</label>
            <input type="text" name="fName" class="form-control" placeholder="New Username" required=false autofocus=true>
            
            <label for="password" class="sr-only">New Password</label>
            <input type="text" name="password" class="form-control" placeholder="New Password" required=false autofocus=true>
            
            <label for="email" class="sr-only">New Email</label>
            <input type="text" name="email" class="form-control" placeholder="New Email" required=false autofocus=true>
            
            <label for="street" class="sr-only">New Street</label>
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
        </div>
      </div>
    </div>
  </div>
        </div>
      </div>
    </div>
  </div>
  
  <!-- ELECTION -->
  <div id="createElection" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create Election</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Election">
            <label for="electionOffice" class="sr-only">Election Office</label>
            Election Office <br>
            <input id = "electionOffice" name ="electionOffice" type="text" class="form-control" placeholder="election Office Name" required=true autofocus=true>
            
     
            Is Election Partisan?
            <label for="isPartisan" class="sr-only">Is Election Partisan?</label>
            <input type = "radio" name = "isPartisan" value = "true">True
            <input type = "radio" name = "isPartisan" value = "false" checked>False <br>
   <!--       <input name ="isPartisan" type="text" class="form-control" placeholder="True or False" required=true autofocus=true>  -->   
       	
           
            
			<input type = "hidden" name = "todo" value = "create">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Create</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>
  
  
 <div id="deleteElection" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Election</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Election" method = "post">
            <label for="electionOffice" class="sr-only">Election</label>
            <input type="text" name="electionOffice" class="form-control" placeholder="Election Name" required=true autofocus=true>
            <input type = "hidden" name = "todo" value = "delete">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Delete</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div> 

<!-- ELECTORAL DISTRICT -->  
<div id="createED" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create District</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="ElectoralDistrict">
            <label for="districtName" class="sr-only">District Name</label>
            <input name ="districtName" type="text" class="form-control" placeholder="District Name" required=true autofocus=true>
			<input type = "hidden" name = "todo" value = "create">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Create</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>

 <div id="updateED" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Update District</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "ElectoralDistrict" method = "post">
            <label for="districtName" class="sr-only">District Name</label>
            <input type="text" name="districtName" class="form-control" placeholder="District Name" required=true autofocus=true>

            <label for="newDistrictName" class="sr-only">New District Name</label>
            <input type="text" name="newDistrictName" class="form-control" placeholder="New District Name" required=true autofocus=true>
			<input type = "hidden" name = "todo" value = "update">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>

<div id="deleteED" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Electoral District</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "ElectoralDistrict" method = "post">
            <label for="districtName" class="sr-only">Electoral District Name</label>
            <input type="text" name="districtName" class="form-control" placeholder="Electoral District Name" required=true autofocus=true>
            <input type = "hidden" name = "todo" value = "delete">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>

<!--  Political Parties -->
<div id="createPP" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create Political Party</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="PoliticalParty">
            <label for="politicalPartyName" class="sr-only">Political Party Name</label>
            <input name ="politicalPartyName" type="text" class="form-control" placeholder="Political Party Name" required=true autofocus=true>
			<input type = "hidden" name = "todo" value = "create">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Create</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>


 <div id="updatePP" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Update Political Party</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "PoliticalParty" method = "post">
            <label for="politicalPartyName" class="sr-only">Political Party Name</label>
            <input type="text" name="politicalPartyName" class="form-control" placeholder="Political Party Name" required=true autofocus=true>

            <label for="newPoliticalPartyName" class="sr-only">New Political Party Name</label>
            <input type="text" name="newPoliticalPartyName" class="form-control" placeholder="New Political Party Name" required=true autofocus=true>
			<input type = "hidden" name = "todo" value = "update">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>

<div id="deletePP" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Political Party</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "PoliticalParty" method = "post">
            <label for="politicalPartyName" class="sr-only">Political Party Name</label>
            <input type="text" name="politicalPartyName" class="form-control" placeholder="Political Party Name" required=true autofocus=true>

          
			<input type = "hidden" name = "todo" value = "delete">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>

<!-- CANDIDATE -->
<div id="createCand" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create District</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Candidate">
            <label for="candidateName" class="sr-only">Candidate Name</label>
            Candidate Name <br>
            <input id = "candidateName" name ="candidateName" type="text" class="form-control" placeholder="Candidate Name" required=true autofocus=true>
            
            Election Name <br>
            <label for="electionName" class="sr-only">Election Name</label>
            <input name ="electionName" type="text" class="form-control" placeholder="Election Name" required=true autofocus=true>
            
            Is Candidate Partisan?
            <label for="isPartisan" class="sr-only">Is Candidate Partisan?</label>
            <input type = "radio" name = "isPartisan" value = "true">True
            <input type = "radio" name = "isPartisan" value = "false" checked>False <br>
   <!--       <input name ="isPartisan" type="text" class="form-control" placeholder="True or False" required=true autofocus=true>  -->   
            
            <label for="partyName" class="sr-only">Party Name</label>
     		Party Name (if checked true for partisan) <br>
             <%
       		 List<PoliticalParty> temp = logicLayer.findAllPoliticalParty();
        	i=0;
            		while(i < temp.size())
            		{
        				 %><input type = "radio" name = "partyName" value = "<%=temp.get(i).getName()%>"><%=temp.get(i).getName()%> <br>
            	    <%	i++;
            	    }%>
     	
           <!--  <input name ="partyName" type="text" class="form-control" placeholder="Party Name" required=false autofocus=true>  -->
            
			<input type = "hidden" name = "todo" value = "create">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Create</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>


<div id="deleteCand" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Candidate</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Candidate" method = "post">
            <label for="candidateName" class="sr-only">Candidate Name to Delete</label>
            <input type="text" name="candidateName" class="form-control" placeholder="Candidate Name" required=true autofocus=true>
            
			<input type = "hidden" name = "todo" value = "delete">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Delete</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>


<!-- ISSUE -->
 <div id="updateIssue" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Update Issue</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Issue" method = "post">
            <label for="issueId" class="sr-only">Issue Id</label>
            <input type="text" name="issueId" class="form-control" placeholder="Issue Id" required=true autofocus=true>

            <label for="newQuestion" class="sr-only">New Question</label>
            <input type="text" name="newQuestion" class="form-control" placeholder="New Question" required=true autofocus=true>
			
			<label for="newYesCount" class="sr-only">New Yes Count</label>
            <input type="number" name="newYesCount" class="form-control" placeholder="New Yes Count" required=true autofocus=true>
			
			<input type = "hidden" name = "todo" value = "update">


            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>


</body>
</html>
