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
 * Servlet implementation class Issue
 */
@WebServlet("/Issue")
public class Issue extends HttpServlet {
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
		String		   option = null;
		long		   updateIssueId;
		long		   issueId;
		int			   newYesCount = 0;
		     
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
        
       
        if(option.equals("create")){
            String question = request.getParameter("question");

        	try {  
	            issueId = logicLayer.createIssue(question);
	            response.sendRedirect("eoHomepage.jsp#Issue");
	        } 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
        }
        else if(option.equals("update")){
            String temp = request.getParameter("issueId");
    		String newQuestion = null;
            if(temp == null){
            	System.out.println("IssueId is null");
            	return;
            }else{
            	issueId = Integer.parseInt(temp);
            }
        	newQuestion = request.getParameter("newQuestion");
            if(newQuestion == null){
            	System.out.println("New Question is null");
            	return;
            }
            
            temp = request.getParameter("newYesCount");
            if(temp == null){
            	System.out.println("New Yes Count is null");
            	return;
            }else{
            	newYesCount = Integer.parseInt(temp);
            }
            
            try {      
	            updateIssueId = logicLayer.updateIssue(issueId, newQuestion, newYesCount );
	            response.sendRedirect("eoHomepage.jsp#Issue");
			} 
	        catch ( Exception e ) {
	        	e.printStackTrace();
	        }
        }
        else if(option.equals("delete")){
            String temp = request.getParameter("issueId");
        	issueId = Integer.parseInt(temp);
        	try{
        		issueId = logicLayer.deleteIssue(issueId);
        		response.sendRedirect("eoHomepage.jsp#Issue");
        	}
        	catch( Exception e ){
        		e.printStackTrace();
        	}
        }
        
	}

}
