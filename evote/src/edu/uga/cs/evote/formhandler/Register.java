package edu.uga.cs.evote.formhandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.uga.cs.evote.EVException;
import edu.uga.cs.evote.entity.impl.ElectionsOfficerImpl;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.logic.EOController;
import edu.uga.cs.evote.logic.VoterController;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	try{
    		VoterImpl voter = new VoterImpl();
    		voter.setUserName(request.getParameter("uname"));
    		voter.setPassword(request.getParameter("pword"));
    		//String uname = request.getParameter("uname");
    		//String pword = request.getParameter("pword");
    		String option = request.getParameter("option");
    		
    		
    		//calling method login in EO controller?
    		voter = VoterController.add(voter);
    		
    		if (voter.isPersistent()){
    			HttpSession session = request.getSession(true);
    			session.setAttribute("currentSessionUser", voter);
    			//need to creat homepage for EO
    			response.sendRedirect("eoHomepage.jsp");
    		}
    		else
    			//create invalid login page.
    			response.sendRedirect("invalidLogin.jsp");
    		
    		/*
    		RequestDispatcher rd = request.getRequestDispatcher("/Index.jsp");
    		request.setAttribute("message", "Thank You!");
    		request.setAttribute("uname", uname);
    		rd.forward(request, response);
    		*/
    	} 
    	catch (EVException e) {
			e.printStackTrace();
    	} 
    	finally{
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
