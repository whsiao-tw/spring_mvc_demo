package com.demo.data;

public class DemoErrorMessage {
	
	public enum ErrorCode {

		BadParam("Invalid parameter"),
		CustomerExist("Customer_Already_Exist"),
		CustomerNotExist("Customer_Not_Exist");
		
		private String name;       

	    private ErrorCode(String s) {
	        this.name = s;
	    }

	    public String toString() {
	        return this.name;
	    }
	    
	    public boolean equalsName(String otherName) {
	        return (otherName == null) ? false : name.equals(otherName);
	    }
	}
}
