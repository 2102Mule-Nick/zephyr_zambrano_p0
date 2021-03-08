package com.banking;

import java.util.Scanner;

import org.apache.log4j.Logger;

import com.banking.dao.AccountDaoPostgres;
import com.banking.ui.LoginMenu;
import com.banking.ui.MainMenu;
import com.banking.ui.Menu;
import com.banking.ui.SignupMenu;
import com.banking.ui.WelcomeMenu;

public class Driver {
	
	private Logger log = Logger.getRootLogger();

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		AccountDaoPostgres accountDao = new AccountDaoPostgres();
		
		MainMenu mainMenu = new MainMenu(accountDao);
		Menu signupMenu = new SignupMenu(accountDao, mainMenu);
		Menu loginMenu = new LoginMenu(accountDao, mainMenu);
		Menu welcomeMenu = new WelcomeMenu(loginMenu, signupMenu);
		
		((SignupMenu)signupMenu).setMainMenu(mainMenu);
		((LoginMenu)loginMenu).setMainMenu(mainMenu);
		
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
