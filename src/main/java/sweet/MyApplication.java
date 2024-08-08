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
                openSignUpFrame();
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

    private void openSignUpFrame() {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(400, 400);
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setLayout(null);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField countryField = new JTextField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"User", "Store Owner", "Admin", "Material Supplier"});

        addCommonComponents(signUpFrame, usernameField, passwordField, emailField, countryField, roleComboBox);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 220, 100, 25);
        signUpFrame.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String country = countryField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || country.isEmpty()) {
                    JOptionPane.showMessageDialog(signUpFrame, "All fields must be filled.");
                    return;
                }

                boolean userExists = false;

                switch (role) {
                    case "User":
                        userExists = userList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "User already exists.");
                        } else {
                            userList.add(new User(username, password, email, country));
                            JOptionPane.showMessageDialog(signUpFrame, "User registered successfully.");
                        }
                        break;
                    case "Store Owner":
                        userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner already exists.");
                        } else {
                            storeOwnerList.add(new StoreOwner(username, password, email, country));
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner registered successfully.");
                        }
                        break;
                    case "Admin":
                        userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Admin already exists.");
                        } else {
                            adminList.add(new Admin(username, password, email, country));
                            JOptionPane.showMessageDialog(signUpFrame, "Admin registered successfully.");
                        }
                        break;
                    case "Material Supplier":
                        userExists = materialSupplierList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Material Supplier already exists.");
                        } else {
                            materialSupplierList.add(new MaterialSupplier(username, password, email, country));
                            JOptionPane.showMessageDialog(signUpFrame, "Material Supplier registered successfully.");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(signUpFrame, "Invalid role selected.");
                        break;
                }

                signUpFrame.dispose();
            }
        });

        signUpFrame.setVisible(true);
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
            openRoleSpecificFrame(role, username);
        } else {
            outputArea.setText("Account not found. Please check your credentials.");
        }
    }

    private void openRoleSpecificFrame(String role, String username) {
        JFrame roleFrame = new JFrame(role + " Dashboard");
        roleFrame.setSize(400, 300);
        roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        roleFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 400, 25);
        roleFrame.add(titleLabel);

        if (role.equals("User")) {
            JButton settingsButton = new JButton("Settings");
            settingsButton.setBounds(150, 100, 100, 25);
            roleFrame.add(settingsButton);

            settingsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSettingsFrame(username);
                }
            });
        }

        roleFrame.setVisible(true);
    }

    private void openSettingsFrame(String username) {
        JFrame settingsFrame = new JFrame("Account Settings");
        settingsFrame.setSize(400, 300);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(null);

        JLabel usernameLabel = new JLabel("New Username:");
        usernameLabel.setBounds(50, 50, 100, 25);
        settingsFrame.add(usernameLabel);

        JTextField usernameField = new JTextField(username);
        usernameField.setBounds(150, 50, 200, 25);
        settingsFrame.add(usernameField);

        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        settingsFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 25);
        settingsFrame.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 100, 25);
        settingsFrame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 150, 200, 25);
        settingsFrame.add(emailField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(50, 200, 100, 25);
        settingsFrame.add(countryLabel);

        JTextField countryField = new JTextField();
        countryField.setBounds(150, 200, 200, 25);
        settingsFrame.add(countryField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(150, 240, 120, 25);
        settingsFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newPassword = new String(passwordField.getPassword());
                String newEmail = emailField.getText();
                String newCountry = countryField.getText();

                if (newUsername.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty() || newCountry.isEmpty()) {
                    JOptionPane.showMessageDialog(settingsFrame, "All fields must be filled.");
                    return;
                }

                // Find and update the user in the list
                User user = userList.stream().filter(u -> u.getUsername().equals(username)).findFirst().orElse(null);
                if (user != null) {
                    user.setUsername(newUsername);
                    user.setPassword(newPassword);
                    user.setEmail(newEmail);
                    user.setCountry(newCountry);
                    JOptionPane.showMessageDialog(settingsFrame, "User details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "User not found.");
                }

                settingsFrame.dispose();
            }
        });

        settingsFrame.setVisible(true);
    }

    private void addCommonComponents(JFrame frame, JTextField usernameField, JPasswordField passwordField, JTextField emailField, JTextField countryField, JComboBox<String> roleComboBox) {
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 150, 25);
        frame.add(usernameLabel);

        usernameField.setBounds(180, 20, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 150, 25);
        frame.add(passwordLabel);

        passwordField.setBounds(180, 60, 200, 25);
        frame.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 150, 25);
        frame.add(emailLabel);

        emailField.setBounds(180, 100, 200, 25);
        frame.add(emailField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(20, 140, 150, 25);
        frame.add(countryLabel);

        countryField.setBounds(180, 140, 200, 25);
        frame.add(countryField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 180, 150, 25);
        frame.add(roleLabel);

        roleComboBox.setBounds(180, 180, 200, 25);
        frame.add(roleComboBox);
    }

    public static void main(String[] args) {
        new MyApplication();
    }
}
