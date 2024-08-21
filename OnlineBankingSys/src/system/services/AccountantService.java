package system.services;

import java.sql.Timestamp;

import system.dao.AccountsDao;
import system.dao.TransactionDao;
import system.exceptions.AccountException;
import system.exceptions.TransactionException;
import system.model.Accounts;
import system.model.Transactions;

public class AccountantService {
	private AccountsDao accountDao;
	private TransactionDao transactionDao;
	
	public AccountantService(AccountsDao accountDao, TransactionDao transactionDao) { 
		this.accountDao = accountDao;
		this.transactionDao = transactionDao;
	}
	
	//Method to create a new account
	public boolean createAccount(int customerId, String accountNumber, double initialBalance) throws AccountException {
		Accounts account = null;
		account = new Accounts(0, customerId, accountNumber, initialBalance);
		return accountDao.createAccount(account);
    }
	
	// Method to deposit money into an account
    public boolean deposit(int account_number, double amount) throws AccountException, TransactionException{
    	Accounts account = accountDao.getAccountDetailsByAccountNo(String.valueOf(account_number));
        if (account != null) {
            double newBalance = account.getBalance() + amount;
            account.setBalance(newBalance);
            boolean accountUpdated = accountDao.updateAccountDetails(account);

            if (accountUpdated) {
                Transactions transaction = new Transactions(0, account_number, amount, "Deposit", new Timestamp(System.currentTimeMillis()));
                return transactionDao.doTransaction(transaction);
            }
        } 
        return false;
    }
    
    // Method to withdraw money from an account
    public boolean withdraw(int account_number, double amount) {
        try {
        	Accounts account = accountDao.getAccountDetailsByAccountNo(String.valueOf(account_number));
            if (account != null && account.getBalance() >= amount) {
                double newBalance = account.getBalance() - amount;
                account.setBalance(newBalance);
                boolean accountUpdated = accountDao.updateAccountDetails(account);

                if (accountUpdated) {
                    Transactions transaction = new Transactions(0, account_number, -amount, "Withdrawal", new Timestamp(System.currentTimeMillis()));
                    return transactionDao.doTransaction(transaction);
                }
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }
    
    // Method to get account details by account number
    public Accounts getAccountByAccountNumber(String accountNumber) throws AccountException {
        return accountDao.getAccountDetailsByAccountNo(accountNumber);
    }
}
