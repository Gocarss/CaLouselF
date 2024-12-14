package controllers;

import java.util.Vector;

import models.Item;

public class ItemController {
	private static ItemController ic = null;
	private Item itemModel;
	
	private ItemController() {
		itemModel = new Item();
	}
	
	public static ItemController getInstance() {
		if(ic == null) {
			ic = new ItemController();
		}
		return ic;
	}

	public boolean UploadItem(String Item_name, String Item_category, String Item_size, String Item_price, String Seller_id) {
		return itemModel.UploadItem(Item_name, Item_category, Item_size, Item_price, Seller_id);
	}
	
	public boolean EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		return itemModel.EditItem(Item_id, Item_name, Item_category, Item_size, Item_price);
	}
	
	public boolean DeleteItem(String Item_id) {
		return itemModel.DeleteItem(Item_id);
	}
	
	public Item BrowseItem(String Item_name){
		return itemModel.BrowseItem(Item_name);
	}
	
	public Vector<Item> ViewItem(){
		return itemModel.ViewItem();
	}
	
	public String CheckItemValidation(String Item_name, String Item_category, String Item_size, String Item_price) {
		return itemModel.CheckItemValidation(Item_name, Item_category, Item_size, Item_price);
	}

	public Vector<Item> ViewRequestedItem(String Item_id, String item_status){
		return itemModel.ViewRequestedItem(Item_id, item_status);
	}
	
	public boolean RemoveOffer(String Item_id) {
		return itemModel.RemoveOffer(Item_id);
	}
	
	public boolean OfferPrice(String Item_id, String item_price, String Buyer_id) {
		return itemModel.OfferPrice(Item_id, item_price, Buyer_id);
	}
	
	public String getCurrentOfferBuyer(String Item_id) {
		return itemModel.getCurrentOfferBuyer(Item_id);
	}
	
	public Double getCurrentOfferPrice(String Item_id) {
		return itemModel.getCurrentOfferPrice(Item_id);
	}
	
	public String CheckOfferValidation(String Item_id, String Offer_price) {
		return itemModel.CheckOfferValidation(Item_id, Offer_price);
	}
	
	public boolean AcceptOffer(String Item_id) {
		return itemModel.AcceptOffer(Item_id);
	}
	
	public boolean GiveReason(String reason_text, String item_id, String user_id) {
		return itemModel.GiveReason(reason_text, item_id, user_id);
	}
	
	public boolean DeclineOffer(String Item_id){
		return itemModel.DeclineOffer(Item_id);
	}
	
	public boolean ApproveItem(String Item_id) {
		return itemModel.ApproveItem(Item_id);
	}
	
	public boolean DeclineItem(String Item_id) {
		return itemModel.DeclineItem(Item_id);
	}
	
	public Vector<Item> ViewAcceptedItem(String Item_id, String Buyer_id){
		return itemModel.ViewAcceptedItem(Item_id, Buyer_id);
	}
	
	public Vector<Item> ViewOfferItem(String User_id){
		return itemModel.ViewOfferItem(User_id);
	}
	
	public Vector<Item> ViewItemAdmin(){
		return itemModel.ViewItemAdmin();
	}

	
	
	
}
