package sweet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MyApplication {

    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<StoreOwner> storeOwnerList = new ArrayList<>();
    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<MaterialSupplier> materialSupplierList = new ArrayList<>();

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton signUpButton;
    private JButton signInButton;
    private JTextArea outputArea;

    public MyApplication() {
        frame = new JFrame("Sweet Management System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel roleLabel = new JLabel("Select your role:");
        roleLabel.setBounds(20, 20, 150, 25);
        frame.add(roleLabel);

        String[] roles = {"User", "Store Owner", "Admin", "Material Supplier"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(180, 20, 200, 25);
        frame.add(roleComboBox);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 60, 150, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 60, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 100, 150, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 100, 200, 25);
        frame.add(passwordField);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(100, 140, 100, 25);
        frame.add(signUpButton);

        signInButton = new JButton("Sign In");
        signInButton.setBounds(220, 140, 100, 25);
        frame.add(signInButton);

        outputArea = new JTextArea();
        outputArea.setBounds(20, 180, 350, 70);
        outputArea.setEditable(false);
        frame.add(outputArea);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignUp();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        frame.setVisible(true);
    }
    

    private void handleSignUp() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            outputArea.setText("Username and password cannot be empty.");
            return;
        }

        boolean userExists = false;

        switch (role) {
            case "User":
                userExists = userList.stream().anyMatch(u -> u.getUsername().equals(username));
                if (userExists) {
                    outputArea.setText("User already exists.");
                } else {
                    userList.add(new User(username, password));
                    outputArea.setText("User registered successfully.");
                }
                break;
            case "Store Owner":
                userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username));
                if (userExists) {
                    outputArea.setText("Store Owner already exists.");
                } else {
                    storeOwnerList.add(new StoreOwner(username, password));
                    outputArea.setText("Store Owner registered successfully.");
                }
                break;
            case "Admin":
                userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username));
                if (userExists) {
                    outputArea.setText("Admin already exists.");
                } else {
                    adminList.add(new Admin(username, password));
                    outputArea.setText("Admin registered successfully.");
                }
                break;
            case "Material Supplier":
                userExists = materialSupplierList.stream().anyMatch(u -> u.getUsername().equals(username));
                if (userExists) {
                    outputArea.setText("Material Supplier already exists.");
                } else {
                    materialSupplierList.add(new MaterialSupplier(username, password));
                    outputArea.setText("Material Supplier registered successfully.");
                }
                break;
            default:
                outputArea.setText("Invalid role selected.");
                break;
        }
    }

    private void handleSignIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            outputArea.setText("Username and password cannot be empty.");
            return;
        }

        boolean userExists = false;

        switch (role) {
            case "User":
                userExists = userList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Store Owner":
                userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Admin":
                userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Material Supplier":
                userExists = materialSupplierList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            default:
                outputArea.setText("Invalid role selected.");
                return;
        }

        if (userExists) {
            Frame1();
        } else {
            outputArea.setText("Account not found. Please check your credentials.");
        }
    }

    private void Frame1() {
        JFrame emptyFrame = new JFrame("sign in");
        emptyFrame.setSize(400, 300);
        emptyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        emptyFrame.setLayout(null);

        JLabel messageLabel = new JLabel("heloooooooooooooo");
        messageLabel.setBounds(50, 50, 300, 25);
        emptyFrame.add(messageLabel);

        emptyFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MyApplication();
    }

    // Getters and Setters for the lists
    public static ArrayList<User> getUserList() {
        return userList;
    }

    public static void setUserList(ArrayList<User> userList) {
        MyApplication.userList = userList;
    }

    public static ArrayList<StoreOwner> getStoreOwnerList() {
        return storeOwnerList;
    }

    public static void setStoreOwnerList(ArrayList<StoreOwner> storeOwnerList) {
        MyApplication.storeOwnerList = storeOwnerList;
    }

    public static ArrayList<Admin> getAdminList() {
        return adminList;
    }

    public static void setAdminList(ArrayList<Admin> adminList) {
        MyApplication.adminList = adminList;
    }

    public static ArrayList<MaterialSupplier> getMaterialSupplierList() {
        return materialSupplierList;
    }

    public static void setMaterialSupplierList(ArrayList<MaterialSupplier> materialSupplierList) {
        MyApplication.materialSupplierList = materialSupplierList;
    }
}
