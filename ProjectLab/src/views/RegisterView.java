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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RegisterView extends Application{
	
	Scene sc;
	BorderPane bp;
	
	Alert alert;
	
	GridPane gpForm;
	HBox hbTitle;
	
	ToggleGroup tgRadio;
	
	Label registerLabel, usernameLabel, passwordLabel, confirmPasswordLabel, phonenumberLabel, addressLabel, roleLabel;
	
	TextField usernameField, phonenumberField, addressField;
	PasswordField passwordField, confirmPasswordField;
	
	RadioButton buyerRadio, sellerRadio;
	
	Button registerButton;
	Hyperlink loginLink;
	
	private void initialize() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		gpForm = new GridPane();
		hbTitle = new HBox();
		
		tgRadio = new ToggleGroup();
		
		registerLabel = new Label("Register your account");
		usernameLabel = new Label("Username");
		passwordLabel = new Label("Password");
		confirmPasswordLabel = new Label("Confirm password");
		phonenumberLabel = new Label("Phone Number");
		addressLabel = new Label("Address");
		roleLabel = new Label("Role");
		
		usernameField = new TextField();
		passwordField = new PasswordField();
		confirmPasswordField = new PasswordField();
		phonenumberField = new TextField();
		addressField = new TextField();
		buyerRadio = new RadioButton("Buyer");
		sellerRadio = new RadioButton("Seller");
		
		registerButton = new Button("Register");
		loginLink = new Hyperlink("I already have an account");
		
		alert = new Alert(AlertType.NONE);
	}
	
	private void layouting() {
		hbTitle.getChildren().add(registerLabel);
		hbTitle.setAlignment(Pos.CENTER);
		
		// username
		gpForm.add(usernameLabel, 0, 0);
		gpForm.add(usernameField, 0, 1);
		
		// password
		gpForm.add(passwordLabel, 0, 2);
		gpForm.add(passwordField, 0, 3);
		
		// confirm password
		gpForm.add(confirmPasswordLabel, 0, 4);
		gpForm.add(confirmPasswordField, 0, 5);
		
		// phonenumber
		gpForm.add(phonenumberLabel, 0, 6);
		gpForm.add(phonenumberField, 0, 7);
		
		// address
		gpForm.add(addressLabel, 0, 8);
		gpForm.add(addressField, 0, 9);
		
		// role
		gpForm.add(roleLabel, 0, 10);
		buyerRadio.setToggleGroup(tgRadio);
		sellerRadio.setToggleGroup(tgRadio);
		tgRadio.selectToggle(buyerRadio);
		gpForm.add(buyerRadio, 0, 11);
		gpForm.add(sellerRadio, 0, 12);
		
		// register button
		registerButton.setMaxWidth(Double.MAX_VALUE);
		gpForm.add(registerButton, 0, 13);
		
		// LoginLink
		gpForm.add(loginLink, 0, 14);		
		
		// Set size for gridpane
		gpForm.setVgap(6);
		gpForm.setPadding(new Insets(6, 40, 40, 40)); 
		gpForm.setAlignment(Pos.CENTER);
		
		bp.setTop(hbTitle);
		BorderPane.setMargin(hbTitle, new Insets(10));
		
		bp.setCenter(gpForm);
		BorderPane.setMargin(gpForm, new Insets(10));
	}
	
	private void Register() {
		String Username = usernameField.getText().toString();
		String Password = passwordField.getText().toString();
		String confirmPassword = confirmPasswordField.getText().toString();
		String Phone_Number = phonenumberField.getText().toString();
		String Address = addressField.getText().toString();
		RadioButton selected = (RadioButton) tgRadio.getSelectedToggle();
		String Role = selected.getText().toString();
		
		String validation = UserController.getInstance().checkAccountValidation(Username, Password, Phone_Number, Address, confirmPassword);
		
		if(validation != null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(validation);
			alert.show();
			return;
		}else {
			boolean result = UserController.getInstance().Register(Username, Password, Phone_Number, Address, Role);
			if(result) {
				usernameField.clear();
				passwordField.clear();
				confirmPasswordField.clear();
				phonenumberField.clear();
				addressField.clear();
				addressField.clear();
				tgRadio.selectToggle(buyerRadio);
				
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Account Registered");
				alert.show();
			}
		}
	}
	
	private void setAction(Stage primaryStage) {
		registerButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Register();
			}
		});
		
		loginLink.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				LoginView login = new LoginView();
				try {
					login.start(primaryStage);
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
		primaryStage.setTitle("CaLouselF");
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}

}
