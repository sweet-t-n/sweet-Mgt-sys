package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import sweet.Post;

public class PostTest {

    private Post post;
    private boolean postCreationSuccess;
    private String errorMessage;
    private String postDescription;
    private String postImagePath;

    @Given("the user {string} is logged in")
    public void userIsLoggedIn(String username) {
        // Simulate user login, e.g., set up user session or state.
        System.out.println("User " + username + " is logged in.");
    }

    @When("the user posts content with description {string} and image {string}")
    public void userPostsContent(String description, String imagePath) {
        if (description.isEmpty()) {
            postCreationSuccess = false;
            errorMessage = "Description cannot be empty";
        } else if (imagePath.isEmpty()) {
            postCreationSuccess = false;
            errorMessage = "Image cannot be empty";
        } else {
            // Create the post
            post = new Post("Sample Title", description, "Sample Author", imagePath);
            postCreationSuccess = true;
            errorMessage = "";
            postDescription = post.getContent();
            postImagePath = post.getImagePath();
        }
    }

    @Then("the post should be successfully created")
    public void postShouldBeCreated() {
        assertTrue("Post creation was expected to succeed but it failed.", postCreationSuccess);
    }

    @Then("the post description should be {string}")
    public void postDescriptionShouldBe(String description) {
        assertEquals("The post description does not match the expected value.", description, postDescription);
    }

    @Then("the post image should be {string}")
    public void postImageShouldBe(String imagePath) {
        assertEquals("The post image path does not match the expected value.", imagePath, postImagePath);
    }

    @Then("the post should fail with an error message {string}")
    public void postShouldFailWithErrorMessage(String expectedErrorMessage) {
        assertFalse("Post creation was expected to fail but it succeeded.", postCreationSuccess);
        assertEquals("The error message does not match the expected value.", expectedErrorMessage, errorMessage);
    }
}
