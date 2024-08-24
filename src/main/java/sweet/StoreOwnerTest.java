package sweet;

import static org.junit.Assert.*;

import org.junit.Test;

public class StoreOwnerTest {

    @Test
    public void testStoreOwnerCreation() {
        StoreOwner owner = new StoreOwner("john_doe", "password123", "john@example.com", "USA");
        
        assertEquals("john_doe", owner.getUsername());
        assertEquals("password123", owner.getPassword());
        assertEquals("john@example.com", owner.getEmail());
        assertEquals("USA", owner.getCountry());
    }

    @Test
    public void testSetters() {
        StoreOwner owner = new StoreOwner("john_doe", "password123", "john@example.com", "USA");

        owner.setUsername("jane_doe");
        owner.setPassword("newpassword");
        owner.setEmail("jane@example.com");
        owner.setCountry("Canada");

        assertEquals("jane_doe", owner.getUsername());
        assertEquals("newpassword", owner.getPassword());
        assertEquals("jane@example.com", owner.getEmail());
        assertEquals("Canada", owner.getCountry());
    }

    @Test
    public void testDefaultValues() {
        StoreOwner owner = new StoreOwner("", "", "", "");

        assertEquals("", owner.getUsername());
        assertEquals("", owner.getPassword());
        assertEquals("", owner.getEmail());
        assertEquals("", owner.getCountry());
    }

    @Test
    public void testNullValues() {
        StoreOwner owner = new StoreOwner(null, null, null, null);

        assertNull(owner.getUsername());
        assertNull(owner.getPassword());
        assertNull(owner.getEmail());
        assertNull(owner.getCountry());
    }

    @Test
    public void testEmailValidation() {
        StoreOwner owner = new StoreOwner("john_doe", "password123", "john@example.com", "USA");

        // Assuming you have a method validateEmail to validate email format
        assertTrue(validateEmail(owner.getEmail()));
        
        owner.setEmail("invalid-email");
        assertFalse(validateEmail(owner.getEmail()));
    }

    @Test
    public void testPasswordStrength() {
        StoreOwner owner = new StoreOwner("john_doe", "password123", "john@example.com", "USA");

        // Assuming you have a method validatePasswordStrength to validate password strength
        assertTrue(validatePasswordStrength(owner.getPassword()));
        
        owner.setPassword("weak");
        assertFalse(validatePasswordStrength(owner.getPassword()));
    }

    private boolean validateEmail(String email) {
        // Simple email validation logic
        return email != null && email.contains("@");
    }

    private boolean validatePasswordStrength(String password) {
        // Simple password strength validation logic
        return password != null && password.length() >= 8;
    }
}
