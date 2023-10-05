package com.orderManagement.classMain;

public class Product{
    @Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	private String id;
    private String name;
    private double price;
    public Product() {
    	
    }

    public Product(String id,String name,double price){
        this.id=id;
        this.name=name;
        this.price=price;
    }
    //getter functions
    // to get the id
    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    // to get the price
    public double getPrice(){
        return price;
    }
    
    //setter functions
    public void setId(String id) {
    	this.id=id;
    }
    public void setName(String name) {
    	this.name=name;
    }
    public void setPrice(double price) {
    	this.price=price;
    }
}

