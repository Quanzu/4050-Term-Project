package edu.uga.cs.evote.presentation;

import java.io.IOException;
import java.util.Date;

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
@WebServlet("/Ballot")
public class Ballot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
        LogicLayer     logicLayer = null;
        HttpSession    httpSession = null;
        Session        session = null;
        String         ssid = null;
        long		   ballotId = -1;
        String 			open = null;
        String			close = null;
        String			option = null;
        Date			openDate;
        Date			closeDate;
        
        String			theId = null;
        
        String			newOpen = null;
        String 			newClose = null;
        Date 			newOpenDate = null;
        Date			newCloseDate = null;
        
        
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
        
        if (option.equals("create"))
        {
		open = request.getParameter("openDate");
		//System.out.println(districtName);
		if(open == null){
			System.out.println("Open Date is null");
        	return;
		}
		else
		{
			int year, day, month;
			String[] openX = open.split("-");
			year = Integer.parseInt(openX[0]);
			month = Integer.parseInt(openX[1]);
			day = Integer.parseInt(openX[2]);
			openDate = new Date(year, month, day);
			System.out.println("Close date: " + openDate);
		}
		
		close = request.getParameter("closeDate");
		//System.out.println(districtName);
		if(close == null){
			System.out.println("Open Date is null");
        	return;
		}
		else
		{
			int year, day, month;
			String[] openX = close.split("-");
			year = Integer.parseInt(openX[0]);
			month = Integer.parseInt(openX[1]);
			day = Integer.parseInt(openX[2]);
			closeDate = new Date(year, month, day);
			System.out.println("Close date: " + closeDate);
		}
		try {  
            ballotId = logicLayer.createBallot(openDate, closeDate);
            response.sendRedirect("eoHomepage.jsp#Ballot");
        } 
        catch ( Exception e ) {
        	e.printStackTrace();
        }
        }
		//For delete and select
       
	
		else if (option.equals("delete")){
			theId = request.getParameter("ballot");
        	if(theId == null){
        		System.out.println("No ballot selected");
        		return;
        	}
			try {  
	            ballotId = logicLayer.deleteBallot(theId);
	            response.sendRedirect("eoHomepage.jsp#Ballot");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		}
		
        
		else
		{
			theId = request.getParameter("ballot");
        	if(theId == null){
        		System.out.println("No ballot selected");
        		return;
        	}
			newOpen = request.getParameter("newOpenDate");
			//System.out.println(districtName);
			if(open == null){
				System.out.println("Open Date is null");
	        	openDate = null;
			}
			else
			{
				int year, day, month;
				String[] openX = open.split("-");
				year = Integer.parseInt(openX[0]);
				month = Integer.parseInt(openX[1]);
				day = Integer.parseInt(openX[2]);
				openDate = new Date(year, month, day);
				
			}
			
			newClose = request.getParameter("newCloseDate");
			//System.out.println(districtName);
			if(close == null){
				System.out.println("Close Date is null");
	        	closeDate = null;
			}
			else
			{
				int year, day, month;
				String[] openX = close.split("-");
				year = Integer.parseInt(openX[0]);
				month = Integer.parseInt(openX[1]);
				day = Integer.parseInt(openX[2]);
				closeDate = new Date(year, month, day);
			}
			try {  
	            ballotId = logicLayer.updateBallot(openDate, closeDate, theId);
	            response.sendRedirect("eoHomepage.jsp#Ballot");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		
		
		}
	}

}

