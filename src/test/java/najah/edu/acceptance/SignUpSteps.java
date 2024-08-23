package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sweet.MyApplication;

import static org.junit.Assert.*;

public class SignUpSteps {
    private MyApplication myApplication;
    private String username;
    private String password;
    private String email;
    private String country;
    private boolean signupSuccess;
    private String feedbackMessage;

    public SignUpSteps() {
        this.myApplication = new MyApplication();
    }

    @Given("that the user {string} is not signed up")
    public void givenUserIsNotSignedUp(String username) {
        this.username = username;
        // Call MyApplication to ensure the user is not in the system
        myApplication.removeUser(username);  // Ensure method exists to remove the user
    }

    @When("the user enters a username {string}, password {string}, email {string}, and country {string}")
    public void whenUserEntersDetails(String username, String password, String email, String country) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.country = country;

        // Call the signUp method from MyApplication
        signupSuccess = myApplication.signUp(username, password, email, country);
        feedbackMessage = myApplication.getSignUpFeedback() ; // Get feedback message from MyApplication
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
}
