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
@WebServlet("/Candidate")
public class Candidate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Candidate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html");
		
        LogicLayer     logicLayer = null;
        HttpSession    httpSession = null;
        Session        session = null;
        String         ssid = null;
        long		   candidateId = -1;
        String		   candidateName = null;
        String 			newCandidateName = null;
        String			option = null;
        String		   partyName = null;
        String		   electionName = null;
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
        
		candidateName = request.getParameter("candidateName");
		//System.out.println(districtName);
		if(candidateName == null){
			System.out.println("Candidate Name null");
        	return;
		}
		electionName = request.getParameter("electionName");
		if(electionName == null){
			System.out.println("Election Name null");
        	return;
		}
		
		isPartisan = request.getParameter("isPartisan");
		System.out.println(isPartisan);
		if(isPartisan == null){
			System.out.println("Partisan is null");
        	return;
		}
		
		if (option.equals("create"))
		{
			try {  
	            candidateId = logicLayer.createCand(candidateName, partyName, electionName, isPartisan);
	            response.sendRedirect("eoHomepage.jsp#Candidate");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		else if (option.equals("delete")){
			try {  
	            candidateId = logicLayer.deleteCand(candidateName);
	            response.sendRedirect("eoHomepage.jsp#Candidate");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		
		else
		{
			newCandidateName = request.getParameter("newCandidateName");
			if(newCandidateName == null){
				System.out.println("New Candidate Name null");
				return;
			}
		
		
			
			try {          
	            candidateId = logicLayer.updateED(candidateName, newCandidateName );
	            response.sendRedirect("eoHomepage.jsp#Candidate");
			} 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		
		}
	}

}
