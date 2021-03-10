package com.banking.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.banking.util.ConnectionFactoryPostgres;

class ConnectionFactoryPostgresTest {

	ConnectionFactoryPostgres connectionFactory;
	
	@Test
	void testDatabaseConnection() {
		assertNotNull(ConnectionFactoryPostgres.getConnection(), "Connection to database should be created.");
	}

}
