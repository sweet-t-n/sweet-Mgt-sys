package najah.edu.acceptance;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import sweet.MyApplication;

public class SignUpTest {

    private MyApplication myApplication;

    @Before
    public void setUp() {
        myApplication = new MyApplication();
    }

    @Test
    public void testSignUpSuccess() {
        String username = "newUser";
        String password = "newPassword";
        String email = "newUser@example.com";
        String country = "NewCountry";

        boolean result = myApplication.signUp(username, password, email, country);
        assertTrue("Sign up should succeed", result);
        assertEquals("Sign up successful", myApplication.getSignUpFeedback());
    }

    @Test
    public void testSignUpFailureDueToExistingUsername() {
        String username = "tasneem"; // Assuming this username already exists
        String password = "password";
        String email = "tasneem@example.com";
        String country = "ExistingCountry";

        // Ensure user is already signed up
        myApplication.signUp(username, password, email, country);

        // Attempt to sign up with the same username
        boolean result = myApplication.signUp(username, password, email, country);
        assertFalse("Sign up should fail due to existing username", result);
        assertEquals("Sign up failed: username already exists", myApplication.getSignUpFeedback());
    }


    @Test
    public void testUserRedirectedToLoginPageOnSuccessfulSignUp() {
        String username = "redirectUser";
        String password = "redirectPassword";
        String email = "redirectUser@example.com";
        String country = "RedirectCountry";

        boolean result = myApplication.signUp(username, password, email, country);
        assertTrue("Sign up should succeed", result);
        // This assumes that successful sign-up implies redirection to login
        assertEquals("Sign up successful", myApplication.getSignUpFeedback());
    }


    @Test
    public void testUserPromptedToTryAgainOnFailedSignUp() {
        String username = "tasneem"; // Assuming this username already exists
        String password = "wrongPassword";
        String email = "tasneem@example.com";
        String country = "WrongCountry";

        // Ensure user is already signed up
        myApplication.signUp(username, "password", "email@example.com", "country");

        // Attempt to sign up with wrong details
        boolean result = myApplication.signUp(username, password, email, country);
        assertFalse("User should be prompted to try again", result);
        assertEquals("Sign up failed: please try again", myApplication.getSignUpFeedback());
    }

}
