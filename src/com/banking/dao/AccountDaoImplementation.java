package com.banking.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.banking.MoneyManagement;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;

public class AccountDaoImplementation implements AccountDao {
	
	public static List<Account> accountList; // list of all accounts
	
	public AccountDaoImplementation() {
		super();
		accountList = new ArrayList<>();
		
		Account account = new Account("username", "password", "First", "Middle", "Last",
				"email@email.com", "732-732-7732", "123 Main Street", "City", "State", "Zip");
		account.setCheckingAccountBalance(1000);
		account.setSavingsAccountBalance(1000);
		accountList.add(account);
	}
	
	@Override
	public List<Account> getAllAccounts() {
		return accountList;
	}

	@Override
	public boolean getAccountByUsername(String username) {
		
		Iterator<Account> iterator = accountList.iterator();
		
		while (iterator.hasNext()) {
			
			Account currentAccount = iterator.next();
			
			if (currentAccount.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public boolean getAccountByPassword(String password) {

		Iterator<Account> iterator = accountList.iterator();
		
		while (iterator.hasNext()) {
			
			Account currentAccount = iterator.next();
			
			if (currentAccount.getPassword().equals(password)) {
				return true;
			}
		}
		
		return false;
		
	}

	@Override
	public Account getAccountByUsernameAndPassword(String username, String password) throws InvalidUsername, InvalidPassword, AccountNotFound {
		
		Iterator<Account> iterator = accountList.iterator();
		
		while (iterator.hasNext()) {
			
			Account currentAccount = iterator.next();
			
			if (currentAccount.getUsername().equals(username) && currentAccount.getPassword().equals(password)) {
				System.out.println("Welcome " + username + "!");
				System.out.println();
				return currentAccount;
			}
		}
		
		throw new AccountNotFound("No account with that username and password exists. Please try logging in again or creating a new account.");
	}
	
	@Override
	public void createAccount(Account account) {
		accountList.add(account);
	}

	@Override
	public void updateAccount(Account account) {
		
		Iterator<Account> iterator = accountList.iterator();
		
		while (iterator.hasNext()) {
			
			Account oldAccount = iterator.next();
			if (account.getUsername().equals(oldAccount.getUsername())) {
				deleteAccount(oldAccount);
				createAccount(account);
				break;
			}
		}
	}

	@Override
	public void deleteAccount(Account account) {
		
		Iterator<Account> iterator = accountList.iterator();
		
		while (iterator.hasNext()) {
			if (account.equals(iterator.next())) {
				accountList.remove(account);
				break;
			}
		}
		
	}
	
	@Override
	public void viewAccountDetails(Account account) {
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
		System.out.println("Checking account balance: " + account.getCheckingAccountBalance());
		System.out.println("Savings account balance: " + account.getSavingsAccountBalance());
		System.out.println();
	}

	@Override
	public void depositIntoChecking(Account account, String amount) {
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
