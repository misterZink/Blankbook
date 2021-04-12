<%@page import="database.SQLConnection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="model.UserBean"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<title>Logged in</title>

	<!-- Bootstrap core CSS -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
	<script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>    
	
	<!--Fontawesome CDN-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css" integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous">
    
	<link href="css/signIn.css" rel="stylesheet">
</head>
<body>


<div class="d-flex justify-content-center h-100">
	<div class="feed">
	
	
		<div class="row">
			<div class="col-sm-8">
				<%
				// Check to see if the session has a user bean.
				if (session.getAttribute("user") == null) {
					// if there is not one, goto the logout servlet
					RequestDispatcher rd = request.getRequestDispatcher("Logout");
					rd.forward(request, response);
				} else {
					// if there is a session , then all is well  
					// get the bean to unpack the user data
					UserBean userBean = (UserBean) request.getAttribute("user");
					out.print("<h3>Logged in as, " + userBean.getName() + "</h3>");
				}
				%>
			</div>
			<div class="col">
				</form>
				<form action="<%=request.getContextPath()%>/Logout" method="post">
					 <button class="btn btn-primary" type="submit">Log out</button>
				</form>
			</div>
		</div>
	
	
	
		<h2>Feed <button onclick="myFunction()" type="button" class="btn"><i class="fas fa-adjust"></i></button></h2>
	
	
		<div class="feeds">
			<%
			
			ResultSet resultSet = SQLConnection.getFeedFromSql();
			
			//Print messages
			String divClassForContainer = "container container-feed";
			String timePlacement = "time-right";
			while (resultSet.next()) {
				
				out.println("<div class=\"" + divClassForContainer + "\">");
				out.println("<div class=\"feed-header\"><span class=\"feedUsersName\">" + resultSet.getString(4) + "</span> <span class=\"feedHashtag\">" + resultSet.getString(3) + "</span></div>");
				out.println("<p>" + resultSet.getString(2) + "</p>");
				out.println("<span class=\"" + timePlacement + "\">" + resultSet.getString(5) +"</span>");
				out.println("</div>");
				
				if(divClassForContainer.equals("container container-feed")) {
					divClassForContainer = "container container-feed darker";
					timePlacement = "time-left";
				} else {
					divClassForContainer = "container container-feed";
					timePlacement = "time-right";
				}
			}
			
			%>
		</div>	
		<form class="feed-form" action="<%=request.getContextPath()%>/feedController"  method="POST">
		<input type="text" class="formMessage" placeholder="Message" name="message" required="required" maxlength="255" />
		<input type="text" class="formHashTag" placeholder= "#Hashtag" name="hashTag" maxlength="25" />
		<input type="submit" value="Send" class="btn float-right login_btn">
	</div>
</div>


		


<script>
function myFunction() {
   var element = document.body;
   element.classList.toggle("body-dark");
}
</script>




</body>
</html>