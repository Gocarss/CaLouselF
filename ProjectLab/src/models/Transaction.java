package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;

public class Transaction {
	
	private String User_id, Item_id, transaction_id;
	private Item itemDetails;

	public Transaction(String user_id, String Item_id, String Transaction_id, Item itemDetails) {
		super();
		this.User_id = user_id;
		this.Item_id = Item_id;
		this.transaction_id = Transaction_id;
		this.itemDetails = itemDetails;
	}
	
	public Transaction() {}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Item getItemDetails() {
		return itemDetails;
	}

	public void setItemDetails(Item itemDetails) {
		this.itemDetails = itemDetails;
	}

	private String transactionIdGenerator() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        
        // Transaction ID always start with an TR
        id.append("TR");
        
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
	}
	
	
	public boolean PurchaseItems(String User_id, String Item_id){
		String query = "UPDATE `item` SET `item_status`=? WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, "sold");
			ps.setString(2, Item_id);
			
			this.User_id = User_id;
			this.Item_id = Item_id;
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Vector<Transaction> ViewHistory(String user_id){
		Vector<Transaction> history = new Vector<>();
		String query = "SELECT * FROM `transaction` WHERE `user_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				history.add(new Transaction(rs.getString("user_id"), rs.getString("item_id"), rs.getString("transaction_id"), ItemDetails(rs.getString("item_id")))) ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return history;
	}
	
	
	public Item ItemDetails(String Item_id){
		Item itemDetails = new Item();
		String query = "SELECT * FROM `item` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String Item_Name = rs.getString("item_name");
				String Item_Category = rs.getString("item_category");
				String Item_Size = rs.getString("item_size");
				Double price = rs.getDouble("item_price");
				String Item_Price = price.toString();
				itemDetails = new Item(null, Item_Name, Item_Size, Item_Price, Item_Category, null);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return itemDetails;
	}
	
	public boolean CreateTransaction(String Transaction_id) {
		String query1 = "INSERT INTO `transaction`(`transaction_id`, `user_id`, `item_id`) VALUES (?,?,?)";
		PreparedStatement ps1 = Connect.getConnection().prepareStatement(query1);
		try {
			ps1.setString(1, transactionIdGenerator());
			ps1.setString(2, User_id);
			ps1.setString(3, Item_id);
			return ps1.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
