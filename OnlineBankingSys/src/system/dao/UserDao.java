package system.dao;

import java.util.List; 
import system.exceptions.UserException;
import system.model.Users;

public interface UserDao {
	//Create
	public boolean addUser(Users u1) throws UserException;
	//Read
	//public Users getUserDetailsByUsername(String username) throws UserException;
	public Users getUserDetailsByUsernameAndPassword(String username, String password) throws UserException;
	public List<Users> getAllUserDetails() throws UserException;
	//Update
	public boolean updateUser(Users u1) throws UserException;
	//Delete
	public boolean deleteUser(int user_id) throws UserException;
	//public Users getUserByUsernameAndPassword(String username, String password);
	
	
}
