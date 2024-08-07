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
    private boolean loginSuccess = false;
    private boolean loginAttempted = false;
    
    private static final Set<String> loggedInUsers = new HashSet<>();
    private static final Map<String, String> userCredentials = new HashMap<>();

    
    static {
        userCredentials.put("tasneem", "password123");
        userCredentials.put("masa", "securepassword");
    }

    
    @Given("that the user {string} is not logged in")
    public void thatTheUserIsNotLoggedIn(String username) {
    	
        this.username = username;
        loggedInUsers.remove(username);
        loginSuccess = false; 
        loginAttempted = false; 
    }

    @When("user tries to login")
    public void userTriesToLogin() {
        loginAttempted = true;
    }

    @When("username is {string} and password is {string}")
    public void usernameIsAndPasswordIs(String username, String password) {
        this.username = username;
        this.password = password;

        if (loginAttempted) {
            String correctPassword = userCredentials.get(username);
            if (correctPassword != null && correctPassword.equals(password)) {
                loginSuccess = true;
                loggedInUsers.add(username);
            } else {
                loginSuccess = false;
            }
            loginAttempted = false;
        }
    }

    @Then("the user login succeeds")
    public void theUserLoginSucceeds() {
        assertTrue("Expected login to succeed but it failed", loginSuccess);
    }

    @Then("the user is logged in")
    public void theUserIsLoggedIn() {
        assertTrue("Expected user to be logged in but they are not", loggedInUsers.contains(username));
    }

    @Then("the user login fails")
    public void theUserLoginFails() {
        assertFalse("Expected login to fail but it succeeded", loginSuccess);
    }

    @Then("the user is not logged in")
    public void theUserIsNotLoggedIn() {
        assertFalse("Expected user to not be logged in but they are", loggedInUsers.contains(username));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
