package najah.edu.acceptance;

import org.junit.Before;
import org.junit.Test;
import sweet.MyApplication;

import static org.junit.Assert.*;

public class LoginTest {
    private MyApplication myApplication;

    @Before
    public void setUp() {
        myApplication = new MyApplication();
    }

    @Test
    public void testUserLoginSuccess() {
        String username = "user";
        String password = "123456";
        myApplication.signUp(username, password, "user@example.com", "Country1");
        boolean result = myApplication.login(username, password);
        assertTrue("User login should succeed", result);
        assertTrue("User should be logged in", myApplication.isLoggedIn(username));
    }

    @Test
    public void testAdminLoginSuccess() {
        String username = "admin";
        String password = "654321";
        myApplication.signUp(username, password, "admin@example.com", "Country1");
        boolean result = myApplication.login(username, password);
        assertTrue("Admin login should succeed", result);
        assertTrue("Admin should be logged in", myApplication.isLoggedIn(username));
    }

    @Test
    public void testOwnerLoginSuccess() {
        String username = "owner";
        String password = "9999";
        myApplication.signUp(username, password, "owner@example.com", "Country1");
        boolean result = myApplication.login(username, password);
        assertTrue("Owner login should succeed", result);
        assertTrue("Owner should be logged in", myApplication.isLoggedIn(username));
    }

    @Test
    public void testSupplierLoginSuccess() {
        String username = "supplier";
        String password = "9876";
        myApplication.signUp(username, password, "supplier@example.com", "Country1");
        boolean result = myApplication.login(username, password);
        assertTrue("Supplier login should succeed", result);
        assertTrue("Supplier should be logged in", myApplication.isLoggedIn(username));
    }

    @Test
    public void testInvalidLoginFails() {
        String username = "user";
        String password = "wrongpassword";
        boolean result = myApplication.login(username, password);
        assertFalse("Login should fail with wrong password", result);
        assertFalse("User should not be logged in", myApplication.isLoggedIn(username));
    }
}
