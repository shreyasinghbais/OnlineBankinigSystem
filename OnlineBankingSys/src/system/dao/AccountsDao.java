package system.dao;

import java.util.List;

import system.exceptions.AccountException;
import system.model.Accounts;

public interface AccountsDao {
	//Create
	public boolean createAccount(Accounts a1) throws AccountException;
	//Read
	public Accounts getAccountDetailsByAccountId(int account_id) throws AccountException;
	public List<Accounts> getAllAccountDetails() throws AccountException;
	//Update
	public boolean updateAccountDetails(Accounts u1) throws AccountException;
	//Delete
	public boolean deleteAccount(int account_id) throws AccountException;
}
