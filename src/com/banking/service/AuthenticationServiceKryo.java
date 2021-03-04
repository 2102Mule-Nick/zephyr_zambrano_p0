package com.banking.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

import com.banking.dao.AccountDao;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.exception.UsernameTaken;
import com.banking.pojo.Account;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class AuthenticationServiceKryo implements AuthenticationService {
	
	private Kryo kryo = new Kryo();

	private Logger log = Logger.getRootLogger();
	
	private static final String FOLDER_NAME = "accounts\\";
	
	private static final String FILE_EXTENSION = ".dat";

	private AccountDao accountDao;
	
	public AccountDao getAccountDao() {
		return accountDao;
	}
	
	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}
	
	@Override
	public boolean authenticateUsername(String username) {
		
		log.info("Checking if username exists");
		
		try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + username + FILE_EXTENSION)) {
			return true;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean authenticatePassword(String username, String password) {
		log.info("Checking if password exists");
		
		try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + username + FILE_EXTENSION)) {
			Input input = new Input(inputStream);
			Account account = kryo.readObject(input, Account.class);
			if (account.getPassword().equals(password)) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Account authenticateAccount(String username, String password)
			throws InvalidUsername, InvalidPassword, AccountNotFound {
		
		/*boolean usernameExists = authenticateUsername(username);
		boolean passwordExists = false;
		
		if (usernameExists == true) {
			passwordExists = authenticatePassword(password);
		}*/
		
		log.info("Attempting to authenticate an account by username and password");
		
		try (FileInputStream inputStream = new FileInputStream(FOLDER_NAME + username + FILE_EXTENSION)) {
			Input input = new Input(inputStream);
			Account account = kryo.readObject(input, Account.class);
			
			System.out.println(account.toString());
			input.close();
			
			return account;
			
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public boolean usernameTaken(String username) throws UsernameTaken {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Account registerAccount(Account account) {
		
		log.info("Attempting to create an account");
		
		try(FileOutputStream outputStream = new FileOutputStream(FOLDER_NAME + account.getUsername() + FILE_EXTENSION)) {
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
		return account;
	}
	
	
	public AuthenticationServiceKryo() {
		super();
	}

	public AuthenticationServiceKryo(AccountDao accountDao) {
		super();
		this.accountDao = accountDao;
	}

	@Override
	public boolean authenticatePassword(String password) {
		// TODO Auto-generated method stub
		return false;
	}

}
