package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sweet.MyApplication;

import static org.junit.Assert.*;

import java.util.Map;

public class LoginStep {

    private MyApplication myApplication;
    private String username;
    private String password;
    private boolean loginSuccess;

    // تعريف كلمات المرور للأدوار المختلفة
    private static final Map<String, String> rolePasswords = Map.of(
        "user", "123456",
        "admin", "654321",
        "owner", "9999",
        "supplier", "9876"
    );

    public LoginStep() {
        this.myApplication = new MyApplication();
    }

    @Given("{string} is not logged in")
    public void givenUserIsNotLoggedIn(String username) {
        this.username = username;
        myApplication.logout(username);
    }

    @When("{string} tries to login")
    public void triesToLogin(String role) {
        this.password = rolePasswords.getOrDefault(role, "defaultPassword");
        myApplication.setCredentials(username, password);
        loginSuccess = myApplication.login(this.username, this.password);
    }

    @When("password is {string}")
    public void passwordIs(String password) {
        this.password = password;
        myApplication.setCredentials(username, password);
        loginSuccess = myApplication.login(this.username, this.password);
    }

    @Then("{string} login succeeds")
    public void loginSucceeds(String role) {
        assertTrue(role + " should be logged in", myApplication.isLoggedIn(this.username));
    }

    @Then("{string} login fails")
    public void loginFails(String role) {
        assertFalse(role + " login should fail", loginSuccess);
    }
}
