package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.Connect;

public class User {
	private String User_id, Username, Password, Phone_Number, Address, Role;

	public User(String user_id, String username, String password, String phone_Number, String address, String role) {
		super();
		User_id = user_id;
		Username = username;
		Password = password;
		Phone_Number = phone_Number;
		Address = address;
		Role = role;
	}
	
	public User() {}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getPhone_Number() {
		return Phone_Number;
	}

	public void setPhone_Number(String phone_Number) {
		Phone_Number = phone_Number;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}
	
	public String fetchUsername(String user_id) {
		String query = "SELECT * FROM `user` WHERE `user_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getString("username");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String userIdGenerator(String Role) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        
        // If role buyer than have identifier which is BU at the start of the id
        // or SE for seller
        if(Role.equals("Buyer")) {
        	id.append("BU");
        	
        }else if(Role.equals("Seller")) {
        	id.append("SE");
        }
        
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
	}
	
	public User Login(String Username, String Password) {
		String query = ("SELECT * FROM `user` WHERE `username`=? AND `password`=?");
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Username);
			ps.setString(2, Password);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new User(rs.getString("user_id"), 
						rs.getString("username"), 
						rs.getString("password"), 
						rs.getString("phone_number"), 
						rs.getString("address"), 
						rs.getString("role"));
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public boolean Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		String query = ("INSERT INTO `user`(`user_id`, `username`, `password`, `phone_number`, `address`, `role`) VALUES (?,?,?,?,?,?)");
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, userIdGenerator(Role));
			ps.setString(2, Username);
			ps.setString(3, Password);
			ps.setString(4, Phone_Number);
			ps.setString(5, Address);
			ps.setString(6, Role);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String CheckAccountValidation(String Username, String Password, String Phone_Number, String Address, String confirmPassword) {
		String query = "SELECT * FROM `user` WHERE `username`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		
		// Check if empty
		if (Username.isBlank() || Password.isBlank() || Phone_Number.isBlank() || Address.isBlank()) {
			return "All fields must be filled";
		}
		
		// Check Username
		if(Username.length() < 3) {
			return "Username must at least be 3 character long";
		}else{
			try {
				ps.setString(1, Username);
				ResultSet rs = ps.executeQuery();
				if(rs.next()) {
					return "Username is already been taken";
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		// Check Password
		if(Password.length() < 8) {
			return "Password must at least be 8 character long!";
		}else if(!(Password.contains("!") || Password.contains("@") || Password.contains("#") || Password.contains("$") || Password.contains("%") || Password.contains("^") || Password.contains("&") || Password.contains("*"))) {
			return "Password must include special characters (!, @, #, $, %, ^, &, *)";
		}else if(!Password.equals(confirmPassword)) {
			return "Password and Confirm password must be the same";
		}
	    
		// Check Phone Number
		if(!(Phone_Number.startsWith("+62"))){
        	return "Phone Number must start with +62";
        }else {
    		String tempNumber = Phone_Number.replace("+62", "");
    		if(tempNumber.length() != 9) {
    			return "Must be 10 numbers long (+62 counts as one)";
    		}
    		
    		try {
    			Integer.parseInt(tempNumber);
    			
			} catch (NumberFormatException e) {
				return "Must be in number";
			}
    		
    	}
		return null;
	}

}
