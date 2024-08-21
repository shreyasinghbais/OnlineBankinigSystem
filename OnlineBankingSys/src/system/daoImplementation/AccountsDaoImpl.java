package system.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import system.dao.AccountsDao;
import system.exceptions.AccountException; 
import system.model.Accounts; 
import system.utility.DAO;

public class AccountsDaoImpl implements AccountsDao{
	
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
	public boolean createAccount(Accounts a1) throws AccountException {
		String query = "INSERT INTO Accounts (account_id, customer_id, account_number, balance) VALUES (?, ?, ?, ?)";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
        	ppst.setInt(1, a1.getAccount_id());
            ppst.setInt(2, a1.getCustomer_id());
            ppst.setString(3, a1.getAccount_number());
            ppst.setDouble(4, a1.getBalance());
            int ans = ppst.executeUpdate();     
            if (ans != 0) {
				System.out.println("Account created succesfully ");
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
	public Accounts getAccountDetailsByAccountNo(String account_number) throws AccountException {
		ArrayList<Accounts> listOfAccount = new ArrayList<>() ;
		String query = "SELECT * FROM Accounts WHERE account_number = ?";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
            ppst.setString(1, account_number);
            rs = ppst.executeQuery();
            if (rs.next()) {
                listOfAccount.add( 
                	new Accounts(
                		rs.getInt("account_id"),
                		rs.getInt("customer_id"),
                		rs.getString("account_number"),
                		rs.getDouble("balance")
                   )
                );
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
        System.out.println(listOfAccount);
        return null;
	}
	@Override
	public List<Accounts> getAllAccountDetails() throws AccountException {
		List<Accounts> listOfAccounts = new ArrayList<>();
        String query = "SELECT * FROM Accounts";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
            rs = ppst.executeQuery(query);
            while (rs.next()) {
                listOfAccounts.add(new Accounts(
                    rs.getInt("account_id"),
                    rs.getInt("customer_id"),
                    rs.getString("account_number"),
                    rs.getDouble("balance")	                    
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
        listOfAccounts.forEach(System.out::println) ;
        return listOfAccounts;
	}
	@Override
	public boolean updateAccountDetails(Accounts a1) throws AccountException {
		String query = "UPDATE Accounts SET customer_id=?, account_number = ?, balance = ? WHERE account_id = ?";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
        	ppst.setInt(1, a1.getCustomer_id());
            ppst.setString(2, a1.getAccount_number());
            ppst.setDouble(3, a1.getBalance());
            ppst.setInt(4, a1.getAccount_id());
            int ans = ppst.executeUpdate();     
            if (ans != 0) {
				System.out.println("Account updated succesfully ");
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
	public boolean deleteAccount(int account_id) throws AccountException {
		String query = "DELETE FROM Accounts WHERE account_id=?";
		try {
			con = establishConnection();
			ppst = con.prepareStatement(query);
			ppst.setInt(1, account_id);
			int	ans = ppst.executeUpdate();
			if (ans != 0) {
				System.out.println("Account with account_id " + account_id + " deleted succesfully ");
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
		AccountsDaoImpl a = new AccountsDaoImpl();
		try {
			//a.createAccount(new Accounts(1, 3, "00001", 10000.00));
			//a.createAccount(new Accounts(3, 1, "00003", 8000.00));
			//a.updateAccountDetails(new Accounts(2, 3, "00002", 7000.00));
			//a.deleteAccount(2);
			//a.getAccountDetailsByAccountNo("00003");
			a.getAllAccountDetails();
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
