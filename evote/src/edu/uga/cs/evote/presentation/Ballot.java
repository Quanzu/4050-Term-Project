package edu.uga.cs.evote.presentation;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.EVException;
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
        Date			openDate = null;
        Date			closeDate = null;
        
        String			theId = null;
        
        String			newOpen = null;
        String 			newClose = null;
        Date 			newOpenDate = null;
        Date			newCloseDate = null;
        String 			district = null;
        
        
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
        	district = request.getParameter("districtName");
        	open = request.getParameter("openDate");
        	if(open == null){
        		System.out.println("Open Date is null");
        		return;
        	}
        	else
        	{
        	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	    try {
            		int year, day, month;
            		String[] openX = open.split("-");
            		year = Integer.parseInt(openX[0]);
            		month = Integer.parseInt(openX[1]);
            		day = Integer.parseInt(openX[2]);
            		open = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
					openDate = format.parse(open);
					System.out.println(open);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
        	}
		
        	close = request.getParameter("closeDate");
        	if(close == null){
        		System.out.println("Open Date is null");
        		return;
        	}
        	else
        	{
        	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        	    try {
            		int year, day, month;
            		String[] openX = close.split("-");
            		year = Integer.parseInt(openX[0]);
            		month = Integer.parseInt(openX[1]);
            		day = Integer.parseInt(openX[2]);
            		close = Integer.toString(year)+"-"+Integer.toString(month)+"-"+Integer.toString(day);
					closeDate = format.parse(close);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			
        	}
        	try {  
        		ballotId = logicLayer.createBallot(openDate, closeDate, district);
        		response.sendRedirect("eoHomepage.jsp#Ballot");
        	} 
        	catch ( Exception e ) {
        		e.printStackTrace();
        	}
        	
        	//adds in the issues
        	String[] theIssues;
			theIssues = request.getParameterValues("issues");
			if (theIssues != null)
			{
				try {  
					logicLayer.addIssue(ballotId, theIssues);
	        		
	        	} 
	        	catch ( Exception e ) {
	        		e.printStackTrace();
	        	}
			}
			
			//adds in the elections
			String[] elections;
			elections = request.getParameterValues("elections");
			if (elections != null)
			{
				try {  
					logicLayer.addElection(ballotId, elections);
	        		
	        	} 
	        	catch ( Exception e ) {
	        		e.printStackTrace();
	        	}
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
		
        //For ADD ISSUE
		else if (option.equalsIgnoreCase("addIssue"))
		{
			
			theId = request.getParameter("ballot");
			String[] theIssues;
			theIssues = request.getParameterValues("theIssues");
			if (theIssues ==null)
			{
				//put error message;
			}
			/*
			try {	
				//logicLayer.addIssue(theId, theIssues);
				response.sendRedirect("eoHomepage.jsp#Ballot");
			} catch (EVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
		else if (option.equalsIgnoreCase("addElection"))
		{
			
			theId = request.getParameter("ballot");
			String[] theElections;
			theElections = request.getParameterValues("theElections");
			if (theElections ==null)
			{
				//put error message;
			}
			/*
			try {	
				//logicLayer.addElection(theId, theElections);
				response.sendRedirect("eoHomepage.jsp#Ballot");
			} catch (EVException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
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
			if(newOpen == null){
				System.out.println("Open Date is null");
	        	openDate = null;
			}
			else
			{
				int year, day, month;
				String[] openX = newOpen.split("-");
				year = Integer.parseInt(openX[0]);
				month = Integer.parseInt(openX[1]);
				day = Integer.parseInt(openX[2]);
				newOpenDate = new Date(year, month, day);
				
			}
			
			newClose = request.getParameter("newCloseDate");
			//System.out.println(districtName);
			if(newClose == null){
				System.out.println("Close Date is null");
	        	closeDate = null;
			}
			else
			{
				int year, day, month;
				String[] openX = newClose.split("-");
				year = Integer.parseInt(openX[0]);
				month = Integer.parseInt(openX[1]);
				day = Integer.parseInt(openX[2]);
				newCloseDate = new Date(year, month, day);
			}
			try {  
	            ballotId = logicLayer.updateBallot(newOpenDate, newCloseDate, theId);
	            response.sendRedirect("eoHomepage.jsp#Ballot");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
		
		
		}
	}

}

