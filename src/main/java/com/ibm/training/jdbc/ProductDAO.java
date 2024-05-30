package com.ibm.training.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {

	public int save(Product toBeSaved) {
		int id = -1;
		try (Connection con = DBUtil.getConnection();) {
			String sql = "insert into product(product_name,product_price,product_qoh) values(?,?,?)";
			PreparedStatement pStmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			pStmt.setString(1, toBeSaved.getName());
			pStmt.setFloat(2, toBeSaved.getPrice());
			pStmt.setInt(3, toBeSaved.getQoh());
			pStmt.executeUpdate();
			
			ResultSet keys = pStmt.getGeneratedKeys();
			keys.next();
			id = keys.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// insert using data from toBeSaved obj
		return id;
	}

	public Optional<Product> findById(int prdId) {
		// query using prdId and transform the rs into a Product obj
		try (Connection con = DBUtil.getConnection();) {
			String sql = "select * from product where product_id="+prdId;
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				Product p = mapRow(rs);
				return Optional.of(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}

	private Product mapRow(ResultSet rs) throws SQLException {
		Product p = new Product();
		p.setId(rs.getInt(1));
		p.setName(rs.getString(2));
		p.setPrice(rs.getFloat(3));
		p.setQoh(rs.getInt(4));
		return p;
	}

	public List<Product> findAll() {
		List<Product> all = new ArrayList<>();
		try (Connection con = DBUtil.getConnection();) {
			String sql = "select * from product";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Product aProduct = mapRow(rs);
				all.add(aProduct);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// select * and transform rs into a List<Product>
		return all;
	}

	public void update(Product toBeUpdated) {
		try (Connection con = DBUtil.getConnection();) {
			String sql = "update product set product_name=?,product_price=?,product_qoh=? where product_id=?";
			PreparedStatement pStmt = con.prepareStatement(sql);
			pStmt.setString(1, toBeUpdated.getName());
			pStmt.setFloat(2, toBeUpdated.getPrice());
			pStmt.setInt(3, toBeUpdated.getQoh());
			pStmt.setInt(4, toBeUpdated.getId());
			pStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// update row using data from toBeUpdated obj
	}

	public boolean deleteById(int prdId) {
		boolean foundAndDeleted = false;
		try (Connection con = DBUtil.getConnection();) {
			String sql = "delete from product where product_id="+prdId;
			Statement stmt = con.createStatement();
			int rows = stmt.executeUpdate(sql);
			foundAndDeleted = (rows == 1); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// delete row using prdId
		return foundAndDeleted;
	}

}
