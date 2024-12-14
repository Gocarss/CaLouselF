package views;

import java.util.Vector;

import controllers.TransactionController;
import controllers.UserController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Transaction;

public class PurchaseHistoryView extends Application{
	Scene sc;
	
	String user_id, user_role;
	
	BorderPane bp;
	
	TableView<Transaction> tvItems;
	Vector<Transaction> transactions;
	
	TableColumn<Transaction, String> transactionIdColumn;
	TableColumn<Transaction, String> nameColumn;
	TableColumn<Transaction, String> categoryColumn;
	TableColumn<Transaction, String> sizeColumn;
	TableColumn<Transaction, String> priceColumn;
	
	Alert alert;
	
	ComboBox<String> cb;

	HBox spacer;
	
	HBox hbTopbar, hbButtons;
	
	Label historyViewLabel;
	
	Button backButton;
	
	public PurchaseHistoryView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}

	private void initialize() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		transactions = new Vector<>();
		tvItems = new TableView<Transaction>();
		
		transactionIdColumn = new TableColumn<Transaction, String>("Transaction ID");
		nameColumn = new TableColumn<Transaction, String>("Name");
		categoryColumn = new TableColumn<Transaction, String>("Category");
		sizeColumn = new TableColumn<Transaction, String>("Size");
		priceColumn = new TableColumn<Transaction, String>("Price");
		
		spacer = new HBox();
		
		hbTopbar = new HBox();
		hbButtons = new HBox();
		
		alert = new Alert(AlertType.NONE);
		
		historyViewLabel = new Label("Purchase History");

		backButton = new Button("Back");
		
	}
	
	private void layouting() {
		// Set the ComboBox show the userName and making the selection of cb is only Logout
		cb.setPromptText(UserController.getInstance().fetchUsername(user_id));
		cb.getItems().add("Logout");
		cb.setBackground(null);
				
		// Making spacer so is always grow
		HBox.setHgrow(spacer, Priority.ALWAYS);
				
		// hbTopbar setting the topbar of the view
		hbTopbar.setPadding(new Insets(5));
		hbTopbar.setSpacing(10);
		hbTopbar.setAlignment(Pos.CENTER);
		hbTopbar.getChildren().add(historyViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);
		
		transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
		nameColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_name()));
		categoryColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_category()));
		sizeColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_size()));
		priceColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_price()));
		
		// Set table view layout and input the column
		tvItems.setPadding(new Insets(10));
		tvItems.getColumns().addAll(transactionIdColumn, nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		hbButtons.setPadding(new Insets(10));
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(backButton);
		
		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	// Method View History
	private void ViewHistory() {
		// Calling item controller for using ViewItem() function
		transactions = TransactionController.getInstance().ViewHistory(user_id);
		
		// Refresh the table
		tvItems.getItems().clear();
		tvItems.getItems().addAll(transactions);
	}
	
	// Set action
	private void setAction(Stage primaryStage) {
		cb.setOnMouseEntered(event -> {
			cb.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		
		cb.setOnMouseExited(event -> {
			cb.setBackground(null);
		});
		
		cb.setOnAction(event -> {
			String selectLogout = cb.getValue();
			if(selectLogout.equals("Logout")) {
				LoginView login = new LoginView();
				try {
					login.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Set back button
		backButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change view into homepage 
				HomepageView homepage = new HomepageView(user_id, user_role);
				try {
					homepage.start(primaryStage);
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
		ViewHistory();
		setAction(primaryStage);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}
}
