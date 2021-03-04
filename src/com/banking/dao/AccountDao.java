package com.banking.dao;

import java.util.List;

import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;

public interface AccountDao {
	
	public List<Account> getAllAccounts();
	
	public boolean getAccountByUsername(String username);
	
	public boolean getAccountByPassword(String password);
	
	public Account getAccountByUsernameAndPassword(String username, String password) throws InvalidUsername, InvalidPassword, AccountNotFound;
	
	public void createAccount(Account account);
	
	public void updateAccount(Account account);
	
	public void deleteAccount(Account account);
	
	public void viewAccountDetails(Account account);
	
	public void viewAccountBalances(Account account);
	
	public void depositIntoChecking(Account account, String amount);
	
	public void depositIntoSavings(Account account, String amount);
	
	public void withdrawFromChecking(Account account, String amount);
	
	public void withdrawFromSavings(Account account, String amount);
	
	public void transferFromCheckingToSavings(Account account, String amount);
	
	public void transferFromSavingsToChecking(Account account, String amount);
	
}
