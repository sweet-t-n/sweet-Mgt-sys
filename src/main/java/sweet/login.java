package sweet;

public class login {
    private String username;
    private String password;
    private boolean loggedIn;

    public login() {
        this.loggedIn = false;
    }

    // تعيين بيانات الاعتماد
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // التحقق من بيانات الاعتماد
    private boolean validateCredentials(String inputUsername, String inputPassword) {
        return inputUsername.equals(this.username) && inputPassword.equals(this.password);
    }

    // تسجيل الدخول
    public void login(String inputUsername, String inputPassword) {
        if (validateCredentials(inputUsername, inputPassword)) {
            this.loggedIn = true;
        } else {
            this.loggedIn = false;
        }
    }

    // التحقق من حالة تسجيل الدخول
    public boolean isLoggedIn() {
        return this.loggedIn;
    }

    // تسجيل الخروج
    public void logout() {
        this.loggedIn = false;
    }
}
