package com.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.dao.impl.CustomerDAO;
import com.demo.model.Customer;
import com.demo.service.CustomerFacade;

@Service
public class CustomerFacadeImpl implements CustomerFacade {

	@Autowired
	private CustomerDAO customerDao;

	public CustomerDAO getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDAO customerDao) {
		this.customerDao = customerDao;
	}

	public List<Customer> findAllCustomer() {
		return customerDao.findAll();
	}

	public Customer findCustomerById(String customerId) {
		return customerDao.findOne(customerId);
	}

	public boolean delCustomerById(String customerId) {
		return customerDao.deleteById(customerId);
	}

	public boolean updateCustomer(Customer customer) {
		customerDao.update(customer);
		
		return true;
	}

	public boolean createCustomer(Customer customer) {
		customerDao.insert(customer);
		
		return true;
	}

	public Customer findCustomerByEmail(String email) {
		return customerDao.findCustomerByEmail(email);
	}
}
