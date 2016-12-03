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

@WebServlet("/ElectionsOfficer")
public class ElectionsOfficer extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	
    	HttpSession httpSession = null;
    	String firstName;
    	String lastName;
    	String username;
    	String password;
    	String email;
    	String address;
    	String option;
   		String ssid = null;
   		Session session = null;
   		LogicLayer logicLayer = null;
   		
   		httpSession = request.getSession();
        if( httpSession == null ) {       // assume not logged in!
            System.out.println("Session expired or illegal; please log in" );
            return;
        }
        
    	ssid = (String)httpSession.getAttribute("ssid");
        if( ssid == null ) {       // not logged in!
            System.out.println("Session expired or illegal; please log in" );
            return;
        }
          
        session = SessionManager.getSessionById( ssid );
        if( session == null ) {
            System.out.println("Session expired or illegal; please log in" );
            return; 
        }
        
        logicLayer = session.getLogicLayer();
        if( logicLayer == null ) {
            System.out.println("Session expired or illegal; please log in" );
            return; 
        }       
        
        option = request.getParameter("todo");
            
        if(option.equals("updateProfile")){
        	firstName = request.getParameter("fname");
        	lastName = request.getParameter("lname");
        	username = request.getParameter("username");
        	password = request.getParameter("password");
        	email = request.getParameter("email");
        	address = request.getParameter("address");
    	
        	try {             	
        		 session.setUser(logicLayer.updateElectionsOfficer(firstName, lastName, username, password, email, address));
            	response.sendRedirect("eoHomepage.jsp");
        	} 
        	catch ( Exception e ) {
        		e.printStackTrace();
        	}
        }
	}

}

