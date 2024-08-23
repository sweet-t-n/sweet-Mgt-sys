package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

import sweet.MyApplication;

public class SignUpTest {
    private String username;
    private String password;
    private String email;
    private String country;
    private boolean signupSuccess;
    private String feedbackMessage;
    private MyApplication myApplication;

    public SignUpTest() {
        this.myApplication = new MyApplication();
    }

    @Given("that the user {string} is not signed up")
    public void givenUserIsNotSignedUp(String username) {
        this.username = username;
        // Ensure the user is not in the system
        myApplication.removeUser(username);  // Ensure this method exists in MyApplication
    }

    @When("the user enters a username {string}, password {string}, email {string}, and country {string}")
    public void whenUserEntersDetails(String username, String password, String email, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;

        // Call the signUp method from MyApplication
        signupSuccess = myApplication.signUp(username, password, email, country);
        feedbackMessage = myApplication.getSignUpFeedback(); // Get feedback message from MyApplication
    }

    @Then("the sign up succeeds")
    public void thenSignUpSucceeds() {
        assertTrue(signupSuccess);
        assertEquals("Sign up successful", feedbackMessage);
    }

    @Then("the sign up fails")
    public void thenSignUpFails() {
        assertFalse(signupSuccess);
        assertEquals("Sign up failed: username already exists", feedbackMessage);
    }

    @Given("that the user {string} is signed up")
    public void givenUserIsSignedUp(String username) {
        this.username = username;
        // Ensure the user is in the system
        myApplication.signUp(username, "password", "email@example.com", "country"); // Ensure this is a valid sign-up
    }

    @When("the user enters a username {string} and password {string}")
    public void whenUserEntersUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
        // Attempt to log in
        signupSuccess = myApplication.login(username, password);
        feedbackMessage = myApplication.getLoginFeedback(); // Get feedback message from MyApplication
    }

    @Then("the user is redirected to the login page")
    public void thenUserIsRedirectedToLoginPage() {
        assertTrue("User should be redirected to the login page", feedbackMessage.contains("redirected to login page"));
    }

    @Then("the user is prompted to try again")
    public void thenUserIsPromptedToTryAgain() {
        assertEquals("Sign up failed: please try again", feedbackMessage);
    }
}
