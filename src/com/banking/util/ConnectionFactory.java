package com.banking.util;

import java.sql.Connection;

public interface ConnectionFactory {
	
	public Connection getConnection();
	
}
