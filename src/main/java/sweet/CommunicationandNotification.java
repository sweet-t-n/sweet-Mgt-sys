package sweet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CommunicationandNotification {
    private static final Logger logger = Logger.getLogger(CommunicationandNotification.class.getName());
    private Map<String, String> messages = new HashMap<>();
    private Map<String, Boolean> messageReadStatus = new HashMap<>();
    private boolean confirmationDisplayed = false;
	private MyApplication myApplication;

    public CommunicationandNotification(MyApplication myApplication) {
        this.myApplication = myApplication;
	}

	public boolean sendMessage(String recipient, String message) {
        logger.log(Level.INFO, "Sending message to {0}: {1}", new Object[]{recipient, message});
        messages.put(recipient, message);
        messageReadStatus.put(message, false); // تعيين حالة الرسالة على أنها غير مقروءة عند الإرسال
        confirmationDisplayed = true;
        return true;
    }

    public String receiveMessage(String recipient) {
        String message = messages.getOrDefault(recipient, "لا توجد رسائل");
        logger.log(Level.INFO, "Receiving message for {0}: {1}", new Object[]{recipient, message});
        return message;
    }

    public boolean isMessageSentConfirmationDisplayed() {
        return confirmationDisplayed;
    }

    public void markMessageAsRead(String message) {
        logger.log(Level.INFO, "Marking message as read: {0}", message);
        if (messages.containsValue(message)) {
            messageReadStatus.put(message, true);
        }
    }

    public boolean isMessageRead(String message) {
        logger.log(Level.INFO, "Checking if message is read: {0}", message);
        return messageReadStatus.getOrDefault(message, false);
    }
}
