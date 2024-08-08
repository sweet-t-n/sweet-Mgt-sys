package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class Signup {

    private String username;
    private String password;
    private boolean signupSuccess = false;
    private String feedbackMessage = "";

    private static final Set<String> existingUsernames = new HashSet<>();
    private static final Set<String> usersWithAccounts = new HashSet<>();

    static {
        existingUsernames.add("tasneem");
        existingUsernames.add("nareman");
        existingUsernames.add("masa");
        existingUsernames.add("shahed");
    }

    @Given("that the user {string} is not sign up")
    public void givenUserIsNotSignUp(String username) {
        this.username = username;
    }

    @Given("they do not have an account in the system")
    public void givenUserDoesNotHaveAccount() {
        usersWithAccounts.remove(username);
    }

    @Given("they do have an account in the system")
    public void givenUserHasAccount() {
        usersWithAccounts.add(username);
    }

    @When("the user enters a username {string} and password {string}")
    public void whenUserEntersUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;

        if (existingUsernames.contains(username) || usersWithAccounts.contains(username)) {
            signupSuccess = false;
            feedbackMessage = "Sign up failed: username already exists";
        } else {
            signupSuccess = true;
            feedbackMessage = "Sign up successful";
            existingUsernames.add(username);
            usersWithAccounts.add(username);
        }
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

    @Then("the user is redirected to the login page")
    public void thenUserIsRedirectedToLoginPage() {
        assertTrue("User should be redirected to the login page", signupSuccess);
    }

    @Then("the user is prompted to try again")
    public void thenUserIsPromptedToTryAgain() {
        assertFalse("User should be prompted to try again", signupSuccess);
    }
}
