package com.pahanaedu.service;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class CustomerService {
    private final CustomerDAO customerDAO;
    private final Validator validator;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // Add a new customer
    public String addCustomer(Customer customer) {
        // Validate customer object
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<Customer> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            customerDAO.addCustomer(customer);
            return "Customer added successfully with ID: " + customer.getId();
        } catch (IllegalArgumentException e) {
            throw e; // e.g., duplicate account number
        } catch (Exception e) {
            throw new RuntimeException("Failed to add customer: " + e.getMessage(), e);
        }
    }

    // Update an existing customer
    public String updateCustomer(Customer customer) {
        // Validate customer object
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<Customer> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            customerDAO.updateCustomer(customer);
            return "Customer updated successfully with ID: " + customer.getId();
        } catch (Exception e) {
            throw new RuntimeException("Failed to update customer: " + e.getMessage(), e);
        }
    }

    // Delete a customer by ID
    public String deleteCustomer(String customerId) {
        try {
            customerDAO.deleteCustomer(customerId);
            return "Customer deleted successfully with ID: " + customerId;
        } catch (IllegalArgumentException e) {
            throw e; // e.g., customer not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete customer: " + e.getMessage(), e);
        }
    }

    // Get a customer by ID
    public Customer getCustomerById(String customerId) {
        try {
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
            }
            return customer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customer: " + e.getMessage(), e);
        }
    }

    // Get a customer by account number
    public Customer getCustomerByAccountNumber(String accountNumber) {
        try {
            Customer customer = customerDAO.getCustomerByAccountNumber(accountNumber);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with account number " + accountNumber + " not found");
            }
            return customer;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customer: " + e.getMessage(), e);
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        try {
            return customerDAO.getAllCustomers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customers: " + e.getMessage(), e);
        }
    }
}