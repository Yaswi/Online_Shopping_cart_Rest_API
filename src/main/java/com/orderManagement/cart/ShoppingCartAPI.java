package com.orderManagement.cart;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.orderManagement.classMain.Product;
import com.orderManagement.exceptions.ItemNotFound;

import jakarta.ws.rs.Consumes;
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
    
    // adding item to the cart
    @Path("/items")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response addItemtocart(CartProduct cartproduct){
    	try {
    		String pid=cartproduct.getProduct_id();
    		shoppingcart.addCartItem(pid,customerId);
    		return Response.status(Response.Status.CREATED).entity(cartproduct).build();
    	}
    	catch(ItemNotFound e) {
    		return Response.status(Response.Status.NOT_FOUND).entity("The item with id"+cartproduct.getProduct_id()+"is not found").build();
    	}
    	catch(SQLException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Item not Found").build();
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
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteItemFromcart(CartProduct cartproduct)  {
    	try {
    	shoppingcart.removeCartItem(cartproduct.getProduct_id(),customerId);
    	System.out.println("Product with "+cartproduct.getProduct_id()+" deleted");
    	return Response.status(Response.Status.OK).entity(cartproduct.getProduct_id()).build();
    	}
    	catch(ItemNotFound e) {
    		return Response.status(Response.Status.NOT_FOUND).entity("The item with id"+cartproduct.getProduct_id()+"is not found").build();
    	}
    	catch(SQLException e) {
    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Item not Found").build();
    	}
    }
   
    
//    //Update the items in the cart
//    
//    @Path("/items")
//    @PUT	
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response UpdateCart(CartProduct cartproduct) {
//    	try {
//    		shoppingcart.updateItem(cartproduct.getProduct_id(), cartproduct.getCustomer_id(), cartproduct.getQuantity());
//    		return Response.status(Response.Status.OK).entity(cartproduct.getProduct_id()).build();
//    	}
//    	catch(Exception e) {
//    		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to Update").build();
//    	}
//    }
}
    