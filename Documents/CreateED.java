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
 * Servlet implementation class CreateED
 */
@WebServlet("/CreateED")
public class CreateED extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
        LogicLayer     logicLayer = null;
        HttpSession    httpSession = null;
        Session        session = null;
        String         ssid = null;
        long		   districtId = -1;
        String		   districtName = null;
        
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
        
		districtName = request.getParameter("districtName");
<<<<<<< Updated upstream:evote/src/edu/uga/cs/evote/presentation/CreateED.java
=======
<<<<<<< HEAD:Documents/CreateED.java
		//System.out.println(districtName);
=======
>>>>>>> origin/master:evote/src/edu/uga/cs/evote/presentation/CreateED.java
>>>>>>> Stashed changes:Documents/CreateED.java
		if(districtName == null){
			System.out.println("District Name null");
        	return;
		}
		
		try {  
            districtId = logicLayer.createED(districtName);
        	response.sendRedirect("eoHomepage.jsp#District");
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
		
	}
}
