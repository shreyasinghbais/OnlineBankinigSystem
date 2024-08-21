package system.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DAO {
	
	//Singleton class
		String driverClassName = "com.mysql.cj.jdbc.Driver";
		public  String url = "jdbc:mysql://localhost:3306/onlinebank";
		public  String username = "root";
		public  String pw = "1stdba@shreya";
		
		private DAO() {
			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

		}
		
		private static DAO connectionFactory = null;
		
		public static DAO getConnectionFactory() {
			if(connectionFactory == null) {
				connectionFactory = new DAO();
			}
			return connectionFactory;
		}
		
		//method to get connection from this singleton class
		public Connection getConnection() throws SQLException {
			 Connection con = DriverManager.getConnection( url, username, pw);
			 return con;
		}
}
