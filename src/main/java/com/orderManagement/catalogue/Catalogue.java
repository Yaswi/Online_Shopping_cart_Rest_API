package com.orderManagement.catalogue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import com.orderManagement.classMain.*;
import com.orderManagement.exceptions.*;

public class Catalogue{

    public static LinkedHashMap<String,Product> products_list=OrderManagementMain.products_list;
    
    private static Catalogue instance = null;
    CatalogueDAO catalogDAO = new CatalogueDAO();

    // Singleton Design pattern
    private Catalogue() {
    	try {
			this.addingDatatoMap();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static Catalogue getInstance() {
        if (instance == null) {
            instance = new Catalogue();
        }
        return instance;
    }
    
    public void initialize () throws SQLException{
        products_list = catalogDAO.getAllItemsFromDb();
    }
    
    public Product getProduct(String Product_id) {
    	return products_list.get(Product_id);
    }

    //adding the database values to the products_list
    public void addingDatatoMap() throws SQLException {
    		ResultSet rs1=null;
    		Connection con= OrderManagementMain.connection;
    		if(con!=null) {
    			try {
    				String sqlStatement = "SELECT * FROM PRODUCTS";
    				PreparedStatement preparedStatement = con.prepareStatement(sqlStatement);
    				rs1 = preparedStatement.executeQuery();
    				while (rs1.next()) {
    					String pid=rs1.getString(1);
    					System.out.println(pid+"meow");
    					String pname=rs1.getString(2);
    					double price=rs1.getDouble(3);
    					Product p=new Product(pid,pname, price);
    					products_list.put(pid,p);
    				}
    			}
    			catch (SQLException e) {
    				// TODO: handle exception
    				e.printStackTrace();
    			}
    		}
    	}
    
    // Printing all the values
    public List<Product> displayCatalogue(){
        // using this object to access the global linkedhashmap.
    	List<Product> product_list=new ArrayList<>();
        for(Map.Entry<String,Product> each_entry: products_list.entrySet()){
            String id=each_entry.getKey();
            Product p=each_entry.getValue();
            String ID= id;  
            String productname=p.getName();
            double productprice= p.getPrice();
            product_list.add(p);
            System.out.println(ID+" "+productname+" "+productprice);
         }
        return product_list;
    }
    
    public void updateItem(Product p) throws SQLException,ItemNotFound{
        catalogDAO.updateIteminDb(p);
    }
    
    // adding the item to the catalogue
    public void addItem(Product p) throws ItemAlreadyExists, SQLException{
        String id= p.getId();
        if(products_list.containsKey(id)){
            throw new ItemAlreadyExists("The item"+p.getName()+"that you're trying to add already exists");
        }
       // LinkedHashMap<String,Product> products_list=OrderManagement.products_list;
        products_list.put(id,p);
        catalogDAO.addItemtoDb(p);
    }
    
    // deleting the item from catalogue
    public void deleteItem(String id) throws ItemNotFound, SQLException{
        //LinkedHashMap<String,Product> products_list=OrderManagement.products_list;
        if(!products_list.containsKey(id)){
            throw new ItemNotFound("The item with the id"+id+"is not available in the catalogue");
        }
        products_list.remove(id);
        catalogDAO.deleteItemfromDB(id);
    }
    
    //displaying all the orders associating with the customer
    public void displayOrders(String customerId) {
    	OrderDAO orderDAO=new OrderDAO();
    	try {
    		orderDAO.getAllOrders(customerId);
    	}
    	catch (Exception e) {
			e.printStackTrace();
		}
    }
}
