package edu.uga.cs.evote.formhandler;

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
import edu.uga.cs.evote.logic.EOController;



/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
    	PrintWriter out = response.getWriter();
    	try{
    		ElectionsOfficerImpl eo = new ElectionsOfficerImpl();
    		eo.setUserName(request.getParameter("uname"));
    		eo.setPassword(request.getParameter("pword"));
    		//String uname = request.getParameter("uname");
    		//String pword = request.getParameter("pword");
    		String option = request.getParameter("option");
    		
    		
    		//calling method login in EO controller?
    		eo = EOController.login(eo);
    		
    		if (eo.isPersistent())
    		{
    			HttpSession session = request.getSession(true);
    			session.setAttribute("currentSessionUser", eo);
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
