package views;

import java.util.Vector;
import controllers.ItemController;
import controllers.TransactionController;
import controllers.UserController;
import controllers.WishlistController;
import javafx.application.Application;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Item;

public class HomepageView extends Application{
	Scene sc;
	
	// Passing through the userId and userRole data
	String user_id, user_role;
	
	// BorderPane for setting the element position in the page
	BorderPane bp;
	
	// Table View
	TableView<Item> tvItems;
	Vector<Item> items;
	// Table Column
	TableColumn<Item, String> nameColumn;
	TableColumn<Item, String> categoryColumn;
	TableColumn<Item, String> sizeColumn;
	TableColumn<Item, String> priceColumn;
	
	// Alert to show alert for information, confirmation, error;
	Alert alert;
	
	// TextInputDialog to take input for simple task
	TextInputDialog tdInput;
	
	// ComboBox for showing userName and logging out
	ComboBox<String> cb;
	
	VBox vb;
	
	// GridPane for Seller TextField
	GridPane gpForm;
	
	// Region for spacing
	HBox spacer, spacer2;
	
	HBox hbButtons, hbButtons2, hbTopbar;

	// Label for item view title/label
	Label itemViewLabel;
	
	// Label for Seller 
	Label UploapEditLabel, nameLabel, categoryLabel, sizeLabel, priceLabel;
	
	// TextField for Seller
	TextField nameField, categoryField, sizeField, priceField;
	
	// Button for logging out
	Button logoutButton;
	
	// Button for Buyer
	Button purchaseButton, offerButton, addWishlistButton, viewWishlistButton, purchaseHistoryButton;
	
	// Button for Seller
	Button editButton, deleteButton, uploadButton, viewOfferButton;
	
	// Button for Admin
	Button approveButton, declineButton;
	
