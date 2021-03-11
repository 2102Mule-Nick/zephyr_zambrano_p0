package com.banking.ui;

import java.util.Scanner;

import com.banking.dao.AccountDao;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.pojo.Account;

public class LoginMenu implements Menu {
	
	private MainMenu mainMenu;
	
	private Menu nextMenu;
	
	private Menu previousMenu;
	
	private Scanner scanner;
	
	private AccountDao accountDao;

	@Override
	public Menu advance() {
		return nextMenu;
	}

	@Override
	public void displayOptions() {
		System.out.println("You have selected login");
		System.out.println();
		
		String username = "";
		while (username.equals("")) {
			System.out.print("Please enter your username: ");
			username = scanner.nextLine();
			System.out.println();
		}
		
		String password = "";
		while (password.equals("")) {
			System.out.print("Please enter your password: ");
			password = scanner.nextLine();
			System.out.println();
		}
		
		Account account = null;
		
		try {
			account = accountDao.getAccountByUsernameAndPassword(username, password);
		}
		catch (AccountNotFound e) {
			System.out.println("No account with that username exists; please try logging in again or create a new account.");
		}
		catch (InvalidPassword e) {
			System.out.println("Invalid password; please try again.");
		}
		finally {
			System.out.println();
		}
		
		if (account != null) {
			mainMenu.setAccount(account);
			nextMenu = mainMenu;
		}
		else {
			nextMenu = previousMenu;
		}
		
	}

	@Override
	public Menu previousMenu() {
		return null;
	}

	@Override
	public Scanner getScanner() {
		return this.scanner;
	}

	@Override
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public Menu getMainMenu() {
		return mainMenu;
	}

	public void setMainMenu(MainMenu mainMenu) {
		this.mainMenu = mainMenu;
	}

	public Menu getNextMenu() {
		return nextMenu;
	}

	public void setNextMenu(Menu nextMenu) {
		this.nextMenu = nextMenu;
	}
	
	

	public Menu getPreviousMenu() {
		return previousMenu;
	}

	public void setPreviousMenu(Menu previousMenu) {
		this.previousMenu = previousMenu;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

	public LoginMenu() {
		super();
	}

	public LoginMenu(AccountDao accountDao, MainMenu mainMenu) {
		super();
		this.accountDao = accountDao;
		this.mainMenu = mainMenu;
	}

}
