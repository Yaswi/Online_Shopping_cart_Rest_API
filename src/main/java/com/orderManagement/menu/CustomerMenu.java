package com.orderManagement.menu;

import java.sql.SQLException;
import java.util.*;
import com.orderManagement.cart.*;
import com.orderManagement.exceptions.*;
import com.orderManagement.catalogue.*;
import com.orderManagement.classMain.*;

enum MenuOptions{
	VIEW_CATALOGUE,
	ADD_ITEMS_TO_CART,
	REMOVE_ITEMS_FROM_CART,
	VIEW_CART,
	CALCULATE_COST,
	CHECKOUT_ORDER,
	EXIT;
}

public class CustomerMenu implements Menu{
	
	public static ArrayList<ShoppingCart> orders=new ArrayList<ShoppingCart>();
	
	// creating a singleton pattern for the customerMenu to avoid creating the same user multiple times.
	
	String customerID="CUSTOMER1";
	
	private static CustomerMenu instance=null;
	
	CustomerMenu(){
		
	}
	
	//checking if there is an active instance, if not return new instance. To preserve my cart items
    public static CustomerMenu getInstance() {
        if (instance == null) {
            instance = new CustomerMenu();
        }
        return instance;
    }

    ShoppingCart shopcart=new ShoppingCart(customerID);
	
    public void displayMenu() throws SQLException, ItemAlreadyExists{
    	
        Scanner sc=new Scanner(System.in);
        
        while(true){
            System.out.println("Welcome to the menu");
            System.out.println("Enter your choice");
            System.out.println("1. View the Products Catalog");
            System.out.println("2. Add Items to the cart");
            System.out.println("3. Remove items from the cart");
            System.out.println("4. View the products in the cart");
            System.out.println("5. View the total cost of products in the shopping cart");
            System.out.println("6. Checkout the order");
            System.out.println("7. Update the product in the cart");
            System.out.println("7. Exit");
            System.out.println("Enter your choice");
            int input1= sc.nextInt();
            //ShoppingCart shopcart=(ShoppingCart)UserFactory.getObj(choice);
            
            Catalogue c=Catalogue.getInstance();
            // Enum usage
            
            MenuOptions menuOptions =  MenuOptions.values()[input1-1];
        
            switch(menuOptions){
                case VIEW_CATALOGUE:	
                    c.displayCatalogue();
                    break;
                    
                case ADD_ITEMS_TO_CART:
                	
                	c.displayCatalogue();
                    System.out.println("Enter the product ID");
                    String product_id=sc.next();
                    Product product= OrderManagementMain.products_list.get(product_id);
                    //System.out.println(product);
						try {
							shopcart.addCartItem(product_id,customerID);
						}
						catch (ItemNotFound e) {
							System.out.println("The item "+product_id+" is not in the catalogue");
						}
                    
                    break;
                    
                case REMOVE_ITEMS_FROM_CART:
                	shopcart.viewCartItems();
                	List<String> items=new ArrayList<>(shopcart.cart_items.keySet());
                	if(items.size()==0) {
                		System.out.println("The cart is empty");
                		return;
                	}
                    System.out.println("Enter the product ID that you want to update");
                    product_id= sc.next();
                    try{
                        shopcart.removeCartItem(product_id,customerID);
                    }
                    catch(ItemNotFound inf){
                        System.out.println("The item "+product_id+" is not in the cart to remove");
                    }
                    break;
                    
                case VIEW_CART:
                    shopcart.viewCartItems();
                    break;
                    
                case CALCULATE_COST:
                	System.out.println("The total cost of the order/products in the cart:");
                    System.out.println(shopcart.calculate());
                    break;
                    
                case CHECKOUT_ORDER:
                	System.out.println("Checking out the orders");
                	shopcart.checkout(customerID);
                    break;
                    
                case EXIT:
                	System.out.println("Returning to the Previous Menu");
                    return;
                    
                default:
                    System.out.println("Please enter the correct choice");
            }
        }
    }
}

