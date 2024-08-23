package sweet;

import java.lang.System.Logger.Level;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.SimpleFormatter;

import io.cucumber.core.logging.Logger;

public class login {
    private String username;
    private String password;
    private boolean loggedIn;

    // Constructor
    public login() {
        this.loggedIn = false; // Initially, no user is logged in
    }

    // Set user credentials
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Validate credentials against the current username and password
    private boolean validateCredentials(String inputUsername, String inputPassword) {
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    // Attempt to log in with the provided username and password
    public boolean login(String inputUsername, String inputPassword) {
        if (validateCredentials(inputUsername, inputPassword)) {
            this.loggedIn = true;
            return true;
        } else {
            this.loggedIn = false;
            return false;
        }
    }

    // Check if the user is currently logged in
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    // Log out the current user
    public void logout() {
        this.loggedIn = false;
    }
}
