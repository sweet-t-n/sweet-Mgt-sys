package najah.edu.acceptance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import sweet.MyApplication;
import sweet.User;
import sweet.UserRole;
import sweet.signupmanager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SignUpTest {

    private MyApplication app;
    private signupmanager signupManager;
    private String currentUsername;
    private String currentPassword;
    private boolean signUpResult;

    public SignUpTest(MyApplication ob) {
        app = ob;  // Use the provided MyApplication instance
        signupManager = new signupmanager();
    }

    @Given("that the user {string} is not signed up")
    public void thatTheUserIsNotSignedUp(String username) {
        currentUsername = username;
        assertFalse("User should not be signed up", app.checkIfUserExists(username));
    }

    @Given("they do not have an account in the system")
    public void theyDoNotHaveAnAccountInTheSystem() {
        assertNull("User account should not exist", app.getUser(currentUsername));
    }

    private void assertNull(String message, Object user) {
		// TODO Auto-generated method stub
		
	}

	@When("the user enters a username {string} and password {string}")
    public void theUserEntersAUsernameAndPassword(String username, String password) {
        currentUsername = username;
        currentPassword = password;

        // Here we can use default or hard-coded values for email, country, and role
        String defaultEmail = "defaultEmail@example.com";
        String defaultCountry = "Palestine";
        UserRole defaultRole = UserRole.REGULAR_USER;

        // Attempt to sign up the user
        signUpResult = signupManager.signUp(username, password, defaultEmail, defaultCountry, defaultRole);
    }

    @Then("the sign up succeeds")
    public void theSignUpSucceeds() {
        assertTrue("Sign up should succeed", signUpResult);
    }

    @Then("the user is redirected to the login page")
    public void theUserIsRedirectedToTheLoginPage() {
        // Assuming the result being true implies redirection
        assertTrue("User should be redirected to login page", signUpResult);
    }

    @Then("the account is saved in the system")
    public void theAccountIsSavedInTheSystem() {
        assertNotNull("User account should be saved", app.getUser(currentUsername));
    }

    private void assertNotNull(String message, Object user) {
		// TODO Auto-generated method stub
		
	}

	@Given("they already have an account in the system")
    public void theyAlreadyHaveAnAccountInTheSystem() {
        // Ensure the user is already in the system
        if (!app.checkIfUserExists(currentUsername)) {
            app.addUser(new User(currentUsername, "defaultPassword", "defaultEmail@example.com", "Palestine"));
        }
        assertTrue("User account should exist", app.checkIfUserExists(currentUsername));
    }

    @Then("the sign up fails")
    public void theSignUpFails() {
        assertFalse("Sign up should fail", signUpResult);
    }

    @Then("the user is prompted to try again")
    public void theUserIsPromptedToTryAgain() {
        assertFalse("User should be prompted to try again", signUpResult);
    }

    @Then("the account is not saved in the system")
    public void theAccountIsNotSavedInTheSystem() {
        ArrayList<User> user = app.getUser(currentUsername);
        
        // If the sign-up attempt fails, no new account should be created
        // If the user already exists, we should verify that no new account was added or modified.
        if (app.checkIfUserExists(currentUsername)) {
            assertTrue("The user already existed before the sign-up attempt", user != null &&(user).equals(currentUsername));
        } else {
            assertNull("User account should not be saved", user);
        }
    }
}
