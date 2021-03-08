package com.banking.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import com.banking.service.MoneyManagement;

class MoneyManagementTest {
	/**
	 * Tests methods from the MoneyManagement class.
	 */
	
	String validIntNoDollarSign = "100";
	String validIntDollarSign = "$100";
	
	String invalidIntNoDollarSign = "lizard";
	String invalidIntDollarSign = "$lizard";
	
	String zero = "0";
	String negativeInt = "-100";
	String negativeIntDollarSign = "$-100";

	/*@BeforeEach
	private void setUp() {
		
	}
	
	@AfterEach
	private void tearDown() {
		
	}*/
	
	@Test
	void testValidIntgNoDollarSign() {
		assertTrue("100 is a valid int", MoneyManagement.isPositiveIntGreaterThanZero(validIntNoDollarSign));
	}
	
	@Test
	void testValidIntDollarSign() {
		assertTrue("$100 (with '$' stripped) is a valid int", MoneyManagement.isPositiveIntGreaterThanZero(validIntDollarSign));
	}
	
	@Test
	void testInvalidIntNoDollarSign() {
		assertFalse("lizard is an invalid int", MoneyManagement.isPositiveIntGreaterThanZero(invalidIntNoDollarSign));
	}
	
	@Test
	void testInvalidIntDollarSign() {
		assertFalse("$lizard (with '$' stripped) is an invalid int", MoneyManagement.isPositiveIntGreaterThanZero(invalidIntDollarSign));
	}
	
	@Test
	void testConvertValidStringToIntNoDollarSign() {
		assertEquals("'100' converted to int is 100", 100, MoneyManagement.convertStringToInt(validIntNoDollarSign));
	}
	
	@Test
	void testConvertValidStringToIntDollarSign() {
		assertEquals("'$100' converted to int is 100", 100, MoneyManagement.convertStringToInt(validIntDollarSign));
	}
	
	@Test
	void testZero() {
		assertFalse("can't deposit 0", MoneyManagement.isPositiveIntGreaterThanZero(zero));
	}
	
	@Test
	void testNegativeIntNoDollarSign() {
		assertFalse("-100 is an invalid amount of money", MoneyManagement.isPositiveIntGreaterThanZero(negativeInt));
	}
	
	@Test
	void testNegativeIntDollarSign() {
		assertFalse("$-100 is an invalid amount of money", MoneyManagement.isPositiveIntGreaterThanZero(negativeIntDollarSign));
	}

}
