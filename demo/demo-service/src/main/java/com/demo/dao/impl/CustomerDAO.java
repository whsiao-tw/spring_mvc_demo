package com.demo.dao.impl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.IBaseDAO;
import com.demo.dao.ICustomerDAO;
import com.demo.model.Customer;

@Transactional
@Repository 
public class CustomerDAO  extends IBaseDAO<Customer> implements ICustomerDAO {

	public CustomerDAO() {
		super();
		setClazz(Customer.class);
	}

	public boolean updateCustomer(Customer customer) {
		update(customer);
		
		return true;
	}

	public Customer findCustomerByEmail(String email) {
		return mongoTemplate.findOne(new Query(where("mail").is(email)), getClazz());
	}
}
