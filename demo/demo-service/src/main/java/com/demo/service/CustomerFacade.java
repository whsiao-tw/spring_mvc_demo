package com.demo.service;

import java.util.List;

import com.demo.model.Customer;

public interface CustomerFacade {

	List<Customer> findAllCustomer();

	Customer findCustomerById(String customerId);

	boolean delCustomerById(String customerId);
	
	boolean updateCustomer(Customer customer);

	boolean createCustomer(Customer customer);

	Customer findCustomerByEmail(String email);
}
