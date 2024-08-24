package sweet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

public class MyApplication {
    private static final Logger logger = Logger.getLogger(MyApplication.class.getName());

    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<StoreOwner> storeOwnerList = new ArrayList<>();
    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<MaterialSupplier> materialSupplierList = new ArrayList<>();
    private Set<String> registeredUsers = new HashSet<>();
    private String feedbackMessage;
    
    private boolean loggedIn = false;

    public MyApplication() {
        this.registeredUsers = new HashSet<>();
        this.feedbackMessage = "No feedback";
        loadUserData();
    }

    public boolean login(String username) {
        if (registeredUsers.contains(username)) {
            this.loggedIn = true;
            return true;
        } else {
            this.loggedIn = false;
            return false;
        }
    }
    


    public void logout() {
        
        this.loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getLoginFeedback() {
        return isLoggedIn() ? "Login successful" : "Login failed";
    }

    public boolean signUp(String username) {
        if (registeredUsers.contains(username)) {
            feedbackMessage = "Sign up failed: username already exists";
            return false;
        } else {
            registeredUsers.add(username);
            feedbackMessage = "Sign up successful, redirected to login page";
            return true;
        }
    }

    public String getSignUpFeedback() {
        return feedbackMessage;
    }

   public void removeUser(String username) {
    if (registeredUsers.contains(username)) {
        registeredUsers.remove(username);

        logger.info(String.format("User %s removed successfully.", username));
    } else {
        
        logger.warning(String.format("User %s not found.", username));
    }
}



    public boolean simulateRedirectToLoginPage(boolean success) {
        if (success) {
            feedbackMessage = "Sign up successful, redirected to login page";
            return true;
        }
        return false;
    }

    public boolean checkIfUserExists(String username) {
        String filePath = "users.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().anyMatch(line -> line.equals(username));
        } catch (IOException e) {
            logger.severe("Error checking if user exists: " + e.getMessage());
            return false;
        }
    }

    public double applyDiscount(double price, int quantity) {
        if (quantity > 10) {
            return price * 0.10;
        }
        return 0.0;
    }

    private void loadUserData() {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\|");
                if (data.length < 5) continue; // Skip invalid lines

                switch (data[0]) {
                    case "User":
                        userList.add(new User(data[1], data[2], data[3], data[4]));
                        break;
                    case "StoreOwner":
                        storeOwnerList.add(new StoreOwner(data[1], data[2], data[3], data[4]));
                        break;
                    case "Admin":
                        adminList.add(new Admin(data[1], data[2], data[3], data[4]));
                        break;
                    case "MaterialSupplier":
                        materialSupplierList.add(new MaterialSupplier(data[1], data[2], data[3], data[4]));
                        break;
                    default:
                        logger.warning("Unknown user type: " + data[0]);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            logger.severe("Error loading user data: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new MyApplication();
    }

    public ArrayList<User> getUser(String string) {
        return null;
    }

    public void addUser(User user) {
        // TODO
    }
}
