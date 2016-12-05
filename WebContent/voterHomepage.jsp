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

<button type = "button" data-toggle="modal" data-target= "#vote" >Vote</button>
  <!-- CURRENT -->
  <div id="Current" class="container">
     <h3>Current</h3>
    <%
        List<Ballot> ballots = logicLayer.findAllBallot();
        Voter currentVoter = (Voter)hpSession.getUser();
	ElectoralDistrict edVoter = null;
       i = 0;
       String candName = "";
       String candParty = "";
       String userED = "";
       String ballotED = "";
       ElectoralDistrict ballotEDist = null;
       while(i < ballots.size()){

//	edVoter = logicLayer.getElectoralDistrictFromVoter(currentVoter);
//     	userED = edVoter.getName();
//        ballotEDist = logicLayer.findED(ballots.get(i));
//    	   ballotED = ballotEDist.getName();
 //   	   if(userED.equals(ballotED)){  

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
    <%	 //  }
       }
      %>
  </div>



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
        ElectoralDistrict ballotEDistrict = null;
        Voter currentVoter3 = (Voter)hpSession.getUser();
        ElectoralDistrict eDistrictVoter = null;
        i=0;
        String userEDD = "";
        String ballotEDD= "";
      %>
      
      <% while(i < ballots.size()) {
   // 	eDistrictVoter = logicLayer.getElectoralDistrictFromVoter(currentVoter3);
    //   	userEDD = eDistrictVoter.getName();
   //     ballotEDistrict = logicLayer.findED(ballots.get(i));
   //   	ballotEDD = ballotEDistrict.getName();
    //  	   if(userEDD.equals(ballotEDD)){ 
      
      %>
          	<input type = "radio" name = "choseBallot" value = "<%= ballots.get(i).getId() %>"> <%= ballots.get(i++).getId()%> <br>
          	
      <%//}
    } %>
           
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
       ElectoralDistrict edVoter2 = null;
       while(ii < ballotList.size()){
	     edVoter2 = logicLayer.getElectoralDistrictFromVoter(currentVoter2);
	     System.out.println(edVoter2);
       	     userED2 = edVoter2.getName();
    	     ballotEDist2 = logicLayer.findED(ballotList.get(ii));
    	     ballotED2 = ballotEDist2.getName();
    	     if(userED2.equals(ballotED2)){

    		// closeDate = ballotList.get(ii).getCloseDate();
    		// if(closeDate.before(dateCurrent)){
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
          		      	List<Candidate> candidates = logicLayer.getObjectLayer().getPersistence().restoreCandidateIsCandidateInElection(election);
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
    <%	 }  
    		// }
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
        
        <%
        Voter account = (Voter)hpSession.getUser();
        String passwordLength = "";
        for(int n=0; n<account.getPassword().length(); n++){
			passwordLength = passwordLength + "*";
		
        }
       	System.out.println(account.getUserName());
        %>
        <script>
			function editAccount() {
				
   				 document.getElementById("fnamedisplay").style.display = 'none';
   				 document.getElementById("lnamedisplay").style.display = 'none';
   				document.getElementById("usernamedisplay").style.display = 'none';
   				 document.getElementById("passworddisplay").style.display = 'none';
   				 document.getElementById("agedisplay").style.display = 'none';
   				 document.getElementById("emaildisplay").style.display = 'none';
   				 document.getElementById("addressdisplay").style.display = 'none';
   				 
   				 document.getElementById("fname").style.display = 'block';
   				 document.getElementById("lname").style.display = 'block';
   				document.getElementById("username").style.display = 'block';
   				 document.getElementById("password").style.display = 'block';
   				 document.getElementById("age").style.display = 'block';
   				 document.getElementById("email").style.display = 'block';
   				 document.getElementById("address").style.display = 'block';
   				 
  				 document.getElementById("editOption").style.display = 'none';
  				 document.getElementById("submitChange").style.display = 'block';
  				 document.getElementById("cancelChange").style.display = 'block';

			}
			
			function cancelEdit(){
  				 document.getElementById("fnamedisplay").style.display = 'block';
   				 document.getElementById("lnamedisplay").style.display = 'block';
   				document.getElementById("usernamedisplay").style.display = 'block';
   				 document.getElementById("passworddisplay").style.display = 'block';
   				 document.getElementById("agedisplay").style.display = 'block';
   				 document.getElementById("emaildisplay").style.display = 'block';
   				 document.getElementById("addressdisplay").style.display = 'block';
   				 
   				 document.getElementById("fname").style.display = 'none';
   				 document.getElementById("lname").style.display = 'none';
   				document.getElementById("username").style.display = 'none';
   				 document.getElementById("password").style.display = 'none';
   				 document.getElementById("age").style.display = 'none';
   				 document.getElementById("email").style.display = 'none';
   				 document.getElementById("address").style.display = 'none';
  				 
  				 document.getElementById("editOption").style.display = 'block';
  				 document.getElementById("submitChange").style.display = 'none';
  				 document.getElementById("cancelChange").style.display = 'none';

			}
			function reload(){
				<% account = (Voter)hpSession.getUser(); %>
				document.getElementById("fnamedisplay").innerHTML = '<%=account.getFirstName()%>';
  				document.getElementById("lnamedisplay").innerHTML = '<%=account.getLastName()%>';
  				document.getElementById("usernamedisplay").innerHTML = '<%=account.getUserName()%>';
  				document.getElementById("passworddisplay").innerHTML = '<%=account.getPassword()%>';
  				document.getElementById("agedisplay").innerHTML = '<%=account.getAge()%>';
  				document.getElementById("emaildisplay").innerHTML = '<%=account.getEmailAddress()%>';
  				document.getElementById("addressdisplay").innerHTML = '<%=account.getAddress()%>';
  				document.forms["account"].submit();
			}
		</script>
        <div class="modal-body">
          <form id="account" class="form-signin" action="VoterUpdate" method = "post">
            <label for="fname">First Name: </label>
            <p id="fnamedisplay"><%=account.getFirstName()%></p>
			<input type="text" id="fname" name="fname" class="form-control" style="display:none;">
			

            <label for="lname">Last Name: </label>
            <p id="lnamedisplay"><%=account.getLastName()%></p>
            <input type="text" id="lname" name="lname" class="form-control" style="display:none;"> 
            
            <br>
            <label for="username">Username: </label>
            <p id="usernamedisplay"><%=account.getUserName()%></p>
            <input type="text" id="username" name="username" class="form-control" style="display:none;"> 
            
            
            <br>
            <label for="password">Password: </label>
            <p id="passworddisplay"><%=passwordLength%></p>
            <input type="text" id="password" name="password" class="form-control" style="display:none;">
 			
 			<br>
 			<label for="age">Age: </label>
            <p id="agedisplay"><%=account.getAge()%></p>
            <input type="text" id="age" name="age" class="form-control" style="display:none;">
 
 
            <br>
            <label for="email">Email: </label>
            <p id="emaildisplay"><%=account.getEmailAddress()%></p>
            <input type="text" id="email" name="email" class="form-control" style="display:none;">
            
            <br>
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

 

</body>
</html>
