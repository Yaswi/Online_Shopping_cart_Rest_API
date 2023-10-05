package com.orderManagement.classMain;

import java.sql.*;

public class DbConnection {

	public static Connection connection = null;
	
	public static Connection createConnection() {
		String jdbcUrl = "jdbc:db2://ea286ace-86c7-4d5b-8580-3fbfa46b1c66.bs2io90l08kqb1od8lcg.databases.appdomain.cloud:31505/bludb:user=npv60223;password=sLTvvM57D0Zv0mQg;sslConnection=true;";
		
		try {
			connection=DriverManager.getConnection(jdbcUrl);
			if(connection!=null) {
				System.out.println("Connection is made successfully");
			}
		}
		catch(SQLException e) {
				e.printStackTrace();
			}
			return connection;
		} 
	
	public static void closeConnection() throws SQLException{
		if(connection==null) {
			throw new SQLException("No available connection");
		}
		else {
			connection.close();
		}
}
}
