package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.UserBean;

public class SQLConnection {
	
	
	static Connection userConnection = null;
	static Connection feedConnection = null;
	static PreparedStatement prepStatement = null;
	static ResultSet resultSet = null;
	
	
	
	
	public static boolean connectSQL() {
		
		
		try {
			
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			
		} catch (Exception e) {
			System.out.println("Exe Driver: " + e);
			return false;
		}
		
		
		try {
			
			userConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?serverTimezone=UTC",DatabaseLogin.getUserName(), DatabaseLogin.getUserPass());
			feedConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/feed?serverTimezone=UTC",DatabaseLogin.getUserName(), DatabaseLogin.getUserPass());
			
			return true;
			
		} catch(SQLException e) {
			System.out.println("connectSQL");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
			return false;
		}
	}
	
	
	public static boolean stateSql(UserBean userBean) {
		
		
		try {
			
			String requestQuery = "SELECT * FROM users WHERE email = ? and password = ?";

			prepStatement = userConnection.prepareStatement(requestQuery);

			prepStatement.setString(1, userBean.getEmail());
			prepStatement.setString(2, userBean.getPassword());

			resultSet = prepStatement.executeQuery();
			
			// ResultSet return
			while (resultSet.next()) {

				// print them
				System.out.println(resultSet.getInt(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + " " + resultSet.getString(4));

				userBean.setName(resultSet.getString("fullname"));
				return true;
			}
			
			userConnection.endRequest();
			userConnection.close();
			
			
		} catch (SQLException e) {
			System.out.println("stateSql");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return false;
	}
	
	public static void addFeedMessageToSql(String message, String hashTag, String creator) {
		try {
			
			String requestQuery = "INSERT INTO `feed`(`message`, `hashtag`, `creator`, `datetime`) VALUES (?,?,?,now())";

			prepStatement = feedConnection.prepareStatement(requestQuery);

			prepStatement.setString(1, message);
			prepStatement.setString(2, hashTag);
			prepStatement.setString(3, creator);

			prepStatement.executeUpdate();
			
			feedConnection.endRequest();
			
		} catch (SQLException e) {
			System.out.println("stateSql");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	
	
	
	
	public static ResultSet getFeedFromSql() {
		try {
			
			String requestQuery = "SELECT * FROM feed";

			prepStatement = feedConnection.prepareStatement(requestQuery);
			resultSet = prepStatement.executeQuery();
			feedConnection.endRequest();			
			
			return resultSet;
			
		} catch (SQLException e) {
			System.out.println("stateSql");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
		return null;
	}
	
	public static void stopFeedConnectionSql() {
		
		try {
			feedConnection.close();			
		} catch (SQLException e) {
			System.out.println("stopFeedConnectionSql");
			System.out.println("SQLException: " + e.getMessage());
			System.out.println("SQLState: " + e.getSQLState());
			System.out.println("VendorError: " + e.getErrorCode());
		}
	}
	
	
	
}
