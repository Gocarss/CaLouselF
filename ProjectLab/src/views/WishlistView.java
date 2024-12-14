package views;

import java.util.Vector;
import controllers.WishlistController;
import javafx.application.Application;
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
import models.Item;

public class WishlistView extends Application{
	Scene sc;
	BorderPane bp;
	TableView<Item> tvWishlist;
	
	String user_id, user_role;
	
	Alert alert;
	
	Vector<Item> itemsWishlist;
	
	TableColumn<Item, String> nameColumn;
	TableColumn<Item, String> categoryColumn;
	TableColumn<Item, String> sizeColumn;
	TableColumn<Item, String> priceColumn;
	
	GridPane gpForm;
	HBox hbButtons;
	
	Label itemViewLabel;
	
	Button backButton, removeWishlistButton;
	
	
	public WishlistView(String user_id, String user_role) {
		this.user_id = user_id;
		this.user_role = user_role;
	}
	
	private void initialize() {
		itemsWishlist = new Vector<>();
		
		bp = new BorderPane();
		sc = new Scene(bp);
		tvWishlist = new TableView<>();
		
		nameColumn = new TableColumn<Item, String>("Name");
		categoryColumn = new TableColumn<Item, String>("Category");
		sizeColumn = new TableColumn<Item, String>("Size");
		priceColumn = new TableColumn<Item, String>("Price");
		
		gpForm = new GridPane();
		hbButtons = new HBox();
		
		itemViewLabel = new Label("Wishlist View");
		
		alert = new Alert(AlertType.NONE);

		backButton = new Button("Back");
		removeWishlistButton = new Button("Remove Item");
		
	}
	
	private void layouting() {
		bp.setTop(itemViewLabel);
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("item_name"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("item_category"));
		sizeColumn.setCellValueFactory(new PropertyValueFactory<>("item_size"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("item_price"));
			
		tvWishlist.getColumns().addAll(nameColumn, categoryColumn, sizeColumn, priceColumn);
		
		hbButtons.getChildren().add(backButton);
		hbButtons.getChildren().add(removeWishlistButton);

		bp.setCenter(tvWishlist);
		bp.setBottom(hbButtons);
	}
	
	private void ViewWishlist() {
		// panggil controller untuk validasi select data
		itemsWishlist = WishlistController.getInstance().ViewWishlist(null, user_id);
		// refresh tampilan tableviewnya
		tvWishlist.getItems().clear();
		tvWishlist.getItems().addAll(itemsWishlist);
	}
	
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
				RemoveWishlist(selectedItem.getItem_id());
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
