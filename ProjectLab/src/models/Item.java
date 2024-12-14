package models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import database.Connect;

public class Item {
	private String Item_id, Item_name, Item_size, Item_price, Item_category, item_status, item_wishlist, item_offer_status;

	public Item(String item_id, String item_name, String item_size, String item_price, String item_category, String item_status) {
		super();
		Item_id = item_id;
		Item_name = item_name;
		Item_size = item_size;
		Item_price = item_price;
		Item_category = item_category;
		this.item_status = item_status;
	}

	public Item() {}
	
	public String getItem_id() {
		return Item_id;
	}

	public void setItem_id(String item_id) {
		Item_id = item_id;
	}

	public String getItem_name() {
		return Item_name;
	}

	public void setItem_name(String item_name) {
		Item_name = item_name;
	}

	public String getItem_size() {
		return Item_size;
	}

	public void setItem_size(String item_size) {
		Item_size = item_size;
	}

	public String getItem_price() {
		return Item_price;
	}

	public void setItem_price(String item_price) {
		Item_price = item_price;
	}

	public String getItem_category() {
		return Item_category;
	}

	public void setItem_category(String item_category) {
		Item_category = item_category;
	}

	public String getItem_status() {
		return item_status;
	}

	public void setItem_status(String item_status) {
		this.item_status = item_status;
	}

	public String getItem_wishlist() {
		return item_wishlist;
	}

	public void setItem_wishlist(String item_wishlist) {
		this.item_wishlist = item_wishlist;
	}

	public String getItem_offer_status() {
		return item_offer_status;
	}

	public void setItem_offer_status(String item_offer_status) {
		this.item_offer_status = item_offer_status;
	}
	
	private String idGenerator(String type) {
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder id = new StringBuilder();
        
        // Item id start with IT and Offer id start with OF
        if(type.equals("item")) {
        	id.append("IT");
        }else if(type.equals("offer")) {
        	id.append("OF");
        }else if(type.equals("reason")) {
        	id.append("RE");
        }
        
        for (int i = 0; i < 7; i++) {
            int index = (int) (Math.random() * characters.length());
            id.append(characters.charAt(index));
        }
        return id.toString();
	}
	
