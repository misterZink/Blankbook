package model;

import database.SQLConnection;

public class UserBean {
	private String name, password, email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	
	}
	public boolean validate(UserBean userBean) {
		
		if (SQLConnection.connectSQL()) {
			return SQLConnection.userSql(userBean);
    	}
	
		return false;
	}
	public void resetUserBean() {
		this.password = null;
		this.name = null;
		this.email = null;
	}

}