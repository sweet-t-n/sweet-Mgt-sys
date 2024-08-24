package sweet;



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

    
    public login(String inputUsername, String inputPassword) {
        if (validateCredentials(inputUsername, inputPassword)) {
            this.loggedIn = true;
           
        } else {
            this.loggedIn = false;
           
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
