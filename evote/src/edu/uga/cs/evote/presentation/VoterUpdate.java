package edu.uga.cs.evote.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

@WebServlet("/VoterUpdate")
public class VoterUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	
    	HttpSession httpSession = null;
    	int age;
    	long voterId = -1;
    	String firstName;
    	String lastName;
    	String username;
    	String password;
    	String email;
    	String address;
   		String ssid = null;
   		String ssid2 = null;
   		String option = null;
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
    	age = 17;
    	//age = Integer.parseInt(request.getParameter("age"));
    	firstName = request.getParameter("fname");
    	lastName = request.getParameter("lname");
    	username = request.getParameter("username");
    	password = request.getParameter("password");
    	email = request.getParameter("email");
    	address = request.getParameter("street") + " " + request.getParameter("city")
    		+ " " + request.getParameter("state") + " " + request.getParameter("zip");
    	option = request.getParameter("todo");
       /* if( firstName == null || lastName == null || username == null || password == null || email == null || address == null
        		|| age == 0) {
        	System.out.println("A parameter is null");
        	return;
        }*/

    	
        try {          
        	//Make sure updateVoter is written
            voterId = logicLayer.updateVoter(firstName, lastName, username, password, email, address, age );
            System.out.println( "Obtained ssid: " + ssid );
            httpSession.setAttribute( "ssid", ssid );
            System.out.println( "Connection: " + session.getConnection() );
            response.sendRedirect("voterHomepage.jsp");
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
        
        if(option.equals("delete")){
        	try{
        		voterId = logicLayer.deleteVoter(username);
        	// redirect where?	response.sendRedirect();
        	}
        	catch(Exception e){
        		e.printStackTrace();
        	}
        	
        }
        
        if(ssid2 != null)
        	response.sendRedirect("voterHomepage.jsp");
      //  else
        	//response.sendRedirect("invalidLogin.jsp");
        	//Where send
	}

}
