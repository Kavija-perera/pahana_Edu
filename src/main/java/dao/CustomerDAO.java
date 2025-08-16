package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final String filePath;

    public CustomerDAO(String filePath) {
        this.filePath = filePath;
    }

    public List<Customer> getAllCustomers() throws IOException {
        List<Customer> customers = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return customers;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 5) {
                    String accountNumber = data[0];
                    String name = data[1];
                    String address = data[2];
                    String phone = data[3];
                    int units = Integer.parseInt(data[4]);
                    customers.add(new Customer(accountNumber, name, address, phone, units));
                }
            }
        }
        return customers;
    }

    public void addCustomer(Customer customer) throws IOException {
        List<Customer> customers = getAllCustomers();
        customers.add(customer);
        saveCustomers(customers);
    }

    public void updateCustomer(Customer updatedCustomer) throws IOException {
        List<Customer> customers = getAllCustomers();
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getAccountNumber().equals(updatedCustomer.getAccountNumber())) {
                customers.set(i, updatedCustomer);
                break;
            }
        }
        saveCustomers(customers);
    }

    public Customer getCustomerByAccountNumber(String accountNumber) throws IOException {
        List<Customer> customers = getAllCustomers();
        for (Customer c : customers) {
            if (c.getAccountNumber().equals(accountNumber)) {
                return c;
            }
        }
        return null;
    }

    private void saveCustomers(List<Customer> customers) throws IOException {
        try (FileWriter fw = new FileWriter(filePath)) {
            for (Customer c : customers) {
                fw.write(c.getAccountNumber() + "," + c.getName() + "," + c.getAddress() + "," + c.getPhone() + "," + c.getUnitsConsumed() + "\n");
            }
        }
    }
}