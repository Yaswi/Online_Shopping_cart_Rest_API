package com.orderManagement.cart;


import java.sql.SQLException;
import java.util.*;

import com.orderManagement.catalogue.Catalogue;
import com.orderManagement.classMain.OrderManagementMain;
import com.orderManagement.classMain.Product;
import com.orderManagement.exceptions.*;


public class ShoppingCart{
	
    ShoppingCartDAO cartDAO = new ShoppingCartDAO();
    Catalogue catalogue= Catalogue.getInstance();
    CartProduct cartproduct=new CartProduct();
    
    public LinkedHashMap<String,Integer> cart_items;
    
    ShoppingCart(){
    	
    }
    
    // Retrieving all the cart item details related to customerId
    // cart is storing productid, quantity
    public ShoppingCart(String customerId) {
    	try {
			cart_items=cartDAO.getAllItemsFromCart(customerId);
		} 
    	catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    };

    public void addCartItem(String productID,String customerId) throws ItemNotFound,SQLException{

    	if(!OrderManagementMain.products_list.containsKey(productID)) {
    		throw new ItemNotFound("The below with the id"+productID+"is not in the catalogue");
    	}

    	else if(cart_items.containsKey(productID)){
        	int quantity = cart_items.get(productID);
         	cart_items.put(productID, quantity+1);
         	cartDAO.addItem(productID, customerId, quantity+1);
        }
        else 
        {
        	cart_items.put(productID,1);
         	cartDAO.addItem(productID, customerId, 1);
        }
        //System.out.println(cart_items.get(id));
    }

    public void removeCartItem (String productID,String customerID) throws ItemNotFound, SQLException{
    	if(!cart_items.containsKey(productID)){
            throw new ItemNotFound("The below with the id"+productID+"is not in the cart");
        }
        int quantity=cart_items.get(productID);
        if(quantity==1){
        	cart_items.remove(productID);
        	cartDAO.deleteItem(productID,customerID);
        }
        else{
        	quantity=quantity-1;
            cart_items.put(productID,quantity);
            cartDAO.updateItem(productID, customerID, quantity);
        }
    }

    public void viewCartItems(){
    	if (cart_items.size()==0){
            System.out.println("The cart is Empty");
            return;
        }
    	// This is basically if the items are added to the cart, but removed by the business owner before checking out
    	//if not, print the cart items
        int count=1;
        ArrayList<String> missingItems = new ArrayList<>();
  
        for (String id : cart_items.keySet()){
            Product item = OrderManagementMain.products_list.get(id);
            if (item==null){
                missingItems.add(id);
            }
            else{
                int quantity = cart_items.get(id);
                System.out.println(count+"    "+id + "      "+ item.getName() +"       "+item.getPrice()+"       "+quantity);
                count++;
            }
        }
        if (missingItems.size() > 0){
            String result = String.join(", ", missingItems);
            System.out.println("Items with id : "+result + "are removed from the catalog by the Business Partner");
        }
        System.out.println("Total : "+this.calculate());
    }
    
    public List<CartProduct> printCartItems() {
    	List<CartProduct> cartitemslist=new ArrayList<>();
    	for(String id: cart_items.keySet()) {
    		CartProduct cartproduct=new CartProduct();
    		Product p=OrderManagementMain.products_list.get(id);
    		cartproduct.setProduct_id(p.getId());
    		cartproduct.setQuantity(cart_items.get(id));
    		cartitemslist.add(cartproduct);
    	}
    	return cartitemslist;
    }
    
    // printing the total cost of the cart
    public double calculate(){
        double cost=0.0;
        for(String id:cart_items.keySet()) {
        	Product product= Catalogue.products_list.get(id);
        	cost+=(product.getPrice())*(cart_items.get(id));
        }
        return cost;
    }
    
    // checking out the order
    public void checkout(String customerID) throws SQLException {
    	if (cart_items.size()==0){
            System.out.println("\nEMPTY!!!");
            return;
    	}
    	this.viewCartItems();
    	cartDAO.checkout(customerID, this.cart_items,this.calculate());
    	this.cart_items.clear();   	
    	
    }
    

}
