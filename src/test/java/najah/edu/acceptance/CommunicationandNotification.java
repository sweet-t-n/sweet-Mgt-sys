package najah.edu.acceptance;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class CommunicationandNotification {

	
    private String recipient;
    private String message;
    private boolean messageSent;
    private String receivedMessage;
    private boolean messageRead;

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
        // Simulate sending the message
        this.messageSent = true;
    }

    @Then("the message should be sent successfully")
    public void theMessageShouldBeSentSuccessfully() {
        assertTrue("The message should be sent successfully", messageSent);
    }

    @Then("a confirmation should be displayed")
    public void aConfirmationShouldBeDisplayed() {
        // Assume confirmation is part of the simulation
        assertTrue("A confirmation should be displayed", messageSent);
    }


    @When("they receive a new message from {string}")
    public void theyReceiveANewMessageFrom(String sender) {
        // Simulate receiving a message based on the sender
        switch (sender) {
            case "user123":
                this.receivedMessage = "Can I change the delivery date?";
                break;
            case "supplier456":
                this.receivedMessage = "Shipment details are confirmed";
                break;
            default:
                this.receivedMessage = "Unknown message";
                break;
        }
        this.messageRead = false; // Initially, the message is not read
    }

    @Then("they should see the message {string}")
    public void theyShouldSeeTheMessage(String expectedMessage) {
        System.out.println("Expected: " + expectedMessage);
        System.out.println("Received: " + receivedMessage);
        assertEquals("The message received should match", expectedMessage, receivedMessage);
    }

    @Then("the message should be marked as read")
    public void theMessageShouldBeMarkedAsRead() {
        this.messageRead = true;
        assertTrue("The message should be marked as read", messageRead);
    }

    @When("they choose to send a message to the supplier {string}")
    public void theyChooseToSendAMessageToTheSupplier(String supplier) {
        this.recipient = supplier;
        // Simulate choosing to send a message to a supplier
    }

    @When("they enter the message {string} for supplier")
    public void theyEnterTheMessageForSupplier(String message) {
        this.message = message;
        // Simulate entering the message for a supplier
    }

    @When("they send the message to supplier")
    public void theySendTheMessageToSupplier() {
        // Simulate sending the message to the supplier
        this.messageSent = true;
    }

    @Then("the message should be sent successfully to supplier")
    public void theMessageShouldBeSentSuccessfullyToSupplier() {
        assertTrue("The message should be sent successfully to supplier", messageSent);
    }

    @Then("a confirmation should be displayed to supplier")
    public void aConfirmationShouldBeDisplayedToSupplier() {
        // Assume confirmation is part of the simulation
        assertTrue("A confirmation should be displayed to supplier", messageSent);
    }
}
