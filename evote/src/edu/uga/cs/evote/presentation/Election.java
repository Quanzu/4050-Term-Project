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

/**
 * Servlet implementation class ElectoralDistrict
 */
@WebServlet("/Election")
public class Election extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html");
		
        LogicLayer     logicLayer = null;
        HttpSession    httpSession = null;
        Session        session = null;
        String         ssid = null;
        long		   electionId = -1;
        String		   electionOffice = null;
        String 			newElectionOffice = null;
        String			option = null;
        String			ballot = null;
        String			isPartisan = null;
        
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
        
		electionOffice = request.getParameter("electionOffice");
		isPartisan = request.getParameter("isPartisan");
		//System.out.println(districtName);
		if(electionOffice == null){
			System.out.println("Election Office null");
        	return;
		}
		
		
		if (option.equals("create"))
		{
			
			try {  
	            electionId = logicLayer.createElection(electionOffice, isPartisan);
	            response.sendRedirect("eoHomepage.jsp#Election");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		else if (option.equals("delete")){
			try {  
	            electionId = logicLayer.deleteElection(electionOffice);
	            response.sendRedirect("eoHomepage.jsp#Election");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		
		else
		{
			newElectionOffice = request.getParameter("newCandidateName");
			if(newElectionOffice == null){
				System.out.println("New Candidate Name null");
				return;
			}
		
		
			
			try {          
	            electionId = logicLayer.updateED(electionOffice, newElectionOffice );
	            response.sendRedirect("eoHomepage.jsp#Candidate");
			} 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		
		}
	}

}
