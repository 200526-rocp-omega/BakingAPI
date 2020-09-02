package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private static Connection conn = null;
	
	// Private constructor PREVENTS us from ever instatiatimg this class
	private ConnectionUtil() {
		super();
	}
	
	public static Connection getConnection() {
		// here we connect to the DB using DriveManager
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			try {
				conn = DriverManager.getConnection(
						"jdbc:oracle:thin:@training.cqjnlnexi0ao.us-east-2.rds.amazonaws.com:1521:ORCL",
						"******",
						"******");
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		} catch(ClassNotFoundException e) {
			System.out.println("Did NOT find Oracle JDBC Driver Class");
		}
		
		return conn;
	}
}
