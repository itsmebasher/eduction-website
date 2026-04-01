package com.education.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for the AuthenticationUtil class.
 */
public class AuthenticationUtilTest {

    @Test
    public void testPasswordHashing() {
        String password = "testPassword123";
        String hash = AuthenticationUtil.hashPassword(password);
        
        assertNotNull(hash);
        assertNotEquals(password, hash);
    }

    @Test
    public void testPasswordVerification() {
        String password = "testPassword123";
        String hash = AuthenticationUtil.hashPassword(password);
        
        assertTrue(AuthenticationUtil.verifyPassword(password, hash));
    }

    @Test
    public void testIncorrectPassword() {
        String password = "testPassword123";
        String wrongPassword = "wrongPassword";
        String hash = AuthenticationUtil.hashPassword(password);
        
        assertFalse(AuthenticationUtil.verifyPassword(wrongPassword, hash));
    }

    @Test
    public void testEmailValidation() {
        assertTrue(AuthenticationUtil.isValidEmail("user@example.com"));
        assertTrue(AuthenticationUtil.isValidEmail("test.user@domain.co.uk"));
        assertFalse(AuthenticationUtil.isValidEmail("invalid.email"));
        assertFalse(AuthenticationUtil.isValidEmail("@example.com"));
    }

    @Test
    public void testUserIdGeneration() {
        String userId1 = AuthenticationUtil.generateUserId();
        String userId2 = AuthenticationUtil.generateUserId();
        
        assertNotNull(userId1);
        assertNotNull(userId2);
        assertNotEquals(userId1, userId2);
        assertTrue(userId1.startsWith("USER_"));
    }
}
