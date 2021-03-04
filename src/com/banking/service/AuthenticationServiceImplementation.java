package com.banking.service;

import com.banking.dao.AccountDao;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.exception.UsernameTaken;
import com.banking.pojo.Account;

public class AuthenticationServiceImplementation implements AuthenticationService {
	
	private AccountDao accountDao;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Override
	public boolean authenticateUsername(String username) {
		return accountDao.getAccountByUsername(username);
	}
	
	@Override
	public boolean authenticatePassword(String password) {
		return accountDao.getAccountByPassword(password);
	}
	

	@Override
	public Account authenticateAccount(String username, String password) throws InvalidUsername, InvalidPassword, AccountNotFound {
		
		boolean usernameExists = authenticateUsername(username);
		boolean passwordExists = authenticatePassword(password);

		if (usernameExists == false && passwordExists == true) {
			throw new InvalidUsername();
		}
		else if (usernameExists == true && passwordExists == false) {
			throw new InvalidPassword();
		}
		else if (usernameExists == false && passwordExists == false) {
			throw new AccountNotFound();
		}
		else {
			return accountDao.getAccountByUsernameAndPassword(username, password);
		}
		
	}
	
	@Override
	public boolean usernameTaken(String username) throws UsernameTaken {
		boolean usernameTaken =  authenticateUsername(username);
		if (usernameTaken == true) {
			throw new UsernameTaken();
		}
		
		return usernameTaken;
		
	}

	@Override
	public Account registerAccount(Account account) {
		accountDao.createAccount(account);
		return account;
	}
	
	public AuthenticationServiceImplementation() {
		super();
	}

	public AuthenticationServiceImplementation(AccountDao accountDao) {
		super();
		this.accountDao = accountDao;
	}
	
}
