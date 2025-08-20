package com.pahanaedu.service;

import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class ItemService {
    private final ItemDAO itemDAO;
    private final Validator validator;

    public ItemService() {
        this.itemDAO = new ItemDAO();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // Add a new item
    public String addItem(Item item) {
        // Validate item object
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<Item> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            itemDAO.addItem(item);
            return "Item added successfully with ID: " + item.getId();
        } catch (IllegalArgumentException e) {
            throw e; // e.g., duplicate item ID
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item: " + e.getMessage(), e);
        }
    }

    // Update an existing item
    public String updateItem(Item item) {
        // Validate item object
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<Item> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            itemDAO.updateItem(item);
            return "Item updated successfully with ID: " + item.getId();
        } catch (IllegalArgumentException e) {
            throw e; // e.g., item not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to update item: " + e.getMessage(), e);
        }
    }

    // Delete an item by ID
    public String deleteItem(String itemId) {
        try {
            itemDAO.deleteItem(itemId);
            return "Item deleted successfully with ID: " + itemId;
        } catch (IllegalArgumentException e) {
            throw e; // e.g., item not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete item: " + e.getMessage(), e);
        }
    }

    // Get an item by ID
    public Item getItemById(String itemId) {
        try {
            Item item = itemDAO.getItemById(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Item with ID " + itemId + " not found");
            }
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve item: " + e.getMessage(), e);
        }
    }

    // Get an item by itemId
    public Item getItemByItemId(String itemId) {
        try {
            Item item = itemDAO.getItemByItemId(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Item with item ID " + itemId + " not found");
            }
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve item: " + e.getMessage(), e);
        }
    }

    // Get all items
    public List<Item> getAllItems() {
        try {
            return itemDAO.getAllItems();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve items: " + e.getMessage(), e);
        }
    }
}