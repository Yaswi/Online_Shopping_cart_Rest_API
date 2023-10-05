package com.orderManagement.cart;


import java.sql.*;
import java.time.Instant;
import java.util.*;

import com.orderManagement.classMain.OrderManagementMain;


public class ShoppingCartDAO  {

    private LinkedHashMap<String,Integer> cartItems = new LinkedHashMap<>();
    
    public LinkedHashMap<String,Integer> getAllItemsFromCart(String customerID) throws SQLException {
        Connection connection =OrderManagementMain.connection;
        
        ResultSet rs=null;
        String query1="SELECT CART_ID,PRODUCT_ID,QUANTITY FROM CART_ITEMS WHERE CART_ID=?";
        PreparedStatement ps1= connection.prepareStatement(query1);
        // The customer ID is the cart ID
        ps1.setString(1, customerID);
        rs=ps1.executeQuery();
        while(rs.next()) {
        	String productid=rs.getString(2);
        	int quantity=rs.getInt(3);
        	cartItems.put(productid, quantity);
        }
        return cartItems;
        
    }
    
    public void deleteItem(String productID , String customerID) throws SQLException {
        Connection connection =OrderManagementMain.connection;
        String query2 = "DELETE FROM cart_items WHERE CART_ID = ? AND PRODUCT_ID = ?";
		PreparedStatement preparedStatement2 = connection.prepareStatement(query2);
		preparedStatement2.setString(1, customerID);
		preparedStatement2.setString(2, productID);
        preparedStatement2.executeUpdate();
    }

    
    public void addItem(String productID, String customerID, int quantity) throws SQLException {
        Connection connection =OrderManagementMain.connection;        
	    int rowCount= updateItem(productID, customerID, quantity);
		if (rowCount == 0) {
			String sqlStatement = String.format("INSERT INTO CART_ITEMS (CART_ID,PRODUCT_ID, quantity) VALUES ('%s','%s', 1)",customerID,productID);
			PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement);
			preparedStatement.executeUpdate();
		}
    }
    
    public int updateItem(String productID, String customerID ,int quantity) throws SQLException {
        Connection connection = OrderManagementMain.connection;
        // exception to catch the error if the productid is wrong or the quantity is zero should be written.
        	String query4="UPDATE CART_ITEMS SET QUANTITY=? WHERE PRODUCT_ID=? AND CART_ID=?";
        	PreparedStatement ps4=connection.prepareStatement(query4);
        	ps4.setInt(1, quantity);
        	ps4.setString(2, productID);
        	ps4.setString(3, customerID);
        	return ps4.executeUpdate();      	
    }
   
    
    public void checkout(String customerID, LinkedHashMap<String, Integer> cartItems, double totalcost) throws SQLException{
    	
    	// UUID In java represents an immutable universally unique identifier, used for creating random filenames, transaction id etc.,
    	
    	String orderId= UUID.randomUUID().toString();
    	Connection connection= OrderManagementMain.connection;
    	
    	String query4="INSERT INTO CUSTOMER_ORDERS (CUSTOMER_ID,ORDER_ID,ORDER_TIME,TOTAL_COST) VALUES(?,?,?,?)";
    	PreparedStatement preparedStatement= connection.prepareStatement(query4);
    	preparedStatement.setString(1, customerID);
		preparedStatement.setString(2, orderId);
		preparedStatement.setString(3, Timestamp.from(Instant.now()).toString());
		preparedStatement.setDouble(4, totalcost);
    	preparedStatement.executeUpdate();
    	
    			String subqueryString= String.format("INSERT INTO ORDERS (ORDER_ID, PRODUCT_ID, PRODUCT_NAME, PRODUCT_PRICE, QUANTITY)"
    					+ " SELECT o.ORDER_ID, ct.PRODUCT_ID, ct.PRODUCT_NAME, ct.PRICE, c.quantity"
    					+ " FROM cart_items c"
    					+ " JOIN CUSTOMER_ORDERS o ON o.CUSTOMER_ID = c.CART_ID"
    					+ " JOIN PRODUCTS ct ON ct.PRODUCT_ID = c.PRODUCT_ID;");
    			preparedStatement= connection.prepareStatement(subqueryString);
    			preparedStatement.executeUpdate();
    	String query5="DELETE FROM CART_ITEMS WHERE CART_ID=?";
    	preparedStatement= connection.prepareStatement(query5);
    	preparedStatement.setString(1, customerID);
    	preparedStatement.executeUpdate();
    
    
    }

    
}
