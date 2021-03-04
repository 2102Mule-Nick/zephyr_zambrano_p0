package com.banking;

public class MoneyManagement {
	/**
	 * Handles String to int conversions to determine how much money a user wants to deposit, withdraw, or transfer.
	 */

	public static boolean isPositiveIntGreaterThanZero(String string) {
		/**
		 * Checks if a string input is a valid int and returns true or false
		 * Strips '$' from the beginning of the string if present
		 * @param String
		 * @return boolean
		 */
		
		if (string == null) {
			return false;
		}
		
		if (string.charAt(0) == '$') {
			string = string.substring(1, string.length());
		}
		
		int i;
		
		try { 
			i = Integer.parseInt(string);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input; please enter a valid positive USD.");
			return false;
		}
		
		if (i > 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public static int convertStringToInt(String string) {
		/**
		 * Converts valid USD string to int and returns it
		 * Strips '$' from the beginning of the string if present
		 * @param String
		 * @return int
		 */
		
		if (string.charAt(0) == '$') {
			string = string.substring(1, string.length());
		}
		
		return Integer.parseInt(string);
		
	}
	
}
