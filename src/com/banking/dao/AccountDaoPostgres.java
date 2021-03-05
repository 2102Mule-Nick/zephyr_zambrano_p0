package com.banking.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;
import com.banking.util.ConnectionFactoryPostgres;

public class AccountDaoPostgres implements AccountDao {

	private Logger log = Logger.getRootLogger();
	
	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getAccountByUsername(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getAccountByPassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Account getAccountByUsernameAndPassword(String username, String password)
			throws InvalidUsername, InvalidPassword, AccountNotFound {
		
		// issue with this version of the postgres driver where it doesn't actually load the driver
		// you have to specifically tell it to load this driver into memory
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch (ClassNotFoundException e) {
			System.out.println("Failed to load Driver");
		}

				
		// our database is run on localhost, but a real database would have a real url location
		// 5432 is the default postgres port
		// ? in a url script is for the query parameters (example: username = username)
		
		Account account = new Account();
		
		log.info("Attempting to retrieve the account from the database");
		
		String url = "jdbc:postgresql://" + System.getenv("BankingApplication_DB_URL") + ":5432/" + "postgres" + "?";
		
		String user = System.getenv("BankingApplication_DB_Username");
		String pass = System.getenv("BankingApplication_DB_Password");
		
		try (Connection connection = DriverManager.getConnection(url, user, pass)) { // auto closes resources; don't need close statement
			log.info("Successfully connected to the database");
			
			String sql = "select * from account where username = " + username;
			
			Statement statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				account.setUsername("username");
				account.setPassword(rs.getString("pass_word"));
				
				account.setFirstname(rs.getString("first_name"));
				account.setMiddlename(rs.getString("middle_name"));
				account.setLastname(rs.getString("last_name"));
				
				account.setFullName(account.getFirstname(), account.getMiddlename(), account.getLastname());
				
				account.setStreet(rs.getString("street"));
				account.setCity(rs.getString("city"));
				account.setState(rs.getString("state"));
				account.setZipcode(rs.getString("zipcode"));
				
				account.setFullAddress(account.getStreet(), account.getCity(), account.getState(), account.getZipcode());
				
				account.setEmail(rs.getString("email"));
				account.setPhoneNumber(rs.getString("phone_number"));
				
				account.setCheckingAccountBalance(rs.getInt("checking_account_balance"));
				account.setCheckingAccountBalance(rs.getInt("savings_account_balance"));
			}
			
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
		
		Connection connection = ConnectionFactoryPostgres.getConnection();
		
		// TODO update this string
		String sql = "insert into accounts "
				+ "(username, pass_word, first_name, middle_name, last_name, street, city, state, zipcode,"
				+ "email, phone_number, checking_account_balance, savings_account_balance) "
				+ "values ("
				+ account.getUsername() + ", " + account.getPassword() + ", " + account.getFirstname()
				+ ", " + account.getMiddlename() + ", " + account.getLastname() + ", " + account.getStreet() 
				+ ", " + account.getCity() + ", " + account.getState() + ", " + account.getZipcode() + ", " + 
				0 + ", " + 0 + ")";
		
		Statement statement;
		
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
			connection.close();
		}
		catch (SQLException e) {
			log.error("Unable to connect to the database; unable to create user", e);
			e.printStackTrace();
		}

	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAccount(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewAccountDetails(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void viewAccountBalances(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void depositIntoChecking(Account account, String amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void depositIntoSavings(Account account, String amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void withdrawFromChecking(Account account, String amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void withdrawFromSavings(Account account, String amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferFromCheckingToSavings(Account account, String amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void transferFromSavingsToChecking(Account account, String amount) {
		// TODO Auto-generated method stub

	}

}
