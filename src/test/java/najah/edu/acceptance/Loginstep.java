package najah.edu.acceptance;

import org.junit.Before;
import org.junit.Test;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sweet.Admin;
import sweet.MaterialSupplier;
import sweet.MyApplication;
import sweet.StoreOwner;
import sweet.User;

import static org.junit.Assert.*;

public class Loginstep {
    private MyApplication myApplication;

    private User user;
    private Admin admin;
    private StoreOwner storeOwner;
    private MaterialSupplier materialSupplier;
    private boolean loginSuccess;
    @Before
    public void setUp() {
        myApplication = new MyApplication();
    }

    @Test
    public void testUserLoginSuccess() {
        String username = "user";
        String password = "123456";
        myApplication.signUp(username);
        boolean result = myApplication.login(username, password);
        assertTrue("User login should succeed", result);
        assertTrue("User should be logged in", myApplication.isLoggedIn());
    }

    @Test
    public void testAdminLoginSuccess() {
        String username = "admin";
        String password = "654321";
        myApplication.signUp(username);
        boolean result = myApplication.login(username, password);
        assertTrue("Admin login should succeed", result);
        assertTrue("Admin should be logged in", myApplication.isLoggedIn());
    }

    @Test
    public void testOwnerLoginSuccess() {
        String username = "owner";
        String password = "9999";
        myApplication.signUp(username);
        boolean result = myApplication.login(username, password);
        assertTrue("Owner login should succeed", result);
        assertTrue("Owner should be logged in", myApplication.isLoggedIn());
    }

    @Test
    public void testSupplierLoginSuccess() {
        String username = "supplier";
        String password = "9876";
        myApplication.signUp(username);
        boolean result = myApplication.login(username, password);
        assertTrue("Supplier login should succeed", result);
        assertTrue("Supplier should be logged in", myApplication.isLoggedIn());
    }

    @Test
    public void testInvalidLoginFails() {
        String username = "user";
        String password = "wrongpassword";
        boolean result = myApplication.login(username, password);
        assertFalse("Login should fail with wrong password", result);
        assertFalse("User should not be logged in", myApplication.isLoggedIn());
    }
    
        @Given("that the user {string} is not logged in")
        public void thatTheUserIsNotLoggedIn(String username) {
            user = new User(username, "123456", "defaultEmail", "defaultCountry"); // كلمة السر الصحيحة
            loginSuccess = false; 
        }

        @Given("that the admin {string} is not logged in")
        public void thatTheAdminIsNotLoggedIn(String username) {
            admin = new Admin(username, "654321", "defaultEmail", "defaultCountry"); // كلمة السر الصحيحة
            loginSuccess = false; 
        }

        @Given("that the owner {string} is not logged in")
        public void thatTheOwnerIsNotLoggedIn(String username) {
            storeOwner = new StoreOwner(username, "9999", "defaultEmail", "defaultCountry"); // كلمة السر الصحيحة
            loginSuccess = false; 
        }

        @Given("that the supplier {string} is not logged in")
        public void thatTheSupplierIsNotLoggedIn(String supplierName) {
            materialSupplier = new MaterialSupplier(supplierName, "9876", "defaultEmail", "defaultCountry"); // كلمة السر الصحيحة
            loginSuccess = false; 
        }

        @When("user tries to login")
        public void userTriesToLogin() {
           
        }

        @When("admin tries to login")
        public void adminTriesToLogin() {
            
        }

        @When("owner tries to login")
        public void ownerTriesToLogin() {
            
        }

        @When("supplier tries to login")
        public void supplierTriesToLogin() {
            
        }

        @When("password is {string}")
        public void passwordIs(String password) {
            if (user != null && user.getPassword().equals(password)) {
                loginSuccess = true;
            } else if (admin != null && admin.getPassword().equals(password)) {
                loginSuccess = true;
            } else if (storeOwner != null && storeOwner.getPassword().equals(password)) {
                loginSuccess = true;
            } else if (materialSupplier != null && materialSupplier.getPassword().equals(password)) {
                loginSuccess = true;
            } else {
                loginSuccess = false;
            }
        }

        @Then("the user login succeeds")
        public void theUserLoginSucceeds() {
            assertTrue("User should be logged in", loginSuccess);
        }

        @Then("the admin login succeeds")
        public void theAdminLoginSucceeds() {
            assertTrue("Admin should be logged in", loginSuccess);
        }

        @Then("the owner login succeeds")
        public void theOwnerLoginSucceeds() {
            assertTrue("Owner should be logged in", loginSuccess);
        }

        @Then("the supplier login succeeds")
        public void theSupplierLoginSucceeds() {
            assertTrue("Supplier should be logged in", loginSuccess);
        }

        @Then("the user is logged in")
        public void theUserIsLoggedIn() {
            assertTrue("User should be logged in", loginSuccess);
        }

        @Then("the admin is logged in")
        public void theAdminIsLoggedIn() {
            assertTrue("Admin should be logged in", loginSuccess);
        }

        @Then("the owner is logged in")
        public void theOwnerIsLoggedIn() {
            assertTrue("Owner should be logged in", loginSuccess);
        }

        @Then("the supplier is logged in")
        public void theSupplierIsLoggedIn() {
            assertTrue("Supplier should be logged in", loginSuccess);
        }

        @Then("the user login fails")
        public void theUserLoginFails() {
            assertFalse("User should not be logged in", loginSuccess);
        }

        @Then("the admin login fails")
        public void theAdminLoginFails() {
            assertFalse("Admin should not be logged in", loginSuccess);
        }

        @Then("the owner login fails")
        public void theOwnerLoginFails() {
            assertFalse("Owner should not be logged in", loginSuccess);
        }

        @Then("the supplier login fails")
        public void theSupplierLoginFails() {
            assertFalse("Supplier should not be logged in", loginSuccess);
        }


        @Then("the user is not logged in")
        public void theUserIsNotLoggedIn() {
            assertFalse("User should not be logged in", loginSuccess);
        }

        @Then("the admin is not logged in")
        public void theAdminIsNotLoggedIn() {
            assertFalse("Admin should not be logged in", loginSuccess);
        }

        @Then("the owner is not logged in")
        public void theOwnerIsNotLoggedIn() {
            assertFalse("Owner should not be logged in", loginSuccess);
        }

        @Then("the supplier is not logged in")
        public void theSupplierIsNotLoggedIn() {
            assertFalse("Supplier should not be logged in", loginSuccess);
        }
    }
