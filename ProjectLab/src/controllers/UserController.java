package controllers;

import models.User;

public class UserController {
	
	private static UserController uc = null;
	private User userModel;
	
	private UserController() {
		userModel = new User();
	}
	
	public static UserController getInstance() {
		if(uc == null) {
			uc = new UserController();
		}
		return uc;
	}
	public String fetchUsername(String user_id) {
		return userModel.fetchUsername(user_id);
	}
	
	public User Login(String Username, String Password) {
		return userModel.Login(Username, Password);
	}
	
	public boolean Register(String Username, String Password, String Phone_Number, String Address, String Role) {
		return userModel.Register(Username, Password, Phone_Number, Address, Role);
	}
	
	public String checkAccountValidation(String Username, String Password, String Phone_Number, String Address) {
		return userModel.CheckAccountValidation(Username, Password, Phone_Number, Address);
	}
}
