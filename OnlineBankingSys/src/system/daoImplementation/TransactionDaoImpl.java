package system.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.ArrayList;
import java.util.List;

import system.dao.TransactionDao;
import system.exceptions.TransactionException;
import system.model.Transactions;
import system.utility.DAO;

public class TransactionDaoImpl implements TransactionDao{
	
	Connection con = null;
	PreparedStatement ppst = null;
	ResultSet rs = null; 
	private Connection establishConnection() throws SQLException{
		Connection c = null;
		try{			
			c=DAO.getConnectionFactory().getConnection() ; 
		}catch (SQLException e) { 
			e.printStackTrace();
		} 
		return c;
	}

	@Override
	public boolean doTransaction(Transactions t1) throws TransactionException {
		String query = "INSERT INTO Transactions (transaction_id, account_id, amount, account_number, transaction_type, date) VALUES (?, ?, ?, ?, ?, ?)";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
        	ppst.setInt(1, t1.getTransaction_id());
            ppst.setInt(2, t1.getAccount_id());
            ppst.setDouble(3, t1.getAmount());
            ppst.setString(4, t1.getAccount_number());
            ppst.setString(5, t1.getTransaction_type());
            ppst.setTimestamp(6, t1.getDate());
            int ans = ppst.executeUpdate();     
            if (ans != 0) {
				System.out.println("Transaction done succesfully ");
			}else {
				System.out.println("Some thing went wrong");
			}
            return ans > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			try {
				if (ppst !=  null) {
					ppst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) { 
				e.printStackTrace();
			}
        }
        return false;
	}

	@Override
	public List<Transactions> viewTransactionsHistory(int accountId) throws TransactionException {
		List<Transactions> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE account_id = ?";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
            ppst.setInt(1, accountId);
            rs = ppst.executeQuery();
            while (rs.next()) {
                transactions.add(new Transactions(
                    rs.getInt("transaction_id"),
                    rs.getInt("account_id"),
                    rs.getDouble("amount"),
                    rs.getString("transaction_type"),
                    rs.getTimestamp("date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
			try {
				if (ppst !=  null) {
					ppst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) { 
				e.printStackTrace();
			}
        }
        transactions.forEach(System.out::println) ;
        return transactions;
	}
	@Override
	public boolean removeTransactionHistory(int accountId) throws TransactionException {
		String query = "DELETE FROM Accounts WHERE account_id=?";
		try {
			con = establishConnection();
			ppst = con.prepareStatement(query);
			ppst.setInt(1, accountId);
			int	ans = ppst.executeUpdate();
			if (ans != 0) {
				System.out.println("Transaction History with account_id " + accountId + " deleted succesfully ");
			}else {
				System.out.println("some thing went wrong");
			} 
			return ans>0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ppst !=  null) {
					ppst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) { 
				e.printStackTrace();
			}
	    }
		return false;
	}
	
	public static void main(String[] args) {
		TransactionDaoImpl t = new TransactionDaoImpl();
		try {
			//t.doTransaction(new Transactions(1, 3, 3000.00, "Deposit", new Timestamp(System.currentTimeMillis())));
			//t.doTransaction(new Transactions(2, 1, 1000.00, "Withdrawal", new Timestamp(System.currentTimeMillis())));
			//t.doTransaction(new Transactions(3, 3, 2000.00, "Transfer", new Timestamp(System.currentTimeMillis())));
			t.viewTransactionsHistory(1);
			//t.removeTransactionHistory(3);
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
