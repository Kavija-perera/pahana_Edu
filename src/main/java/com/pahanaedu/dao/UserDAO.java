package com.pahanaedu.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.pahanaedu.config.MongoDBConfig;
import com.pahanaedu.model.User;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private final MongoCollection<Document> users;

    public UserDAO() {
        MongoDatabase database = MongoDBConfig.getDatabase();
        this.users = database.getCollection("users");
    }

    // Add a new user
    public void addUser(User user) {
        // Check for duplicate username
        Document existingUser = users.find(Filters.eq("username", user.getUsername())).first();
        if (existingUser != null) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists");
        }

        Document doc = new Document()
                .append("username", user.getUsername())
                .append("password", user.getPassword()) // TODO: Hash in production
                .append("role", user.getRole());

        try {
            users.insertOne(doc);
            user.setId(doc.getObjectId("_id").toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
        }
    }

    // Update an existing user
    public void updateUser(User user) {
        Document doc = new Document()
                .append("username", user.getUsername())
                .append("password", user.getPassword()) // TODO: Hash in production
                .append("role", user.getRole());

        try {
            UpdateResult result = users.replaceOne(
                    Filters.eq("_id", new ObjectId(user.getId())),
                    doc
            );

            if (result.getMatchedCount() == 0) {
                throw new IllegalArgumentException("User with ID " + user.getId() + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    // Delete a user by ID
    public void deleteUser(String userId) {
        try {
            Document result = users.findOneAndDelete(Filters.eq("_id", new ObjectId(userId)));
            if (result == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    // Get a user by ID
    public User getUserById(String userId) {
        try {
            Document doc = users.find(Filters.eq("_id", new ObjectId(userId))).first();
            if (doc == null) {
                return null;
            }
            return documentToUser(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage(), e);
        }
    }

    // Get a user by username (for login)
    public User getUserByUsername(String username) {
        try {
            Document doc = users.find(Filters.eq("username", username)).first();
            if (doc == null) {
                return null;
            }
            return documentToUser(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage(), e);
        }
    }

    // Authenticate user (for login)
    public User authenticate(String username, String password) {
        try {
            Document doc = users.find(Filters.and(
                    Filters.eq("username", username),
                    Filters.eq("password", password) // TODO: Hash in production
            )).first();
            if (doc == null) {
                return null;
            }
            return documentToUser(doc);
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user: " + e.getMessage(), e);
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try {
            for (Document doc : users.find()) {
                userList.add(documentToUser(doc));
            }
            return userList;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage(), e);
        }
    }

    // Helper method to convert Document to User
    private User documentToUser(Document doc) {
        User user = new User();
        user.setId(doc.getObjectId("_id").toString());
        user.setUsername(doc.getString("username"));
        user.setPassword(doc.getString("password"));
        user.setRole(doc.getString("role"));
        return user;
    }
}
