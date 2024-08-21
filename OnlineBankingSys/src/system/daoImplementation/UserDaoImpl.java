package system.daoImplementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import system.dao.UserDao;
import system.exceptions.UserException;
import system.model.Users;
import system.utility.DAO;

public class UserDaoImpl implements UserDao{
	
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
	public boolean addUser(Users u1) throws UserException {
		String query = "INSERT INTO Users (user_id, username, upi_id, password) VALUES (?, ?, ?, ?)";
        try{
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
            ppst.setInt(1, u1.getUser_id());
            ppst.setString(2, u1.getUsername());
            ppst.setString(3, u1.getUpi_id());
            ppst.setString(4,u1.getPassword());
            int	ans = ppst.executeUpdate() ;
	        if (ans != 0) {
				System.out.println("User added succesfully ");
			}else {
				System.out.println("Some thing went wrong");
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
	 
	@Override
	public List<Users> getAllUserDetails() throws UserException {
		 List<Users> users = new ArrayList<>();
	        String query = "SELECT * FROM Users";
	        try {
	        	con = establishConnection();
	        	ppst = con.prepareStatement(query);
	            rs = ppst.executeQuery(query);
	            while (rs.next()) {
	                users.add(new Users(
	                    rs.getInt("user_id"),
	                    rs.getString("username"),
	                    rs.getString("upi_id"),
	                    rs.getString("password")	                    
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
	        System.out.println(users);
	        return users;
	}
	@Override
	public boolean updateUser(Users u1) throws UserException {
		String query = "UPDATE Users SET username = ?, password = ?, upi_id = ? WHERE user_id = ?";
        try {
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
            ppst.setString(1, u1.getUsername());
            ppst.setString(2, u1.getPassword());
            ppst.setString(3, u1.getUpi_id());
            ppst.setInt(4, u1.getUser_id());
            int	ans = ppst.executeUpdate() ;
	        if (ans != 0) {
				System.out.println("User updated succesfully ");
			}else {
				System.out.println("Some thing went wrong");
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
	@Override
	public boolean deleteUser(int user_id) throws UserException {
		String query = "DELETE FROM Users WHERE user_id=?";
		try {
			con = establishConnection();
			ppst = con.prepareStatement(query);
			ppst.setInt(1, user_id);
			int	ans = ppst.executeUpdate();
			if (ans != 0) {
				System.out.println("User details deleted succesfully ");
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

	@Override
	public Users getUserDetailsByUsernameAndPassword(String username, String password) throws UserException {
		//ArrayList<Users> listOfUsers = new ArrayList<>() ;
		Users s = null;
		String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try{
        	con = establishConnection();
        	ppst = con.prepareStatement(query);
        	ppst.setString(1, username);
        	ppst.setString(2, password);
            rs = ppst.executeQuery();
            if (rs.next()) {
                s = new Users(
           			rs.getInt("user_id"),
                      rs.getString("username"),
                      rs.getString("upi_id"),
                      rs.getString("password")
                 ); 
            }
            
            
//            new Users(
//           			rs.getInt("user_id"),
//                    rs.getString("username"),
//                    rs.getString("upi_id"),
//                    rs.getString("password")
////                )
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
		System.out.println(s);
		return s;
	}
 
	
//	public static void main(String[] args) {
//		UserDaoImpl u = new UserDaoImpl();
//		try {
//			//u.updateUser(new Users(1, "Peter", "xyz@sbi", "abcd" ) );
//			//u.addUser(new Users(2, "Glory", "qwery@rbi", "xyz"));
//			//u.addUser(new Users(1, "Merry", "zxcv@hdfc", "cfdh"));
//			u.addUser(new Users(3, "Joe", "ghjk@ybl", "lby"));
//			//u.deleteUser(3);
//			//u.getUserDetailsByUsername("glory");
//			//u.getAllUserDetails();
//		} catch (UserException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
