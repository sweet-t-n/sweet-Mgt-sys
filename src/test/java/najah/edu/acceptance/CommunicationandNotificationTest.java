package najah.edu.acceptance;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import sweet.CommunicationandNotification;
import sweet.MyApplication;

public class CommunicationandNotificationTest {

    private CommunicationandNotification communication;
    private MyApplication myApplication;

    @Before
    public void setUp() {
        myApplication = new MyApplication();
        communication = new CommunicationandNotification(); // Pass MyApplication to CommunicationandNotification
    }

    @Test
    public void testSendMessage() {
        boolean result = communication.sendMessage("user1", "Shipment details are confirmed");
        assertTrue("The message should be sent successfully", result);
    }



    @Test
    public void testReceiveMessage() {
        communication.sendMessage("user1", "Shipment details are confirmed");

        // إضافة تأخير بسيط إذا لزم الأمر
        try {
            Thread.sleep(500); // تأخير لمدة نصف ثانية
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
}
