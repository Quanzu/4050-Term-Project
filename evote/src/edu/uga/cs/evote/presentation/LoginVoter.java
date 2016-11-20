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
import edu.uga.cs.evote.entity.Voter;
import edu.uga.cs.evote.entity.impl.VoterImpl;
import edu.uga.cs.evote.logic.impl.VoterController;

/**
 * Servlet implementation class LoginVoter
 */
@WebServlet("/LoginVoter")
public class LoginVoter extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginVoter() {
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
    		VoterImpl vo = new VoterImpl();
    		vo.setUserName(request.getParameter("uname"));
    		vo.setPassword(request.getParameter("pword"));
    		//Necessary for Voter?
    		String option = request.getParameter("option");
    		
    		
    		//calling method login in VO controller?
    		vo = VoterController.login(vo);
    		
    		if (vo.isPersistent())
    		{
    			HttpSession session = request.getSession(true);
    			session.setAttribute("currentSessionUser", vo);
    			//What should the voter be sent to?
    			response.sendRedirect("voHomepage.jsp");
    		}
    		else
    			//create invalid login page.
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
		doGet(request, response);
	}

}
