package com.orderManagement.classMain;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.orderManagement.cart.CartProduct;
import com.orderManagement.cart.ShoppingCart;
import com.orderManagement.catalogue.*;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("")
public class MyResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     * @throws java.sql.SQLException 
     */

    
    //the map has product_id and customer_id
    //the cart_item has product_id and quantity
    // If we pass the JSON as product_id and customer_id
    }
