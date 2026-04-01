package com.education.utils;

import com.education.models.User;
import java.sql.*;

/**
 * Service class for user authentication and management.
 */
public class AuthenticationService {
    private static final DatabaseManager dbManager = DatabaseManager.getInstance();
    private static User currentUser;

    /**
     * Registers a new user in the system.
     */
    public static boolean registerUser(String name, String email, String password, String role) {
        if (!AuthenticationUtil.isValidEmail(email)) {
            System.err.println("Invalid email format");
            return false;
        }

        String userId = AuthenticationUtil.generateUserId();
        String passwordHash = AuthenticationUtil.hashPassword(password);

        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO users (id, name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)")) {
            
            stmt.setString(1, userId);
            stmt.setString(2, name);
            stmt.setString(3, email);
            stmt.setString(4, passwordHash);
            stmt.setString(5, role);
            
            stmt.executeUpdate();
            System.out.println("User registered successfully: " + email);
            return true;
        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    /**
     * Authenticates a user with email and password.
     */
    public static boolean login(String email, String password) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, name, role, password_hash FROM users WHERE email = ?")) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                if (AuthenticationUtil.verifyPassword(password, storedHash)) {
                    currentUser = new User(
                            rs.getString("id"),
                            rs.getString("name"),
                            email,
                            rs.getString("role")
                    );
                    System.out.println("Login successful: " + email);
                    return true;
                }
            }
            System.err.println("Invalid email or password");
            return false;
        } catch (SQLException e) {
            System.err.println("Error during login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Logs out the current user.
     */
    public static void logout() {
        currentUser = null;
        System.out.println("User logged out successfully");
    }

    /**
     * Gets the currently logged-in user.
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks if a user is currently logged in.
     */
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    /**
     * Gets a user by ID.
     */
    public static User getUserById(String userId) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, name, email, role FROM users WHERE id = ?")) {
            
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        return null;
    }

    /**
     * Checks if an email is already registered.
     */
    public static boolean emailExists(String email) {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM users WHERE email = ?")) {
            
            stmt.setString(1, email);
            return stmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Error checking email: " + e.getMessage());
            return false;
        }
    }
}
