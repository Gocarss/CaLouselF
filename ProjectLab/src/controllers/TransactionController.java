package controllers;

import java.util.Vector;

import models.Transaction;

public class TransactionController {
	
	private static TransactionController tc = null;
	private Transaction transactionModel;
	
	private TransactionController() {
		transactionModel = new Transaction();
	}
	
	public static TransactionController getInstance() {
		if(tc == null) {
			tc = new TransactionController();
		}
		return tc;
	}
	
	public boolean PurchaseItems(String User_id, String Item_id){
		return transactionModel.PurchaseItems(User_id, Item_id);
	}
	public Vector<Transaction> ViewHistory(String user_id){
		return transactionModel.ViewHistory(user_id);
	}

	public boolean CreateTransaction(String Transaction_id) {
		return transactionModel.CreateTransaction(Transaction_id);
	}
}
