package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;

public class Wishlist {
	private String Wishlist_id, Item_id, User_id;

	public Wishlist(String wishlist_id, String item_id, String user_id) {
		super();
		Wishlist_id = wishlist_id;
		Item_id = item_id;
		User_id = user_id;
	}

	public Wishlist() {
	}

	public String getWishlist_id() {
		return Wishlist_id;
	}

	public void setWishlist_id(String wishlist_id) {
		Wishlist_id = wishlist_id;
	}

	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getUser_id() {
		return User_id;
	}

	public void setUser_id(String user_id) {
		User_id = user_id;
	}
	
	private String wishlistIdGenerator() {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        
        // Wishlist ID always start with an WL
        id.append("WL");
        
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
	}
	
	public Vector<Item> ViewWishlist(String Wishlist_id, String User_id){
		Vector<Item> wishlist = new Vector<>();
		String query = "SELECT * FROM `wishlist` WHERE `user_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, User_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String query1 = "SELECT * FROM `item` WHERE `item_id`=?";
				PreparedStatement ps1 = Connect.getConnection().prepareStatement(query1);
				ps1.setString(1, rs.getString("item_id"));
				ResultSet rs1 = ps1.executeQuery();
				if(rs1.next()) {
					String Item_id = rs1.getString("item_id");
					String Item_Name = rs1.getString("item_name");
					String Item_Category = rs1.getString("item_category");
					String Item_Size = rs1.getString("item_size");
					Double price = rs1.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					wishlist.add(new Item(Item_id, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return wishlist;
	}
	
	public boolean CheckDuplicate(String item_id, String User_id) {
		String query ="SELECT * FROM `wishlist` WHERE `item_id`=? AND `user_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, item_id);
			ps.setString(2, User_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean AddWishlist(String item_id, String User_id){
		String query ="INSERT INTO `wishlist`(`Wishlist_id`, `item_id`, `user_id`) VALUES (?,?,?)";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, wishlistIdGenerator());
			ps.setString(2, item_id);
			ps.setString(3, User_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void CheckWishlists(String Item_id) {
		String query = "SELECT * FROM `wishlist` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				RemoveWishlist(rs.getString("wishlist_id"), null);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean RemoveWishlist(String Wishlist_id, String user_id) {
		String queryAll = "DELETE FROM `wishlist` WHERE `wishlist_id`=?";
		String queryUser = "DELETE FROM `wishlist` WHERE `item_id`=? AND `user_id`=?";
		PreparedStatement ps;
		
		if(user_id == null) {
			ps = Connect.getConnection().prepareStatement(queryAll);
			try {
				ps.setString(1, Wishlist_id);
				return ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else {
			ps = Connect.getConnection().prepareStatement(queryUser);
			try {
				ps.setString(1, Wishlist_id);
				ps.setString(2, user_id);
				return ps.executeUpdate()==1;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return false;
	}

	
	
	
}
