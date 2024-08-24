package najah.edu.acceptance;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import sweet.MessagingService;

public class MessagingTest {
	

    private boolean messageSent;
    private String feedbackMessage;
    private MessagingService messagingService;

    public MessagingTest() {
        messagingService = new MessagingService();
    }

    @Given("they are on the messaging page")
    public void theyAreOnTheMessagingPage() {
        messagingService.navigateToMessagingPage();
    }

    @When("they send the message")
    public void theySendTheMessage() {
        messageSent = messagingService.sendMessage("Hello, World!");
        feedbackMessage = messagingService.getLastMessage();
    }

    @Then("the message should be sent successfully")
    public void theMessageShouldBeSentSuccessfully() {
        assertTrue("Expected message to be sent successfully, but it wasn't.", messageSent);
        assertEquals("Message sent successfully", messagingService.getSuccessMessage());
    }

    @Then("they should see the message {string}")
    public void theyShouldSeeTheMessage(String expectedMessage) {
        assertEquals("Expected message to be displayed, but it wasn't.", expectedMessage, messagingService.getLastMessage());
    }

    @Then("a confirmation should be displayed")
    public void aConfirmationShouldBeDisplayed() {
        assertTrue("A confirmation should be displayed", messagingService.isConfirmationDisplayed());
    }
}
