package sweet;
public class Login {
    private String username;
    private String password;
    private boolean loggedIn;

    
    public Login() {
        this.loggedIn = false; 
    }

  
    public Login(String inputUsername, String inputPassword) {
        this.loggedIn = false; 
        if (validateCredentials(inputUsername, inputPassword)) {
            this.loggedIn = true;
        }
    }

   
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
    private boolean validateCredentials(String inputUsername, String inputPassword) {
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

 
    public void logout() {
        this.loggedIn = false;
    }
}
