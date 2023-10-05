package com.orderManagement.cart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.orderManagement.classMain.Product;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/cart")
public class ShoppingCartAPI {
    String customerId="CUSTOMER1";
    ShoppingCart shoppingcart=new ShoppingCart(customerId);
    
    // viewing the cart
    @Path("/items")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CartProduct> printAllProducts() {
        return shoppingcart.printCartItems();
    }
    
    @Path("/items")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    
    //the map has product_id and customer_id
    //the cart_item has product_id and quantity
    // If we pass the JSON as product_id and customer_id
    public Response addItemtocart(CartProduct cartproduct){
    	try {
    		String pid=cartproduct.getProduct_id();
    		shoppingcart.addCartItem(pid,customerId);
    		return Response.status(Response.Status.CREATED).entity(cartproduct).build();
    	}
    	catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add the product").build();
        }
    }
    
    // calculate cost of the cart
    @Path("/totalcost")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    
    public double calculateCart() {
    	return shoppingcart.calculate();
    }
    
    //delete item from the cart
    
    
    @Path("/items/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteItemFromcart(CartProduct cartproduct)  {
    	try {
    	System.out.println("Product with "+cartproduct.getProduct_id()+" deleted");
    	shoppingcart.removeCartItem(cartproduct.getProduct_id(),customerId);
    	return Response.status(Response.Status.OK).entity(cartproduct.getProduct_id()).build();
    	}
    	catch(Exception e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Item not Found").build();
    	}
    }
   
    
    //Update the items in the cart
    
    @Path("/items")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response UpdateCart(CartProduct cartproduct) {
    	try {
//    		String pid=product.get("productid");
//    		int quantity=Integer.parseInt(product.get("quantity"));
//    		shoppingcart.updateItem(pid, customerId,quantity);
    		shoppingcart.updateItem(cartproduct.getProduct_id(), cartproduct.getCustomer_id(), cartproduct.getQuantity());
    		return Response.status(Response.Status.OK).entity(cartproduct.getProduct_id()).build();
    	}
    	catch(Exception e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Update").build();
    	}
    }
}
    