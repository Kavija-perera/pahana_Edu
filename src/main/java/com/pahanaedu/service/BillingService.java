package com.pahanaedu.service;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Customer;
import com.pahanaedu.model.Item;

public class BillingService {
    private final CustomerDAO customerDAO;
    private final ItemDAO itemDAO;

    public BillingService() {
        this.customerDAO = new CustomerDAO();
        this.itemDAO = new ItemDAO();
    }

    // Calculate bill for a customer and item
    public BillDetails calculateBill(String customerId, String itemId, double unitsConsumed) {
        try {
            // Retrieve customer and item
            Customer customer = customerDAO.getCustomerById(customerId);
            if (customer == null) {
                throw new IllegalArgumentException("Customer with ID " + customerId + " not found");
            }

            Item item = itemDAO.getItemById(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Item with ID " + itemId + " not found");
            }

            // Validate units consumed
            if (unitsConsumed <= 0) {
                throw new IllegalArgumentException("Units consumed must be greater than zero");
            }

            // Check stock availability
            if (item.getStock() < unitsConsumed) {
                throw new IllegalArgumentException("Insufficient stock for item " + item.getName() + ". Available: " + item.getStock());
            }

            // Calculate bill amount
            double billAmount = unitsConsumed * item.getPrice();

            // Update customer units consumed
            customer.setUnitsConsumed(customer.getUnitsConsumed() + unitsConsumed);
            customerDAO.updateCustomer(customer);

            // Update item stock
            item.setStock(item.getStock() - (int) unitsConsumed);
            itemDAO.updateItem(item);

            // Return bill details
            return new BillDetails(customer, item, unitsConsumed, billAmount);
        } catch (IllegalArgumentException e) {
            throw e; // Validation or not found errors
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate bill: " + e.getMessage(), e);
        }
    }

    // Inner class to hold bill details
    public static class BillDetails {
        private final Customer customer;
        private final Item item;
        private final double unitsConsumed;
        private final double billAmount;

        public BillDetails(Customer customer, Item item, double unitsConsumed, double billAmount) {
            this.customer = customer;
            this.item = item;
            this.unitsConsumed = unitsConsumed;
            this.billAmount = billAmount;
        }

        // Getters
        public Customer getCustomer() {
            return customer;
        }

        public Item getItem() {
            return item;
        }

        public double getUnitsConsumed() {
            return unitsConsumed;
        }

        public double getBillAmount() {
            return billAmount;
        }
    }
}