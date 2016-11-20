package edu.uga.cs.evote.presentation;

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
import edu.uga.cs.evote.logic.impl.EOLoginCtrl;
import edu.uga.cs.evote.logic.impl.VoterRegCtrl;

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
    		int age = Integer.parseInt(request.getParameter("age"));
    		
    		VoterImpl voter = new VoterImpl();
    		voter.setFirstName(request.getParameter("fname"));
    		voter.setLastName(request.getParameter("lname"));
    		voter.setUserName(request.getParameter("uname"));
    		voter.setPassword(request.getParameter("pword"));
    		voter.setEmailAddress(request.getParameter("email"));
    		voter.setAge(age);
    		voter.setAddress(request.getParameter("street") + " " + request.getParameter("city") + " "
    				+ request.getParameter("state") + " " + request.getParameter("zip"));
    		String option = request.getParameter("option");
    		
    		
    		//calling method add in voter controller?
    		VoterRegCtrl.add(voter);
    	
    		if (voter.isPersistent()){
    			HttpSession session = request.getSession(true);
    			session.setAttribute("currentSessionUser", voter);
    			//need to creat homepage for voter
    			response.sendRedirect("voterHomepage.jsp");
    		}
    		else
    			//create invalid login page.
    			response.sendRedirect("invalidLogin.jsp");
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
