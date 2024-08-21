package system.application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner; 
import system.dao.UserDao;
import system.daoImplementation.AccountsDaoImpl;
import system.daoImplementation.TransactionDaoImpl;
import system.daoImplementation.UserDaoImpl;
import system.exceptions.AccountException;
import system.exceptions.TransactionException;
import system.exceptions.UserException;
import system.model.Transactions;
import system.model.Users;
import system.services.AccountantService;
import system.services.UserService;
import system.utility.DAO;

public class Main {
	private static AccountantService accountantService;
    private static UserService userService;
    private static UserDao userDAO;
    private static Users loggedInUser;
	
	private static Connection establishConnection() throws SQLException{
		Connection c = null;
		try{			
			c=DAO.getConnectionFactory().getConnection() ; 
		}catch (SQLException e) { 
			e.printStackTrace();
		} 
		return c;
	}
	
	public static void main(String[] args) {
        try {
            // Establish a database connection
            Connection connection = establishConnection();

            // Initialize DAOs and Services
            AccountsDaoImpl accountDAO = new AccountsDaoImpl();
            TransactionDaoImpl transactionDAO = new TransactionDaoImpl();
            userDAO = new UserDaoImpl();
            accountantService = new AccountantService(accountDAO, transactionDAO);
            userService = new UserService(accountDAO, transactionDAO);

            // Start the application
            runApplication();

            // Close the connection when done
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	private static void runApplication() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Welcome to the Bank Application");
            System.out.println("1. Register as a new customer");
            System.out.println("2. Login to your account");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerAccount(scanner);
                    break;
                case 2:
                    if (loginToAccount(scanner)) {
                        showMainMenu(scanner);
                    }
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
         scanner.close();
    }
	
	private static boolean loginToAccount(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        // Validate login credentials
        try {
			loggedInUser = userDAO.getUserDetailsByUsernameAndPassword(username, password);
			if (loggedInUser != null) {
	            System.out.println("Login successful! Welcome, " + loggedInUser.getUsername());
	            return true;
	        } else {
	            System.out.println("Invalid username or password. Please try again.");
	            return false;
	        }
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
      
    }
	
	private static void registerAccount(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter upi_id: ");
        String upi_id = scanner.nextLine();

        // Register the user as a customer
        Users newUser = new Users();
        newUser.setUsername(username);
        newUser.setPassword(password); 
        newUser.setUpi_id(upi_id);

        boolean success = false;
		try {
			success = userDAO.addUser(newUser);
		} catch (UserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (success) {
            System.out.println("Registration successful! You can now log in.");
        } else {
            System.out.println("Failed to register. Username may already be taken.");
        }
    }

	private static void showMainMenu(Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Main Menu");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. View Transaction History");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    if (loggedInUser != null) {
                        createAccount(scanner);
                    } else {
                        System.out.println("Only accountants can create accounts.");
                    }
                    break;
                case 2:
                    depositMoney(scanner);
                    break;
                case 3:
                    withdrawMoney(scanner);
                    break;
                case 4:
                    transferMoney(scanner);
                    break;
                case 5:
                    viewTransactionHistory(scanner);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Logged out successfully!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
	
	private static void createAccount(Scanner scanner) {
        System.out.print("Enter customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double initialBalance = scanner.nextDouble();

        boolean success = false;
		try {
			success = accountantService.createAccount(customerId, accountNumber, initialBalance);
			if (!success) {
	            System.out.println("Account created successfully!");
	        } else {
	            System.out.println("Failed to create account. Please try again.");
	        }
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	private static void depositMoney(Scanner scanner) {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();
        boolean success;
		try {
			success = accountantService.deposit(accountId, amount);
			if (success) {
				System.out.println("Deposit successful!");
			} else {
				System.out.println("Failed to deposit money. Please try again.");
			}
		} catch (AccountException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
    }
	
	private static void withdrawMoney(Scanner scanner) {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        boolean success = accountantService.withdraw(accountId, amount);
        if (success) {
            System.out.println("Withdrawal successful!");
        } else {
            System.out.println("Failed to withdraw money. Please try again.");
        }
    }
	
	private static void transferMoney(Scanner scanner) {
        System.out.print("Enter source account ID: ");
        int fromAccountId = scanner.nextInt();
        System.out.print("Enter destination account No.: ");
        String toAccountNo = scanner.nextLine();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        boolean success = userService.transfer(fromAccountId, toAccountNo, amount);
        if (success) {
            System.out.println("Transfer successful!");
        } else {
            System.out.println("Failed to transfer money. Please try again.");
        }
    }
	
	private static void viewTransactionHistory(Scanner scanner) {
        System.out.print("Enter account ID: ");
        int accountId = scanner.nextInt();

        List<Transactions> transactions = null;
		try {
			transactions = userService.getTransactionHistory(accountId);
		} catch (TransactionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transactions transaction : transactions) {
                System.out.println("Transaction ID: " + transaction.getTransaction_id() +
                                   ", Amount: " + transaction.getAmount() +
                                   ", Type: " + transaction.getTransaction_type() +
                                   ", Date: " + transaction.getDate());
            }
        }
    }
}
