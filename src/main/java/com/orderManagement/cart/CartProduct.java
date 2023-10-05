package com.orderManagement.cart;

public class CartProduct {
	
	private int quantity;
	private String product_id;
	private String customer_id;
	
	public CartProduct() {
    	
    }

//    public CartProduct(String product_id,String customer_id){
//        this.product_id=product_id;
//        this.customer_id=customer_id;
//    }
	
	@Override
	public String toString() {
		return "CartProduct [quantity=" + quantity + ", product_id=" + product_id + ", customer_id=" + customer_id
				+ "]";
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

}