	// Homepage constructor for changing page and passing through the data
	public HomepageView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}
	
	// BUYER HOMEPAGE
	
	// Initialize all that are needed in buyer homepage
	private void initializeBuyer() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		items = new Vector<>();
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		spacer = new HBox();
		
		hbButtons = new HBox();
		hbTopbar = new HBox();
		
		alert = new Alert(AlertType.NONE);
		tdInput = new TextInputDialog();
		
		itemViewLabel = new Label("Homepage");
		
		logoutButton = new Button("Logout");
		purchaseButton = new Button("Purchase");
		offerButton = new Button("Offer");
		addWishlistButton = new Button("Add to wishlist");
		viewWishlistButton = new Button("View wishlist");
		purchaseHistoryButton = new Button("Purchase history");
		
	}
	
	// Layouting the page for buyer
	private void layoutingBuyer() {
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
		hbTopbar.getChildren().add(itemViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);

		// Table Column set which column have what data
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
	
		// Set table view layout and input the column
		tvItems.setPadding(new Insets(10));
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		// Set HBox for buttons that will be invisible when an item is not click
		hbButtons.setPadding(new Insets(10));
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(viewWishlistButton);
		hbButtons.getChildren().add(purchaseHistoryButton);
		hbButtons.getChildren().add(purchaseButton);
		hbButtons.getChildren().add(offerButton);
		hbButtons.getChildren().add(addWishlistButton);
		purchaseButton.setVisible(false);
		offerButton.setVisible(false);
		addWishlistButton.setVisible(false);
		
		// Setting the border pane top, center, and bottom
		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	// Viewing item in display
	private void ViewItemBuyer() {
		// Calling item controller for using ViewItem() function
		items = ItemController.getInstance().ViewItem();
		
		// Refresh the table
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	// Method when buyer purchase an item
	public void PurchaseItems(Item item){
		// Calling Transaction controller to call purchase items
		boolean result = TransactionController.getInstance().PurchaseItems(user_id, item.getItem_id());
		
		// If the purchase successful, it will remove the offer for the item, all the wishlist,
		// and make a transaction history
		if(result) {
			ItemController.getInstance().RemoveOffer(item.getItem_id());
			WishlistController.getInstance().CheckWishlists(item.getItem_id());
			TransactionController.getInstance().CreateTransaction(null);
			
			// Set alert
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Purchase Successful");
			alert.show();
			
			// Show View again
			ViewItemBuyer();
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Purchase Failed");
			alert.show();
		}
	}
	
	// Method when buyer give an offer for an item
	public void OfferItems(String item_id){
		String offerPrice = tdInput.getEditor().getText().toString();
		String offerValidation = ItemController.getInstance().CheckOfferValidation(item_id, offerPrice);
		
		if(offerValidation.equals(offerPrice)) {
			if(ItemController.getInstance().OfferPrice(item_id, offerPrice, user_id)) {
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Offer Successful");
				alert.show();
			}
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(offerValidation);
			alert.show();
		}
	}
	
	public void AddWishlist(String item_id) {
		boolean result = WishlistController.getInstance().CheckDuplicate(item_id, user_id);
		if(!result) {
			WishlistController.getInstance().AddWishlist(item_id, user_id);
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Added to wishlist");
			alert.show();
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Item already added into your wishlist");
			alert.show();
		}
	}

	// Set action for buyer
	private void setActionBuyer(Stage primaryStage) {
		// When hovering cb it will change the color into light gray
		cb.setOnMouseEntered(event -> {
			cb.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		
		// When exit hover cb it will change into transparent
		cb.setOnMouseExited(event -> {
			cb.setBackground(null);
		});
		
		// Select Logout back to login view
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
		
		// Show button when clicking an item
		tvItems.setOnMouseClicked(event -> {
			purchaseButton.setVisible(true);
			offerButton.setVisible(true);
			addWishlistButton.setVisible(true);
		});
		
		// Set purchase button
		purchaseButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Select item
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				// if not show error
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
						// Purchase
						PurchaseItems(selectedItem);
						// Hide button
						purchaseButton.setVisible(false);
						offerButton.setVisible(false);
						addWishlistButton.setVisible(false);
					}
				});	
			}
		});
		
		// Set offer button
		offerButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				
				// Input response
				tdInput.getEditor().clear();
				tdInput.setHeaderText("Enter offer, current offer : $" + ItemController.getInstance().getCurrentOfferPrice(selectedItem.getItem_id()));
				tdInput.setContentText("Offer Price : ");
				tdInput.showAndWait().ifPresent(response -> {
					// Offer item
					OfferItems(selectedItem.getItem_id());
					// Hide button
					purchaseButton.setVisible(false);
					offerButton.setVisible(false);
					addWishlistButton.setVisible(false);
				});
			}
		});
		
		// Set add wishlist button
		addWishlistButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				// Add wishlist
				AddWishlist(selectedItem.getItem_id());
				// Hide button
				purchaseButton.setVisible(false);
				offerButton.setVisible(false);
				addWishlistButton.setVisible(false);
			}
		});
		
		// Set view wishlist button
		viewWishlistButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change page into wishlistview 
				WishlistView wishlistView = new WishlistView(user_id, user_role);
				try {
					wishlistView.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Set Purchase History button
		purchaseHistoryButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change page into historyView 
				PurchaseHistoryView historyView = new PurchaseHistoryView(user_id, user_role);
				try {
					historyView.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Seller Homepage
	
	// Initialize all that are needed in seller homepage
	private void initializeSeller() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		items = new Vector<>();
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		spacer = new HBox();
		spacer2 = new HBox();
		
		gpForm = new GridPane();
		hbTopbar = new HBox();
		hbButtons = new HBox();
		hbButtons2 = new HBox();
		vb = new VBox();
		
		alert = new Alert(AlertType.NONE);
		
		itemViewLabel = new Label("Homepage");
		UploapEditLabel = new Label("Upload/Edit Form :");
		nameLabel = new Label("Item Name");
		categoryLabel = new Label("Item Category");
		sizeLabel = new Label("Item Size");
		priceLabel = new Label("Item Price");
		
		nameField = new TextField();
		categoryField = new TextField();
		sizeField = new TextField();
		priceField = new TextField();

		editButton = new Button("Edit");
		deleteButton = new Button("Delete");
		uploadButton = new Button("Upload");
		viewOfferButton = new Button("View offer");
	}
	
	// Layouting the page for seller
	private void layoutingSeller() {
		// Set the ComboBox show the userName and making the selection of cb is only Logout
		cb.setPromptText(UserController.getInstance().fetchUsername(user_id));
		cb.getItems().add("Logout");
		cb.setBackground(null);
		
		// Making spacer so is always grow
		HBox.setHgrow(spacer, Priority.ALWAYS);
		HBox.setHgrow(spacer2, Priority.ALWAYS);
		
		// hbTopbar setting the topbar of the view
		hbTopbar.setPadding(new Insets(5));
		hbTopbar.setSpacing(10);
		hbTopbar.setAlignment(Pos.CENTER);
		hbTopbar.getChildren().add(itemViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);
		
		// Table Column set which column have what data
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		
		// Set table view layout and input the column
		tvItems.setPadding(new Insets(10));
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		// Set GridPane for inputing item data by seller
		gpForm.setHgap(2);
		gpForm.setVgap(5);
		
		gpForm.add(UploapEditLabel, 0, 0);
		
		gpForm.add(nameLabel, 0, 1);
		gpForm.add(nameField, 1, 1);
		
		gpForm.add(categoryLabel, 0, 2);
		gpForm.add(categoryField, 1, 2);
		
		gpForm.add(sizeLabel, 0, 3);
		gpForm.add(sizeField, 1, 3);
		
		gpForm.add(priceLabel, 0, 4);
		gpForm.add(priceField, 1, 4);
		
		// Set HBox for buttons
		hbButtons.setSpacing(5);
		hbButtons.setAlignment(Pos.CENTER_RIGHT);
		hbButtons.getChildren().add(uploadButton);
		hbButtons.getChildren().add(editButton);

		gpForm.add(hbButtons, 1, 5);		
		
		// Set HBox for buttons2
		hbButtons2.setSpacing(8);
		hbButtons2.getChildren().add(viewOfferButton);
		hbButtons2.getChildren().add(spacer2);
		deleteButton.setVisible(false);
		hbButtons2.getChildren().add(deleteButton);
		
		// Set VBox
		vb.setPadding(new Insets(10));
		vb.setSpacing(10);
		vb.getChildren().add(hbButtons2);
		vb.getChildren().add(gpForm);
		
		// Set BorderPane layout
		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(vb);
	}
	
	// Viewing item in display
	private void ViewItemSeller() {
		// Calling item controller for using ViewItem() function
		items = ItemController.getInstance().ViewAcceptedItem(null, user_id);
		
		// Refresh the table
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	// Method when seller delete an item
	public void DeleteItem(Item item) {
		// Remove offer and wishlist for that item
		ItemController.getInstance().RemoveOffer(item.getItem_id());
		WishlistController.getInstance().CheckWishlists(item.getItem_id());
		// Delete item
		boolean result = ItemController.getInstance().DeleteItem(item.getItem_id());
		
		if(result) {
			// Set alert
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Deleted item Successful");
			alert.show();
			
			// Show View again
			ViewItemSeller();
		}else {
			// Set alert
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Deleted item Failed");
			alert.show();
		}
	}
	
	// Method when seller upload an item
	public void UploadItem() {
		// Getting the data from TextField
		String name = nameField.getText().toString();
		String category = categoryField.getText().toString();
		String size = sizeField.getText().toString();
		String price = priceField.getText().toString();
		
		// Validate
		String validation = ItemController.getInstance().CheckItemValidation(name, category, size, price);
		
		if(validation != null) {
			// Set alert
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(validation);
			alert.show();
		}else {
			// Upload item
			boolean result = ItemController.getInstance().UploadItem(name, category, size, price, user_id);
			if(result) {
				// Clear TextField
				nameField.clear();
				categoryField.clear();
				sizeField.clear();
				priceField.clear();
				
				// Set alert
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Item upload successful");
				alert.show();
			}else {
				// Set alert
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Item upload failed");
				alert.show();
			}
		}
	}
	
	public void EditItem(String item_id) {
		// Getting the data from TextField
		String name = nameField.getText().toString();
		String category = categoryField.getText().toString();
		String size = sizeField.getText().toString();
		String price = priceField.getText().toString();
		
		// Validate
		String validation = ItemController.getInstance().CheckItemValidation(name, category, size, price);
		
		if(validation != null) {
			// Set alert
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(validation);
			alert.show();
		}else {
			// Edit item
			boolean result = ItemController.getInstance().EditItem(item_id, name, category, size, price);
			if(result) {
				// Clear TextField
				nameField.clear();
				categoryField.clear();
				sizeField.clear();
				priceField.clear();
				
				// Set alert
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Item edit successful");
				alert.show();
				// Show View again
				ViewItemSeller();
			}else {
				// Set alert
				alert.setAlertType(AlertType.ERROR);
				alert.setContentText("Item edit failed");
				alert.show();
			}
		}
	}
	
	// Set action for seller
	private void setActionSeller(Stage primaryStage) {
		// When hovering cb it will change the color into light gray
		cb.setOnMouseEntered(event -> {
			cb.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
		});
		
		// When exit hover cb it will change into transparent
		cb.setOnMouseExited(event -> {
			cb.setBackground(null);
		});
		
		// Select Logout back to login view
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
		
		// Show button when clicking an item
		tvItems.setOnMouseClicked(event -> {
			deleteButton.setVisible(true);
		});
		
		// Set delete button
		deleteButton.setOnMouseClicked(new EventHandler<Event>() {
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
				alert.setContentText("Delete Item?");
				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						// Delete item
						DeleteItem(selectedItem);
						deleteButton.setVisible(false);
					}
				});	
			}
		});
		
		// Set view offer button
		viewOfferButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change page into Offered Item page
				OfferedItemView offeredView = new OfferedItemView(user_id, user_role);
				try {
					offeredView.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		// Set upload button
		uploadButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Upload Item
				UploadItem();
			}
		});
		
		// Set edit button
		editButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvItems.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				// Edit item
				EditItem(selectedItem.getItem_id());
				deleteButton.setVisible(false);
			}
		});
	}
	
	// Admin Homepage
	
	// Initialize all that are needed in admin homepage
	private void initializeAdmin() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		items = new Vector<>();
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		spacer = new HBox();
		
		hbTopbar = new HBox();
		hbButtons = new HBox();
		
		alert = new Alert(AlertType.NONE);
		tdInput = new TextInputDialog();
		
		itemViewLabel = new Label("Homepage");

		approveButton = new Button("Approve Item");
		declineButton = new Button("Decline Item");
	}
	
	// Layouting the page for admin
	private void layoutingAdmin() {
		// Set the ComboBox show the userName and making the selection of cb is only Logout
		cb.setPromptText(user_id);
		cb.getItems().add("Logout");
		cb.setBackground(null);
		
		// Making spacer so is always grow
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		// hbTopbar setting the topbar of the view
		hbTopbar.setPadding(new Insets(5));
		hbTopbar.setSpacing(10);
		hbTopbar.setAlignment(Pos.CENTER);
		hbTopbar.getChildren().add(itemViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);

		// Table Column set which column have what data
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		
		// Set table view layout and input the column
		tvItems.setPadding(new Insets(10));
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		hbButtons.setPadding(new Insets(10));
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(approveButton);
		hbButtons.getChildren().add(declineButton);
		hbButtons.setVisible(false);

		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	// Viewing item in display
	private void ViewItemAdmin() {
		// Calling item controller for using ViewItem() function
		items = ItemController.getInstance().ViewItemAdmin();
		// Refresh the table
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	// Method when approve item
	private void ApproveItem(String item_id) {
		boolean result = ItemController.getInstance().ApproveItem(item_id);
		
		if(result) {
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Item approved Successful");
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Failed to approve item");
		}
		ViewItemAdmin();
		alert.show();
	}
	
	// Method when decline item
	private void DeclineItem(String item_id) {
		String reason_text = tdInput.getEditor().getText().toString();
		
		if(!reason_text.isBlank()) {
			ItemController.getInstance().GiveReason(reason_text, item_id, user_id);
			ItemController.getInstance().DeclineItem(item_id);
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Item Declined Successful");
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Reason cannot be empty");
		}
		ViewItemAdmin();
		alert.show();
	}
	
	// Set action admin
	private void setActionAdmin(Stage primaryStage) {
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
			hbButtons.setVisible(true);
		});
		
		// Set approve button
		approveButton.setOnMouseClicked(new EventHandler<Event>() {
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
				alert.setContentText("Approve Item?");
				alert.showAndWait().ifPresent(response -> {
					if(response == ButtonType.OK) {
						// Approve Item
						ApproveItem(selectedItem.getItem_id());
						hbButtons.setVisible(false);
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
				
				// Input response
				tdInput.getEditor().clear();
				tdInput.setHeaderText("Enter Reason");
				tdInput.showAndWait().ifPresent(response -> {
					// Decline item
					DeclineItem(selectedItem.getItem_id());
					hbButtons.setVisible(false);
				});
			}
		});
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			// Buyer Role
			if(user_role.equals("buyer")) {
				initializeBuyer(); 
		        layoutingBuyer();   
		        ViewItemBuyer();  
		        setActionBuyer(primaryStage);
		     // Seller Role
			}else if(user_role.equals("seller")) {
				initializeSeller(); 
		        layoutingSeller();  
		        ViewItemSeller();
		        setActionSeller(primaryStage);
		     // Admin Role
			}else if(user_role.equals("admin")) {
				initializeAdmin(); 
		        layoutingAdmin();  
		        ViewItemAdmin();
		        setActionAdmin(primaryStage);
			}
			// Set Scene
	        primaryStage.setScene(sc);
	        // Show stage
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setContentText("An error occurred: " + e.getMessage());
	        alert.showAndWait();
	    }
		
	}

}
