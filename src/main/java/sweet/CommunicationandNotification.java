package sweet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationandNotification {
    private static final Logger logger = Logger.getLogger(CommunicationandNotification.class.getName());
    private Map<String, String> messages = new HashMap<>();
    private Map<String, Boolean> messageReadStatus = new HashMap<>();
    private String confirmationMessage = ""; // لتخزين رسالة التأكيد
    private boolean confirmationDisplayed = false;
    private MyApplication myApplication;

    public CommunicationandNotification(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public boolean sendMessage(String recipient, String message) {
        logger.log(Level.INFO, "Attempting to send message to {0}: {1}", new Object[]{recipient, message});
        // تسجيل الرسالة وإعداد حالة القراءة على "غير مقروء"
        messages.put(recipient, message);
        messageReadStatus.put(message, false);
        // إعداد رسالة التأكيد وعرضها
        confirmationMessage = "Message sent successfully";
        confirmationDisplayed = true;
        logger.log(Level.INFO, "Message sent and confirmation displayed: {0}", confirmationMessage);
        return true;
    }

    public String receiveMessage(String recipient) {
        String message = messages.getOrDefault(recipient, "No messages");
        logger.log(Level.INFO, "Received message for {0}: {1}", new Object[]{recipient, message});
        return message;
    }

    public boolean isMessageSentConfirmationDisplayed() {
        boolean isDisplayed = confirmationDisplayed && "Message sent successfully".equals(confirmationMessage);
        logger.log(Level.INFO, "Is message sent confirmation displayed: {0}", isDisplayed);
        return isDisplayed;
    }

    public String getConfirmationMessage() {
        logger.log(Level.INFO, "Getting confirmation message: {0}", confirmationMessage);
        return confirmationMessage;
    }

    public void markMessageAsRead(String message) {
        logger.log(Level.INFO, "Marking message as read: {0}", message);
        // تحقق من وجود الرسالة ثم تحديث حالتها
        if (messageReadStatus.containsKey(message)) {
            messageReadStatus.put(message, true);
            logger.log(Level.INFO, "Message marked as read: {0}", message);
        } else {
            logger.log(Level.WARNING, "Message not found for marking as read: {0}", message);
        }
    }

    public boolean isMessageRead(String message) {
        boolean isRead = messageReadStatus.getOrDefault(message, false);
        logger.log(Level.INFO, "Checking if message is read ({0}): {1}", new Object[]{message, isRead});
        return isRead;
    }
}
