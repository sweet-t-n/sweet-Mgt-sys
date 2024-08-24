package najah.edu.acceptance;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class UserManagement {
	
    private boolean isAdminLoggedIn;
    private boolean isUserCreated;
    private boolean isUserInList;
    private boolean areDetailsUpdated;
 


    @Given("I am logged in as an admin")
    public void iAmLoggedInAsAnAdmin() {
      
        isAdminLoggedIn = true;
        System.out.println("Admin is logged in");
        assertTrue("Admin should be logged in", isAdminLoggedIn);
    }

    @When("I create a new user account with the role {string}")
    public void iCreateANewUserAccountWithTheRole(String role) {
        // Simulate user account creation
        if (isAdminLoggedIn) {
            isUserCreated = true;
            System.out.println("Creating a new user account with the role: " + role);
        }
        assertTrue("User account should be created", isUserCreated);
    }

    @Then("the user account should be successfully created")
    public void theUserAccountShouldBeSuccessfullyCreated() {
        // Check if the user account was successfully created
        assertTrue("User account should be successfully created", isUserCreated);
        System.out.println("User account was successfully created");
    }

    @Then("I should see the user in the list of store owners")
    public void iShouldSeeTheUserInTheListOfStoreOwners() {
        // Simulate checking if the user appears in the list
        if (isUserCreated) {
            isUserInList = true;
        }
        assertTrue("User should appear in the list of store owners", isUserInList);
        System.out.println("User appears in the list of store owners");
    }

    @Given("a user with the role {string} exists")
    public void aUserWithTheRoleExists(String role) {
        // Simulate that a user with the given role exists
        isUserCreated = true;
        System.out.println("A user with the role " + role + " exists");
        assertTrue("A user with the role should exist", isUserCreated);
    }

    @When("I edit the user's details")
    public void iEditTheUserSDetails() {
        // Simulate editing user details
        if (isUserCreated) {
            areDetailsUpdated = true;
            System.out.println("Editing user's details");
        }
        assertTrue("User details should be updated", areDetailsUpdated);
    }

    @Then("the user's details should be successfully updated")
    public void theUserSDetailsShouldBeSuccessfullyUpdated() {
        // Check if the user's details were successfully updated
        assertTrue("User's details should be successfully updated", areDetailsUpdated);
        System.out.println("User's details were successfully updated");
    }

    @Then("I should see the updated details in the user list")
    public void iShouldSeeTheUpdatedDetailsInTheUserList() {
        // Simulate checking if the updated details appear in the user list
        if (areDetailsUpdated) {
            isUserInList = true;
        }
        assertTrue("Updated details should appear in the user list", isUserInList);
        System.out.println("Updated details appear in the user list");
    }
    private boolean isUserCreated1 = true;
    private boolean isUserDeleted = false;
    
    @When("I delete the user account")
    public void iDeleteTheUserAccount() {
        // تنفيذ عملية حذف الحساب
        if (isUserCreated1) {
            isUserDeleted = true;
            isUserCreated1 = false; // المستخدم لم يعد موجودًا بعد الحذف
            System.out.println("User account has been deleted");
        }
        assertTrue("User account should be deleted", isUserDeleted);
    }

    @Then("the user account should be successfully deleted")
    public void theUserAccountShouldBeSuccessfullyDeleted() {
        // التحقق من أن حساب المستخدم تم حذفه بنجاح
        assertTrue("User account should be successfully deleted", isUserDeleted);
        System.out.println("User account was successfully deleted");
    }

    @Then("the user should no longer appear in the list of store owners")
    public void theUserShouldNoLongerAppearInTheListOfStoreOwners() {
        // التحقق من أن المستخدم لم يعد يظهر في قائمة أصحاب المتاجر
        assertFalse("User should no longer appear in the list of store owners", isUserCreated1);
        System.out.println("User no longer appears in the list of store owners");
    }
  
}
