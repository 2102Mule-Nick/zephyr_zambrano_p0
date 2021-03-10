package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.banking.dao.AccountDaoPostgres;
import com.banking.pojo.Account;
import com.banking.util.ConnectionFactoryPostgres;

@ExtendWith(MockitoExtension.class)
class AccountDaoPostgresTest {
	
	@Mock (lenient = true)
	private Connection connection;
	
	AccountDaoPostgres accountDao;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		accountDao = new AccountDaoPostgres();
		
		PreparedStatement preparedStatement = ConnectionFactoryPostgres.getConnection().prepareStatement("delete from accounts where user_name = 'AccountDaoPostgresTest';");
		preparedStatement.executeUpdate();
	}

	@AfterEach
	void tearDown() throws Exception {
		PreparedStatement preparedStatement = ConnectionFactoryPostgres.getConnection().prepareStatement("delete from accounts where user_name = 'AccountDaoPostgresTest';");
		preparedStatement.executeUpdate();
	}
	
	@Test
	void createAccountTest() throws SQLException {
		
		Account account = new Account("AccountDaoPostgresTest", "password", "First", "Middle", "Last", "Street",
				"City", "State", "00000", "email@email.com", "777-777-7777");
		
		String sql = "insert into accounts "
				+ "(user_name, pass_word, first_name, middle_name, last_name, street, city, state, zip_code, "
				+ "email, phone_number, checking_account_balance, savings_account_balance) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		
		Connection realConnection = ConnectionFactoryPostgres.getConnection();
		
		PreparedStatement realStatement = realConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		PreparedStatement spy = Mockito.spy(realStatement);
		
		when(connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)).thenReturn(spy);
		
		accountDao.setConnection(connection);
		
		accountDao.createAccount(account);
		
		verify(spy).setString(1, account.getUsername());
		verify(spy).setString(2, account.getPassword());
		verify(spy).setString(3, account.getFirstname());
		verify(spy).setString(4, account.getMiddlename());
		verify(spy).setString(5, account.getLastname());
		verify(spy).setString(6, account.getStreet());
		verify(spy).setString(7, account.getCity());
		verify(spy).setString(8, account.getState());
		verify(spy).setString(9, account.getZipcode());
		verify(spy).setString(10, account.getEmail());
		verify(spy).setString(11, account.getPhoneNumber());
		verify(spy).setInt(12, account.getCheckingAccountBalance());
		verify(spy).setInt(13, account.getSavingsAccountBalance());
		
		verify(spy).executeUpdate();
		
		PreparedStatement checkStatement = ConnectionFactoryPostgres.getConnection().prepareStatement("select * from accounts where user_name = 'AccountDaoPostgresTest';");
		ResultSet rs = checkStatement.executeQuery();
		
		assertTrue(rs.next());
	}

}
