package com.orderManagement.catalogue;

import java.sql.*;
import java.util.*;

import com.orderManagement.classMain.OrderManagementMain;

public class OrderDAO {
	
	//printing all the orders in the table for a particular customer
	
    ArrayList<String> orders = new ArrayList<>();
    
    public void getAllOrders(String customerID) throws SQLException{
    	ResultSet subrSet=null;
    	ResultSet rSet=null;
    	PreparedStatement pStatement=null;
        Connection connection =OrderManagementMain.connection;
        
        String query="SELECT ORDER_ID FROM CUSTOMER_ORDERS WHERE CUSTOMER_ID=?";
        
        pStatement= connection.prepareStatement(query);
        pStatement.setString(1, customerID);
        rSet=pStatement.executeQuery();
        
        // storing the order ID in a list and travesing the order_id 
        
        while(rSet.next()) {
        	String orderid=rSet.getString(1);
        	orders.add(orderid);
        }
        
        for(String orderID: orders) {
        	
        	String subquery="SELECT * FROM ORDERS WHERE ORDER_ID=?";
        	
        	PreparedStatement ps= connection.prepareStatement(subquery);
        	
        	ps.setString(1, orderID);
        	
        	subrSet=ps.executeQuery();
        	
        	while(subrSet.next()) {
        		String pname=subrSet.getString(3);
        		double productprice=subrSet.getDouble(4);
        		int quantity= subrSet.getInt(5);
        		System.out.println(pname+"  "+productprice+"  "+quantity);
        	}
        	
        }
        
        
    }
    
}
