<%@ page language="java" contentType="text/html; charset=UTF-8" 
    pageEncoding="UTF-8"%> 
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
    <h1 class="text-center">eVote</h1> 
    <p>Welcome to our eVote website. Please select the appropiate option below.</p> 
  </div> 
</div> 
 
<div class="container"> 
  <!-- Officer and Voter Button -> modal --> 
  <div class="btn-group btn-group-justified" role="group"> 
    <div class="btn-group" role=group> 
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#officerModal">Officer</button> 
    </div> 
    <div class="btn-group" role=group> 
      <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#voterModal">Voter</button> 
    </div> 
  </div> 
 
  <!-- officer modal --> 
  <div id="officerModal" class="modal fade" role="dialog"> 
    <div class="modal-dialog"> 
 
      <div class="modal-content"> 
        <div class="modal-header"> 
          <button type="button" class="close" data-dismiss="modal">&times;</button> 
          <h1 class="modal-title text-center">Officer</h1> 
        </div> 
        <div class="modal-body"> 
          <form class="form-signin" action = "EOLogin" method = "post"> 
            <h3 class="form-signin-heading">Sign In</h3> 
            <label for="username" class="sr-only">UserName</label> 
            <input type="text" name="username" class="form-control" placeholder="UserName" required=true autofocus=true> 
            <label for="password" class="sr-only">Password</label> 
            <input type="password" name="password" class="form-control" placeholder="Password" required=true> 
            <div class="modal-footer"> 
              <button class="btn btn-lg btn-primary" type="submit">Sign in</button> 
              <button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button> 
            </div> 
          </form> 
        </div> 
      </div> 
 
    </div> 
  </div> 
 
  <!-- voter modal --> 
  <div id="voterModal" class="modal fade" role="dialog"> 
    <div class="modal-dialog"> 
 
      <div class="modal-content"> 
        <div class="modal-header"> 
          <button type="button" class="close" data-dismiss="modal">&times;</button> 
          <h1 class="modal-title text-center">Voter</h1> 
        </div> 
        <div class="modal-body"> 
          <ul class="nav nav-tabs" id="tabContent"> 
              <li class="active"><a href="#voterSignIn" data-toggle="tab">Sign In</a></li> 
              <li><a href="#register" data-toggle="tab">Register</a></li> 
          </ul> 
 
          <div class="tab-content"> 
              <div class="tab-pane active" id="voterSignIn"> 
                <form class="form-signin"> 
                  <label for="username" class="sr-only">UserName</label> 
                  <input type="text" id="username" class="form-control" placeholder="UserName" required autofocus> 
                  <label for="password" class="sr-only">Password</label> 
                  <input type="password" id="password" class="form-control" placeholder="Password" required> 
                </form> 
                <div class="modal-footer"> 
                  <button type="button" class="btn btn-lg btn-primary" type="submit">Sign in</button> 
                  <button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button> 
                </div> 
              </div> 
 
              <div class="tab-pane" id="register"> 
                <form class="form-signin" action="Register" method="post"> 
                  <fieldset> 
                    <legend>General Information:</legend> 
                    <label for="fname" class="sr-only">First Name</label> 
                    <input type="text" id="fname" class="form-control" placeholder="First Name" required autofocus> 
                    <label for="lname" class="sr-only">Last Name</label> 
                    <input type="text" id="lname" class="form-control" placeholder="Last Name" required> 
                    <label for="username" class="sr-only">UserName</label> 
                    <input type="text" id="username" class="form-control" placeholder="Username" required> 
                    <label for="password" class="sr-only">Password</label> 
                    <input type="password" id="password" class="form-control" placeholder="Password" required> 
                    <label for="passwordRetype" class="sr-only">Retype Password</label> 
<!--   				<input type="password" id="passwordRetype" class="form-control" placeholder="Retype Password" required>-->
                    <label for="email" class="sr-only">Email</label> 
                    <input type="email" id="email" class="form-control" placeholder="Email" required> 
                    <label for="age" class="sr-only">Age</label> 
                    <input type="number" id="age" class="form-control" placeholder="Age: Must be a number" required> 
                  </fieldset> 
                  <br> 
                  <fieldset> 
                    <legend>Address:</legend> 
                    <label for="street" class="sr-only"></label> 
                    <input type="text" id="street" class="form-control" placeholder="Street" required> 
                    <label for="city" class="sr-only"></label> 
                    <input type="text" id="city" class="form-control" placeholder="City" required> 
                    <label for="state" class="sr-only"></label> 
                    <input type="text" id="state" class="form-control" placeholder="State" required> 
                    <label for="zip" class="sr-only"></label> 
                    <input type="number" id="zip" class="form-control" placeholder="Zip Code: Must be a number" required> 
                  </fieldset> 
                </form> 
                <div class="modal-footer"> 
                  <button type="button" class="btn btn-lg btn-primary" type="submit">Register</button> 
                  <button type="button" class="btn btn-lg btn-primary" data-dismiss="modal">Close</button> 
                </div> 
              </div> 
          </div> 
        </div> 
      </div> 
 
    </div> 
  </div> 
 
</div> 
</body> 
</html>