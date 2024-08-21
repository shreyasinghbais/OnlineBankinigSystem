package system.services;

import java.sql.Timestamp;
import java.util.List;

import system.dao.AccountsDao;
import system.dao.TransactionDao;
import system.exceptions.TransactionException;
import system.model.Accounts;
import system.model.Transactions;

public class UserService {
	private AccountsDao accountDAO;
    private TransactionDao transactionDAO;
    
	public UserService(AccountsDao accountDAO, TransactionDao transactionDAO) {
		super();
		this.accountDAO = accountDAO;
		this.transactionDAO = transactionDAO;
	}
    
	// Method to transfer money between accounts
    public boolean transfer(int accountId, String to_account_number, double amount) {
        try {
        	Accounts fromAccount = accountDAO.getAccountDetailsByAccountNo(String.valueOf(accountId));
            Accounts toAccount =accountDAO.getAccountDetailsByAccountNo(String.valueOf(to_account_number));

            if (fromAccount != null && toAccount != null && fromAccount.getBalance() >= amount) {
                // Withdraw from the source account
                fromAccount.setBalance(fromAccount.getBalance() - amount);
                accountDAO.updateAccountDetails(fromAccount);
                transactionDAO.doTransaction(new Transactions(0, accountId, -amount, "Transfer", new Timestamp(System.currentTimeMillis())));

                // Deposit into the destination account
                toAccount.setBalance(toAccount.getBalance() + amount);
                accountDAO.updateAccountDetails(toAccount);
                transactionDAO.doTransaction(new Transactions(0, accountId, amount, "Transfer", new Timestamp(System.currentTimeMillis())));

                return true;
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return false;
    }
    
    // Method to view transaction history for an account
    public List<Transactions> getTransactionHistory(int accountId) throws TransactionException {
        return transactionDAO.viewTransactionsHistory(accountId);
    }
}
