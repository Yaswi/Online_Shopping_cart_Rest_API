package com.orderManagement.catalogue;

import java.util.*;
import java.sql.*;
import com.orderManagement.classMain.*;
public class CatalogueDAO {
	public static void main(String args[]) throws SQLException {
		CatalogueDAO c=new CatalogueDAO();
		System.out.println(c.getAllItemsFromDb());
	}

	//add to catalog in DB
	public void addItemtoDb(Product p) throws SQLException {
		Connection connection= OrderManagementMain.connection;
		String query1 = "INSERT INTO PRODUCTS (PRODUCT_ID , PRODUCT_NAME ,PRICE) values(?,?,?);";
		PreparedStatement ps1 =connection.prepareStatement(query1);
		
		ps1.setString(1,p.getId());
		ps1.setString(2,p.getName());
		ps1.setDouble(3, p.getPrice());
		
		ps1.executeUpdate();
	}
	
	// can update cost, name and id, need to write these methods SEPERATELY.
	
	// updating item in the DB
	public void updateIteminDb(Product p) throws SQLException{
		Connection connection=OrderManagementMain.connection;
		String query2="UPDATE PRODUCTS SET PRODUCT_NAME=?, PRICE=? WHERE PRODUCT_ID=?";
		PreparedStatement ps2=connection.prepareStatement(query2);
		
		ps2.setString(1, p.getName());
		ps2.setDouble(2, p.getPrice());
		ps2.setString(3, p.getId());
		
		ps2.executeUpdate();
	}
	
	// delete item in the DB
	
	 public void deleteItemfromDB(String id) throws SQLException{
	        Connection connection = OrderManagementMain.connection;
	        String query3="DELETE FROM PRODUCTS WHERE PRODUCT_ID=?";
	        PreparedStatement ps3=connection.prepareStatement(query3);
	        ps3.setString(1, id);
	        ps3.executeUpdate();
	    }
	 
	 // just getting all the db values into a hashmap
	 public LinkedHashMap<String,Product> getAllItemsFromDb() throws SQLException{
		 
	        LinkedHashMap <String,Product> products = new LinkedHashMap<>();
	        Connection connection =OrderManagementMain.connection;
	        String sql = "SELECT * FROM PRODUCTS";
	        PreparedStatement preparedStatement = connection.prepareStatement(sql);
	        ResultSet rs = preparedStatement.executeQuery();
	        while (rs.next()) {
	            String id = rs.getString("product_id").trim();
	            String name = rs.getString("product_name").trim();
	            double cost = rs.getDouble("price");
	            Product item =new Product(id,name,cost);
	            item.setId(id);
	            item.setName(name);
	            item.setPrice(cost);
	            products.put(id, item);
	       }
	        return products;
	    }
}






