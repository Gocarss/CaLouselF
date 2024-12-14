package controllers;

import java.util.Vector;

import models.Item;
import models.Wishlist;

public class WishlistController {
	private static WishlistController wc = null;
	private Wishlist wishlistModel;
	
	private WishlistController() {
		wishlistModel = new Wishlist();
	}
	
	public static WishlistController getInstance() {
		if(wc == null) {
			wc = new WishlistController();
		}
		return wc;
	}
	
	public Vector<Item> ViewWishlist(String Wishlist_id, String User_id){
		return wishlistModel.ViewWishlist(Wishlist_id, User_id);
	}
	
	public boolean CheckDuplicate(String item_id, String User_id) {
		return wishlistModel.CheckDuplicate(item_id, User_id);
	}
	
	public boolean AddWishlist(String item_id, String User_id){
		return wishlistModel.AddWishlist(item_id, User_id);
	}
	
	public void CheckWishlists(String Item_id) {
		wishlistModel.CheckWishlists(Item_id);
	}
	
	public boolean RemoveWishlist(String Wishlist_id, String User_id) {
		return wishlistModel.RemoveWishlist(Wishlist_id, User_id);
	}

}
