package views;

import java.util.Vector;

import controllers.ItemController;
import controllers.TransactionController;
import controllers.UserController;
import controllers.WishlistController;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
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
import models.Item;

public class OfferedItemView extends Application{
	Scene sc;
	
	String user_id, user_role;
	
	BorderPane bp;
	
	TableView<Item> tvItems;
	Vector<Item> offeredItem;
	
	TableColumn<Item, String> nameColumn;
	TableColumn<Item, String> categoryColumn;
	TableColumn<Item, String> sizeColumn;
	TableColumn<Item, String> priceColumn;
	TableColumn<Item, String> offerPriceColumn;
	
	Alert alert;
	TextInputDialog tdReason;
	
	ComboBox<String> cb;
	
	HBox spacer;
	
	HBox hbTopbar, hbButtons;
	
	Label offerViewLabel;
	
	Button backButton, acceptButton, declineButton;
	
	// OfferedItemView Constructor
	public OfferedItemView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}

	// Initialize the element
	private void initialize() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		offeredItem = new Vector<>();
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		offerPriceColumn = new TableColumn<Item, String>("Offer Price");
		
		spacer = new HBox();
		
		hbTopbar = new HBox();
		hbButtons = new HBox();
		
		alert = new Alert(AlertType.NONE);
		tdReason = new TextInputDialog();
		
		offerViewLabel = new Label("Offered Item");

		backButton = new Button("Back");
		acceptButton = new Button("Accept");
		declineButton = new Button("Decline");
	}
	
	// Layouting
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
		hbTopbar.getChildren().add(offerViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		offerPriceColumn.setCellValueFactory(CellDataFeatures -> {
			String itemId = CellDataFeatures.getValue().getItem_id();
			// Call getCurrentOffer using the item_id
			Double offerPrice = CellDataFeatures.getValue().getCurrentOfferPrice(itemId);
			// Return a SimpleStringProperty wrapping the offerPrice
			return new SimpleStringProperty("$ " + offerPrice.toString());
		});
		
		// Set table view layout and input the column
		tvItems.setPadding(new Insets(10));
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn, offerPriceColumn);
		
		// Set HBox for buttons that will be invisible when an item is not click
		hbButtons.setPadding(new Insets(10));
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(backButton);
		hbButtons.getChildren().add(acceptButton);
		hbButtons.getChildren().add(declineButton);
		acceptButton.setVisible(false);
		declineButton.setVisible(false);
		
		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	// Method to view offered item
	private void ViewOfferedItem() {
		// Calling item controller for using ViewItem() function
		offeredItem = ItemController.getInstance().ViewOfferItem(user_id);
		
		// Refresh the table
		tvItems.getItems().clear();
		tvItems.getItems().addAll(offeredItem);
	}
	
	// Set accept offer
	private void AcceptOffer(Item item) {
		String buyer_id = ItemController.getInstance().getCurrentOfferBuyer(item.getItem_id());
		boolean result = (ItemController.getInstance().AcceptOffer(item.getItem_id()) 
				&& TransactionController.getInstance().PurchaseItems(buyer_id, item.getItem_id()));
		if(result) {
			WishlistController.getInstance().CheckWishlists(item.getItem_id());
			TransactionController.getInstance().CreateTransaction(null);
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Offer Accepted");
			alert.show();
			ViewOfferedItem();
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Accept Action Failed");
			alert.show();
		}
	}
	
	// Set decline offer
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
		// Refresh
		ViewOfferedItem();
		alert.show();
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
		
		tvItems.setOnMouseClicked(event -> {
			acceptButton.setVisible(true);
			declineButton.setVisible(true);
		});
		
		// Set back button
		backButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change back into homepage
				HomepageView homepage = new HomepageView(user_id, user_role);
				try {
					homepage.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Set accept button
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
				
				// Confirmation
				alert.setAlertType(AlertType.CONFIRMATION);
				alert.setContentText("Confirm Purchase?");
				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						// Accept button
						AcceptOffer(selectedItem);
						acceptButton.setVisible(false);
						declineButton.setVisible(false);
					}
				});	
			}
		});
		
		// Set decline button
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
				
				// Enter reason
				tdReason.getEditor().clear();
				tdReason.setHeaderText("Enter Reason");
				tdReason.showAndWait().ifPresent(response -> {
					// Decline offer
					DeclineOffer(selectedItem.getItem_id());
					acceptButton.setVisible(false);
					declineButton.setVisible(false);
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
