package edu.uga.cs.evote.presentation;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
 * Servlet implementation class Vote
 */
@WebServlet("/Vote")
public class SubmitVote extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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
        long		   ballotId = -1;
        String 			theId;
        String 			option = null;
        long			issueId;
        long			electionId;
        String			issueVote;
        String			question = null;
       long				x;
        int			voteCount;
        
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
        
        option = request.getParameter("todo");
        theId = request.getParameter("choseBallot");
  /*      httpSession.setAttribute("choseBallot", theId);
      httpSession = request.getSession(false);*/
        
       if (option.equalsIgnoreCase("issue"))
       {
    	  
    	   String str = request.getParameter("issueId");
    	   issueId = Integer.parseInt(str);
    	   voteCount = Integer.parseInt(request.getParameter("voteCount")); 
    	   issueVote = request.getParameter("issueVote");
    	   question = request.getParameter("question");
    	   
    	   try {  
	            x = logicLayer.recordIssue(issueId, question, voteCount, issueVote);
	            
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
    	   
       }
       else if (option.equalsIgnoreCase("election"))
       {
    	   
       }
        
       
        
        //httpSession = request.getSession(false); //use false to use the existing session
        
        
        //System.out.println(theId);
       /* if(theId == null){
    		System.out.println("No ballot selected");
    		return;
    	}*/
        
		
	}

}
