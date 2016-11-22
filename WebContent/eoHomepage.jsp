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
    #Election {padding-top:50px;height:100vh;}
    #Issue {padding-top:50px;height:100vh;}
    #District {padding-top:50px;height:100vh;}
    #Party {padding-top:50px;height:100vh;}

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

  <!-- Election -->
  <div id="Election" class="container">
    <h3>Election</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
          <th>Date</th>
          <th>Details</th>
        </tr>
      </thead>
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>Trump VS Hillary</td>
          <td>11-11-2016</td>
          <td>some details</td>
        </tr>
      </tbody>
    </table>
  </div>

  <!-- Issue -->
  <div id="Issue" class="container">
    <h3>Issue</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Name</th>
          <th>Date</th>
          <th>Details</th>
        </tr>
      </thead>
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>Can we get curve?</td>
          <td>10-18-2016</td>
          <td>Pleaseeeee</td>
        </tr>
      </tbody>
    </table>
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
          Details Here
        </div>
      </div>
    </div>
  </div>
  
  
<div id="createED" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create District</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="CreateED">
            <label for="districtName" class="sr-only">District Name</label>
            <input name ="districtName" type="text" class="form-control" placeholder="District Name" required=true autofocus=true>


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
          <form class="form-signin" action = "UpdateED" method = "post">
            <label for="districtName" class="sr-only">District Name</label>
            <input type="text" name="districtName" class="form-control" placeholder="District Name" required=true autofocus=true>

            <label for="newDistrictName" class="sr-only">New District Name</label>
            <input type="text" name="newDistrictName" class="form-control" placeholder="New District Name" required=true autofocus=true>

            <div class="modal-footer">
              <button class="btn btn-lg btn-primary" type="submit">Update</button>
              <button class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>
    </div>
</div>

<div id="createPP" class="modal fade" role="dialog">
    <div class="modal-dialog">

      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h1 class="modal-title text-center">Create Political Party</h1>
        </div>
        <div class="modal-body">
          <form class="form-signin" method ="post" action ="CreatePP">
            <label for="politicalPartyName" class="sr-only">Political Party Name</label>
            <input name ="politicalPartyName" type="text" class="form-control" placeholder="Political Party Name" required=true autofocus=true>


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
          <form class="form-signin" action = "UpdateED" method = "post">
            <label for="politicalPartyName" class="sr-only">Political Party Name</label>
            <input type="text" name="politicalPartyName" class="form-control" placeholder="Political Party Name" required=true autofocus=true>

            <label for="newPoliticalPartyName" class="sr-only">New Political Party Name</label>
            <input type="text" name="newPoliticalPartyName" class="form-control" placeholder="New Political Party Name" required=true autofocus=true>

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
