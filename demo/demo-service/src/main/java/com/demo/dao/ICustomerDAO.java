package com.demo.dao;

import com.demo.model.Customer;

public interface ICustomerDAO {
	
	public boolean updateCustomer(Customer customer);
	
	public Customer findCustomerByEmail(String email);
}
