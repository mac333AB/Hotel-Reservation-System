package service;

import java.util.*;

import models.Customer;

public class CustomerService {
	
	private static Map<String, Customer> customers = new HashMap<>();
	private static CustomerService customerService = new CustomerService();
	
	// adding dummy data
	static {
		customers.put("abc@mail.com", new Customer("abc", "abc", "abc@mail.com"));
		customers.put("xyz@mail.com", new Customer("xyz", "xyz", "xyz@mail.com"));
		customers.put("abhi@mail.com", new Customer("abhi", "abhi", "abhi@mail.com"));
	}
	
	
	
	
	
	
	
	public void addCustomer(String email, String firstname, String lastName) {
		
		if (customers.containsKey(email)) {
			throw new IllegalArgumentException("Customer with this email already exists. ");
		}
		customers.put(email, new Customer(firstname, lastName,email));
		
	}

	public Customer getCustomer(String customerEmail) {
		
		if (!customers.containsKey(customerEmail)) {
			throw new IllegalArgumentException("No customer found with this email.");
		}
		return customers.get(customerEmail);
		
	}
	
	
	public Collection<Customer> getAllCustomers(){
		if (customers.isEmpty()) {
			throw new IllegalArgumentException("No customers found.");
		}
		return customers.values();
		
	}

	
	public static CustomerService getInstance() {
		return customerService;
	}
	

	private CustomerService() {}
}
