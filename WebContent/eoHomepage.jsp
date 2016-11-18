<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
      
      <div class="btn-group btn-group-justified" role="group">
    <div class="btn-group" role=group>
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#createED">Create District</button>
    </div>
    <div class="btn-group" role=group>
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#voterModal">Update</button>
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
          <form class="form-signin">
            <h3 class="form-signin-heading">Name for District</h3>
            <label for="districtName" class="sr-only">District Name</label>
            <input type="text" id="districtName" class="form-control" placeholder="District Name" required=true autofocus=true>
            
            
            <div class="modal-footer">
              <button type="button" class="btn btn-lg btn-primary" type="submit" data-dismiss="modal">Create</button>
              <button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button>
            </div>
          </form>
        </div>
      </div>

    </div>
  </div>
      
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>1</td>
          <td>District 9</td>
        </tr>
      </tbody>
    </table>
  </div>


  <!-- Party -->
  <div id="Party" class="container">
    <h3>Party</h3>
      <table class="table table-hover">
      <thead>
        <tr>
          <th>Id</th>
          <th>Name</th>
        </tr>
      </thead>
      <tbody>
        <tr data-toggle="modal" data-target="#tableElement">
          <td>1</td>
          <td>Democrat</td>
        </tr>
      </tbody>
    </table>
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