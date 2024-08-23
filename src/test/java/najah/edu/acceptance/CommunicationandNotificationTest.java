package najah.edu.acceptance;

import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import sweet.CommunicationandNotification;
import sweet.MyApplication;

public class CommunicationandNotificationTest {

    private CommunicationandNotification communication;
    private String recipient;
    private String message;

    @Before
    public void setUp() {
        communication = new CommunicationandNotification(new MyApplication());
    }

    @When("they choose to send a message to the user {string}")
    public void theyChooseToSendAMessageToTheUser(String user) {
        this.recipient = user;
    }

    @When("they enter the message {string}")
    public void theyEnterTheMessage(String message) {
        this.message = message;
    }

    @When("they send the message")
    public void theySendTheMessage() {
        communication.sendMessage(recipient, message);
    }

    @Then("the message should be sent successfully")
    public void theMessageShouldBeSentSuccessfully() {
        assertTrue("The message should be sent successfully", communication.isMessageSentConfirmationDisplayed());
    }

    @Then("they should see the message {string}")
    public void theyShouldSeeTheMessage(String expectedMessage) {
        String actualMessage = communication.receiveMessage(recipient);
        assertEquals("The received message should be correct", expectedMessage, actualMessage);
    }

    @Then("the message should be marked as read")
    public void theMessageShouldBeMarkedAsRead() {
        communication.markMessageAsRead(message);
        assertTrue("The message should be marked as read", communication.isMessageRead(message));
    }
}
