package system.dao;

import java.util.List;

import system.exceptions.TransactionException;
import system.model.Transactions;

public interface TransactionDao {
	public boolean doTransaction(Transactions t1) throws TransactionException;
	public List<Transactions> viewTransactionsHistory(int accountId) throws TransactionException;
	public boolean removeTransactionHistory(int accountId) throws TransactionException;
}
