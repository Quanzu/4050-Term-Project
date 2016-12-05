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
  
  <script type="text/javascript">

      function loadXMLDoc()
      {
        var xmlhttp;
        var config=document.getElementsByName('configselect').value;
        var url="eoHomepage.jsp";
        if (window.XMLHttpRequest)
        {
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                document.getElementById("myDiv").innerHTML=xmlhttp.responseText;
            }
        }

        xmlhttp.open("GET", url, true);
        xmlhttp.send();
      }
        </script>
  
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
          <li><a href="#Ballot">Ballot</a></li>
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
	  <%
        List<Ballot> ballots = logicLayer.findAllBallot();
        i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>ID</th>
          <th>Open date</th>
          <th>Close date</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < ballots.size()) {%>
      	<tr id=ed<%=i%>>
          	<td><%= ballots.get(i).getId() %></td>
          	<td><%= ballots.get(i).getOpenDate() %></td>
          	<td><%= ballots.get(i++).getCloseDate() %></td>
        </tr>
      <%} %>
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createBallot"></span>
      	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateBallot"></span> 
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteBallot"></span>
    </div>
    <div class="pull-left">
    	<button type = "button" data-toggle="modal" data-target= "#addElection" >Add Election</button> 
    	<button type = "button" data-toggle="modal" data-target= "#addIssue" >Add Issue</button>   
    </div>
  </div>

