package com.orderManagement.classMain;

import com.orderManagement.menu.*;

public class FactoryClass {

	public Menu getUserType(int choice) {
	       if(choice==1){
	            return CustomerMenu.getInstance();
	        }
	        else if(choice==2){
	            return new BusinessMenu();
	        }
		return null;
	}
}