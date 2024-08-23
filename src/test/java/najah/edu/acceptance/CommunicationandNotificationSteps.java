package najah.edu.acceptance;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;
import sweet.CommunicationandNotification;

public class CommunicationandNotificationSteps {

    private CommunicationandNotification communication;
    private String recipient;
    private String message;
    private boolean messageSent;
    private String receivedMessage;
    private boolean messageRead;

    public CommunicationandNotificationSteps() {
        this.communication = new CommunicationandNotification();
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
        messageSent = communication.sendMessage(recipient, message);
    }

    @Then("the message should be sent successfully")
    public void theMessageShouldBeSentSuccessfully() {
        assertTrue("The message should be sent successfully", messageSent);
    }

    @Then("a confirmation should be displayed")
    public void aConfirmationShouldBeDisplayed() {
        assertTrue("A confirmation should be displayed", communication.isMessageSentConfirmationDisplayed());
    }

    @When("they receive a new message from {string}")
    public void theyReceiveANewMessageFrom(String sender) {
        receivedMessage = communication.receiveMessage(sender);
        messageRead = false;
    }

    @Then("they should see the message {string}")
    public void theyShouldSeeTheMessage(String expectedMessage) {
        assertEquals("The message received should match", expectedMessage, receivedMessage);
    }

    @Then("the message should be marked as read")
    public void theMessageShouldBeMarkedAsRead() {
        communication.markMessageAsRead(receivedMessage);
        messageRead = communication.isMessageRead(receivedMessage);
        assertTrue("The message should be marked as read", messageRead);
    }
}
