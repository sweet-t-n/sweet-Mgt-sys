package sweet;

public class login {
    private String username;
    private String password;
    private boolean loggedIn;

    // Default Constructor
    public login() {
        this.loggedIn = false; // Initially, no user is logged in
    }

    // Constructor with credentials and login attempt
    public login(String inputUsername, String inputPassword) {
        this(); // Call default constructor to initialize loggedIn
        setCredentials(inputUsername, inputPassword);
        this.loggedIn = validateCredentials(inputUsername, inputPassword);
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

    // Check if the user is currently logged in
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    // Log out the current user
    public void logout() {
        this.loggedIn = false;
    }
}
