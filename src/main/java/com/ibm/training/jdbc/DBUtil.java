package com.ibm.training.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	
	public static Connection getConnection() throws SQLException {
		//Class.forName("com.mysql.cj.jdbc.Driver"); //for old versions of Java only
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/ibm_b2", "root", "secret");
	}

	public static void main(String[] args) {
		
		try(Connection c = getConnection()) {
			System.out.println(c);
			DatabaseMetaData meta = c.getMetaData();
			System.out.println("Name: "+meta.getDatabaseProductName());
			System.out.println("Version: "+meta.getDatabaseProductVersion());
			
		} catch (Exception e) {
			System.out.println("Some error connecting to DB");
			e.printStackTrace();
		} 
	}

}
