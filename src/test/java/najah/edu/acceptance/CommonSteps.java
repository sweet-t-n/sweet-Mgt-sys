package najah.edu.acceptance;

import io.cucumber.java.en.Given;

import java.util.HashSet;
import java.util.Set;

public class CommonSteps {

    protected static final Set<String> loggedInUsers = new HashSet<>();

    @Given("that the user {string} is not logged in")
    public void thatTheUserIsNotLoggedIn(String username) {
        loggedInUsers.remove(username);
    }
    
    public static boolean isUserLoggedIn(String username) {
        return loggedInUsers.contains(username);
    }
}
