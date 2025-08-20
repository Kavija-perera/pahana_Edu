package com.pahanaedu.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConfig {
    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    private static final String MONGO_URI = "mongodb+srv://kavichiranthikaa2001:PiK2fRlNAESUTn9r@cluster0.on7nbhi.mongodb.net/";
    private static final String DATABASE_NAME = "pahanaedu";

    // Private constructor to prevent instantiation
    private MongoDBConfig() {
    }

    // Get MongoDB database instance (singleton)
    public static MongoDatabase getDatabase() {
        if (database == null) {
            try {
                // Initialize MongoClient with the provided URI
                mongoClient = MongoClients.create(MONGO_URI);
                // Get the database instance
                database = mongoClient.getDatabase(DATABASE_NAME);
                System.out.println("Successfully connected to MongoDB database: " + DATABASE_NAME);
            } catch (Exception e) {
                System.err.println("Error connecting to MongoDB: " + e.getMessage());
                throw new RuntimeException("Failed to connect to MongoDB", e);
            }
        }
        return database;
    }

    // Close MongoDB connection
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("MongoDB connection closed.");
        }
    }
}