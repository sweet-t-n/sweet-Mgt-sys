package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class Post {

    private String username;
    private String description;
    private String image;
    private boolean postSuccess;
    private String postDescription;
    private String postImage;
    private String errorMessage;

    @Given("the user {string} is logged in")
    public void theUserIsLoggedIn(String username) {
        this.username = username;
        
    }

    @When("the user posts content with description {string} and image {string}")
    public void theUserPostsContentWithDescriptionAndImage(String description, String image) {
        this.description = description;
        this.image = image;

        
        if (description.isEmpty()) {
            postSuccess = false;
            errorMessage = "Description cannot be empty";
        } else if (image.isEmpty()) {
            postSuccess = false;
            errorMessage = "Image cannot be empty";
        } else {
            postSuccess = true;
            postDescription = description;
            postImage = image;
            errorMessage = null;
        }
    }

    @Then("the post should be successfully created")
    public void thePostShouldBeSuccessfullyCreated() {
        assertTrue("Post should be created successfully", postSuccess);
    }

    @Then("the post description should be {string}")
    public void thePostDescriptionShouldBe(String expectedDescription) {
        assertEquals("Post description should match", expectedDescription, postDescription);
    }

    @Then("the post image should be {string}")
    public void thePostImageShouldBe(String expectedImage) {
        assertEquals("Post image should match", expectedImage, postImage);
    }

    @Then("the post should fail with an error message {string}")
    public void thePostShouldFailWithAnErrorMessage(String expectedErrorMessage) {
        assertFalse("Post should not be created", postSuccess);
        assertEquals("Error message should match", expectedErrorMessage, errorMessage);
    }
}
