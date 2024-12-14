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
	HBox spacer;
	
	HBox hbButtons, hbTopbar, hbBottombar;

	// Label for item view title/label
	Label itemViewLabel;
	
	// Label for Seller 
	Label nameLabel, categoryLabel, sizeLabel, priceLabel;
	
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
		hbBottombar = new HBox();
		
		alert = new Alert(AlertType.NONE);
		tdInput = new TextInputDialog();
		
		itemViewLabel = new Label("Item View");
		
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
		
		
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(purchaseButton);
		hbButtons.getChildren().add(offerButton);
		hbButtons.getChildren().add(addWishlistButton);
		hbButtons.setVisible(false);
		
		hbBottombar.setPadding(new Insets(10));
		hbBottombar.setSpacing(8);
		hbBottombar.getChildren().add(viewWishlistButton);
		hbBottombar.getChildren().add(purchaseHistoryButton);
		hbBottombar.getChildren().add(hbButtons);
		
		bp.setTop(hbTopbar);
		bp.setCenter(tvItems);
		bp.setBottom(hbBottombar);
	}
	
	private void ViewItemBuyer() {
		// panggil controller untuk validasi select data
		items = ItemController.getInstance().ViewItem();
		// refresh tampilan tableviewnya
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	public void PurchaseItems(Item item){
		boolean result = TransactionController.getInstance().PurchaseItems(user_id, item.getItem_id());
		
		if(result) {
			ItemController.getInstance().RemoveOffer(item.getItem_id());
			WishlistController.getInstance().CheckWishlists(item.getItem_id());
			TransactionController.getInstance().CreateTransaction(null);
			ViewItemBuyer();
		}
	}
	
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

	
	private void setActionBuyer(Stage primaryStage) {
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
		
		purchaseButton.setOnMouseClicked(new EventHandler<Event>() {
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
						PurchaseItems(selectedItem);
						alert.setAlertType(AlertType.INFORMATION);
						alert.setContentText("Purchase Successful");
						alert.show();
						return;
					}
				});	
			}
		});
		
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
				tdInput.getEditor().clear();
				tdInput.setHeaderText("Enter offer, current offer : $" + ItemController.getInstance().getCurrentOffer(selectedItem.getItem_id()));
				tdInput.setContentText("Offer Price : ");
				tdInput.showAndWait().ifPresent(response -> {
					OfferItems(selectedItem.getItem_id());
					return;
				});
			}
		});
		
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
				AddWishlist(selectedItem.getItem_id());
			}
		});
		
		viewWishlistButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				WishlistView wishlistView = new WishlistView(user_id, user_role);
				try {
					wishlistView.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		purchaseHistoryButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
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
	private void initializeSeller() {
		items = new Vector<>();
		
		bp = new BorderPane();
		sc = new Scene(bp);
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		gpForm = new GridPane();
		hbButtons = new HBox();
		vb = new VBox();
		
		itemViewLabel = new Label("Seller Item");
		nameLabel = new Label("Name item");
		categoryLabel = new Label("Category item");
		sizeLabel = new Label("Size item");
		priceLabel = new Label("Price item");
		
		nameField = new TextField();
		categoryField = new TextField();
		sizeField = new TextField();
		priceField = new TextField();
		
		alert = new Alert(AlertType.NONE);

		editButton = new Button("Edit");
		deleteButton = new Button("Delete");
		uploadButton = new Button("Upload");
		viewOfferButton = new Button("View offer");
		
	}
	
	private void layoutingSeller() {
		bp.setTop(itemViewLabel);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		gpForm.add(nameLabel, 0, 0);
		gpForm.add(nameField, 1, 0);
		
		gpForm.add(categoryLabel, 0, 1);
		gpForm.add(categoryField, 1, 1);
		
		gpForm.add(sizeLabel, 0, 2);
		gpForm.add(sizeField, 1, 2);
		
		gpForm.add(priceLabel, 0, 3);
		gpForm.add(priceField, 1, 3);
		
		gpForm.add(uploadButton, 0, 4);
		gpForm.add(editButton, 1, 4);
		
		hbButtons.getChildren().add(deleteButton);
		hbButtons.getChildren().add(viewOfferButton);
		
		vb.getChildren().add(hbButtons);
		vb.getChildren().add(gpForm);

		bp.setCenter(tvItems);
		bp.setBottom(vb);
	}
	
	private void ViewItemSeller() {
		// panggil controller untuk validasi select data
		items = ItemController.getInstance().ViewAcceptedItem(null, user_id);
		// refresh tampilan tableviewnya
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	public void DeleteItem(Item item) {
		ItemController.getInstance().RemoveOffer(item.getItem_id());
		WishlistController.getInstance().CheckWishlists(item.getItem_id());
		boolean result = ItemController.getInstance().DeleteItem(item.getItem_id());
		
		if(result) {

			ViewItemSeller();
		}
	}
	
	public void UploadItem() {
		String name = nameField.getText().toString();
		String category = categoryField.getText().toString();
		String size = sizeField.getText().toString();
		String price = priceField.getText().toString();
		
		String validation = ItemController.getInstance().CheckItemValidation(name, category, size, price);
		
		if(validation != null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(validation);
			alert.show();
			return;
		}else {
			boolean result = ItemController.getInstance().UploadItem(name, category, size, price, user_id);
			if(result) {
				nameField.clear();
				categoryField.clear();
				sizeField.clear();
				priceField.clear();
				
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Upload Successful");
				alert.show();
			}
		}
	}
	
	public void EditItem(String item_id) {
		String name = nameField.getText().toString();
		String category = categoryField.getText().toString();
		String size = sizeField.getText().toString();
		String price = priceField.getText().toString();
		
		String validation = ItemController.getInstance().CheckItemValidation(name, category, size, price);
		
		if(validation != null) {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText(validation);
			alert.show();
			return;
		}else {
			boolean result = ItemController.getInstance().EditItem(item_id, name, category, size, price);
			if(result) {
				nameField.clear();
				categoryField.clear();
				sizeField.clear();
				priceField.clear();
				
				alert.setAlertType(AlertType.INFORMATION);
				alert.setContentText("Edit Successful");
				alert.show();
				ViewItemSeller();
			}
		}
	}
	
	private void setActionSeller(Stage primaryStage) {
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
						DeleteItem(selectedItem);
						alert.setAlertType(AlertType.INFORMATION);
						alert.setContentText("Deleted item Successful");
						alert.show();
						return;
					}
				});	
			}
		});
		
		viewOfferButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				OfferedItemView offeredView = new OfferedItemView(user_id, user_role);
				try {
					offeredView.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		uploadButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				UploadItem();
			}
		});
		
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
				EditItem(selectedItem.getItem_id());
			}
		});
	}
	
	// Admin Homepage
	private void initializeAdmin() {
		items = new Vector<>();
		
		bp = new BorderPane();
		sc = new Scene(bp);
		tvItems = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		gpForm = new GridPane();
		hbButtons = new HBox();
		
		itemViewLabel = new Label("Admin View");
		
		alert = new Alert(AlertType.NONE);
		tdInput = new TextInputDialog();

		approveButton = new Button("Approve Item");
		declineButton = new Button("Decline Item");
	}
	
	private void layoutingAdmin() {
		bp.setTop(itemViewLabel);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
			
		tvItems.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		hbButtons.getChildren().add(approveButton);
		hbButtons.getChildren().add(declineButton);

		bp.setCenter(tvItems);
		bp.setBottom(hbButtons);
	}
	
	
	private void ViewItemAdmin() {
		// panggil controller untuk validasi select data
		items = ItemController.getInstance().ViewItemAdmin();
		// refresh tampilan tableviewnya
		tvItems.getItems().clear();
		tvItems.getItems().addAll(items);
	}
	
	private void ApproveItem(String item_id) {
		boolean result = ItemController.getInstance().ApproveItem(item_id);
		
		if(result) {
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Item Approved Successful");
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Failed to approve item");
		}
		ViewItemAdmin();
		alert.show();
	}
	
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
	
	private void setActionAdmin(Stage primaryStage) {
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
						ApproveItem(selectedItem.getItem_id());
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
				
				tdInput.getEditor().clear();
				tdInput.setHeaderText("Enter Reason");
				tdInput.showAndWait().ifPresent(response -> {
					DeclineItem(selectedItem.getItem_id());
					return;
				});
				
			}
		});

	}
	
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			if(user_role.equals("buyer")) {
				initializeBuyer(); 
		        layoutingBuyer();   
		        ViewItemBuyer();  
		        setActionBuyer(primaryStage);
			}else if(user_role.equals("seller")) {
				initializeSeller(); 
		        layoutingSeller();  
		        ViewItemSeller();
		        setActionSeller(primaryStage);
			}else if(user_role.equals("admin")) {
				initializeAdmin(); 
		        layoutingAdmin();  
		        ViewItemAdmin();
		        setActionAdmin(primaryStage);
			}
			
	        

	        primaryStage.setScene(sc);
	        primaryStage.show();
	    } catch (Exception e) {
	        e.printStackTrace();
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setContentText("An error occurred: " + e.getMessage());
	        alert.showAndWait();
	    }
		
	}

}
