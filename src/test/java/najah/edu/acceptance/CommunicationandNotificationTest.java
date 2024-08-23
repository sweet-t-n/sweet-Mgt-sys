package najah.edu.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import sweet.CommunicationandNotification;
import sweet.MyApplication;

public class CommunicationandNotificationTest {

    private CommunicationandNotification communication;
    private MyApplication myApplication;
    private String recipient;
    private String message;

    @Before
    public void setUp() {
        myApplication = new MyApplication();
        communication = new CommunicationandNotification(myApplication); // تأكد من أن CommunicationandNotification 
        System.out.println("CommunicationandNotification initialized: " + (communication != null));    }


    @Test
    public void testSendMessage() {
        boolean result = communication.sendMessage("user1", "Shipment details are confirmed");
        assertTrue("The message should be sent successfully", result);
    }

    @Test
    public void testReceiveMessage() {
        communication.sendMessage("user1", "Shipment details are confirmed");

        // Add delay if needed
        try {
            Thread.sleep(500); // Delay for half a second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String message = communication.receiveMessage("user1");
        assertEquals("The received message should be 'Shipment details are confirmed'", "Shipment details are confirmed", message);
    }

    @Test
    public void testIsMessageSentConfirmationDisplayed() {
        communication.sendMessage("user1", "Hello User1");
        assertTrue("The confirmation should be displayed", communication.isMessageSentConfirmationDisplayed());
    }

    @Test
    public void testMarkMessageAsRead() {
        // Send message
        communication.sendMessage("user1", "Shipment details are confirmed");

        // Check that the message is not read initially
        assertFalse("The message should not be read initially", communication.isMessageRead("Shipment details are confirmed"));

        // Mark the message as read
        communication.markMessageAsRead("Shipment details are confirmed");

        // Check that the message is marked as read now
        assertTrue("The message should be marked as read", communication.isMessageRead("Shipment details are confirmed"));
    }

    @Test
    public void testIsMessageRead() {
        communication.sendMessage("user1", "Hello User1");
        assertFalse("The message should not be read initially", communication.isMessageRead("Hello User1"));

        communication.markMessageAsRead("Hello User1");
        assertTrue("The message should be marked as read", communication.isMessageRead("Hello User1"));
    }

    @Test
    public void testSendAndReceiveMessage() {
        communication.sendMessage("user1", "Shipment details are confirmed");
        String message = communication.receiveMessage("user1");
        assertEquals("The received message should be 'Shipment details are confirmed'", "Shipment details are confirmed", message);
    }

    @Test
    public void testMarkAndCheckMessageAsRead() {
        communication.sendMessage("user1", "Shipment details are confirmed");
        communication.markMessageAsRead("Shipment details are confirmed");
        assertTrue("The message should be marked as read", communication.isMessageRead("Shipment details are confirmed"));
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
        boolean result = communication.sendMessage(recipient, message);
        assertTrue("The message should be sent successfully", result);
    }

    @Then("the message should be sent successfully")
    public void theMessageShouldBeSentSuccessfully() {
        // Verify the message was sent successfully
        String receivedMessage = communication.receiveMessage(recipient);
        assertEquals("The received message should be as expected", message, receivedMessage);
    }

    @Then("a confirmation should be displayed")
    public void aConfirmationShouldBeDisplayed() {
        assertTrue("The confirmation should be displayed", communication.isMessageSentConfirmationDisplayed());
    }

    @When("they receive a new message from {string}")
    public void theyReceiveANewMessageFrom(String sender) {
        // Simulate receiving a new message
        communication.sendMessage(sender, message);
    }

    @Then("they should see the message {string}")
    public void theyShouldSeeTheMessage(String expectedMessage) {
        String actualMessage = communication.receiveMessage(recipient);
        assertEquals("The message should be as expected", expectedMessage, actualMessage);
    }

    @Then("the message should be marked as read")
    public void theMessageShouldBeMarkedAsRead() {
        assertTrue("The message should be marked as read", communication.isMessageRead(message));
    }

    public MyApplication getMyApplication() {
        return myApplication;
    }

    public void setMyApplication(MyApplication myApplication) {
        this.myApplication = myApplication;
    }
}
