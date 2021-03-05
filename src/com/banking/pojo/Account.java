package com.banking.pojo;

public class Account {
	
	private int accountId;
	
	private String username;
	private String password;
	
	private String fullName;
	private String firstname;
	private String middlename;
	private String lastname;
	
	private String email;
	private String phoneNumber;
	
	private String fullAddress;
	private String street;
	private String city;
	private String state;
	private String zipcode;

	private int checkingAccountBalance;
	private int savingsAccountBalance;
	
	public Account() {
		super();
	}
	
	public Account(String username, String password, String firstname, String middlename,
			String lastname, String email, String phoneNumber, String street, String city,
			String state, String zipcode) {
		super();
		this.username = username;
		this.password = password;
		
		this.firstname = firstname;
		this.middlename = middlename;
		this.lastname = lastname;
		
		setFullName(firstname, middlename, lastname);
		
		this.email = email;
		this.phoneNumber = phoneNumber;
		
		this.street = street;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		
		setFullAddress(street, city, state, zipcode);
		
		// checking and savings accounts balances are initialized to 0
		// user has to deposit money after creating account
		this.checkingAccountBalance = 0;
		this.savingsAccountBalance = 0;
	}

	public int getAccountid() {
		return accountId;
	}

	public void setAccountid(int accountid) {
		this.accountId = accountid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String firstname, String middlename, String lastname) {
		/**
		 * Sets the full name of an account user with the given first name, middle name, and last name.
		 * Saves the full name in the private String fullName.
		 * After doing this, it sets the separate private Strings firstname, middlename, and lastname
		 * with the given first name, middle name, and last name
		 * @param String firstname the account user's first name
		 * @param String middlename the account user's middle name
		 * @param String lastname the account user's last name
		 * 
		 */
		
		if (middlename.equals("")) { // user doesn't have a middle name
			this.fullName = firstname + " " + lastname;
		}
		else { // user has a middle name
			this.fullName = firstname + " " + middlename + " " + lastname;
		}
		
		setFirstname(firstname);
		setMiddlename(middlename);
		setLastname(lastname);
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String street, String city, String state, String zipcode) {
		/**
		 * Sets the account user's full address.
		 * Saves the full address in the private String fullAddress, and then saves each
		 * piece of the address in their respective private String variables.
		 * 
		 * @param String street
		 * @param Strimg city
		 * @param String state
		 * @param String zipcode
		 */
		
		this.fullAddress = street + " " + city + " " + state + " " + zipcode;
		
		setStreet(street);
		setCity(city);
		setState(state);
		setZipcode(zipcode);
		
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public int getCheckingAccountBalance() {
		return checkingAccountBalance;
	}

	public void setCheckingAccountBalance(int checkingAccountBalance) {
		this.checkingAccountBalance = checkingAccountBalance;
	}

	public int getSavingsAccountBalance() {
		return savingsAccountBalance;
	}

	public void setSavingsAccountBalance(int savingsAccountBalance) {
		this.savingsAccountBalance = savingsAccountBalance;
	}
	
}
