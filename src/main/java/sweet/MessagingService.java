package sweet;

public class MessagingService {

    private String lastMessage;
    private boolean messageSent;
    private boolean confirmationDisplayed;  // New property

    public MessagingService() {
        this.lastMessage = "";
        this.messageSent = false;
        this.confirmationDisplayed = false;  // Initialize the new property
    }

    public void navigateToMessagingPage() {
        // Simulate navigating to the messaging page
        System.out.println("Navigated to the messaging page.");
    }

    public boolean sendMessage(String message) {
        // Simulate sending a message
        this.lastMessage = message;
        this.messageSent = true;  // Assume the message is sent successfully
        this.confirmationDisplayed = true;  // Set confirmation to true upon sending
        return messageSent;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    // New method to get success message
    public String getSuccessMessage() {
        return "Message sent"; 
    }

    // New method to check if confirmation is displayed
    public boolean isConfirmationDisplayed() {
        return confirmationDisplayed;
    }

    // Optional: Method to reset the state if needed
    public void reset() {
        this.lastMessage = "";
        this.messageSent = false;
        this.confirmationDisplayed = false;
    }
}
