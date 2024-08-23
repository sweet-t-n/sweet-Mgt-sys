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
    }

    @Test
    public void testSignUpFailureDueToExistingUsername() {
        String username = "tasneem"; // Assuming this username already exists
        String password = "password";
        String email = "tasneem@example.com";
        String country = "ExistingCountry";

        boolean result = myApplication.signUp(username, password, email, country);
        assertFalse("Sign up should fail due to existing username", result);
    }

    @Test
    public void testUserRedirectedToLoginPageOnSuccessfulSignUp() {
        String username = "redirectUser";
        String password = "redirectPassword";
        String email = "redirectUser@example.com";
        String country = "RedirectCountry";

        boolean result = myApplication.signUp(username, password, email, country);
        assertTrue("User should be redirected to the login page", result);
        // Add additional checks if needed to confirm redirection
    }

    @Test
    public void testUserPromptedToTryAgainOnFailedSignUp() {
        String username = "tasneem"; // Assuming this username already exists
        String password = "wrongPassword";
        String email = "tasneem@example.com";
        String country = "WrongCountry";

        boolean result = myApplication.signUp(username, password, email, country);
        assertFalse("User should be prompted to try again", result);
        // Add additional checks if needed to confirm prompt
    }
}
