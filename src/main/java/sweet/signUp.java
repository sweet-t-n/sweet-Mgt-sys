package sweet;

import java.util.HashSet;
import java.util.Set;

public class signUp {

    private Set<String> registeredUsers;
    private String username;
    private boolean signupSuccess = false;
    private String feedbackMessage = "";

    public signUp() {
        this.registeredUsers = new HashSet<>(); 
    }

    public boolean theUserIsNotSignedUp(String username) {
        this.setUsername(username);

        if (registeredUsers.contains(username)) {
            feedbackMessage = "Sign up failed: username already exists";
            return false;
        } else {
            registeredUsers.add(username);  // Register the new user
            feedbackMessage = "Sign up successful";
            return true;
        }
    }

   public void whenUserEntersUsernameAndPassword(String username, String password) {
    this.setUsername(username);
    feedbackMessage = "No feedback";

    if (registeredUsers.contains(username)) {
        signupSuccess = false;
        feedbackMessage = "Sign up failed: username already exists";
    } else {
        registeredUsers.add(username);  // Register the new user
        signupSuccess = true;
        feedbackMessage = "Sign up successful";
    }
}

public boolean isSignupSuccessful() {
    return signupSuccess;
}



   

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public boolean isUserRegistered(String username) {
        return registeredUsers.contains(username);
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
