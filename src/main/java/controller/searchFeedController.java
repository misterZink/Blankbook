package controller;

import java.io.IOException;

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

import java.sql.ResultSet;

import database.SQLConnection;
import model.FeedBean;
import model.UserBean;

/**
 * Servlet implementation class searchFeedController
 */
@WebServlet("/searchFeedController")
@ServletSecurity(value = @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class searchFeedController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchFeedController() {
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

		String searchedWord = request.getParameter("searchInput");
		
		FeedBean feedBean = new FeedBean(SQLConnection.getSearchedFeed(searchedWord));

		
		HttpSession session = request.getSession();
		UserBean userbean = (UserBean)session.getAttribute("user");
		
		request.setAttribute("user", userbean);
		request.setAttribute("feedBean", feedBean);
		
		RequestDispatcher rd = request.getRequestDispatcher("feedpage.jsp");
		rd.forward(request, response);
	}

}
