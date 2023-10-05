package com.orderManagement.classMain;


import java.sql.*;
import java.util.*;
import com.orderManagement.catalogue.*;
import com.orderManagement.menu.*;
import com.orderManagement.cart.*;
import com.orderManagement.exceptions.*;

public class OrderManagementMain {
	
	public static LinkedHashMap<String,Product>products_list=new LinkedHashMap<>();
	public static List<ShoppingCart> orders=new ArrayList<>();
	public static Connection connection= DbConnection.createConnection();
	
	public static void main(String[] args) {
        	Catalogue c=Catalogue.getInstance();
        	try {
				c.addingDatatoMap();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    Scanner sc=new Scanner(System.in);
		    while(true){
		    System.out.println("Welcome to the Menu");
		    System.out.println("1. Customer");
		    System.out.println("2. Business owner");
		    System.out.println("3. Exit");
		    System.out.println("Please Enter the type of User you are");
		    int choice=sc.nextInt();

		        if (choice==3){
		            System.out.println("Exiting the program");
		            break;
		        }
		        else if(choice==1 || choice==2){
		            FactoryClass f=new FactoryClass();
		            Menu user=f.getUserType(choice);  
					try {
						user.displayMenu();
					} catch (SQLException | ItemAlreadyExists e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        else {
		        	System.out.println("Invalid option");
		        }
		    }
			if (connection != null) {
			     try {
			            connection.close();
			        }
			     catch (SQLException e) {
			            e.printStackTrace();
			        }
			    }
	}

}




