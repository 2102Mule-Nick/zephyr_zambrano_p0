package com.banking.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.banking.MoneyManagement;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AccountDaoKryo implements AccountDao {

	private Kryo kryo = new Kryo();

	private Logger log = Logger.getRootLogger();
	
	private static final String FOLDER_NAME = "accounts\\";
	
	private static final String FILE_EXTENSION = ".dat";

	public AccountDaoKryo() {
		super();
		kryo.register(Account.class);
	}
	
	@Override
	public List<Account> getAllAccounts() {
		// TODO Auto-generated method stub
		// no longer needed
		return null;
	}

	@Override
	public boolean getAccountByUsername(String username) {

		log.info("Checking if the given username is already taken");
		
		File file = new File(FOLDER_NAME + username + FILE_EXTENSION);
		if (file.exists()) {
			log.info("Username is taken");
			return true;
		}
		else {
			log.info("Username is available");
			return false;
		}
		
	}

	@Override
	public boolean getAccountByPassword(String password) {
		// TODO Auto-generated method stub
		// no longer needed
		return false;
	}
	
	public boolean accountExists(String username) {
		
		log.info("Checking if an account with the given username exists");
		
		File file = new File(FOLDER_NAME + username + FILE_EXTENSION);
		if (file.exists()) {
			log.info("Found account with matching username");
			return true;
		}
		else {
			log.info("No account with the given username exists");
			return false;
		}
	}

	@Override
	public Account getAccountByUsernameAndPassword(String username, String password)
			throws InvalidUsername, InvalidPassword, AccountNotFound {
		
		log.info("Attempting to get an account by username and password");
		
		// if an account with that username exists, open the file and authenticate the password
		// otherwise, throw error
		if (accountExists(username)) {
			log.info("Found account with matching username");
			
			try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + username + FILE_EXTENSION)) {
				log.info("Found account with matching username");
				Input input = new Input(inputStream);
				Account account = kryo.readObject(input, Account.class);
				input.close();
				
				log.info("Attempting to authenticate the password");
				if (account.getPassword().equals(password)) {
					log.info("The given password matches the account; logging in now");
					return account;
				}
				else {
					log.info("Password is invalid; can't log in");
					throw new InvalidPassword();
				}
				
			}
			catch (FileNotFoundException e) {
				log.error("Could not open file", e);
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			throw new InvalidUsername();
		}
		
		return null;
		
	}

	@Override
	public void createAccount(Account account) {
		
		log.info("Attempting to create an account");
		
		try (FileOutputStream outputStream = new FileOutputStream(FOLDER_NAME + account.getUsername() + FILE_EXTENSION)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, account);
			output.close();
		}
		catch (FileNotFoundException e) {
			log.error("Could not open file", e);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean renameFileWithNewUsername(String oldUsername, String newUsername) {
		
		log.info("Attempting to update the username and rename the account file");
		
		File file = new File(FOLDER_NAME + oldUsername + FILE_EXTENSION);
		File file2 = new File(FOLDER_NAME + newUsername + FILE_EXTENSION);
		
		if (file.renameTo(file2)) {
			log.info("Successfully renamed the file");
			return true;
		}
		else {
			log.error("Unable to rename the file");
			return false;
		}
		
	}

	@Override
	public void updateAccount(Account account) {
		
		log.info("Attempting to update the account file");
		
		try (FileOutputStream outputStream = new FileOutputStream(FOLDER_NAME + account.getUsername() + FILE_EXTENSION)) {
			Output output = new Output(outputStream);
			kryo.writeObject(output, account);
			output.close();
			log.info("Successfully updated the account's file");
		}
		catch (FileNotFoundException e) {
			log.error("Could not open file", e);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAccount(Account account) {
		
		log.error("Attempting to delete account");
		
		File file = new File(FOLDER_NAME + account.getUsername() + FILE_EXTENSION);
		if (file.delete()) {
			log.info("Successfully deleted account");
		}
		else {
			log.error("Unable to delete account");
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
