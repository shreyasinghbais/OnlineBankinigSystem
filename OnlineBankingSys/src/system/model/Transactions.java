package system.model;

import java.sql.Timestamp;

public class Transactions {
	
	//Private variables
	 private int transaction_id;
	 private int account_id;
	 private double amount;
	 private String account_number; 
	 private String transaction_type;
	 private Timestamp date ;
	 
//	 public static void main(String[] args) {
//		date = new Timestamp(System.currentTimeMillis());
//		System.out.println(date);
//	}
	 
	//Constructors
	 public Transactions(int transaction_id, int account_id, double amount, String transaction_type, Timestamp date) {
		super();
		this.transaction_id = transaction_id;
		this.account_id = account_id;
		this.amount = amount;
		this.transaction_type = transaction_type;
		this.date = date;
	 }
	 public Transactions(int transaction_id, int account_id, double amount, String account_number, String transaction_type, Timestamp date) {
			super();
			this.transaction_id = transaction_id;
			this.account_id = account_id;
			this.amount = amount;
			this.account_number = account_number;
			this.transaction_type = transaction_type;
			this.date = date;
	 }
	 public Transactions() {}
	
	 //Getters & Setters
	 public int getTransaction_id() {
			return transaction_id;
		}
		public void setTransaction_id(int transaction_id) {
			this.transaction_id = transaction_id;
		}
		public int getAccount_id() {
			return account_id;
		}
		public void setAccount_id(int account_id) {
			this.account_id = account_id;
		}
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
		}
		public String getAccount_number() {
			return account_number;
		}
		public void setAccount_number(String account_number) {
			this.account_number = account_number;
		}
		public String getTransaction_type() {
			return transaction_type;
		}
		public void setTransaction_type(String transaction_type) {
			this.transaction_type = transaction_type;
		}
		public Timestamp getDate() {
			return date;
		}
		public void setDate(Timestamp date) {
			this.date = date;
		}
		
		//ToString method
		@Override
		public String toString() {
			return "Transactions [transaction_id=" + transaction_id + ", account_id=" + account_id + ", amount="
					+ amount + ", account_number=" + account_number + ", transaction_type=" + transaction_type
					+ ", date=" + date + "]";
		} 
		 
}
