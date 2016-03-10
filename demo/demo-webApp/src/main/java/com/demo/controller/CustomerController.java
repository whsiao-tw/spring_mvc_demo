package com.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.demo.service.CustomerFacade;
import com.demo.data.DemoErrorMessage;
import com.demo.data.DemoGenericException;
import com.demo.form.CustomerForm;
import com.demo.model.Customer;

@Controller
public class CustomerController {

	final static private Logger log = Logger.getLogger(CustomerController.class);
	
	@Autowired
	protected CustomerFacade customerService;

	public CustomerFacade getCustomerService() {
		return this.customerService;
	}

	public void setCustomerService(CustomerFacade customerService) {
		this.customerService = customerService;
	}
	
	// Customer page.
	@RequestMapping(value = Views.actionUser_CustomerPage, method = RequestMethod.GET)
	public ModelAndView UserPage() {
		
		ModelAndView mv = new ModelAndView("customer");
		
		return mv; 
	}
	
	// Retrieve all Customer.
	@RequestMapping(value = Views.actionUser_CustomerAllProfile, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetCustomers() throws DemoGenericException {
		log.info(Views.actionUser_CustomerAllProfile);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<Customer> customers = customerService.findAllCustomer();

		result.put("result", "success");
		result.put("customers", customers);
		
		return result;
	}
	
	// CRUD : Create
	@RequestMapping(value = Views.actionUser_CustomerPage, method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> CreateCustomer(@RequestBody @Valid CustomerForm customerForm,
			BindingResult bindingResults, Model uiModel) throws DemoGenericException {
		log.info(Views.actionUser_CustomerProfile);
		
		if(bindingResults.hasErrors()) throw new DemoGenericException(DemoErrorMessage.ErrorCode.BadParam);

		Customer customer = customerService.findCustomerByEmail(customerForm.getEmail());

		if (null != customer) throw new DemoGenericException(DemoErrorMessage.ErrorCode.CustomerExist);
		
		customer = new Customer();
		customer.setName(customerForm.getName());
		customer.setAddress(customerForm.getAddress());
		customer.setPhone(customerForm.getPhone());
		customer.setEmail(customerForm.getEmail());
		boolean done = customerService.createCustomer(customer);
		
		// customer id?
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (done) result.put("result", "success");
		else result.put("result", "fail");
		
		return result;
	}
	
	// CRUD : Read
	@RequestMapping(value = Views.actionUser_CustomerProfile, method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> GetCustomer(@PathVariable("customerId") String customerId) throws DemoGenericException {
		log.info(Views.actionUser_CustomerProfile);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Customer customer = customerService.findCustomerById(customerId);

		if (null == customer) throw new DemoGenericException(DemoErrorMessage.ErrorCode.CustomerNotExist);
		
		result.put("result", "success");
		result.put("customer", customer);
		
		return result;
	}
	
	// CRUD : Update
	@RequestMapping(value = Views.actionUser_CustomerProfile, method = RequestMethod.PUT)
	@ResponseBody
	public Map<String, Object> UpdateCustomer(@PathVariable("customerId") String customerId, @RequestBody @Valid CustomerForm customerForm,
			BindingResult bindingResults, Model uiModel) throws DemoGenericException {
		log.info(Views.actionUser_CustomerProfile);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(bindingResults.hasErrors()) throw new DemoGenericException(DemoErrorMessage.ErrorCode.BadParam);

		Customer customer = customerService.findCustomerById(customerId);

		if (null == customer) throw new DemoGenericException(DemoErrorMessage.ErrorCode.CustomerNotExist);
		
		customer.setAddress(customerForm.getAddress());
		customer.setEmail(customerForm.getEmail());
		customer.setName(customerForm.getName());
		customer.setPhone(customerForm.getPhone());

		boolean done = customerService.updateCustomer(customer);
		
		if (done) result.put("result", "success");
		result.put("result", "fail");
		
		return result;
	}
	
	// CRUD : Delete
	@RequestMapping(value = Views.actionUser_CustomerProfile, method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> DeleteCustomer(@PathVariable("customerId") String customerId) throws DemoGenericException {
		log.info(Views.actionUser_CustomerProfile);
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		Customer customer = customerService.findCustomerById(customerId);

		if (null == customer) throw new DemoGenericException(DemoErrorMessage.ErrorCode.CustomerNotExist);
		
		boolean done =  customerService.delCustomerById(customerId);
		
		if (done) result.put("result", "success");
		result.put("result", "fail");
		
		return result;
	}
}
