package controller;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.ServletSecurity.TransportGuarantee;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.SQLConnection;
import model.FeedBean;
import model.UserBean;

/**
 * Servlet implementation class feedController
 */
@WebServlet("/feedController")
@ServletSecurity(value = @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class feedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public feedController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check if there is an user in session.
		if (request.getSession().getAttribute("user") != null) {
			// get the user out of session 
			UserBean userBean = (UserBean) request.getSession().getAttribute("user");

			// Validate username and password again  
			if (userBean.validate(userBean)) {
				
							
				// get the session and the request to go to the feedpage page 
				HttpSession session = request.getSession();
				session.setAttribute("user", userBean);
				request.setAttribute("user", userBean);

				RequestDispatcher rd = request.getRequestDispatcher("feedpage.jsp");
				rd.forward(request, response);
			} else {
				// this only happens if the sessionid is removed, manually or because it timed out and you try to go directly to the "feedpage.jsp"
				//  goto logout to clean up
			
				RequestDispatcher rd = request.getRequestDispatcher("Logout");
				rd.forward(request, response);
				
			}
		} else {
			// this should only happen if you try to goto "/Login" manually 
			response.sendRedirect("index.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = request.getParameter("message");
		String hashTag = request.getParameter("hashTag");
		
		
		// Add # to string if it is missing
		if(hashTag.indexOf("#") != 0 && !hashTag.equals("")) {
			hashTag = "#" + hashTag;
		}
		
		HttpSession session = request.getSession();
		UserBean userbean = (UserBean)session.getAttribute("user");
		
		SQLConnection.addFeedMessageToSql(message, hashTag, userbean.getName());
		
		FeedBean feedBean = new FeedBean(SQLConnection.getFeedFromSql());

		
		request.setAttribute("user", userbean);
		request.setAttribute("feedBean", feedBean);
		
		RequestDispatcher rd = request.getRequestDispatcher("feedpage.jsp");
		rd.forward(request, response);
	}

}
