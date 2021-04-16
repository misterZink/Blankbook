package controller;

import java.io.IOException;
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
import model.UserBean;

/**
 * Servlet implementation class LogoutController
 */
@WebServlet("/Logout")
@ServletSecurity(value = @HttpConstraint(transportGuarantee = TransportGuarantee.CONFIDENTIAL))
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Check if there is an user in session.
		if (request.getSession().getAttribute("user") != null) {

			// Get the session
			HttpSession session = request.getSession();

			// get the user data
			UserBean userBean = (UserBean) session.getAttribute("user");
			// and clear it
			userBean.resetUserBean();
			
			SQLConnection.stopFeedConnectionSql();

			// remove the user
			session.removeAttribute("User");
			// turn off the session
			session.invalidate();
			// goto index
			response.sendRedirect("index.jsp");

		}else {
			// this should only happen if you try to goto "/Logout" manually 
						response.sendRedirect("index.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}