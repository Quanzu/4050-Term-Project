package edu.uga.cs.evote.presentation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.logic.impl.EOLoginCtrl;
import edu.uga.cs.evote.logic.impl.VoterRegCtrl;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;


    public Register() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	
    	HttpSession httpSession = null;
    	int age;
    	String firstName;
    	String lastName;
    	String username;
    	String password;
    	String email;
    	String address;
   		String ssid = null;
   		long ssid2 = 0;
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
    	
    	age = Integer.parseInt(request.getParameter("age"));
    	firstName = request.getParameter("fname");
    	lastName = request.getParameter("lname");
    	username = request.getParameter("username");
    	password = request.getParameter("password");
    	email = request.getParameter("email");
    	address = request.getParameter("street") + " " + request.getParameter("city")
    		+ " " + request.getParameter("state") + " " + request.getParameter("zip");

        if( firstName == null || lastName == null || username == null || password == null || email == null || address == null
        		|| age == 0) {
        	System.out.println("A parameter is null");
        	return;
        }

    	
        try {          
            ssid2 = logicLayer.addVoter( session, firstName, lastName, username, password, email, address, age );
            System.out.println( "Obtained ssid: " + ssid );
            httpSession.setAttribute( "ssid", ssid );
            System.out.println( "Connection: " + session.getConnection() );
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
        
        if(ssid2 > 0)
        	response.sendRedirect("voterHomepage.jsp");
        else
        	response.sendRedirect("invalidLogin.jsp");
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
