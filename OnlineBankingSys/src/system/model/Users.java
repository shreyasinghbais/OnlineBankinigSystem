package system.model;

public class Users {
	
	//Private variables
	private int  user_id;
	private String username;
	private String upi_id;
	private String password;
	
	//Constructors
	public Users(int user_id, String username, String upi_id, String password) {
		super();
		this.user_id = user_id;
		this.username = username;
		this.upi_id = upi_id;
		this.password = password;
	}
	public Users() {};
	
	//Getters & Setters	
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUpi_id() {
		return upi_id;
	}
	public void setUpi_id(String upi_id) {
		this.upi_id = upi_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	//ToString method
	@Override
	public String toString() {
		return "Users [user_id=" + user_id + ", username=" + username + ", upi_id=" + upi_id + ", password=" + password + "]";
	}
	
	
}
