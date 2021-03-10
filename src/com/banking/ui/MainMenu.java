package com.banking.ui;

import java.util.Scanner;

import com.banking.dao.AccountDaoPostgres;
import com.banking.pojo.Account;

public class MainMenu implements Menu {

	private Scanner scanner;
	
	private AccountDaoPostgres accountDao;
	
	private Account account;
	
	public void setAccount(Account account) {
		this.account = account;
	}
	
	public MainMenu() {
		super();
	}
	
	public MainMenu(AccountDaoPostgres accountDao) {
		super();
		this.accountDao = accountDao;
	}

	@Override
	public Menu advance() {
		return null;
	}

	@Override
	public void displayOptions() {
		
		System.out.println("Welcome " + account.getUsername() + "!");
		System.out.println();
		
		String selection = "";
		System.out.println();
		
		boolean quit = false;
		
		while (quit == false) {
			
			System.out.println();
			System.out.println("What would you like to do?");
			System.out.println("a: account info");
			System.out.println("b: balances");
			System.out.println("d: deposit");
			System.out.println("h: help");
			System.out.println("m: manage account");
			System.out.println("t: transfer money");
			System.out.println("q: quit");
			System.out.println("w: withdrawal");
			System.out.println();

			System.out.print("Please type in your selection: ");
			selection = scanner.nextLine();
			System.out.println();
			
			if (selection.equals("a") || selection.equals("A")) {
				accountDao.viewAccountDetails(account);
			}
			else if (selection.equals("b") || selection.equals("B")) {
				accountDao.viewAccountBalances(account);
			}
			else if (selection.equals("d") || selection.equals("D")) {
				System.out.println("Which account would you like to deposit money into? ('c' for checking  / 's' for savings)");
				System.out.println();
				accountDao.viewAccountBalances(account);
				System.out.print("Your selection: ");
				selection = scanner.nextLine();
				System.out.println();
				
				if (selection.equals("c") || selection.equals("C")) {
					System.out.println("You selected checking.");
					System.out.print("How much would you like to deposit?: ");
					selection = scanner.nextLine();
					System.out.println();
					
					accountDao.depositIntoChecking(account, selection);
					
				}
				else if (selection.equals("s") || selection.equals("S")) {
					System.out.println("You selected savings.");
					System.out.print("How much would you like to deposit?: ");
					selection = scanner.nextLine();
					System.out.println();
					
					accountDao.depositIntoSavings(account, selection);
					
				}
				else {
					System.out.println("Invalid input; please try again.");
					System.out.println();
				}
			}
			else if(selection.equals("h") || selection.equals("H")) {
				help();
			}
			else if (selection.equals("m") || selection.equals("M")) {
				System.out.println("You would like to manage your account.");
				System.out.println();
				System.out.println("Would you like to update your account information or delete your account?");
				System.out.println("'u' for update / 'd' for delete");
				System.out.print("Your selection: ");
				selection = scanner.nextLine();
				System.out.println();
				
				if (selection.equals("u") || selection.equals("U")) {
					System.out.println("You would like to update your account information.");
					System.out.println();
					
					System.out.println("What would you like to update?");
					System.out.println("a: address");
					System.out.println("c: contact information (email or phone number)");
					System.out.println("n: name");
					System.out.println("p: password");
					System.out.println("u: username");
					System.out.println();
					
					System.out.print("Your selection: ");
					selection = scanner.nextLine();
					
					if (selection.equals("a") || selection.equals("A")) {
						System.out.println("You have selected address");
						System.out.println();
						
						String street = "";
						while (street.equals("")) {
							System.out.print("Street: ");
							street = scanner.nextLine();
							System.out.println();
						}
						
						String city = "";
						while (city.equals("")) {
							System.out.print("City: ");
							city = scanner.nextLine();
							System.out.println();
						}
						
						String state = "";
						while (state.equals("")) {
							System.out.print("State: ");
							state = scanner.nextLine();
							System.out.println();
						}
						
						String zipcode = "";
						while (zipcode.equals("")) {
							System.out.print("Zip code: ");
							zipcode = scanner.nextLine();
							System.out.println();
						}
						
						account.setFullAddress(street, city, state, zipcode);
						
					}
					else if (selection.equals("c") || selection.equals("C")) {
						System.out.println("You have selected contact information");
						System.out.println();
						
						System.out.println("would you like to update your email or phone number? ('e' / 'p')");
						System.out.print("Selection: ");
						selection = scanner.nextLine();
						
						if (selection.equals("e") || selection.equals("E")) {
							String email = "";
							while (email.equals("")) {
								System.out.print("Email: ");
								email = scanner.nextLine();
								System.out.println();
							}
							
							account.setEmail(email);
							
						}
						else if (selection.equals("p") || selection.equals("P")) {
							String phoneNumber = "";
							while (phoneNumber.equals("")) {
								System.out.print("Phone number: ");
								phoneNumber = scanner.nextLine();
								System.out.println();
							}
							account.setPhoneNumber(phoneNumber);
							
						}
						else {
							System.out.println("Invalid selection; please try again");
							System.out.println();
						}
						
					}
					else if (selection.equals("n") || selection.equals("N")) {
						System.out.println("You have selected name");
						System.out.println();
						
						String firstname = "";
						while (firstname.equals("")) {
							System.out.print("First name: ");
							firstname = scanner.nextLine();
							System.out.println();
						}
						
						// middle name can be an empty string because not everyone has a middle name
						String middlename = "";
						System.out.print("Middle name (hit enter key if no middle name): ");
						middlename = scanner.nextLine();
						System.out.println();
						
						String lastname = "";
						while (lastname.equals("")) {
							System.out.print("Last name: ");
							lastname = scanner.nextLine();
							System.out.println();
						}
						
						account.setFullName(firstname, middlename, lastname);
					}
					else if (selection.equals("p") || selection.equals("P")) {
						System.out.println("You have selected password");
						System.out.println();
						
						String password = "";
						while (password.equals("")) {
							System.out.print("New password: ");
							password = scanner.nextLine();
							System.out.println();
						}
						
						account.setPassword(password);
					}
					else if (selection.equals("u") || selection.equals("U")) {
						System.out.println("You have selected username");
						System.out.println();
						
						String newUsername = "";
						
						boolean usernameTaken = true;
						
						while (usernameTaken == true) {

							while (newUsername.equals("")) {
								System.out.print("New username: ");
								newUsername = scanner.nextLine();
								System.out.println();
								
							}
							
							usernameTaken = accountDao.getAccountByUsername(newUsername);
						}
						
						accountDao.updateAccountUsername(account, account.getUsername(), newUsername);
						
						account.setUsername(newUsername);
						
					}
					else {
						System.out.println("Invalid selection; please try again");
						System.out.println();
					}
					
					accountDao.updateAccount(account);
					
					System.out.println("Account information has been successfully updated.");
					System.out.println();
				}
				else if (selection.equals("d") || selection.equals("D")) {
					
					int checkingBalance = account.getCheckingAccountBalance();
					int savingsBalance = account.getSavingsAccountBalance();
					
					// can't delete account with money in it
					if (checkingBalance > 0 || savingsBalance > 0) {
						System.out.println("Can't delete an account with money in either checking or savings.");
						System.out.println("Please withdraw all of your money before deleting your account.");
						System.out.println();
						accountDao.viewAccountBalances(account);
					}
					else {
						System.out.println("Are you sure you want to delete your account? Type 'DELETE ACCOUNT' to confirm.");
						System.out.println("Hit the enter key to cancel account deletion.");
						System.out.print("Your selection: ");
						selection = scanner.nextLine();
						System.out.println();
						
						if (selection.equals("DELETE ACCOUNT")) {
							accountDao.deleteAccount(account);
							
							System.out.println("Account successfully deleted. Have a nice day!");
							System.out.println();
							
							quit = true;
						}
						else {
							System.out.println("Account deletion cancelled");
							System.out.println();
						}
					}
					
				}
				else {
					System.out.println("Invalid input; please try again.");
					System.out.println();
				}
			}
			else if(selection.equals("t") || selection.equals("T")) {
				System.out.println("You would like to make a transfer.");
				System.out.println();
				System.out.println("Which account would you like to transfer money from? ('c' for checking to savings  / 's' for savings to checking");
				System.out.println();
				accountDao.viewAccountBalances(account);
				System.out.print("Your selection: ");
				selection = scanner.nextLine();
				System.out.println();
				
				if (selection.equals("c") || selection.equals("C")) {
					System.out.print("How much would you like to transfer from checking into savings?: ");
					selection = scanner.nextLine();
					System.out.println();
					accountDao.transferFromCheckingToSavings(account, selection);
				}
				else if (selection.equals("s") || selection.equals("S") ){
					System.out.print("How much would you like to transfer from savings into checking?: ");
					selection = scanner.nextLine();
					System.out.println();
					accountDao.transferFromSavingsToChecking(account, selection);
				}
				else {
					System.out.println("Invalid input; please try again.");
					System.out.println();
				}
			}
			else if(selection.equals("q") || selection.equals("Q")) {
				quit = quit(selection, scanner);
			}
			else if (selection.equals("w") || selection.equals("W")) {
				System.out.println("Which account would you like to withdraw money from? ('c' for checking  / 's' for savings");
				System.out.println();
				accountDao.viewAccountBalances(account);
				System.out.print("Your selection: ");
				selection = scanner.nextLine();
				System.out.println();
				
				if (selection.equals("c") || selection.equals("C")) {
					System.out.println("You selected checking.");
					System.out.print("How much would you like to withdraw?: ");
					selection = scanner.nextLine();
					System.out.println();
					
					accountDao.withdrawFromChecking(account, selection);	
				}
				else if (selection.equals("s") || selection.equals("S")) {
					System.out.println("You selected savings.");
					System.out.print("How much would you like to withdraw?: ");
					selection = scanner.nextLine();
					System.out.println();
					
					accountDao.withdrawFromSavings(account, selection);
				}
				else {
					System.out.println("Invalid input; please try again.");
					System.out.println();
				}
			}
			else {
				System.out.println("Invalid input; please try again.");
				System.out.println();
			}
			
		}
		
	}

