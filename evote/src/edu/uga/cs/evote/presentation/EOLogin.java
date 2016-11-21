package edu.uga.cs.evote.presentation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;



/**
 * Servlet implementation class Login
 */
@WebServlet("/EOLogin")
public class EOLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    		
    	HttpSession httpSession = null;
    	String username;
    	String password;
   		String ssid = null;
   		String ssid2 = null;
   		Session session = null;
   		LogicLayer logicLayer = null;
    		
    	httpSession = request.getSession();
    	ssid = (String)httpSession.getAttribute("ssid");
        if( ssid != null ) {
        	System.out.println( "Already have ssid: " + ssid );
            session = SessionManager.getSessionById( ssid );
            //System.out.println( "Connection: " + session.getConnection() );
        }
        else
            System.out.println( "ssid is null" );
          
            
        if( session == null ) {
        	try {
        		session = SessionManager.createSession();
            }
            catch ( Exception e ) {
                e.printStackTrace();
            }
        }
            
        logicLayer = session.getLogicLayer();
            
        username = request.getParameter( "username" );
        password = request.getParameter( "password" );
            
        if( username == null || password == null ) {
        	System.out.println("Username or password null");
        	return;
        }
            
        try {          
            ssid2 = logicLayer.eoLogin( session, username, password );
            System.out.println( "Obtained ssid: " + ssid2 );
            httpSession.setAttribute( "ssid", ssid2 );
            System.out.println( "Connection: " + session.getConnection() );
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
            
        PrintWriter outter = response.getWriter();
            
        if (ssid2!=null)
        	response.sendRedirect("eoHomepage.jsp");
    	else
    	{
    		
    		//outter.println("<html><script type = 'text/javascript'>alert('Wrong username/password')</script>");
    		
    		//response.sendRedirect("invalidLogin.jsp");
    		outter.println("<html> <head>"
    				+ "<title>eVote</title>"
    				+ "<meta charset='utf-8'> "
    				+ "<meta name='viewport' content='width=device-width, initial-scale=1'> "
    				+ "<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'> "
    				+ "<script src='https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js'></script>"
    				+ "<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>"
    				+ "</head>"
    				);
    		
    		//Start of body
    		/*outter.println("<body><div class='container-fluid'> "
    				+ "<div class='jumbotron'> "
    				+ " <h1 class='text-center'>eVote</h1> "
    				+ "<p>Welcome to our eVote website. Please select the appropiate option below.</p>"
    				+ " </div> </div>");
    		*/
    		//Container
    		outter.println("<div class='container'> "
    				+ " <div class='btn-group btn-group-justified' role='group'>"
    				+ "<div class='btn-group btn-group-justified' role='group'> "
    				+ "<div class='btn-group' role=group> "
    				+ "</div> <div class='btn-group' role=group> "
    				+ "</div> </div>"
    				);
  //elections officer
  outter.println("<div class= 'modal-dialog'> "
    				+ "<div class='modal-content'>" + "<div class='modal-header'> " 
    				+ "<button type='button' class='close' data-dismiss='modal'><a href = 'index.jsp'>X</a></button> " 
    				+ "<h1 class='modal-title text-center'>Officer</h1> " + "</div> "
        + "<div class='modal-body'> " +
          "<form class='form-signin' action = 'EOLogin' method = 'post'> "
        + "<h3 class='form-signin-heading'>Sign In</h3> "
          + "<p>Incorrect Username/Password</p>"
          + "<label for='username' class='sr-only'>UserName</label>"
            + "<input type='text' name='username' class='form-control' placeholder='UserName' required=true autofocus=true> "
            + "<label for='password' class='sr-only'>Password</label> "
          + "<input type='password' name='password' class='form-control' placeholder='Password' required=true>"
            + " <div class='modal-footer'> "
           +   "<button class='btn btn-lg btn-primary' type='submit'>Sign in</button>"
           +   "<button type='button' class='btn btn-lg btn-primary' data-dismiss='modal'><a href = 'index.jsp'>Close</a></button>"
           + "</div> </form> </div> </div> </div> </div>"
           );
  
  //Voter
 /* outter.println("<div class= 'modal-dialog'> "
			+ "<div class='modal-content'>" + "<div class='modal-header'> " 
			+ "<button type='button' class='close' data-dismiss='modal'>&times</button> " 
			+ "<h1 class='modal-title text-center'>Officer</h1> " + "</div> "
+ "<div class='modal-body'> " +
"<form class='form-signin' action = 'EOLogin' method = 'post'> "
+ "<h3 class='form-signin-heading'>Sign In</h3> "
+ "<label for='username' class='sr-only'>UserName</label>"
  + "<input type='text' name='username' class='form-control' placeholder='UserName' required=true autofocus=true> "
  + "<label for='password' class='sr-only'>Password</label> "
+ "<input type='password' name='password' class='form-control' placeholder='Password' required=true>"
  + " <div class='modal-footer'> "
 +   "<button class='btn btn-lg btn-primary' type='submit'>Sign in</button>"
 +   "<button type='button' class='btn btn-lg btn-primary' data-dismiss='modal'>Close</button>"
 + "</div> </form> </div> </div> </div> </div>");*/
  
  outter.println("</div></body></html>");
    	}
    		
    		
	}

}
