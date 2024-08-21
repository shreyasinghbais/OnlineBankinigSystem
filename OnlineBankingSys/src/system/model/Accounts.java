package system.model;

public class Accounts {
	
	//Private variables
	private int account_id;
	private int customer_id;
	private String account_number;
	private double balance;
	
	//Constructors
	public Accounts(int account_id, int customer_id, String account_number, double balance) {
		super();
		this.account_id = account_id;
		this.customer_id = customer_id;
		this.account_number = account_number;
		this.balance = balance;
	}
	public Accounts() {}
	
	//Getters & Setters
	public int getAccount_id() {
		return account_id;
	}
	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	//ToString method
	@Override
	public String toString() {
		return "Accounts [account_id=" + account_id + ", customer_id=" + customer_id + ", account_number="
				+ account_number + ", balance=" + balance + "]";
	};
	
	
	
	
}
