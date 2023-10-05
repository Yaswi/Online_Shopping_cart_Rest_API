package com.orderManagement.menu;

import java.sql.SQLException;
import java.util.*;
import com.orderManagement.catalogue.*;
import com.orderManagement.classMain.*;
import com.orderManagement.exceptions.*;

enum BusinessMenuOptions{
	VIEW_CATALOGUE,
	ADD_ITEMS_TO_CATALOGUE,
	UPDATE_ITEMS_IN_CATALOGUE,
	REMOVE_ITEMS_FROM_CATALOGUE,
	VIEW_ORDERS,
	EXIT;
}

public class BusinessMenu implements Menu{
            public void displayMenu() throws SQLException{
            Scanner sc=new Scanner(System.in);
            while(true){
            System.out.println("Welcome to the Menu Business partner");
            System.out.println("Enter your choice");
            System.out.println("1. View the Catalogue");
            System.out.println("2. Add Items to the catalogue");
            System.out.println("3. Update Item in the catalogue");
            System.out.println("4. Remove items from the catalogue");
            System.out.println("5. View Orders");
            System.out.println("6. Exit");
            int input2=sc.nextInt();
            // The userFactory.getObj basically gets a generic object
            // To convert that generic object to a specific required object,
            // we do the (class) Otherclass.method();
            // Catalogue c=(Catalogue)UserFactory.getObj(choice);
            Catalogue c= Catalogue.getInstance();
            BusinessMenuOptions menuOptions =  BusinessMenuOptions.values()[input2-1];
            switch(menuOptions){
                case VIEW_CATALOGUE:
                    c.displayCatalogue();
                    break;
                    
                case ADD_ITEMS_TO_CATALOGUE:
                    System.out.println("Please Enter the id, name and price of the product");
                    String id=sc.next();
                    String name= sc.next();
                    double price=sc.nextDouble();
                    try{
                        Product p= new Product(id,name, price);
                        c.addItem(p);
                    }
                    catch(ItemAlreadyExists i){
                        System.out.println("The item with the id "+id+" already exists");
                    }
                    break;
                    
                case UPDATE_ITEMS_IN_CATALOGUE:
                	System.out.println("Please Enter the product ID");
                	String product_id=sc.next();
                	Product product=c.getProduct(product_id);
                	if(product!=null) {
                		try {
                		System.out.println("Enter the new name for the product");
                		name=sc.next();
                		System.out.println("Enter the new price for the product");
                		price=sc.nextDouble();
                		product.setName(name);
                		product.setPrice(price);
                		c.updateItem(product);
                	}
                	catch (ItemNotFound i) {	
                			System.out.println("The item with the id "+product_id+"is not found");
                		}
                	}
                	else {
                		System.out.println("The following item doesnt exist");
                	}
                	break;
                	
                case REMOVE_ITEMS_FROM_CATALOGUE:
                    System.out.println("Please Enter the product ID");
                    product_id=sc.next();
                    try{
                            c.deleteItem(product_id);
                        }
                    catch(ItemNotFound i)
                        {
                            System.out.println("The item with the product id"+ product_id+" is not found");
                        }
                    break;
                    
                case VIEW_ORDERS:
//                	for(ShoppingCart order:OrderManagementMain.orders) {
//                		order.viewCartItems();
//                	}
                	System.out.println("Please enter your customer ID");
                	String customerID=sc.next();
                	c.displayOrders(customerID);
                	break;
                	
                case EXIT:
                	System.out.println("Exiting the Business Menu");
                	return;
                	
                default:
                    System.out.println("Please enter the correct choice");
                    break;

            }
        }
    
    }
}
