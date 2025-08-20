package com.pahanaedu.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.pahanaedu.config.MongoDBConfig;
import com.pahanaedu.model.Item;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private final MongoCollection<Document> items;

    public ItemDAO() {
        MongoDatabase database = MongoDBConfig.getDatabase();
        this.items = database.getCollection("items");
    }

    // Add a new item
    public void addItem(Item item) {
        // Check for duplicate item ID
        Document existingItem = items.find(Filters.eq("itemId", item.getItemId())).first();
        if (existingItem != null) {
            throw new IllegalArgumentException("Item with ID " + item.getItemId() + " already exists");
        }

        Document doc = new Document()
                .append("itemId", item.getItemId())
                .append("name", item.getName())
                .append("price", item.getPrice())
                .append("stock", item.getStock());

        try {
            items.insertOne(doc);
            item.setId(doc.getObjectId("_id").toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to add item: " + e.getMessage(), e);
        }
    }

    // Update an existing item
    public void updateItem(Item item) {
        Document doc = new Document()
                .append("itemId", item.getItemId())
                .append("name", item.getName())
                .append("price", item.getPrice())
                .append("stock", item.getStock());

        try {
            UpdateResult result = items.replaceOne(
                    Filters.eq("_id", new ObjectId(item.getId())),
                    doc
            );

            if (result.getMatchedCount() == 0) {
                throw new IllegalArgumentException("Item with ID " + item.getId() + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update item: " + e.getMessage(), e);
        }
    }

    // Delete an item by ID
    public void deleteItem(String itemId) {
        try {
            Document result = items.findOneAndDelete(Filters.eq("_id", new ObjectId(itemId)));
            if (result == null) {
                throw new IllegalArgumentException("Item with ID " + itemId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete item: " + e.getMessage(), e);
        }
    }

    // Get an item by ID
    public Item getItemById(String itemId) {
        try {
            Document doc = items.find(Filters.eq("_id", new ObjectId(itemId))).first();
            if (doc == null) {
                return null;
            }
            return documentToItem(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve item: " + e.getMessage(), e);
        }
    }

    // Get an item by itemId
    public Item getItemByItemId(String itemId) {
        try {
            Document doc = items.find(Filters.eq("itemId", itemId)).first();
            if (doc == null) {
                return null;
            }
            return documentToItem(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve item: " + e.getMessage(), e);
        }
    }

    // Get all items
    public List<Item> getAllItems() {
        List<Item> itemList = new ArrayList<>();
        try {
            for (Document doc : items.find()) {
                itemList.add(documentToItem(doc));
            }
            return itemList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve items: " + e.getMessage(), e);
        }
    }

    // Helper method to convert Document to Item
    private Item documentToItem(Document doc) {
        Item item = new Item();
        item.setId(doc.getObjectId("_id").toString());
        item.setItemId(doc.getString("itemId"));
        item.setName(doc.getString("name"));
        item.setPrice(doc.getDouble("price") != null ? doc.getDouble("price") : 0.0);
        item.setStock(doc.getInteger("stock") != null ? doc.getInteger("stock") : 0);
        return item;
    }
}