	@Override
	public Menu previousMenu() {
		return null;
	}

	@Override
	public Scanner getScanner() {
		return scanner;
	}

	@Override
	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public AccountDaoPostgres getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDaoPostgres accountDao) {
		this.accountDao = accountDao;
	}
	
	public static void help() {
		System.out.println("Help Menu");
		System.out.println("In order to select options, type the correct character for the selection you want to use.");
		System.out.println();

		System.out.println("To view your user account information, type 'a' or 'A'");
		System.out.println("To view your balances, type 'b' or 'B'");
		System.out.println("To make a deposit, type 'd' or 'D'");
		System.out.println("To access the help menu, type 'h' or 'H'");
		System.out.println("To manage your account, such as updating your personal information or deleting your account, type 'm' or 'M'");
		System.out.println("To transfer money from one account to another, type 't' or 'T'");
		System.out.println("To quit the system, type 'q' or 'Q'");
		System.out.println("To make a withdrawal, type 'w' or 'W'");
		System.out.println();

		System.out.println("Some menu options will require typing 'y' or 'Y' for yes, or 'n' or 'N' for 'no'");
		System.out.println();

		System.out.println("For deposits, withdrawals, and transfers, you will have to type the amount of money in USD.");
		System.out.println("Example: type '100', '$100' if you want to deposit $100");
		System.out.println();
		
	}
	
	public boolean quit(String selection, Scanner scanner) {
		System.out.println("Are you sure you want to quit? (y / n)");
		System.out.print("Your selection: ");
		String confirmationSelection = scanner.nextLine();
		System.out.println();

		if (confirmationSelection.equals("y") || confirmationSelection.equals("Y") || confirmationSelection.equals("yes") || confirmationSelection.equals("Yes") || confirmationSelection.equals("YES")) {
			accountDao.updateAccount(account);
			System.out.println("Thank you and have a great day!");
			return true;
		}
		else if (confirmationSelection.equals("n") || confirmationSelection.equals("N") || confirmationSelection.equals("no") || confirmationSelection.equals("No") || confirmationSelection.equals("NO")) {
			return false;
		}
		else {
			System.out.println("Invalid selection; please try again.");
			System.out.println();
			return false;
		}
	}

}
