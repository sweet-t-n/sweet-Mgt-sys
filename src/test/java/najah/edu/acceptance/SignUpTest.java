
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
        // تحقق من أن المستخدم لم يتم تسجيله بعد
        assertFalse(signup.isUserRegistered("newUser"));
    }

    @Test
    public void testSignUpSuccess() {
        // محاولة تسجيل مستخدم جديد
        boolean result = signup.theUserIsNotSignedUp("newUser");
        assertTrue(result);
        assertTrue(signup.isUserRegistered("newUser"));
        assertEquals("Sign up successful", signup.getFeedbackMessage());
    }

    @Test
    public void testSignUpFailureDueToExistingUser() {
        // تسجيل مستخدم موجود مسبقاً
        signup.theUserIsNotSignedUp("existingUser");
        boolean result = signup.theUserIsNotSignedUp("existingUser");
        assertFalse(result);
        assertEquals("Sign up failed: username already exists", signup.getFeedbackMessage());
    }

    @Test
    public void testWhenUserEntersUsernameAndPasswordSuccess() {
        // تسجيل مستخدم جديد باستخدام whenUserEntersUsernameAndPassword
        signup.whenUserEntersUsernameAndPassword("newUser", "password");
        assertTrue(signup.isSignupSuccessful());
        assertTrue(signup.isUserRegistered("newUser"));
        assertEquals("Sign up successful", signup.getFeedbackMessage());
    }

    @Test
    public void testWhenUserEntersUsernameAndPasswordFailure() {
        // تسجيل مستخدم موجود مسبقاً باستخدام whenUserEntersUsernameAndPassword
        signup.whenUserEntersUsernameAndPassword("existingUser", "password");
        signup.whenUserEntersUsernameAndPassword("existingUser", "newPassword");
        assertFalse(signup.isSignupSuccessful());
        assertEquals("Sign up failed: username already exists", signup.getFeedbackMessage());
    }

    @Test
    public void testFeedbackMessageIsNotNull() {
        // تحقق من أن رسالة التعليقات ليست null
        signup.whenUserEntersUsernameAndPassword("user", "password");
        assertNotNull(signup.getFeedbackMessage());
    }

    @Test
    public void testSignupSuccessFlagReset() {
        // اختبار تأكيد إعادة تعيين العلم signupSuccess بعد تسجيل مستخدم
        signup.whenUserEntersUsernameAndPassword("user1", "password1");
        assertTrue(signup.isSignupSuccessful());
        signup.whenUserEntersUsernameAndPassword("user1", "password2");
        assertFalse(signup.isSignupSuccessful());
    }
}