<!-- Target for ballot- add election and issue -->
<div id="addElection" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Add Election</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Ballot">
            <label for="ballotId" class="sr-only">Ballot Id</label>
          <br>  Select Ballot<br>
            <%
        i=0;
      %>
      
      <% while(i < ballots.size()) {%>
          	<input type = "radio" name = "ballot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%} %>
            Select Elections to add <br>
       <%
       List<Election> elections = logicLayer.findAllElection();
        i=0;
      %>
      
      <% while(i < elections.size()) {%>
          	<input type = "checkbox" name = "theElections" value = "<%= elections.get(i).getOffice() %>"> <%= elections.get(i++).getOffice()%> <br>
          	
      <%} %>
            
			<input type = "hidden" name = "todo" value = "addElection">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Add</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>

<div id="addIssue" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Add Issue</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Ballot">
            <label for="ballotId" class="sr-only">Ballot Id</label>
          <br>  Select Ballot<br>
            <%
        i=0;
      %>
      
      <% while(i < ballots.size()) {%>
          	<input type = "radio" name = "ballot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%} %>
            Select Issues to add <br>
       <%
       List<Issue> issues = logicLayer.findAllIssue();
        i=0;
      %>
      
      <% while(i < issues.size()) {%>
          	  <input type = "checkbox" name = "theIssues" value = "<%= issues.get(i).getQuestion() %>"> <%= issues.get(i++).getQuestion()%>
          	 <br>
          	
      <%} %>
            
			<input type = "hidden" name = "todo" value = "addIssue">

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Add</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
	</div>
</div>


  <!-- Election -->
  <div id="Election" class="container">
    <h3>Election</h3>
     
      <%
        
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
    	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateElection"></span>
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteElection"></span>
    </div>

    	 
    </div>	 

  <!-- Issue -->
  <div id="Issue" class="container">
    <h3>Issue</h3>

      <%
        
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
        <span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createIssue"></span>
    	<span class="btn glyphicon glyphicon-pencil" data-toggle="modal" data-target="#updateIssue"></span>
    	<span class="btn glyphicon glyphicon-trash" data-toggle="modal" data-target="#deleteIssue"></span>
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
        Candidate tempCandidate;
        PoliticalParty tempParty;
      	i=0;
      %>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
          <th>Party</th>
        </tr>
      </thead>
      <tbody>
      <% while(i < candidates.size()) {
      		tempCandidate = candidates.get(i++);
      		tempParty = logicLayer.getPoliticalPartyFromCandidate(tempCandidate);%>
      		<tr id=candidate<%=i%>>
          		<td><%= tempCandidate.getName() %></td>
          		<%
          		if(tempParty != null){
          		%>
          		<td><%= tempParty.getName()%></td>
          		<%}else{ %>
          		<td>N/A</td>
          		<%} %>
        	</tr>
      <%} %>
      
      </tbody>
    </table>
    
    <div class="pull-right">
    	<span class="btn glyphicon glyphicon-plus" data-toggle="modal" data-target="#createCand"></span>
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
        
        <%
        ElectionsOfficer account = (ElectionsOfficer)hpSession.getUser();
        String passwordLength = "";
        for(int n=0; n<account.getPassword().length(); n++){
			passwordLength = passwordLength + "*";
        }        
        %>
        <script>
			function editAccount() {
   				 document.getElementById("fnamedisplay").style.display = 'none';
   				 document.getElementById("lnamedisplay").style.display = 'none';
   				 document.getElementById("passworddisplay").style.display = 'none';
   				 document.getElementById("emaildisplay").style.display = 'none';
   				 document.getElementById("addressdisplay").style.display = 'none';
   				 
   				 document.getElementById("fname").style.display = 'block';
   				 document.getElementById("lname").style.display = 'block';
   				 document.getElementById("password").style.display = 'block';
   				 document.getElementById("email").style.display = 'block';
   				 document.getElementById("address").style.display = 'block';
   				 
  				 document.getElementById("editOption").style.display = 'none';
  				 document.getElementById("submitChange").style.display = 'block';
  				 document.getElementById("cancelChange").style.display = 'block';

			}
			
			function cancelEdit(){
  				 document.getElementById("fnamedisplay").style.display = 'block';
   				 document.getElementById("lnamedisplay").style.display = 'block';
   				 document.getElementById("passworddisplay").style.display = 'block';
   				 document.getElementById("emaildisplay").style.display = 'block';
   				 document.getElementById("addressdisplay").style.display = 'block';
   				 
   				 document.getElementById("fname").style.display = 'none';
   				 document.getElementById("lname").style.display = 'none';
   				 document.getElementById("password").style.display = 'none';
   				 document.getElementById("email").style.display = 'none';
   				 document.getElementById("address").style.display = 'none';
  				 
  				 document.getElementById("editOption").style.display = 'block';
  				 document.getElementById("submitChange").style.display = 'none';
  				 document.getElementById("cancelChange").style.display = 'none';

			}
			function reload(){
				<% account = (ElectionsOfficer)hpSession.getUser(); %>
				document.getElementById("fnamedisplay").innerHTML = '<%=account.getFirstName()%>';
  				document.getElementById("lnamedisplay").innerHTML = '<%=account.getLastName()%>';
  				document.getElementById("passworddisplay").innerHTML = '<%=account.getPassword()%>';
  				document.getElementById("emaildisplay").innerHTML = '<%=account.getEmailAddress()%>';
  				document.getElementById("addressdisplay").innerHTML = '<%=account.getAddress()%>';
  				document.forms["account"].submit();
			}
		</script>
        <div class="modal-body">
          <form id="account" class="form-signin" action="ElectionsOfficer" method = "post">
            <label for="fname">First Name: </label>
            <p id="fnamedisplay"><%=account.getFirstName()%></p>
			<input type="text" id="fname" name="fname" class="form-control" style="display:none;">
			

            <label for="lname">Last Name: </label>
            <p id="lnamedisplay"><%=account.getLastName()%></p>
            <input type="text" id="lname" name="lname" class="form-control" style="display:none;"> 
            
            <br>
            <label for="username">Username: </label>
            <p id="usernamedisplay"><%=account.getUserName()%></p>
            
            <label for="username">Password: </label>
            <p id="passworddisplay"><%=passwordLength%></p>
            <input type="text" id="password" name="password" class="form-control" style="display:none;">
 
            <br>
            <label for="email">Email: </label>
            <p id="emaildisplay"><%=account.getEmailAddress()%></p>
            <input type="text" id="email" name="email" class="form-control" style="display:none;">
            
            
            <label for="address">Address: </label>
            <p id="addressdisplay"><%=account.getAddress()%></p>
            <input type="text" id="address" name="address" class="form-control" style="display:none;">

			<input type = "hidden" name = "todo" value = "updateProfile">
            
            <div class="modal-footer" id="submitChange" style="display:none;">
          		<input class="btn btn-lg btn-primary" type="button" onclick="reload()" value="Submit">
          	</div>
          </form>

          <div class="modal-footer" id="cancelChange" style="display:none;">
            <span><button class="btn btn-lg btn-primary" onclick="cancelEdit()">Cancel</button></span>
          </div>
          <div class="modal-footer" id="editOption">
              <button class="btn btn-lg btn-primary" onclick="editAccount() ">Edit</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
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
            <label for="electionOffice">Election Office: </label> <br>
            <input name ="electionOffice" type="text" class="form-control">
            <br>
     
            <label for="isPartisan">Partisan: </label>
            <input type = "radio" name = "isPartisan" id = "true" value = "true" onclick = "toggleElectionPartisan()"> True
            <input type = "radio" name = "isPartisan" id = "false" value = "false" onclick = "toggleElectionPartisan()" checked> False <br>
       	
            <script>
           		function toggleElectionPartisan(){
           			if(document.getElementById("partisanCandidateNames").style.display == 'none'){
           				document.getElementById("partisanCandidateNames").style.display = 'block';
           				document.getElementById("notPartisanCandidateNames").style.display = 'none';
           			}else{
           				document.getElementById("partisanCandidateNames").style.display = 'none';
           				document.getElementById("notPartisanCandidateNames").style.display = 'block';
           			}
           		}
            </script>
            
            <div id = "partisanCandidateNames" style = "display: none;">
            <%
            List<Candidate> c = logicLayer.findAllCandidate();
            i=0;
    		while(i < c.size())
    		{
    			PoliticalParty temp = logicLayer.getPoliticalPartyFromCandidate(c.get(i));
    			if (temp != null)
    			{
				 %><input type = "checkbox" name = "cand" value = "<%=c.get(i).getName()%>"> <%=c.get(i).getName()%> <br>
    	    <%	
    			}
    			i++;
    	    }%>
            
            </div>
            
            <div id = "notPartisanCandidateNames">
             <%
            
            i=0;
    		while(i < c.size())
    		{
    			PoliticalParty temp = logicLayer.getPoliticalPartyFromCandidate(c.get(i));
    			
    			if (temp == null)
    			{
				 %><input type = "checkbox" name = "cand" value = "<%=c.get(i).getName()%>"> <%=c.get(i).getName()%> <br>
    	    <%	
    			}
    			i++;
    	    }%>
            
            </div>
            
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
 

 <div id="updateElection" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Update Election</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Election" method = "post">
            <label for="newOfficeName">New Office Name: </label>
            <input type="text" name="newOfficeName" class="form-control">
            
            
            <label for="addCandidatesToElections">Add Candidate: </label> <br>
			<%
			List<Candidate> candidateList = logicLayer.findAllCandidate();
			i=0;
			while(i<candidateList.size()){
				tempBallot = logicLayer.getBallotFromIssue(allIssues.get(i));
				if(tempBallot == null){
					%><input type="checkbox" name="issues" value = "<%=allIssues.get(i).getQuestion()%>"> <%=allIssues.get(i).getQuestion()%> <br>
					<%
				}
				i++;
			}
			%>
            
            
            
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
            <label for="candidateName">Candidate Name: </label>
            <input id = "candidateName" name ="candidateName" type="text" class="form-control" placeholder="Candidate Name" required=true>
            <br>
            <label for="electionName">Election Name: </label>
            <input name ="electionName" type="text" class="form-control" placeholder="Election Name" required=true>
            <br>
            
            <label for="isPartisan" >Partisan: </label>
            <input type = "radio" name = "isPartisan" value = "true" onclick="togglePartisan()"> True
            <input type = "radio" name = "isPartisan" value = "false" onclick="togglePartisan()" checked> False
            <br>
            <br>
            
            <script>
           		function togglePartisan(){
           			if(document.getElementById("partisanPartyNames").style.display == 'none'){
           				document.getElementById("partisanPartyNames").style.display = 'block';
           			}else{
           				document.getElementById("partisanPartyNames").style.display = 'none'
           			}
           		}
            </script>
            
            <div id="partisanPartyNames" style="display:none;">
	            <label for="partyName">Party Names: </label>
	            <br>
	             <%
	       		 List<PoliticalParty> temp = logicLayer.findAllPoliticalParty();
	        	i=0;
	            		while(i < temp.size())
	            		{
	        				 %><input type = "radio" name = "partyName" value = "<%=temp.get(i).getName()%>"> <%=temp.get(i).getName()%> <br>
	            	    <%	i++;
	            	    }%>
     	
            </div>
            
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
<div id="createIssue" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create Issue</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Issue" method = "post">

            <label for="question">Question: </label>
            <input type="text" name="question" class="form-control" required=true>
			
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

<div id="deleteIssue" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Issue</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" action = "Issue" method = "post">

			<label for="issueId">Issue Id: </label>
            <input type="text" name="issueId" class="form-control">
			
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

<!-- BALLOT -->
<div id="createBallot" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create Ballot</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Ballot">
            <label for="openDate">Open Date: </label>
            <input name ="openDate" type="date" class="form-control" placeholder="yyyy-mm-dd">
            <br>
            <label for="openDate">Close Date: </label>
			<input name ="closeDate" type="date" class="form-control" placeholder="yyyy-mm-dd">
			<br>
			
			<label for="districtName">District: </label> <br>
             <%
       		 List<ElectoralDistrict> x = logicLayer.findAllElectoralDistrict();
        	i=0;
            		while(i < x.size())
            		{
        				 %><input type = "radio" name = "districtName" value = "<%=x.get(i).getName()%>"> <%=x.get(i).getName()%> <br>
            	    <%	i++;
            	    }%>
			
			<br>
			<label for="elections">Election: </label> <br>
			<%
			List<Election> allElections = logicLayer.findAllElection();
			i=0;
			Ballot tempBallot = null;
			while(i<allElections.size()){
				tempBallot = logicLayer.getBallotFromElection(allElections.get(i));
				if(tempBallot == null){
					%><input type="checkbox" name="elections" value = "<%=allElections.get(i).getOffice()%>"> <%=allElections.get(i).getOffice()%> <br>
					<%
				}
				i++;
			}
			%>
			
			<br>
			<label for="issues">Issue: </label> <br>
			<%
			List<Issue> allIssues = logicLayer.findAllIssue();
			i=0;
			while(i<allElections.size()){
				tempBallot = logicLayer.getBallotFromIssue(allIssues.get(i));
				if(tempBallot == null){
					%><input type="checkbox" name="issues" value = "<%=allIssues.get(i).getQuestion()%>"> <%=allIssues.get(i).getQuestion()%> <br>
					<%
				}
				i++;
			}
			%>
			
			
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

<div id="updateBallot" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Update Ballot</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Ballot">
            <label for="ballotId" class="sr-only">Ballot Id</label>
            
            <%
        i=0;
      %>
      
      <% while(i < ballots.size()) {%>
          	<input type = "radio" name = "ballot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%} %>
            
            <label for="openDate" class="sr-only">Ballot Dates</label>
            New Ballot Dates
            <input name ="newOpenDate" type="date" class="form-control" placeholder="year(xx)-day-month" required=true autofocus=true>
			 <input name ="newCloseDate" type="date" class="form-control" placeholder="year(xx)-day-month" required=true autofocus=true>
			
            
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

<div id="deleteBallot" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Delete Ballot</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="Ballot">
            <label for="ballotId" class="sr-only">Ballot Name</label>
            
            <%
        List<Ballot> tempBallot2 = logicLayer.findAllBallot();
        i=0;
      %>
      
      <% while(i < ballots.size()) {%>
          	<input type = "radio" name = "ballot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%} %>
            
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

</body>
</html>
