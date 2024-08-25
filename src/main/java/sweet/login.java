package sweet;



public class login {
    private String username;
    private String password;
    private boolean loggedIn;

    // Constructor
    public login() {
        this.loggedIn = false; 
    }

  
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    private boolean validateCredentials(String inputUsername, String inputPassword) {
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    
    public boolean authenticateAndLogin(String inputUsername, String inputPassword) {
        if (validateCredentials(inputUsername, inputPassword)) {
            this.loggedIn = true;
            return true;
        } else {
            this.loggedIn = false;
            return false;
        }
    }


   
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

   
    public void logout() {
        this.loggedIn = false;
    }
}
