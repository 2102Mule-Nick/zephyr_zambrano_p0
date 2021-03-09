package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.pojo.Account;
import com.banking.service.MoneyManagement;
import com.banking.util.ConnectionFactoryPostgres;

public class AccountDaoPostgres implements AccountDao {

	private Logger log = Logger.getRootLogger();

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
	public Account getAccountByUsernameAndPassword(String username, String password) throws AccountNotFound, InvalidPassword {
		
		log.info("Attempting to retrieve an existing account from the database");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		Account account = null;
		
		try {
			
			log.info("Successfully connected to the database");
			
			String sql = "select * from accounts where user_name = '" + username + "';";
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				
				log.info("Account with matching username found in the database");
				
				if (rs.getString("pass_word").equals(password)) {
					log.info("The given password matches the account's password");
				}
				else {
					log.warn("Invalid password; the given password does not match this account's password");
					throw new InvalidPassword();
				}
				
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
			
				connection.close();
				
				return account;
			}
			
			log.warn("Account not found; no account with the given username exists");
			throw new AccountNotFound();
			
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
		/**
		 * Connects to Postgres database and updates the account.
		 * Uses a prepared statement to protect against SQL injection attacks.
		 * @param Account account
		 */
		
		String sql = "update accounts set "
				+ "user_name = ?, "
				+ "pass_word = ?, "
				+ "first_name = ?, "
				+ "middle_name = ?, "
				+ "last_name = ?, "
				+ "street = ?, "
				+ "city = ?, "
				+ "state = ?, "
				+ "zip_code = ?, "
				+ "email = ?, "
				+ "phone_number = ?, "
				+ "checking_account_balance = ?, "
				+ "savings_account_balance = ? "
				+ "where account_id = ?";
		
		log.info("Attempting to update the account in the database using a prepared statement");
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		PreparedStatement preparedStatement = null;
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, account.getUsername());
			preparedStatement.setString(2, account.getPassword());
			preparedStatement.setString(3, account.getFirstname());
			preparedStatement.setString(4, account.getMiddlename());
			preparedStatement.setString(5, account.getLastname());
			preparedStatement.setString(6, account.getStreet());
			preparedStatement.setString(7, account.getCity());
			preparedStatement.setString(8, account.getState());
			preparedStatement.setString(9, account.getZipcode());
			preparedStatement.setString(10, account.getEmail());
			preparedStatement.setString(11, account.getPhoneNumber());
			preparedStatement.setInt(12, account.getCheckingAccountBalance());
			preparedStatement.setInt(13, account.getSavingsAccountBalance());
			preparedStatement.setInt(14, account.getAccountId());
			preparedStatement.execute();
			
			log.info("Account successfully updated in the database using a prepared statement");
		}
		catch (SQLException e) {
			log.error("Unable to update the account in the database using a prepared statement", e);
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
