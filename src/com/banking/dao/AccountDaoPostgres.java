package com.banking.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.banking.MoneyManagement;
import com.banking.pojo.Account;
import com.banking.util.ConnectionFactoryPostgres;

public class AccountDaoPostgres implements AccountDao {

	private Logger log = Logger.getRootLogger();
	
	// TODO change regular SQL statements into PREPARED statements to prevent SQL injection attacks
	
	// TODO remove implements and make this it's own class so I can get rid of inherited methods that aren't needed anymore

	@Override
	public boolean getAccountByUsername(String username) {
		
		log.info("Checking to see if the username is taken");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		try {
			
			log.info("Successfully connected to the database");
			
			String sql = "select * from accounts where user_name = '" + username + "';";
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			connection.close();
			
			if (rs.next()) {
				log.warn("Username already taken");
				return true;
			}
			else {
				log.info("Username is available");
				return false;
			}
		
		}
		catch (SQLException e) { // wrapper for any exception or error state the database would throw (not to be confused with wrapper classes)
			log.error("Unable to connect to the database", e);
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public Account getAccountByUsernameAndPassword(String username, String password) { // TODO throw errors
		
		log.info("Attempting to retrieve an existing account from the database");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		Account account = null;
		
		try {
			
			log.info("Successfully connected to the database");
			
			String sql = "select * from accounts where user_name = '" + username + "';";
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				log.info("Account found in the database");
				
				account = new Account();
				
				account.setAccountId(rs.getInt("account_id"));
				
				account.setUsername(rs.getString("user_name"));
				account.setPassword(rs.getString("pass_word"));
				
				account.setFirstname(rs.getString("first_name"));
				account.setMiddlename(rs.getString("middle_name"));
				account.setLastname(rs.getString("last_name"));
				
				account.setFullName(account.getFirstname(), account.getMiddlename(), account.getLastname());
				
				account.setStreet(rs.getString("street"));
				account.setCity(rs.getString("city"));
				account.setState(rs.getString("state"));
				account.setZipcode(rs.getString("zip_code"));
				
				account.setFullAddress(account.getStreet(), account.getCity(), account.getState(), account.getZipcode());
				
				account.setEmail(rs.getString("email"));
				account.setPhoneNumber(rs.getString("phone_number"));
				
				account.setCheckingAccountBalance(rs.getInt("checking_account_balance"));
				account.setCheckingAccountBalance(rs.getInt("savings_account_balance"));
			}
			
			connection.close();
			
			return account;
			
		}
		catch (SQLException e) { // wrapper for any exception or error state the database would throw (not to be confused with wrapper classes)
			log.error("Unable to connect to the database", e);
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void createAccount(Account account) {
		// TODO change to prepared statements to protect against sql injection attacks
		
		log.info("Attempting to create a new account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "insert into accounts "
				+ "(user_name, pass_word, first_name, middle_name, last_name, street, city, state, zip_code, "
				+ "email, phone_number, checking_account_balance, savings_account_balance) "
				+ "values ('"
				+ account.getUsername() + "', '" 
				+ account.getPassword() + "', '" 
				+ account.getFirstname() + "', '" 
				+ account.getMiddlename() + "', '" 
				+ account.getLastname() + "', '" 
				+ account.getStreet() + "', '" 
				+ account.getCity() + "', '" 
				+ account.getState() + "', '" 
				+ account.getZipcode() + "', '"
				+ account.getEmail() + "', '" 
				+ account.getPhoneNumber() + "', " 
				+ 0 + ", " 
				+ 0 + ");";
		
		Statement statement;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.close();
			log.info("Successfully created a new account");
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database; unable to create user", e);
			e.printStackTrace();
		}
		
	}

	@Override
	public void updateAccount(Account account) {
		// TODO change to prepared statements to protect against sql injection attacks
		
		
		/*
		 * 
		 * Connection conn = null;
		
		PreparedStatement stmt = null;
		
		String sql = "update user_acc set pass_word = ? WHERE username = ?";
		
		conn = ConnectionFactoryPostgres.getConnection();
		
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, new_password);
			stmt.setString(2, user.getUsername());
			stmt.execute();
			log.info("User updated");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.info("Unsuccessful update");
		}
		 * 
		 */
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "update accounts set "
				+ "(user_name, pass_word, first_name, middle_name, last_name, street, city, state, zip_code, "
				+ "email, phone_number, checking_account_balance, savings_account_balance) "
				+ "= ('"
				+ account.getUsername() + "', '" 
				+ account.getPassword() + "', '" 
				+ account.getFirstname() + "', '" 
				+ account.getMiddlename() + "', '" 
				+ account.getLastname() + "', '" 
				+ account.getStreet() + "', '" 
				+ account.getCity() + "', '" 
				+ account.getState() + "', '" 
				+ account.getZipcode() + "', '"
				+ account.getEmail() + "', '" 
				+ account.getPhoneNumber() + "', " 
				+ 0 + ", " 
				+ 0 + ") "
				+ "where account_id = " + account.getAccountId() + ";";
		
		log.info("Attempting to update the account in the database");
		
		Statement statement;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.close();
			
			log.info("Successfully updated the account in the database");
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database; unable to create user", e);
			e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAccount(Account account) {
		
		log.trace("deleteAccount method in AccountDaoPostgres class");
		log.info("Attempting to delete account");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		String sql = "delete from accounts where account_id = " + account.getAccountId() + ";";
		
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.close();
			log.info("Successfully deleted account");
		}
		catch (SQLException e) {
			log.error("Unable to connect to database to delete account");
			e.printStackTrace();
		}

	}

	@Override
	public void viewAccountDetails(Account account) {
		
		log.info("Getting account details");
		
		System.out.println("Username: " + account.getUsername());
		System.out.println("Password: " + account.getPassword());
		System.out.println();
		System.out.println("Name: " + account.getFullName());
		System.out.println("Address: " + account.getFullAddress());
		System.out.println("Email: " + account.getEmail());
		System.out.println("Phone number: " + account.getPhoneNumber());
		System.out.println();
		System.out.println("Checking account balance: " + account.getCheckingAccountBalance());
		System.out.println("Savings account balance: " + account.getSavingsAccountBalance());
		System.out.println();

	}

	@Override
	public void viewAccountBalances(Account account) {
		
		log.info("Getting account balances");
		
		System.out.println("Checking account balance: " + account.getCheckingAccountBalance());
		System.out.println("Savings account balance: " + account.getSavingsAccountBalance());
		System.out.println();

	}

	@Override
	public void depositIntoChecking(Account account, String amount) {
		
		log.info("Attempting to deposit into checking");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to deposit " + amount);
			System.out.println();
			int deposit = MoneyManagement.convertStringToInt(amount);
			int currentBalance = account.getCheckingAccountBalance();
			account.setCheckingAccountBalance(currentBalance + deposit);
			System.out.println(deposit + " has been successfully deposited into your checking account.");
			System.out.println("Current checking account balance: " + account.getCheckingAccountBalance());
			System.out.println();
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}

	}

	@Override
	public void depositIntoSavings(Account account, String amount) {
		
		log.info("Attempting to deposit into savings");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to deposit " + amount);
			System.out.println();
			int deposit = MoneyManagement.convertStringToInt(amount);
			int currentBalance = account.getSavingsAccountBalance();
			account.setSavingsAccountBalance(currentBalance + deposit);
			System.out.println(deposit + " has been successfully deposited into your savings account.");
			System.out.println("Current savings account balance: " + account.getSavingsAccountBalance());
			System.out.println();
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
	}

	@Override
	public void withdrawFromChecking(Account account, String amount) {
		
		log.info("Attempting to withdraw from checking");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to withdraw " + amount);
			System.out.println();
			int withdraw = MoneyManagement.convertStringToInt(amount);
			int currentBalance = account.getCheckingAccountBalance();
			
			if (withdraw > currentBalance) {
				System.out.println("Can't withdraw more money than what is in your account.");
				System.out.println("Nothing has been withdrawn.");
				System.out.println();
			}
			else {
				account.setCheckingAccountBalance(currentBalance - withdraw);
				System.out.println(withdraw + " has been successfully withdrawn from your checking account.");
				System.out.println("Current checking account balance: " + account.getCheckingAccountBalance());
				System.out.println();
			}
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}

	}

	@Override
	public void withdrawFromSavings(Account account, String amount) {
		
		log.info("Attempting to withdraw from savings");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to withdraw " + amount);
			System.out.println();
			int withdraw = MoneyManagement.convertStringToInt(amount);
			int currentBalance = account.getSavingsAccountBalance();
			
			if (withdraw > currentBalance) {
				System.out.println("Can't withdraw more money than what is in your account.");
				System.out.println("Nothing has been withdrawn.");
				System.out.println();
			}
			else {
				account.setSavingsAccountBalance(currentBalance - withdraw);
				System.out.println(withdraw + " has been successfully withdrawn from your savings account.");
				System.out.println("Current savings account balance: " + account.getSavingsAccountBalance());
				System.out.println();
			}
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
	}

	@Override
	public void transferFromCheckingToSavings(Account account, String amount) {
		
		log.info("Attempting to transfer money from checking to savings");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to transfer " + amount);
			System.out.println();
			int transfer = MoneyManagement.convertStringToInt(amount);
			int checkingBalance = account.getCheckingAccountBalance();
			int savingsBalance = account.getSavingsAccountBalance();
			
			if (transfer > checkingBalance) {
				System.out.println("Can't transfer more money than what is in your account.");
				System.out.println("Nothing has been transfered.");
				System.out.println();
			}
			else {
				account.setCheckingAccountBalance(checkingBalance - transfer);
				account.setSavingsAccountBalance(savingsBalance + transfer);
				System.out.println(transfer + " has been successfully transfered from checking to savings.");
				System.out.println();
				viewAccountBalances(account);
			}
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
	}

	@Override
	public void transferFromSavingsToChecking(Account account, String amount) {
		
		log.info("Attempting to transfer money from savings to checking");
		
		boolean isMoney = false;
		try {
			isMoney = MoneyManagement.isPositiveIntGreaterThanZero(amount);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
		if (isMoney == true) {
			System.out.println("You would like to transfer " + amount);
			System.out.println();
			int transfer = MoneyManagement.convertStringToInt(amount);
			int checkingBalance = account.getCheckingAccountBalance();
			int savingsBalance = account.getSavingsAccountBalance();
			
			if (transfer > savingsBalance) {
				System.out.println("Can't transfer more money than what is in your account.");
				System.out.println("Nothing has been transfered.");
				System.out.println();
			}
			else {
				account.setCheckingAccountBalance(checkingBalance + transfer);
				account.setSavingsAccountBalance(savingsBalance - transfer);
				System.out.println(transfer + " has been successfully transfered from savings to checking.");
				System.out.println();
				viewAccountBalances(account);
			}
		}
		else {
			System.out.println("Invalid input; please enter a valid positive USD value.");
			System.out.println();
		}
		
	}

}
