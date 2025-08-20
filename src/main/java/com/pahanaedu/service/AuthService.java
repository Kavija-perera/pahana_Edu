package com.pahanaedu.service;

import com.pahanaedu.dao.UserDAO;
import com.pahanaedu.model.User;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

public class AuthService {
    private final UserDAO userDAO;
    private final Validator validator;

    public AuthService() {
        this.userDAO = new UserDAO();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    // Authenticate user (login)
    public User login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }

        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                throw new IllegalArgumentException("Invalid username or password");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to authenticate user: " + e.getMessage(), e);
        }
    }

    // Add a new user
    public String addUser(User user) {
        // Validate user object
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<User> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            userDAO.addUser(user);
            return "User added successfully with ID: " + user.getId();
        } catch (IllegalArgumentException e) {
            throw e; // e.g., duplicate username
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user: " + e.getMessage(), e);
        }
    }

    // Update an existing user
    public String updateUser(User user) {
        // Validate user object
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation errors: ");
            for (ConstraintViolation<User> violation : violations) {
                errorMessage.append(violation.getMessage()).append("; ");
            }
            throw new IllegalArgumentException(errorMessage.toString());
        }

        try {
            userDAO.updateUser(user);
            return "User updated successfully with ID: " + user.getId();
        } catch (IllegalArgumentException e) {
            throw e; // e.g., user not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to update user: " + e.getMessage(), e);
        }
    }

    // Delete a user by ID
    public String deleteUser(String userId) {
        try {
            userDAO.deleteUser(userId);
            return "User deleted successfully with ID: " + userId;
        } catch (IllegalArgumentException e) {
            throw e; // e.g., user not found
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete user: " + e.getMessage(), e);
        }
    }

    // Get a user by ID
    public User getUserById(String userId) {
        try {
            User user = userDAO.getUserById(userId);
            if (user == null) {
                throw new IllegalArgumentException("User with ID " + userId + " not found");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage(), e);
        }
    }

    // Get a user by username
    public User getUserByUsername(String username) {
        try {
            User user = userDAO.getUserByUsername(username);
            if (user == null) {
                throw new IllegalArgumentException("User with username " + username + " not found");
            }
            return user;
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve user: " + e.getMessage(), e);
        }
    }

    // Get all users
    public List<User> getAllUsers() {
        try {
            return userDAO.getAllUsers();
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve users: " + e.getMessage(), e);
        }
    }
}