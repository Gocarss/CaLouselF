package views;

import java.util.Vector;

import controllers.TransactionController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Transaction;

public class PurchaseHistoryView extends Application{
	Scene sc;
	BorderPane bp;
	TableView tvItems;
	String user_id, user_role;
	
	Alert alert;
	
	Vector<Transaction> transactions;
	
	TableColumn<Transaction, String> transactionIdColumn;
	TableColumn<Transaction, String> nameColumn;
	TableColumn<Transaction, String> categoryColumn;
	TableColumn<Transaction, String> sizeColumn;
	TableColumn<Transaction, String> priceColumn;
	
	GridPane gpForm;
	HBox hbButtons;
	
	Label itemViewLabel;
	
	Button backButton;
	
	public PurchaseHistoryView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}

	private void initialize() {
		transactions = new Vector<>();
		
		bp = new BorderPane();
		sc = new Scene(bp);
		tvItems = new TableView<>();
		
		transactionIdColumn = new TableColumn<Transaction, String>("Transaction ID");
		nameColumn = new TableColumn<Transaction, String>("Name");
		categoryColumn = new TableColumn<Transaction, String>("Category");
		sizeColumn = new TableColumn<Transaction, String>("Size");
		priceColumn = new TableColumn<Transaction, String>("Price");
		
		gpForm = new GridPane();
		hbButtons = new HBox();
		
		itemViewLabel = new Label("Purchase History");
		
		alert = new Alert(AlertType.NONE);

		backButton = new Button("Back");
		
	}
	
	private void layouting() {
		bp.setTop(itemViewLabel);
		
		transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transaction_id"));
		nameColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_name()));
		categoryColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_category()));
		sizeColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_size()));
		priceColumn.setCellValueFactory(CellDataFeatures -> new SimpleStringProperty(CellDataFeatures.getValue().getItemDetails().getItem_price()));
		tvItems.getColumns().addAll(transactionIdColumn, nameColumn, categoryColumn, sizeColumn, priceColumn);
			
		hbButtons.getChildren().add(backButton);

		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	private void ViewHistory() {
		// panggil controller untuk validasi select data
		transactions = TransactionController.getInstance().ViewHistory(user_id);
		// refresh tampilan tableviewnya
		tvItems.getItems().clear();
		tvItems.getItems().addAll(transactions);
	}
	
	private void setAction(Stage primaryStage) {
		backButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
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
