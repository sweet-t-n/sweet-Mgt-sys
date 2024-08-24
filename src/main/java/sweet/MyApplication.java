package sweet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class MyApplication {
    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<StoreOwner> storeOwnerList = new ArrayList<>();
    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<MaterialSupplier> materialSupplierList = new ArrayList<>();
    private login login;
    private Set<String> registeredUsers = new HashSet<>();
    private String feedbackMessage;
   
    public MyApplication() {
        this.login = new login();
        this.registeredUsers = new HashSet<>();
        loadUserData();  
    }

    public boolean login(String username, String password) {
        if (registeredUsers.contains(username)) {
            login.setCredentials(username, password); // تعيين بيانات الاعتماد
            return login.login(username, password); // محاولة تسجيل الدخول
        } else {
            return false;
        }
    }

    public void logout() {
        login.logout(); // تسجيل الخروج
    }

    public boolean isLoggedIn() {
        return login.isLoggedIn();
    }

    public String getLoginFeedback() {
        return login.isLoggedIn() ? "Login successful" : "Login failed";
    }
    
    public boolean signUp(String username, String password, String email, String country) {
        if (registeredUsers.contains(username)) {
            feedbackMessage = "Sign up failed: username already exists";
            return false;
        } else {
            registeredUsers.add(username);  // تسجيل المستخدم الجديد
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
            System.out.println("User " + username + " removed successfully.");
        } else {
            System.out.println("User " + username + " not found.");
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
        boolean exists = false;
        String filePath = "users.txt"; // مسار ملف المستخدمين
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // افترض أن كل سطر يحتوي على اسم المستخدم فقط
                if (line.equals(username)) {
                    exists = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return exists;
    }

    public double applyDiscount(double price, int quantity) {
        if (quantity > 10) {
            return price * 0.10; 
        }
        return 0.0; // لا يوجد خصم
    }
    
    private void loadUserData() {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\|");
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
                }
            }
        } catch (FileNotFoundException e) {
            
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
