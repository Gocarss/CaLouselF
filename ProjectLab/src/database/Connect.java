package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private static Connect con = null;
	String url = "jdbc:mysql://localhost:3306/calouself";
    String username = "root";
    String password = "";
    Connection connection;
    public Statement stmt;
    public ResultSet rs;
    public PreparedStatement ps;
	
	private Connect() {
	    try {
//	    	Class.forName("com.mysql.jdbc.Driver");
	        connection = DriverManager.getConnection(url, username, password);
	        System.out.println("create stmt");
	        stmt = connection.createStatement();
	    } catch (Exception e) {
	        System.err.println("Error connecting to the database: " + e.getMessage());
	    }
	}
	
	public static Connect getConnection() {
		if (con == null) {
			con = new Connect();
		}
		return con;
	}

	public ResultSet execQuery(String query) {
		try {
			rs = stmt.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	public PreparedStatement prepareStatement(String query) {
		try {
			ps = connection.prepareStatement(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
}
