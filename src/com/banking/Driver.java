package com.banking;

import java.util.Scanner;

import com.banking.dao.AccountDaoKryo;
import com.banking.service.AuthenticationService;
import com.banking.service.AuthenticationServiceKryo;
import com.banking.ui.LoginMenu;
import com.banking.ui.MainMenu;
import com.banking.ui.Menu;
import com.banking.ui.SignupMenu;
import com.banking.ui.WelcomeMenu;

public class Driver {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		// Kryo
		AccountDaoKryo accountDao = new AccountDaoKryo();
		AuthenticationService authService = new AuthenticationServiceKryo(accountDao);
		
		// postgres
		// AccountDao accountDao = new AccountDaoPostgress;
		// AccountDaoPostgress accountDao = new AccountDaoPostgress;
		
		MainMenu mainMenu = new MainMenu(authService, accountDao);
		Menu signupMenu = new SignupMenu(authService, accountDao, mainMenu);
		Menu loginMenu = new LoginMenu(authService, accountDao, mainMenu);
		Menu welcomeMenu = new WelcomeMenu(loginMenu, signupMenu);
		
		((SignupMenu)signupMenu).setMainMenu(mainMenu);
		((SignupMenu)signupMenu).setAuthService(authService);
		((LoginMenu)loginMenu).setMainMenu(mainMenu);
		((LoginMenu)loginMenu).setAuthService(authService);
		
		loginMenu.setScanner(scanner);
		signupMenu.setScanner(scanner);
		welcomeMenu.setScanner(scanner);
		mainMenu.setScanner(scanner);
		
		Menu nextMenu = welcomeMenu;
		
		do {
			nextMenu.displayOptions();
			
			nextMenu = nextMenu.advance();
			
		} while (nextMenu != null);
		
		scanner.close();
	}
	
}
