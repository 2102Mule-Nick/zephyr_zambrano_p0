package com.banking.ui;

import java.util.Scanner;

import com.banking.dao.AccountDao;
import com.banking.exception.AccountNotFound;
import com.banking.exception.InvalidPassword;
import com.banking.exception.InvalidUsername;
import com.banking.pojo.Account;
import com.banking.service.AuthenticationService;

public class LoginMenu implements Menu {
	
	private MainMenu mainMenu;
	
	private Menu nextMenu;
	
	private Scanner scanner;
	
	private AuthenticationService authService;
	
	private AccountDao accountDao;

	@Override
	public Menu advance() {
		return nextMenu;
	}

	@Override
	public void displayOptions() {
		System.out.println("You have selected login");
		System.out.println();
		
		System.out.print("Please enter your username: ");
		String username = scanner.nextLine();
		System.out.println();
		
		System.out.print("Please enter your password: ");
		String password = scanner.nextLine();
		System.out.println();
		
		Account account = null;
		
		try {
			account = accountDao.getAccountByUsernameAndPassword(username, password);
		}
		catch (InvalidUsername e) {
			System.out.println("Invalid username; please try again.");
		}
		catch (InvalidPassword e) {
			System.out.println("Invalid password; please try again.");
		}
		catch (AccountNotFound e) {
			System.out.println("No account with that username and password exists. Please try logging in again or creating a new account.");
		}
		finally {
			System.out.println();
		}
		
		if (account != null) {
			mainMenu.setAccount(account);
			nextMenu = mainMenu;
		}
		else {
			nextMenu = null;
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
	
	public AuthenticationService getAuthService() {
		return authService;
	}

	public void setAuthService(AuthenticationService authService) {
		this.authService = authService;
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

	public LoginMenu() {
		super();
	}

	public LoginMenu(AuthenticationService authService, AccountDao accountDao, MainMenu mainMenu) {
		super();
		this.authService = authService;
		this.accountDao = accountDao;
		this.mainMenu = mainMenu;
	}

}
