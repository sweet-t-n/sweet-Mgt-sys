package najah.edu.acceptance;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import sweet.CommunicationandNotification;

public class CommunicationandNotificationTest {

    private CommunicationandNotification communication;

    @Before
    public void setUp() {
        communication = new CommunicationandNotification();
    }

    @Test
    public void testSendMessage() {
        boolean result = communication.sendMessage("user1", "Hello User1");
        assertTrue("The message should be sent successfully", result);
    }

    @Test
    public void testReceiveMessage() {
        communication.sendMessage("user1", "Hello User1");
        String message = communication.receiveMessage("user1");
        assertEquals("The received message should be 'Hello User1'", "Hello User1", message);
    }

    @Test
    public void testReceiveMessageNoMessage() {
        String message = communication.receiveMessage("user2");
        assertEquals("If no message exists, should return 'لا توجد رسائل'", "لا توجد رسائل", message);
    }

    @Test
    public void testIsMessageSentConfirmationDisplayed() {
        boolean confirmationDisplayed = communication.isMessageSentConfirmationDisplayed();
        assertTrue("The confirmation should be displayed", confirmationDisplayed);
    }

    @Test
    public void testMarkMessageAsRead() {
        communication.sendMessage("user1", "Hello User1");
        communication.markMessageAsRead("Hello User1");
        assertTrue("The message should be marked as read", communication.isMessageRead("Hello User1"));
    }

    @Test
    public void testIsMessageRead() {
        communication.sendMessage("user1", "Hello User1");
        assertFalse("The message should not be read initially", communication.isMessageRead("Hello User1"));

        communication.markMessageAsRead("Hello User1");
        assertTrue("The message should be marked as read", communication.isMessageRead("Hello User1"));
    }
}
