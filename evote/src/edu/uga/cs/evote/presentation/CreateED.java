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

import edu.uga.cs.evote.entity.ElectoralDistrict;
import edu.uga.cs.evote.entity.impl.ElectoralDistrictImpl;
//import edu.uga.cs.evote.logic.EDController;

/**
 * Servlet implementation class CreateED
 */
@WebServlet("/CreateED")
public class CreateED extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateED() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//needs to get the string
		//Need to create an electoral district
		//set ed's name to the string
		//ED needs to then call the method: createED( pass in the Electoral District)
		//just like in login
		//Then needs to check if persistent.
		//if it is- give them a "success.jsp"
		//if not - send them to a "error.jsp?"
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try{
			ElectoralDistrictImpl ed = new ElectoralDistrictImpl();
			ed.setName(request.getParameter("districtName"));
		//	ed = EDController.createED(ed);
			
			if(ed.isPersistent())
			{
				//Is Session necessary for this?
				HttpSession session = request.getSession(true);
				session.setAttribute("currentSessionUser", ed);
				response.sendRedirect("success.jsp");
			}
			else
				response.sendRedirect("error.jsp");
			
		}finally{
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
