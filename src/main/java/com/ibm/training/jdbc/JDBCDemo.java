package com.ibm.training.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCDemo {

	public static void main(String[] args) {
		//insertHardCodedUsingRawJdbc();
		selectPrintUsingRawJdbc();
		
	}

	private static void selectPrintUsingRawJdbc() {
		try (Connection con = DBUtil.getConnection();){
			String sql = "select * from product";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			//con.close();
			ResultSetMetaData rMeta = rs.getMetaData();
			int numColumns = rMeta.getColumnCount();
			System.out.println("---------------------------------------------------------------------------------------------------------");
			for(int i = 1; i <= numColumns; i++) {
				System.out.print(rMeta.getColumnName(i)+"\t\t");
			}
			System.out.println("\n---------------------------------------------------------------------------------------------------------");
			
			while(rs.next()) {
				for(int i = 1; i <= numColumns; i++) {
					System.out.print(rs.getString(i)+"\t\t\t");
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	private static void insertHardCodedUsingRawJdbc() {
		
		try(Connection con = DBUtil.getConnection();) {
			
			String sql = "insert into product(product_name,product_price,product_qoh) values('product2',1234,100)";
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate(sql);
			System.out.println("Inserted "+rows+" row(s)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
