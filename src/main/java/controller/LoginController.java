package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.UserBean;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
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
			// get the user out of session 
			UserBean userBean = (UserBean) request.getSession().getAttribute("user");

			// Validate username and password again  
			if (userBean.validate(userBean)) {
				
							
				// get the session and the request to go to the success page 
				HttpSession session = request.getSession();
				session.setAttribute("user", userBean);
				request.setAttribute("user", userBean);

				RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
				rd.forward(request, response);
			} else {
				// this only happens if the sessionid is removed, manually or because it timed out and you try to go directly to the "success.jsp"
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get the login data
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		// Set values of the user bean
		UserBean userBean = new UserBean();
		userBean.setEmail(email);
		userBean.setPassword(password);

		// Check if the email and pass is correct.
		if (userBean.validate(userBean)) {

			
			
			
			// A new thing here "Session", a way to generate a ID to remember some date on
			// the client
			HttpSession session = request.getSession(); // its apart of the request

			
			// the user is logged in or not
			session.setAttribute("user", userBean);

			request.setAttribute("user", userBean);

			// RequestDispatcher for when we want to send the request to the new page
			RequestDispatcher rd = request.getRequestDispatcher("success.jsp");
			rd.forward(request, response);

			// response.sendRedirect only goes to the new page, and nothing else

		} else {
			// go to an error page that includes the index page to have the user try again
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}
	}

}