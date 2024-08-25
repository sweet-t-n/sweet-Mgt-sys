
package najah.edu.acceptance;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import sweet.signUp;

public class SignUpTest {

    private signUp signup;

    @Before
    public void setUp() {
        signup = new signUp();
    }

    @Test
    public void testUserNotSignedUpInitially() {
        
        assertFalse(signup.isUserRegistered("newUser"));
    }

    @Test
    public void testSignUpSuccess() {
       
        boolean result = signup.theUserIsNotSignedUp("newUser");
        assertTrue(result);
        assertTrue(signup.isUserRegistered("newUser"));
        assertEquals("Sign up successful", signup.getFeedbackMessage());
    }

    @Test
    public void testSignUpFailureDueToExistingUser() {
     
        signup.theUserIsNotSignedUp("existingUser");
        boolean result = signup.theUserIsNotSignedUp("existingUser");
        assertFalse(result);
        assertEquals("Sign up failed: username already exists", signup.getFeedbackMessage());
    }

    @Test
    public void testWhenUserEntersUsernameAndPasswordSuccess() {
       
        signup.whenUserEntersUsernameAndPassword("newUser");
        assertTrue(signup.isSignupSuccessful());
        assertTrue(signup.isUserRegistered("newUser"));
        assertEquals("Sign up successful", signup.getFeedbackMessage());
    }

    @Test
    public void testWhenUserEntersUsernameAndPasswordFailure() {
        
        signup.whenUserEntersUsernameAndPassword("existingUser");
        signup.whenUserEntersUsernameAndPassword("existingUser");
        assertFalse(signup.isSignupSuccessful());
        assertEquals("Sign up failed: username already exists", signup.getFeedbackMessage());
    }

    @Test
    public void testFeedbackMessageIsNotNull() {
       
        signup.whenUserEntersUsernameAndPassword("user");
        assertNotNull(signup.getFeedbackMessage());
    }

    @Test
    public void testSignupSuccessFlagReset() {
        
        signup.whenUserEntersUsernameAndPassword("user1");
        assertTrue(signup.isSignupSuccessful());
        signup.whenUserEntersUsernameAndPassword("user1");
        assertFalse(signup.isSignupSuccessful());
    }
}
