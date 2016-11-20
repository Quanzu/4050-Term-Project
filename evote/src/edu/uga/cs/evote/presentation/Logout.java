package edu.uga.cs.evote.presentation;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;

public class Logout extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession    httpSession = null;
	    String         ssid = null;
	
	    httpSession = request.getSession( false );
	    if( httpSession != null ) {
	        ssid = (String) httpSession.getAttribute( "ssid" );
	        if( ssid != null ) {
	            System.out.println( "Already have ssid: " + ssid );
	            Session session = SessionManager.getSessionById( ssid );
	            if( session == null ) {
	            	return; 
	            }
	            LogicLayer logicLayer = session.getLogicLayer();
	            try {
	                logicLayer.logout( ssid );
	                httpSession.removeAttribute("ssid");
	                httpSession.invalidate();
	                System.out.println( "Invalidated http session" );
	            }
	            catch( EVException e ) {
	                e.printStackTrace();
	            }
	        }
	        else
	            System.out.println( "ssid is null" );
	    }
	    else
	        System.out.println( "No http session" );
	}
}
