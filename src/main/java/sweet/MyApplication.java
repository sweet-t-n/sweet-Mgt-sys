package sweet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
//import java.util.logging.Logger;

public class MyApplication {
    //private static final Logger logger = Logger.getLogger(MyApplication.class.getName());

    private static ArrayList<User> userList = new ArrayList<>();
    //private static ArrayList<StoreOwner> storeOwnerList = new ArrayList<>();
   // private static ArrayList<Admin> adminList = new ArrayList<>();
   // private static ArrayList<MaterialSupplier> materialSupplierList = new ArrayList<>();
    private Set<String> registeredUsers = new HashSet<>();
    private boolean loggedIn = false;

    public MyApplication() {
        this.registeredUsers = new HashSet<>();
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
    


    

    public boolean isLoggedIn() {
        return loggedIn;
    }

   

    public boolean signUp(String username) {
        if (registeredUsers.contains(username)) {
            return false;
        } else {
            registeredUsers.add(username);
            return true;
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
            if (data.length < 5) continue; 
            switch (data[0]) {
                case "User":
                    userList.add(new User(data[1], data[2], data[3], data[4]));
                    break;
                case "StoreOwner":
                   // storeOwnerList.add(new StoreOwner(data[1], data[2], data[3], data[4]));
                    break;
                case "Admin":
                   // adminList.add(new Admin(data[1], data[2], data[3], data[4]));
                    break;
                case "MaterialSupplier":
                 //   materialSupplierList.add(new MaterialSupplier(data[1], data[2], data[3], data[4]));
                    break;
                default:
                   // logger.warning(String.format("Unknown user type: %s", data[0]));
                    break;
            }
        }
    } catch (FileNotFoundException e) {
      //  logger.severe(String.format("Error loading user data: %s", e.getMessage()));
    }
}
}


    

   

   
