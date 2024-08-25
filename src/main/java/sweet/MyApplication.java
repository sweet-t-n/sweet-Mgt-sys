package sweet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class MyApplication {
  

    private static ArrayList<User> userList = new ArrayList<>();
   
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
                  
                    break;
                case "Admin":
                   
                    break;
                case "MaterialSupplier":
                
                    break;
                default:
                  
                    break;
            }
        }
    } catch (FileNotFoundException e) {
      
    }
}
}


    

   

   
