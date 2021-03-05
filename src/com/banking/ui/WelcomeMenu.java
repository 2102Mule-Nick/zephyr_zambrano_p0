package com.banking.ui;

import java.util.Scanner;

import org.apache.log4j.Logger;

public class WelcomeMenu implements Menu {
	
	private Logger log = Logger.getRootLogger();
	
	private Scanner scanner;
	
	private Menu loginMenu;
	
	private Menu signupMenu;
	
	private Menu nextMenu;

	@Override
	public Menu advance() {
		return nextMenu;
	}

	@Override
	public void displayOptions() {
		System.out.println("Welcome to the banking application!");
		System.out.println();
		
		// user selects whether they want to log in to an existing account or create a new account
		System.out.println("Would you like to log in or sign up? ('l' or log in, 's' for sign up, 'q' to quit'): ");
		System.out.print("Your selection: ");
		String selection = scanner.nextLine();
		System.out.println();
		
		if (selection.equals("l") || selection.equals("L")) {
			nextMenu = loginMenu;
		}
		else if (selection.equals("s") || selection.equals("S")) {
			nextMenu = signupMenu;
		}
		else if (selection.equals("q") || selection.equals("Q")) {
			System.out.println("Exiting program. Have a nice day!");
			System.out.println();
			nextMenu = null;
		}
		else {
			System.out.println("Invalid selection; please try again.");
			System.out.println();
			nextMenu = this;
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
	
	public WelcomeMenu() {
		super();
	}

	public WelcomeMenu(Menu loginMenu, Menu signupMenu) {
		super();
		this.loginMenu = loginMenu;
		this.signupMenu = signupMenu;
	}
	
}
