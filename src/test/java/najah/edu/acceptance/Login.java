package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class Login {

    private String username;
    private String password;
    private boolean loginSuccess;

    private static final Map<String, String> rolePasswords = new HashMap<>();
    private static final Set<String> loggedInUsers = new HashSet<>();

    static {
        rolePasswords.put("tasneem", "123456");
        rolePasswords.put("nareman", "654321");  
        rolePasswords.put("masa", "9999");      
        rolePasswords.put("nuha", "9876");     
    }

    @Given("that the user {string} is not logged in")
    public void thatTheUserIsNotLoggedIn(String username) {
        this.username = username;
        loggedInUsers.remove(username);
    }

    @Given("that the admin {string} is not logged in")
    public void thatTheAdminIsNotLoggedIn(String username) {
        this.username = username;
        loggedInUsers.remove(username);
    }

    @Given("that the owner {string} is not logged in")
    public void thatTheOwnerIsNotLoggedIn(String username) {
        this.username = username;
        loggedInUsers.remove(username);
    }

    @Given("that the supplier {string} is not logged in")
    public void thatTheSupplierIsNotLoggedIn(String supplierName) {
        this.username = supplierName;
        loggedInUsers.remove(supplierName);
    }
    
    

    
    





    @When("user tries to login")
    public void userTriesToLogin() {
        this.password = rolePasswords.getOrDefault(this.username, "tasneem");
    }

    @When("admin tries to login")
    public void adminTriesToLogin() {
        this.password = rolePasswords.getOrDefault(this.username, "nareman");
    }

    @When("owner tries to login")
    public void ownerTriesToLogin() {
        this.password = rolePasswords.getOrDefault(this.username, "masa");
    }

    @When("supplier tries to login")
    public void supplierTriesToLogin() {
        this.password = rolePasswords.getOrDefault(this.username, "nuha");
    }

    @When("password is {string}")
    public void passwordIs(String password) {
        this.password = password;
        loginSuccess = rolePasswords.containsKey(this.username) && rolePasswords.get(this.username).equals(password);
        if (loginSuccess) {
            loggedInUsers.add(this.username);
        } else {
            loggedInUsers.remove(this.username);
        }
    }

    @Then("the user login succeeds")
    public void theUserLoginSucceeds() {
        assertTrue("User should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the admin login succeeds")
    public void theAdminLoginSucceeds() {
        assertTrue("Admin should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the owner login succeeds")
    public void theOwnerLoginSucceeds() {
        assertTrue("Owner should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the supplier login succeeds")
    public void theSupplierLoginSucceeds() {
        assertTrue("Supplier should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the user is logged in")
    public void theUserIsLoggedIn() {
        assertTrue("User should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the admin is logged in")
    public void theAdminIsLoggedIn() {
        assertTrue("Admin should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the owner is logged in")
    public void theOwnerIsLoggedIn() {
        assertTrue("Owner should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the supplier is logged in")
    public void theSupplierIsLoggedIn() {
        assertTrue("Supplier should be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the user login fails")
    public void theUserLoginFails() {
        assertFalse("User should not be logged in", loginSuccess);
    }

    @Then("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        assertFalse("User should not be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the admin login fails")
    public void theAdminLoginFails() {
        assertFalse("Admin should not be logged in", loginSuccess);
    }

    @Then("the admin is not logged in")
    public void theAdminIsNotLoggedIn() {
        assertFalse("Admin should not be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the owner login fails")
    public void theOwnerLoginFails() {
        assertFalse("Owner should not be logged in", loginSuccess);
    }

    @Then("the owner is not logged in")
    public void theOwnerIsNotLoggedIn() {
        assertFalse("Owner should not be logged in", loggedInUsers.contains(this.username));
    }

    @Then("the supplier login fails")
    public void theSupplierLoginFails() {
        assertFalse("Supplier should not be logged in", loginSuccess);
    }

    @Then("the supplier is not logged in")
    public void theSupplierIsNotLoggedIn() {
        assertFalse("Supplier should not be logged in", loggedInUsers.contains(this.username));
    }
}
