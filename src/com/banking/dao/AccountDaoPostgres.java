package com.banking.dao;

import java.util.List;

import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;

public class AccountDaoPostgres implements AccountDao {

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createAccount(Account account) {
		// TODO Auto-generated method stub

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
