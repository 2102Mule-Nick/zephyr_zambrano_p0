package com.banking.service;

import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.exception.UsernameTaken;
import com.banking.pojo.Account;

public interface AuthenticationService {
	
	public boolean authenticateUsername(String username);
	
	public boolean authenticatePassword(String password);
	
	public Account authenticateAccount(String username, String password) throws InvalidUsername, InvalidPassword, AccountNotFound;
	
	public boolean usernameTaken (String username) throws UsernameTaken;
	
	public Account registerAccount(Account account);
	
}
