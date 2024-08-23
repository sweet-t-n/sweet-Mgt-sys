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
    @SuppressWarnings("unused")
	private String email;
    private String country;
    private boolean signupSuccess;
    private String feedbackMessage;

    public SignUpSteps() {
        this.myApplication = new MyApplication();
    }

    @Given("that the user {string} is not signed up")
    public void givenUserIsNotSignedUp(String username) {
        this.setUsername(username);
        // Ensure the user is not in the system
        myApplication.removeUser(username);  // Ensure this method exists in MyApplication
    }

    @When("the user enters a username {string}, password {string}, email {string}, and country {string}")
    public void whenUserEntersDetails(String username, String password, String email, String country) {
        this.setUsername(username);
        this.setPassword(password);
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
    
    // Handle cases where the user has an account
    @Given("that the user {string} is signed up")
    public void givenUserIsSignedUp(String username) {
        this.setUsername(username);
        // Ensure the user is in the system
        myApplication.signUp(username, "password", "email@example.com", "country"); // Ensure this is a valid sign-up
    }

    @When("the user enters a username {string} and password {string}")
    public void whenUserEntersUsernameAndPassword(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
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
    
    @Given("that the user {string} is not signed up")
    public void thatTheUserIsNotSignedUp(String username) {
        // افترض أنك تستخدم كائن من MyApplication لإدارة المستخدمين
        MyApplication myApplication = new MyApplication();

        // تأكد من أن المستخدم غير مسجل
        boolean isUserRegistered = myApplication.checkIfUserExists(username);
        if (isUserRegistered) {
            // إذا كان المستخدم مسجلاً، قم بحذفه أو قم بتنفيذ إجراء يتأكد من عدم وجوده
            myApplication.removeUser(username);
        }
    }
    @Given("they do have an account in the system")
    public void theyDoHaveAnAccountInTheSystem() {
        // افترض أنك تستخدم كائن من MyApplication لإدارة المستخدمين
        MyApplication myApplication = new MyApplication();

        // أنشئ مستخدمًا جديدًا إذا لم يكن موجودًا
        String username = "testUser"; // اختر اسم مستخدم مناسب
        String password = "password";
        String email = "testUser@example.com";
        String country = "TestCountry";

        boolean isUserRegistered = myApplication.checkIfUserExists(username);
        if (!isUserRegistered) {
            myApplication.signUp(username, password, email, country);
        }
    }

    @Given("that the user {string} is signed up")
    public void thatTheUserIsSignedUp(String username) {
        MyApplication myApplication = new MyApplication();
        String password = "password";
        String email = "example@example.com";
        String country = "Country";

        // تحقق من وجود المستخدم، إذا لم يكن موجودًا، قم بتسجيله
        if (!myApplication.checkIfUserExists(username)) {
            myApplication.signUp(username, password, email, country);
        }
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



}
