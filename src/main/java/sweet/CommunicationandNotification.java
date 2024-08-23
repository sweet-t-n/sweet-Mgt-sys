package sweet;

import java.util.HashMap;
import java.util.Map;

public class CommunicationandNotification {

    private Map<String, String> messages; // لتخزين الرسائل
    private Map<String, Boolean> messageStatus; // لتخزين حالة الرسائل (مقروءة/غير مقروءة)

    public CommunicationandNotification() {
        messages = new HashMap<>();
        messageStatus = new HashMap<>();
    }

    // إرسال رسالة
    public boolean sendMessage(String recipient, String message) {
        // تسجيل الرسالة المرسلة
        messages.put(recipient, message);
        messageStatus.put(message, false); // الرسالة غير مقروءة في البداية
        return true; // افترض أن الرسالة أُرسلت بنجاح
    }

    // استلام رسالة
    public String receiveMessage(String sender) {
        // افترض أنك تحصل على الرسالة بناءً على المرسل
        return messages.getOrDefault(sender, "لا توجد رسائل");
    }

    // تأكيد عرض الرسالة
    public boolean isMessageSentConfirmationDisplayed() {
        // افترض أنك تحقق من عرض التأكيد هنا
        return true; // افترض أن التأكيد دائمًا يعرض
    }

    // تعيين الرسالة كمقروءة
    public void markMessageAsRead(String message) {
        if (messageStatus.containsKey(message)) {
            messageStatus.put(message, true);
        }
    }

    // التحقق مما إذا كانت الرسالة مقروءة
    public boolean isMessageRead(String message) {
        return messageStatus.getOrDefault(message, false);
    }
}
