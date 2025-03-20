package com.example.aimobilebackend.domain.model.aggregate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void createUser_shouldCreateUserWithCorrectValues() {
        // Given
        String username = "testuser";
        String email = "test@example.com";
        
        // When
        User user = User.create(username, email);
        
        // Then
        assertNotNull(user);
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(email, user.getEmail());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
        // They should be the same object reference now that we're using a single 'now' variable
        assertSame(user.getCreatedAt(), user.getUpdatedAt());
    }
    
    @Test
    void updateUsername_shouldUpdateUsernameAndUpdateTimestamp() throws InterruptedException {
        // Given
        User user = User.create("testuser", "test@example.com");
        String newUsername = "updateduser";
        
        // Ensure some time passes to detect timestamp change
        Thread.sleep(10);
        
        // When
        user.updateUsername(newUsername);
        
        // Then
        assertEquals(newUsername, user.getUsername());
        assertTrue(user.getUpdatedAt().isAfter(user.getCreatedAt()));
    }
    
    @Test
    void updateEmail_shouldUpdateEmailAndUpdateTimestamp() throws InterruptedException {
        // Given
        User user = User.create("testuser", "test@example.com");
        String newEmail = "updated@example.com";
        
        // Ensure some time passes to detect timestamp change
        Thread.sleep(10);
        
        // When
        user.updateEmail(newEmail);
        
        // Then
        assertEquals(newEmail, user.getEmail());
        assertTrue(user.getUpdatedAt().isAfter(user.getCreatedAt()));
    }
}