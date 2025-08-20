package com.pahanaedu.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.pahanaedu.config.MongoDBConfig;
import com.pahanaedu.model.Customer;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final MongoCollection<Document> customers;

    public CustomerDAO() {
        MongoDatabase database = MongoDBConfig.getDatabase();
        this.customers = database.getCollection("customers");
    }

    // Add a new customer
    public void addCustomer(Customer customer) {
        // Check for duplicate account number
        Document existingCustomer = customers.find(Filters.eq("accountNumber", customer.getAccountNumber())).first();
        if (existingCustomer != null) {
            throw new IllegalArgumentException("Customer with account number " + customer.getAccountNumber() + " already exists");
        }

        Document doc = new Document()
                .append("accountNumber", customer.getAccountNumber())
                .append("name", customer.getName())
                .append("address", customer.getAddress())
                .append("telephoneNumber", customer.getTelephoneNumber())
                .append("unitsConsumed", customer.getUnitsConsumed());

        try {
            customers.insertOne(doc);
            customer.setId(doc.getObjectId("_id").toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to add customer: " + e.getMessage(), e);
        }
    }

    // Update an existing customer
    public void updateCustomer(Customer customer) {
        Document doc = new Document()
                .append("accountNumber", customer.getAccountNumber())
                .append("name", customer.getName())
                .append("address", customer.getAddress())
                .append("telephoneNumber", customer.getTelephoneNumber())
                .append("unitsConsumed", customer.getUnitsConsumed());

        try {
            customers.replaceOne(Filters.eq("_id", new ObjectId(customer.getId())), doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update customer: " + e.getMessage(), e);
        }
    }

    // Delete a customer by ID
    public void deleteCustomer(String customerId) {
        try {
            Document result = customers.findOneAndDelete(Filters.eq("_id", new ObjectId(customerId)));
            if (result == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete customer: " + e.getMessage(), e);
        }
    }

    // Get a customer by ID
    public Customer getCustomerById(String customerId) {
        try {
            Document doc = customers.find(Filters.eq("_id", new ObjectId(customerId))).first();
            if (doc == null) {
                return null;
            }
            return documentToCustomer(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customer: " + e.getMessage(), e);
        }
    }

    // Get a customer by account number
    public Customer getCustomerByAccountNumber(String accountNumber) {
        try {
            Document doc = customers.find(Filters.eq("accountNumber", accountNumber)).first();
            if (doc == null) {
                return null;
            }
            return documentToCustomer(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customer: " + e.getMessage(), e);
        }
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        try {
            for (Document doc : customers.find()) {
                customerList.add(documentToCustomer(doc));
            }
            return customerList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve customers: " + e.getMessage(), e);
        }
    }

    // Helper method to convert Document to Customer
    private Customer documentToCustomer(Document doc) {
        Customer customer = new Customer();
        customer.setId(doc.getObjectId("_id").toString());
        customer.setAccountNumber(doc.getString("accountNumber"));
        customer.setName(doc.getString("name"));
        customer.setAddress(doc.getString("address"));
        customer.setTelephoneNumber(doc.getString("telephoneNumber"));
        customer.setUnitsConsumed(doc.getDouble("unitsConsumed") != null ? doc.getDouble("unitsConsumed") : 0.0);
        return customer;
    }
}