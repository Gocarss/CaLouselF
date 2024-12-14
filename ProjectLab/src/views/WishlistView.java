package views;

import java.util.Vector;

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
import models.Item;

public class WishlistView extends Application{
	Scene sc;
	
	String user_id, user_role;
	
	BorderPane bp;
	
	TableView<Item> tvWishlist;
	Vector<Item> itemsWishlist;
	
	TableColumn<Item, String> nameColumn;
	TableColumn<Item, String> categoryColumn;
	TableColumn<Item, String> sizeColumn;
	TableColumn<Item, String> priceColumn;
	
	Alert alert;
	
	ComboBox<String> cb;
	
	HBox spacer;
	
	HBox hbTopbar, hbButtons;
	
	Label wishlistViewLabel;
	
	Button backButton, removeWishlistButton;
	
	public WishlistView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}
	
	private void initialize() {
		bp = new BorderPane();
		sc = new Scene(bp);
		
		cb = new ComboBox<String>();
		
		itemsWishlist = new Vector<>();		
		tvWishlist = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		spacer = new HBox();
		
		hbTopbar = new HBox();
		hbButtons = new HBox();
		
		alert = new Alert(AlertType.NONE);
		
		wishlistViewLabel = new Label("Wishlist");

		backButton = new Button("Back");
		removeWishlistButton = new Button("Remove Item");
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
		hbTopbar.getChildren().add(wishlistViewLabel);
		hbTopbar.getChildren().add(spacer);
		hbTopbar.getChildren().add(cb);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
		
		// Set table view layout and input the column
		tvWishlist.setPadding(new Insets(10));
		tvWishlist.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		hbButtons.setPadding(new Insets(10));
		hbButtons.setSpacing(8);
		hbButtons.getChildren().add(backButton);
		hbButtons.getChildren().add(removeWishlistButton);
		removeWishlistButton.setVisible(false);
		
		bp.setTop(hbTopbar);
		bp.setCenter(tvWishlist);
		bp.setBottom(hbButtons);
	}
	
	// Method View Wishlist
	private void ViewWishlist() {
		// Calling item controller for using ViewItem() function
		itemsWishlist = WishlistController.getInstance().ViewWishlist(null, user_id);
		
		// Refresh the table
		tvWishlist.getItems().clear();
		tvWishlist.getItems().addAll(itemsWishlist);
	}
	
	// Method remove wishlist
	public void RemoveWishlist(String item_id) {
		System.out.println(item_id);
		System.out.println(user_id);
		boolean result = WishlistController.getInstance().RemoveWishlist(item_id, user_id);
		if(result) {
			alert.setAlertType(AlertType.INFORMATION);
			alert.setContentText("Removed item from wishlist");
			alert.show();
			ViewWishlist();
		}else {
			alert.setAlertType(AlertType.ERROR);
			alert.setContentText("Remove item failed");
			alert.show();
		}
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
		
		tvWishlist.setOnMouseClicked(event -> {
			removeWishlistButton.setVisible(true);
		});
		
		// Set back button 
		backButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				// Change page into homepage 
				HomepageView homepage = new HomepageView(user_id, user_role);
				try {
					homepage.start(primaryStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		// Set wishlist button
		removeWishlistButton.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				Item selectedItem = tvWishlist.getSelectionModel().getSelectedItem();
				if(selectedItem == null) {
					alert.setAlertType(AlertType.ERROR);
					alert.setContentText("Please Select an Item");
					alert.show();
					return;
				}
				// Remove wishlist
				RemoveWishlist(selectedItem.getItem_id());
				removeWishlistButton.setVisible(false);
			}
		});
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
	        initialize(); 
	        layouting();   
	        ViewWishlist();
	        setAction(primaryStage);

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
