package views;

import java.util.Vector;

import controllers.ItemController;
import controllers.TransactionController;
import controllers.WishlistController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import models.Item;

public class OfferedItemView extends Application{
	Scene sc;
	BorderPane bp;
	TableView<Item> tvItems;
	String user_id, user_role;
	
	Alert alert;
	TextInputDialog tdReason;
	
	Vector<Item> offeredItem;
	
	TableColumn<Item, String> nameColumn;
	TableColumn<Item, String> categoryColumn;
	TableColumn<Item, String> sizeColumn;
	TableColumn<Item, String> priceColumn;
	TableColumn<Item, String> offerPriceColumn;
	
	GridPane gpForm;
	HBox hbButtons;
	
	Label itemViewLabel;
	
	Button backButton, acceptButton, declineButton;
	
	public OfferedItemView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}

	private void initialize() {
		offeredItem = new Vector<>();
		
		bp = new BorderPane();
		sc = new Scene(bp);
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		offerPriceColumn = new TableColumn<Item, String>("Offer Price");
		
		gpForm = new GridPane();
		hbButtons = new HBox();
		
		itemViewLabel = new Label("Offered Item");
		
		alert = new Alert(AlertType.NONE);
		tdReason = new TextInputDialog();

		backButton = new Button("Back");
		acceptButton = new Button("Accept");
		declineButton = new Button("Decline");
		
	}
	
	private void layouting() {
		bp.setTop(itemViewLabel);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		offerPriceColumn.setCellValueFactory(CellDataFeatures -> {
			String itemId = CellDataFeatures.getValue().getItem_id();
			// Call getCurrentOffer using the item_id
			Double offerPrice = CellDataFeatures.getValue().getCurrentOffer(itemId);
			// Return a SimpleStringProperty wrapping the offerPrice
			return new SimpleStringProperty(offerPrice.toString());
		});
		
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn, offerPriceColumn);
			
		hbButtons.getChildren().add(backButton);
		hbButtons.getChildren().add(acceptButton);
		hbButtons.getChildren().add(declineButton);

		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	private void ViewOfferedItem() {
		// panggil controller untuk validasi select data
		offeredItem = ItemController.getInstance().ViewOfferItem(user_id);
		// refresh tampilan tableviewnya
		tvItems.getItems().clear();
		tvItems.getItems().addAll(offeredItem);
	}
	
	private void AcceptOffer(Item item) {
		boolean result = (ItemController.getInstance().AcceptOffer(item.getItem_id()) && TransactionController.getInstance().PurchaseItems(user_id, item.getItem_id()));
		if(result) {
			WishlistController.getInstance().CheckWishlists(item.getItem_id());
			TransactionController.getInstance().CreateTransaction(null);
			ViewOfferedItem();
		}
		
	}
	
	private void DeclineOffer(String item_id) {
		String reason_text = tdReason.getEditor().getText().toString();
		
		if(!reason_text.isBlank()) {
			ItemController.getInstance().GiveReason(reason_text, item_id, user_id);
			ItemController.getInstance().DeclineOffer(item_id);
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Declined offer");
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Reason cannot be empty");
		}
		ViewOfferedItem();
		alert.show();
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
		
		acceptButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				
				alert.setAlertType(AlertType.CONFIRMATION);
				alert.setContentText("Confirm Purchase?");
				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						AcceptOffer(selectedItem);
						alert.setAlertType(AlertType.INFORMATION);
						alert.setContentText("Offer Accepted");
						alert.show();
						return;
					}
				});	
			}
		});
		
		declineButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				tdReason.getEditor().clear();
				tdReason.setHeaderText("Enter Reason");
				tdReason.showAndWait().ifPresent(response -> {
					DeclineOffer(selectedItem.getItem_id());
					return;
				});
			}
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		initialize();
		layouting();
		ViewOfferedItem();
		setAction(primaryStage);
		primaryStage.setScene(sc);
		primaryStage.show();
		
	}
}
