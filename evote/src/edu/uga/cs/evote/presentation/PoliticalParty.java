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
@WebServlet("/PoliticalParty")
public class PoliticalParty extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PoliticalParty() {
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
        long		   partyId = -1;
        String		   partyName = null;
        String 			newPartyName = null;
        String			option = null;
        
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
        
		partyName = request.getParameter("partyName");
		
		if(partyName == null){
			System.out.println("Party Name null");
        	return;
		}
		
		if (option.equals("create"))
		{
			try {  
	            partyId = logicLayer.createPP(partyName);
	            response.sendRedirect("eoHomepage.jsp");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		else
		{
			newPartyName = request.getParameter("newPartyName");
			if(newPartyName == null){
				System.out.println("New Party Name null");
				return;
			}
		
		
			
			try {          
	            partyId = logicLayer.updatePP(partyName, newPartyName );
	            response.sendRedirect("eoHomepage.jsp");
			} 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		
		}
	}

}
