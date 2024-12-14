package views;

import controllers.UserController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.User;

public class LoginView extends Application{
	Scene sc;
	BorderPane bp;
	
	Alert alert;
	
	GridPane gpForm;
	HBox hbTitle, hbRegisterlink;
	
	Label loginLabel, usernameLabel, passwordLabel;
	
	TextField usernameField;
	
	PasswordField passwordField;
	
	Button loginButton;
	Hyperlink registerLink;
	
	private void initialize() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		gpForm = new GridPane();
		hbTitle = new HBox();
		hbRegisterlink = new HBox();
		
		loginLabel = new Label("Login");
		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		
		loginButton = new Button("Login");
		registerLink = new Hyperlink("Register a new account");
		
		alert = new Alert(AlertType.NONE);
	}
	
	private void layouting() {
		hbTitle.getChildren().add(loginLabel);
		hbTitle.setAlignment(Pos.CENTER);
		
		// username
		gpForm.add(usernameLabel, 0, 0);
		gpForm.add(usernameField, 0, 1);
		
		// password
		gpForm.add(passwordLabel, 0, 2);
		gpForm.add(passwordField, 0, 3);
		
		// login button
		loginButton.setMaxWidth(Double.MAX_VALUE);
		gpForm.add(loginButton, 0, 4);
		
		// RegisterLink
		hbRegisterlink.getChildren().add(registerLink);
		hbRegisterlink.setAlignment(Pos.CENTER);
		gpForm.add(hbRegisterlink, 0, 5);		
		
		// Set size for gridpane
		gpForm.setVgap(6);
		gpForm.setPadding(new Insets(6, 40, 40, 40)); 
		gpForm.setAlignment(Pos.CENTER);
		
		bp.setTop(hbTitle);
		BorderPane.setMargin(hbTitle, new Insets(10));
		
		bp.setCenter(gpForm);
		BorderPane.setMargin(gpForm, new Insets(10));
	}
	
	// Login method
	private void login(Stage primaryStage) {
		String username = usernameField.getText().toString();
		String password = passwordField.getText().toString();
		
		if(username.isEmpty() || password.isEmpty()) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Username or Password cannot be empty");
			alert.show();
			return;
		}
		
		if(username.equals("admin") && password.equals("admin")) {
			HomepageView homepage = new HomepageView("admin", "admin");
			try {
				homepage.start(primaryStage);
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		User user = UserController.getInstance().Login(username, password);
		if(user != null) {
			HomepageView homepage = new HomepageView(user.getUser_id(), user.getRole());
			try {
				homepage.start(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Username or Password is wrong");
			alert.show();
			return;
		}
	}
	
	private void setAction(Stage primaryStage) {
		loginButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				login(primaryStage);
			}		
		});
		
		registerLink.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				RegisterView register  = new RegisterView();
				try {
					register.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		layouting();
		setAction(primaryStage);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}

}
