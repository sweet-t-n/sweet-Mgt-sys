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
        rolePasswords.put("user", "123456");
        rolePasswords.put("admin", "654321");
        rolePasswords.put("owner", "9999");
        rolePasswords.put("supplier", "9876");
    }

    @Given("that the {string} is not logged in")
    public void givenUserIsNotLoggedIn(String role) {
        this.username = role;
        loggedInUsers.remove(role);
    }

    @When("{string} tries to login")
    public void whenUserTriesToLogin(String role) {
        this.username = role;
        this.setPassword(rolePasswords.getOrDefault(role, "")); 
    }

    @When("password is {string}")
    public void whenPasswordIs(String password) {
        this.setPassword(password);
        loginSuccess = rolePasswords.containsValue(password);
        if (loginSuccess) {
            loggedInUsers.add(username);
        }
    }

    @Then("the {string} login {string}")
    public void thenUserLogin(String role, String status) {
        boolean expectedSuccess = status.equals("succeeds");
        assertEquals("Login success status should match", expectedSuccess, loginSuccess);
    }

    @Then("the {string} is {string}")
    public void thenUserIs(String role, String loginStatus) {
        boolean expectedLoggedIn = loginStatus.equals("logged in");
        assertEquals("User login status should match", expectedLoggedIn, loggedInUsers.contains(role));
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
