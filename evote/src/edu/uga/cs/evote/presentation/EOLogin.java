package edu.uga.cs.evote.presentation;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.ElectionsOfficer;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.logic.LogicLayer;
import edu.uga.cs.evote.logic.impl.EOLoginCtrl;
import edu.uga.cs.evote.session.Session;
import edu.uga.cs.evote.session.SessionManager;



/**
 * Servlet implementation class Login
 */
@WebServlet("/EOLogin")
public class EOLogin extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EOLogin() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	try{
    		
    		HttpSession httpSession = null;
    		String username;
    		String password;
    		String ssid = null;
    		Session session = null;
    		LogicLayer logicLayer = null;
    		
    		httpSession = request.getSession();
    		ssid = (String)httpSession.getAttribute("ssid");
            if( ssid != null ) {
                System.out.println( "Already have ssid: " + ssid );
                session = SessionManager.getSessionById( ssid );
                System.out.println( "Connection: " + session.getConnection() );
            }
            else
                System.out.println( "ssid is null" );
            
            
            if( session == null ) {
                try {
                    session = SessionManager.createSession();
                }
                catch ( Exception e ) {
                    throw new EVException(e.getMessage());
                }
            }
            
            logicLayer = session.getLogicLayer();
            
            username = request.getParameter( "username" );
            password = request.getParameter( "password" );

            if( username == null || password == null ) {
                throw new EVException("Missing user name or password");
            }
            
            try {          
                ssid = logicLayer.eoLogin( session, username, password );
                System.out.println( "Obtained ssid: " + ssid );
                httpSession.setAttribute( "ssid", ssid );
                System.out.println( "Connection: " + session.getConnection() );
            } 
            catch ( Exception e ) {
                throw new EVException(e.getMessage());
            }
            
            
    		if (ssid!=null)
    			response.sendRedirect("eoHomepage.jsp");
    		else
    			response.sendRedirect("invalidLogin.jsp");
    		
    		
    	} catch (EVException e) {
			e.printStackTrace();
		} finally
    	{
    		out.close();
    	}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
