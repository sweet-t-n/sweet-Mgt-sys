package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import sweet.signUp;

public class SignUpTest {

    private String username;
    private String password;
    private String email;
    private String country;
    private boolean signupSuccess;
    private String feedbackMessage;
    private signUp signUpService;

    public SignUpTest() {
        this.signUpService = new signUp();
    }

    @Given("that the user {string} is not signed up")
    public void givenUserIsNotSignedUp(String username) {
        this.username = username;
        signUpService.theUserIsNotSignedUp(username);  // Ensure the user is not signed up
    }

    @Given("that the user {string} is signed up")
    public void givenUserIsSignedUp(String username) {
        this.username = username;
        signUpService.whenUserEntersUsernameAndPassword(username, "defaultPassword");  // Ensure user is signed up
    }

    @When("the user enters a username {string}, password {string}, email {string}, and country {string}")
    public void whenUserEntersDetails(String username, String password, String email, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;

        // Only username and password are used for sign up
        signUpService.whenUserEntersUsernameAndPassword(username, password);
        signupSuccess = signUpService.isSignupSuccessful();
        feedbackMessage = signUpService.getFeedbackMessage();

        // Debugging: Print values to ensure they're correctly set
        System.out.println("Signup Success: " + signupSuccess);
        System.out.println("Feedback Message: " + feedbackMessage);
    }

    @Then("the sign up succeeds")
    public void thenSignUpSucceeds() {
        assertTrue("Expected sign up to succeed, but it failed.", signupSuccess);
        assertEquals("Sign up successful", feedbackMessage);
    }

    @Then("the sign up fails")
    public void thenSignUpFails() {
        assertFalse("Expected sign up to fail, but it succeeded.", signupSuccess);
        assertEquals("Sign up failed: username already exists", feedbackMessage);
    }

    @Then("the user is redirected to the login page")
    public void thenUserIsRedirectedToLoginPage() {
        // Assuming feedback message contains information about redirection
        assertTrue("Expected user to be redirected to the login page, but it wasn't.", 
                   feedbackMessage.contains("redirected to login page"));
    }

    @Then("the user is prompted to try again")
    public void thenUserIsPromptedToTryAgain() {
        assertEquals("Sign up failed: please try again", feedbackMessage);
    }
}
