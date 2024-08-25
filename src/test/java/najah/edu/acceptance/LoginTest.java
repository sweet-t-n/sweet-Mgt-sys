package najah.edu.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sweet.login;

public class LoginTest {

    private login loginManager;

    @Before
    public void setUp() {
        loginManager = new login();
    }

    @Test
    public void testInitialLoggedInState() {
        
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testSetCredentials() {
        
        loginManager.setCredentials("user1", "password1");
        
        
        assertTrue(loginManager.authenticateAndLogin("user1", "password1"));
    }

    @Test
    public void testValidLogin() {
        
        loginManager.setCredentials("user1", "password1");
        
        
        assertTrue(loginManager.authenticateAndLogin("user1", "password1"));
        assertTrue(loginManager.isLoggedIn());
    }

    @Test
    public void testInvalidLogin() {
        
        loginManager.setCredentials("user1", "password1");
        
       
        assertFalse(loginManager.authenticateAndLogin("user1", "wrongpassword"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLoginWithDifferentUsername() {
        
        loginManager.setCredentials("user1", "password1");
        
        
        assertFalse(loginManager.authenticateAndLogin("user2", "password1"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLoginWithDifferentPassword() {
        
        loginManager.setCredentials("user1", "password1");
        
       
        assertFalse(loginManager.authenticateAndLogin("user1", "differentpassword"));
        assertFalse(loginManager.isLoggedIn());
    }

    @Test
    public void testLogout() {
        
        loginManager.setCredentials("user1", "password1");
        
       
        loginManager.authenticateAndLogin("user1", "password1");
        assertTrue(loginManager.isLoggedIn());
        
        
        loginManager.logout();
        assertFalse(loginManager.isLoggedIn());
    }
}
