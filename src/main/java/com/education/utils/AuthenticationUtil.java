package com.education.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utility class for password hashing and authentication.
 */
public class AuthenticationUtil {
    
    /**
     * Hashes a password with a randomly generated salt using SHA-256.
     */
    public static String hashPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            byte[] saltPlusHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltPlusHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltPlusHash, salt.length, hashedPassword.length);

            return Base64.getEncoder().encodeToString(saltPlusHash);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verifies a password against a stored hash.
     */
    public static boolean verifyPassword(String password, String storedHash) {
        try {
            byte[] saltPlusHash = Base64.getDecoder().decode(storedHash);
            byte[] salt = new byte[16];
            System.arraycopy(saltPlusHash, 0, salt, 0, 16);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());

            byte[] storedHashBytes = new byte[saltPlusHash.length - 16];
            System.arraycopy(saltPlusHash, 16, storedHashBytes, 0, storedHashBytes.length);

            return MessageDigest.isEqual(hashedPassword, storedHashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error verifying password", e);
        }
    }

    /**
     * Generates a unique user ID.
     */
    public static String generateUserId() {
        return "USER_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
    }

    /**
     * Validates email format.
     */
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }
}
