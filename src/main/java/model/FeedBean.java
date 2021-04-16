package model;

import java.sql.ResultSet;

public class FeedBean {
	
	private ResultSet resultSet;
	
	
	public FeedBean() {
	}
	
	public FeedBean (ResultSet resultSet) {
		this.resultSet = resultSet;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	

}
