package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sweet.post;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Test;

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


    @Test
    public void testPostCreation() {
        assertEquals("Test Title", post.getTitle());
        assertEquals("Test Content", post.getContent());
        assertEquals("Test Author", post.getAuthor());
    }

    @Test
    public void testSettersAndGetters() {
        post post2 = new post(null, null, null);
		post2.setTitle("Updated Title");
        post post = new post(null, null, null);
		post.setContent("Updated Content");
        post.setAuthor("Updated Author");

        assertEquals("Updated Title", post.getTitle());
        assertEquals("Updated Content", post.getContent());
        assertEquals("Updated Author", post.getAuthor());
    }

    @Test
    public void testTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        // Allow for slight difference in time
        assertEquals(true, post.getTimestamp().isAfter(now.minusSeconds(1)));
        assertEquals(true, post.getTimestamp().isBefore(now.plusSeconds(1)));
    }
}