	public boolean UploadItem(String Item_name, String Item_category, String Item_size, String Item_price, String Seller_id) {
		String query = "INSERT INTO `item`(`item_id`, `item_name`, `item_category`, `item_size`, `item_price`, `item_status`, `seller_id`) VALUES (?,?,?,?,?,?,?)";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, idGenerator("item"));
			ps.setString(2, Item_name);
			ps.setString(3, Item_category);
			ps.setString(4, Item_size);
			ps.setDouble(5, Double.parseDouble(Item_price));
			ps.setString(6, "pending");
			ps.setString(7, Seller_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	public boolean EditItem(String Item_id, String Item_name, String Item_category, String Item_size, String Item_price) {
		String query = "UPDATE `item` SET `item_name`=?,`item_category`=?,`item_size`=?,`item_price`=? WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_name);
			ps.setString(2, Item_category);
			ps.setString(3, Item_size);
			ps.setString(4, Item_price);
			ps.setString(5, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean DeleteItem(String Item_id) {
		String query = "DELETE FROM `item` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public Item BrowseItem(String Item_name){
		Item item = new Item();
		String query = "SELECT * FROM `item` WHERE `item_name`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_name);
			ResultSet rs = ps.executeQuery();

			if(rs.next() && rs.getString("item_status").equals("approved")){
				String item_id = rs.getString("item_id");
				String item_Category = rs.getString("item_category");
				String item_Size = rs.getString("item_size");
				Double price = rs.getDouble("item_price");
				String item_Price = "$ " + price.toString();
				item = new Item(item_id, Item_name, item_Size, item_Price, item_Category, null);
			}

		} catch(SQLException e){
			e.printStackTrace();
		}
		return item;
	}
	
	public Vector<Item> ViewItem(){
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM `item`";
		ResultSet rs = Connect.getConnection().execQuery(query);
		try {
			while(rs.next()) {
				if(rs.getString("item_status").equals("approved")) {
					String Item_Id = rs.getString("item_id");
					String Item_Name = rs.getString("item_name");
					String Item_Category = rs.getString("item_category");
					String Item_Size = rs.getString("item_size");
					Double price = rs.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					items.add(new Item(Item_Id, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
	public String CheckItemValidation(String Item_name, String Item_category, String Item_size, String Item_price) {
		// Check if empty
		if (Item_name.isEmpty() || Item_category.isEmpty() || Item_size.isEmpty() || Item_price.isEmpty()) {
			return "All fields must be filled";
		}
		
		// Check item name
		if(Item_name.length() < 3) {
			return "Item name must at least be 3 character long";
		}
		
		// Check item category
		if(Item_category.length() < 3) {
			return "Item category must at least be 3 character long";
		}
		
		// Check item price
		if(Item_price.equals("0")){
        	return "Item Price cannot be 0";
        }else {
    		try {
    			Double.parseDouble(Item_price);
			} catch (NumberFormatException e) {
				return "Item Price Must be in number";
			}
    	}
		return null;
	}
	
	public Vector<Item> ViewRequestedItem(String Item_id, String item_status){
		Vector<Item> reviewItems = new Vector<>();
		String query = "SELECT * FROM `item`";
		ResultSet rs = Connect.getConnection().execQuery(query);
		try {
			while(rs.next()) {
				if(rs.getString("item_status").equals("pending")) {
					String Item_Name = rs.getString("item_name");
					String Item_Category = rs.getString("item_category");
					String Item_Size = rs.getString("item_size");
					Double price = rs.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					reviewItems.add(new Item(null, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviewItems;
	}
	
	public boolean OfferPrice(String Item_id, String item_price, String Buyer_id) {
		String query = "SELECT * FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getDouble("offer_price") > Double.parseDouble(item_price)) {
					return false;
				}
				String query1 = "UPDATE `offer` SET `offer_price`=? WHERE `item_id`=?";
				PreparedStatement ps1 = Connect.getConnection().prepareStatement(query1);
				ps1.setString(1, item_price);
				ps1.setString(2, Item_id);
				return ps1.executeUpdate() == 1;
				
			}else {
				String query2 = "INSERT INTO `offer`(`offer_id`, `item_id`, `buyer_id`, `offer_price`, `offer_status`) VALUES (?,?,?,?,?)";
				PreparedStatement ps2 = Connect.getConnection().prepareStatement(query2);
				ps2.setString(1, idGenerator("offer"));
				ps2.setString(2, Item_id);
				ps2.setString(3, Buyer_id);
				ps2.setDouble(4, Double.parseDouble(item_price));
				ps2.setString(5, "pending");
				return ps2.executeUpdate() == 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public boolean RemoveOffer(String Item_id) {
		String query = "DELETE FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			if(ps.executeUpdate() == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String getCurrentOfferBuyer(String Item_id) {
		String query = "SELECT * FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("buyer_id");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Double getCurrentOfferPrice(String Item_id) {
		String query = "SELECT * FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				
				return rs.getDouble("offer_price");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return (double) 0;
	}
	
	public String CheckOfferValidation(String Item_id, String Offer_price) {
		Double price = null;
		String query = "SELECT * FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		
		// Check offer price if is empty
		if (Offer_price.isEmpty()) {
			return "Offer field must be filled";
		}
		
		// Check item price
		if(Offer_price.equals("0")){
        	return "Offer price cannot be 0";
        }else {
    		try {
    			price = Double.parseDouble(Offer_price);
			} catch (NumberFormatException e) {
				return "Must be in number";
			}
    		
    		try {
				ps.setString(1, Item_id);
				ResultSet rs = ps.executeQuery();
				
				if(rs.next()) {
					if(rs.getDouble("offer_price") < price) {
						return Offer_price;
					}else {
						return "Offer price must be higher than the current offer";
					}
				}else {
					return Offer_price;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
		return null;
	}
	
	public boolean AcceptOffer(String Item_id) {
		String query = "UPDATE `offer` SET `offer_status`=? WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, "accept");
			ps.setString(2, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean DeclineOffer(String Item_id){
		String query = "DELETE FROM `offer` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean ApproveItem(String Item_id) {
		String query = "UPDATE `item` SET `item_status`=? WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, "approved");
			ps.setString(2, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean DeclineItem(String Item_id) {
		String query = "DELETE FROM `item` WHERE `item_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, Item_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean GiveReason(String reason_text, String item_id, String user_id) {
		String query = "INSERT INTO `reason`(`reason_id`, `reason_text`, `item_id`, `user_id`) VALUES (?,?,?,?)";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, idGenerator("reason"));
			ps.setString(2, reason_text);
			ps.setString(3, item_id);
			ps.setString(4, user_id);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}
	
	public Vector<Item> ViewAcceptedItem(String Item_id, String user_id){
		Vector<Item> acceptedItems = new Vector<>();
		String query = "SELECT * FROM `item` WHERE `seller_id`=?";
		PreparedStatement ps = Connect.getConnection().prepareStatement(query);
		try {
			ps.setString(1, user_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				if(rs.getString("item_status").equals("approved")) {
					String item_id = rs.getString("item_id");
					String Item_Name = rs.getString("item_name");
					String Item_Category = rs.getString("item_category");
					String Item_Size = rs.getString("item_size");
					Double price = rs.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					acceptedItems.add(new Item(item_id, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return acceptedItems;
	}
	
	public Vector<Item> ViewOfferItem(String User_id){
		Vector<Item> offeredItems = new Vector<>();
		String query = "SELECT * FROM `offer`";
		ResultSet rs = Connect.getConnection().execQuery(query);
		try {
			while(rs.next()) {
				if(rs.getString("offer_status").equals("accept")) {
					continue;
				}
				String query1 = "SELECT * FROM `item` WHERE `item_id`=? AND `seller_id`=?";
				PreparedStatement ps = Connect.getConnection().prepareStatement(query1);
				ps.setString(1, rs.getString("item_id"));
				ps.setString(2, User_id);
				ResultSet rs1 = ps.executeQuery();
				if(rs1.next()) {
					String Item_id = rs1.getString("item_id");
					String Item_Name = rs1.getString("item_name");
					String Item_Category = rs1.getString("item_category");
					String Item_Size = rs1.getString("item_size");
					Double price = rs1.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					offeredItems.add(new Item(Item_id, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return offeredItems;
	}
	
	public Vector<Item> ViewItemAdmin(){
		Vector<Item> items = new Vector<>();
		String query = "SELECT * FROM `item`";
		ResultSet rs = Connect.getConnection().execQuery(query);
		try {
			while(rs.next()) {
				if(rs.getString("item_status").equals("pending")) {
					String Item_Id = rs.getString("item_id");
					String Item_Name = rs.getString("item_name");
					String Item_Category = rs.getString("item_category");
					String Item_Size = rs.getString("item_size");
					Double price = rs.getDouble("item_price");
					String Item_Price = "$ " + price.toString();
					items.add(new Item(Item_Id, Item_Name, Item_Size, Item_Price, Item_Category, null));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	
}
