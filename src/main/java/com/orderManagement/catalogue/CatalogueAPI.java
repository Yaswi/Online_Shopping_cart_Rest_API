package com.orderManagement.catalogue;


import java.sql.SQLException;
import java.util.List;

import com.orderManagement.classMain.Product;
import com.orderManagement.exceptions.ItemAlreadyExists;
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

@Path("/catalogue")
public class CatalogueAPI {
    Catalogue catalogue = Catalogue.getInstance();
    @Path("/Products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> getAllProducts() {
        return catalogue.displayCatalogue();
    }

    @Path("/Products/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduct(@PathParam("id") String id){
    	Product product=catalogue.getProduct(id);
    	if(product!=null) {
    		return Response.status(Response.Status.OK).entity(product).build();
    	}
        return Response.status(Response.Status.NOT_FOUND).entity("The product doesnt exist").build();
    }

    @Path("/Products")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(Product product) throws SQLException, ItemAlreadyExists{
    	try {
    	 catalogue.addItem(product);
    	 return Response.status(Response.Status.CREATED).entity(product).build();
    	}
    	catch(ItemAlreadyExists e) {
    		return Response.status(Response.Status.CONFLICT).entity("The item is already in catalogue").build();
    	}
    	catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to add the product").build();
        }
    	
    }
    
    @Path("/Products/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteProduct(@PathParam("id") String id) throws ItemNotFound, SQLException  {
    	try {
    	catalogue.deleteItem(id);
    	return Response.status(Response.Status.OK).entity("The item with the id "+id+" is deleted successfully").build();
    	}
    	catch(ItemNotFound e) {
    		return Response.status(Response.Status.NOT_FOUND).entity("The item with id "+id+" is not found").build();
    	}
    	catch(SQLException e) {
    		 return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to delete the product").build();
    	}
    }
    
    @Path("/Products")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateProduct(Product product) {
    	try {
    		catalogue.updateItem(product);
    		return Response.status(Response.Status.CREATED).entity(product).build();
    	}
    	catch(Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Failed to update the product").build();

    	}
    }
}