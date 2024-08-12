package sweet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
    
    private String imagePath;

    public MyApplication() {
        loadUserData();  
        frame = new JFrame("Sweet Management System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel roleLabel = new JLabel("Select your role:");
        roleLabel.setBounds(20, 20, 150, 25);
        frame.add(roleLabel);

        String[] roles = {"User", "Store Owner", "Admin"};
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
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"User", "Admin", "owner"});

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
                        userExists = userList.stream().anyMatch(u -> u.getName().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "User already exists.");
                        } else {
                            userList.add(new User(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "User registered successfully.");
                        }
                        break;
                    case "Store Owner":
                        userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner already exists.");
                        } else {
                            storeOwnerList.add(new StoreOwner(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner registered successfully.");
                        }
                        break;
                    case "Admin":
                        userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Admin already exists.");
                        } else {
                            adminList.add(new Admin(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "Admin registered successfully.");
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
                userExists = userList.stream().anyMatch(u -> u.getName().equals(username) && u.getPassword().equals(password));
                
                break;
            case "Store Owner":
                userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Admin":
                userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
               
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
    private void openAdminDashboard() {
        JFrame adminFrame = new JFrame("Admin Dashboard");
        adminFrame.setSize(400, 300);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome Admin", SwingConstants.CENTER);
        welcomeLabel.setBounds(0, 20, 400, 25);
        adminFrame.add(welcomeLabel);

        JButton userManagementButton = new JButton("User Management");
        userManagementButton.setBounds(50, 80, 150, 25);
        adminFrame.add(userManagementButton);

        JButton monitoringReportingButton = new JButton("Monitoring and Reporting");
        monitoringReportingButton.setBounds(50, 120, 250, 25);
        adminFrame.add(monitoringReportingButton);

        JButton contentManagementButton = new JButton("Content Management");
        contentManagementButton.setBounds(50, 160, 200, 25);
        adminFrame.add(contentManagementButton);

        userManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            	opencontentManagementFrame();   
            }
        });

        monitoringReportingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            	openMonitoringAndReportingFrame();
            }
        });

        contentManagementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Content Management frame or functionality
              openContentManagementFrame();
            	         }
        });

        adminFrame.setVisible(true);
    }
    private void openContentManagementFrame() {
        JFrame contentFrame = new JFrame("Content Management");
        contentFrame.setSize(400, 300);
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentFrame.setLayout(null);

        JButton manageRecipesAndPostsButton = new JButton("Manage Recipes and Posts");
        manageRecipesAndPostsButton.setBounds(50, 80, 200, 25);
        contentFrame.add(manageRecipesAndPostsButton);

        JButton manageFeedbackButton = new JButton("Manage Feedback");
        manageFeedbackButton.setBounds(50, 120, 200, 25);
        contentFrame.add(manageFeedbackButton);

        // Action Listeners for the new buttons
        manageRecipesAndPostsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Manage Recipes and Posts functionality
                // You can add the specific functionality here
            	openBrowseAllContentFrame(true);
            }
        });

        manageFeedbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open Manage Feedback functionality
                // You can add the specific functionality here
                showFeedback(true);
            }
        });

        contentFrame.setVisible(true);
    }
   
    private void opencontentManagementFrame() {
    	JFrame contentFrame = new JFrame("user Management");
    	contentFrame.setSize(600, 400);
    	contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	contentFrame.setLayout(null);

        JLabel titlel = new JLabel("userManagement", SwingConstants.CENTER);
        titlel.setBounds(0, 20, 600, 25);
        contentFrame.add(titlel);

        JButton ownersButton = new JButton("Management store owners");
      ownersButton.setBounds(50, 60, 250, 25);
        contentFrame.add(ownersButton);

        JButton suppliersButton = new JButton("Management raw material suppliers");
        suppliersButton.setBounds(50, 100, 250, 25);
        contentFrame.add(suppliersButton);
        ownersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Managementstoreowners();
            }
        });

        suppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	Managementrawmaterialsuppliers();
            }
        });
    	
        contentFrame.setVisible(true);
    }
    private void openFrame() {
    	JFrame contentFrame1 = new JFrame(" Management");
    	contentFrame1.setSize(600, 400);
    	contentFrame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	contentFrame1.setLayout(null);

    	 JLabel usernameLabel = new JLabel("Username:");
         usernameLabel.setBounds(20, 20, 150, 25);
         contentFrame1.add(usernameLabel);

         JTextField usernameField1 = new JTextField();
         usernameField1.setBounds(180, 20, 200, 25);
         contentFrame1.add(usernameField1);

        JButton add = new JButton("add");
        add.setBounds(50, 60, 250, 25);
          contentFrame1.add(add);

        JButton remove = new JButton("remove");
        remove.setBounds(50, 100, 250, 25);
        contentFrame1.add(remove);
       
        add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // إنشاء إطار جديد لإدخال البيانات
                    JFrame inputFrame = new JFrame("Add User");
                    inputFrame.setSize(400, 300);
                    inputFrame.setLayout(null);

                    // Label و TextField لإدخال الاسم
                    JLabel nameLabel = new JLabel("Owner Name:");
                    nameLabel.setBounds(20, 20, 150, 25);
                    inputFrame.add(nameLabel);

                    JTextField nameField = new JTextField();
                    nameField.setBounds(180, 20, 200, 25);
                    inputFrame.add(nameField);

                    JLabel passwordLabel = new JLabel("Password:");
                    passwordLabel.setBounds(20, 60, 150, 25);
                    inputFrame.add(passwordLabel);

                    JTextField passwordField = new JTextField();
                    passwordField.setBounds(180, 60, 200, 25);
                    inputFrame.add(passwordField);

                    JLabel emailLabel = new JLabel("Email:");
                    emailLabel.setBounds(20, 100, 150, 25);
                    inputFrame.add(emailLabel);

                    JTextField emailField = new JTextField();
                    emailField.setBounds(180, 100, 200, 25);
                    inputFrame.add(emailField);

                    JLabel cityLabel = new JLabel("City:");
                    cityLabel.setBounds(20, 140, 150, 25);
                    inputFrame.add(cityLabel);

                    JTextField cityField = new JTextField();
                    cityField.setBounds(180, 140, 200, 25);
                    inputFrame.add(cityField);

                    JButton submitButton = new JButton("Submit");
                    submitButton.setBounds(150, 200, 100, 25);
                    inputFrame.add(submitButton);

                    submitButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name = nameField.getText().trim();
                            String password = passwordField.getText().trim();
                            String email = emailField.getText().trim();
                            String city = cityField.getText().trim();

                            if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !city.isEmpty()) {
                                String userDetails = "owner|" + name + "|" + password + "|" + email + "|" + city;

                                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                                    writer.write(userDetails);
                                    writer.newLine();
                                    JOptionPane.showMessageDialog(inputFrame, "User added successfully.");
                                } catch (IOException ioException) {
                                    JOptionPane.showMessageDialog(inputFrame, "Error writing to file.");
                                }

                                inputFrame.dispose(); 
                            } else {
                                JOptionPane.showMessageDialog(inputFrame, "Please fill in all fields.");
                            }
                        }
                    });

                    inputFrame.setVisible(true); 
                }
            });
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameToDelete = usernameField1.getText().trim(); 

                File file = new File("users.txt");
                if (file.exists()) {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        List<String> updatedLines = lines.stream()
                                .filter(line -> !line.startsWith("owner|" + usernameToDelete + "|"))
                                .collect(Collectors.toList());

                        Files.write(file.toPath(), updatedLines);

                        JOptionPane.showMessageDialog(contentFrame1, "owner deleted successfully.");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(contentFrame1, "Error reading or writing to file.");
                    }
                } else {
                    JOptionPane.showMessageDialog(contentFrame1, "File does not exist.");
                }
            }
        });
     
           


        contentFrame1.setVisible(true);
    }
    private void openFrame1() {
    	JFrame contentFrame11 = new JFrame(" Management");
    	contentFrame11.setSize(600, 400);
    	contentFrame11.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    	contentFrame11.setLayout(null);

    	 JLabel usernameLabel1 = new JLabel("Username:");
         usernameLabel1.setBounds(20, 20, 150, 25);
         contentFrame11.add(usernameLabel1);

         JTextField usernameField11 = new JTextField();
         usernameField11.setBounds(180, 20, 200, 25);
         contentFrame11.add(usernameField11);

        JButton add1 = new JButton("add");
        add1.setBounds(50, 60, 250, 25);
          contentFrame11.add(add1);

        JButton remove1 = new JButton("remove");
        remove1.setBounds(50, 100, 250, 25);
        contentFrame11.add(remove1);
       
        add1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    JFrame inputFrame1 = new JFrame("Add User");
                    inputFrame1.setSize(400, 300);
                    inputFrame1.setLayout(null);

                     
                    JLabel nameLabel1 = new JLabel("Owner Name:");
                    nameLabel1.setBounds(20, 20, 150, 25);
                    inputFrame1.add(nameLabel1);

                    JTextField nameField1 = new JTextField();
                    nameField1.setBounds(180, 20, 200, 25);
                    inputFrame1.add(nameField1);

                    JLabel passwordLabel1 = new JLabel("Password:");
                    passwordLabel1.setBounds(20, 60, 150, 25);
                    inputFrame1.add(passwordLabel1);

                    JTextField passwordField1 = new JTextField();
                    passwordField1.setBounds(180, 60, 200, 25);
                    inputFrame1.add(passwordField1);

                    JLabel emailLabel = new JLabel("Email:");
                    emailLabel.setBounds(20, 100, 150, 25);
                    inputFrame1.add(emailLabel);

                    JTextField emailField1 = new JTextField();
                    emailField1.setBounds(180, 100, 200, 25);
                    inputFrame1.add(emailField1);

                    JLabel cityLabel1 = new JLabel("City:");
                    cityLabel1.setBounds(20, 140, 150, 25);
                    inputFrame1.add(cityLabel1);

                    JTextField cityField1 = new JTextField();
                    cityField1.setBounds(180, 140, 200, 25);
                    inputFrame1.add(cityField1);

                    JButton submitButton1 = new JButton("Submit");
                    submitButton1.setBounds(150, 200, 100, 25);
                    inputFrame1.add(submitButton1);

                    submitButton1.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String name = nameField1.getText().trim();
                            String password = passwordField1.getText().trim();
                            String email = emailField1.getText().trim();
                            String city = cityField1.getText().trim();

                            if (!name.isEmpty() && !password.isEmpty() && !email.isEmpty() && !city.isEmpty()) {
                            	
                                String userDetails = "suppliers|" + name + "|" + password + "|" + email + "|" + city;

                                try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                                    writer.write(userDetails);
                                    writer.newLine();
                                    JOptionPane.showMessageDialog(inputFrame1, "User added successfully.");
                                } catch (IOException ioException) {
                                    JOptionPane.showMessageDialog(inputFrame1, "Error writing to file.");
                                }

                                inputFrame1.dispose(); 
                            } else {
                                JOptionPane.showMessageDialog(inputFrame1, "Please fill in all fields.");
                            }
                        }
                    });

                    inputFrame1.setVisible(true); 
                }
            });
        remove1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameToDelete = usernameField11.getText().trim(); 

                File file = new File("users.txt");
                if (file.exists()) {
                    try {
                        List<String> lines = Files.readAllLines(file.toPath());
                        List<String> updatedLines = lines.stream()
                                .filter(line -> !line.startsWith("suppliers|" + usernameToDelete + "|"))
                                .collect(Collectors.toList());

                        Files.write(file.toPath(), updatedLines);

                        JOptionPane.showMessageDialog(contentFrame11, "owner deleted successfully.");
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(contentFrame11, "Error reading or writing to file.");
                    }
                } else {
                    JOptionPane.showMessageDialog(contentFrame11, "File does not exist.");
                }
            }
        });
     
           


        contentFrame11.setVisible(true);
    }
    private void Managementstoreowners() {
        
    	openFrame();
       
    }

    private void Managementrawmaterialsuppliers() {
        
    	openFrame1();
        
    }

    private void openMonitoringAndReportingFrame() {
        JFrame reportFrame = new JFrame("Monitoring and Reporting");
        reportFrame.setSize(600, 400);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Monitoring and Reporting", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 25);
        reportFrame.add(titleLabel);

        JButton profitsButton = new JButton("Generate Financial Reports");
        profitsButton.setBounds(50, 60, 250, 25);
        reportFrame.add(profitsButton);

        JButton bestSellingButton = new JButton("Identify Best-Selling Products");
        bestSellingButton.setBounds(50, 100, 250, 25);
        reportFrame.add(bestSellingButton);

        JButton cityStatsButton = new JButton("User Statistics by City");
        cityStatsButton.setBounds(50, 140, 250, 25);
        reportFrame.add(cityStatsButton);

        profitsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateFinancialReports();
            }
        });

        bestSellingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                identifyBestSellingProducts();
            }
        });

        cityStatsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayUserStatsByCity();
            }
        });

        reportFrame.setVisible(true);
    }
    private void generateFinancialReports() {
        Map<String, Double> storeProfits = new HashMap<>();
        
        // Read sales data from a file
        try (BufferedReader reader = new BufferedReader(new FileReader("sale.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String store = parts[0];
                double amount = Double.parseDouble(parts[2]);

                storeProfits.put(store, storeProfits.getOrDefault(store, 0.0) + amount);
            }

            // Display the report
            displayReport(storeProfits);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading sales data file.");
            e.printStackTrace();
        }
    }
    
    private void displayReport(Map<String, Double> storeProfits) {
        // Create a new JFrame to display the report
        JFrame reportFrame = new JFrame("Financial Report");
        reportFrame.setSize(400, 300);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        reportFrame.setLayout(new BoxLayout(reportFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Display the report data
        JTextArea reportArea = new JTextArea();
        reportArea.setEditable(false);
        
        StringBuilder reportContent = new StringBuilder("Store Profits:\n");
        for (Map.Entry<String, Double> entry : storeProfits.entrySet()) {
            reportContent.append("Store: ").append(entry.getKey())
                         .append(", Profit: ").append(entry.getValue()).append("\n");
        }
        
        reportArea.setText(reportContent.toString());
        JScrollPane scrollPane = new JScrollPane(reportArea);
        reportFrame.add(scrollPane);

        reportFrame.setVisible(true);
    }
    private void displayUserStatsByCity() {
        Map<String, Integer> cityStats = new HashMap<>();
        
        // قراءة بيانات المستخدمين من الملف
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && "User".equals(parts[0])) {  // التأكد من أن السطر يحتوي على التصنيف الصحيح
                    String city = parts[4];  // المدينة في الجزء 4 من السطر
                    cityStats.put(city, cityStats.getOrDefault(city, 0) + 1);
                }
            }
            
            
            JFrame statsFrame = new JFrame("User Statistics by City");
            statsFrame.setSize(400, 300);
            statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            statsFrame.setLayout(new BoxLayout(statsFrame.getContentPane(), BoxLayout.Y_AXIS));
            
            JTextArea statsArea = new JTextArea();
            statsArea.setEditable(false);
            
            StringBuilder reportContent = new StringBuilder("User Statistics by City:\n");
            for (Map.Entry<String, Integer> entry : cityStats.entrySet()) {
                reportContent.append("City: ").append(entry.getKey())
                             .append(", Number of Users: ").append(entry.getValue())
                             .append("\n");
            }
            
            statsArea.setText(reportContent.toString());
            JScrollPane scrollPane = new JScrollPane(statsArea);
            statsFrame.add(scrollPane);
            
            statsFrame.setVisible(true);
            
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading users data file.");
            e.printStackTrace();
        }
    }


    private void identifyBestSellingProducts() {
        Map<String, Map<String, Integer>> storeProductSales = new HashMap<>();
        Map<String, String[]> productDetails = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("sale.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String store = parts[0];
                String product = parts[1];
                int quantity = Integer.parseInt(parts[2]);
                String price = parts[3];

                storeProductSales
                    .computeIfAbsent(store, k -> new HashMap<>())
                    .put(product, storeProductSales.get(store).getOrDefault(product, 0) + quantity);

                // Store the product details (product name and price)
                productDetails.put(product, new String[]{product, price});
            }

            displayBestSellingProducts(storeProductSales, productDetails);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading sales data file.");
            e.printStackTrace();
        }
    }

    private void displayBestSellingProducts(Map<String, Map<String, Integer>> storeProductSales, Map<String, String[]> productDetails) {
        JFrame bestSellingFrame = new JFrame("Best-Selling Products");
        bestSellingFrame.setSize(400, 300);
        bestSellingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        bestSellingFrame.setLayout(new BorderLayout());

        JTextArea bestSellingArea = new JTextArea();
        bestSellingArea.setEditable(false);

        StringBuilder reportContent = new StringBuilder("Best-Selling Products by Store:\n");
        for (Map.Entry<String, Map<String, Integer>> storeEntry : storeProductSales.entrySet()) {
            String store = storeEntry.getKey();
            Map<String, Integer> productSales = storeEntry.getValue();

            String bestSellingProduct = null;
            int maxSales = 0;

            for (Map.Entry<String, Integer> productEntry : productSales.entrySet()) {
                if (productEntry.getValue() > maxSales) {
                    maxSales = productEntry.getValue();
                    bestSellingProduct = productEntry.getKey();
                }
            }

            // Display the best-selling product first
            String[] bestProductDetails = productDetails.get(bestSellingProduct);
            reportContent.append("Store: ").append(store)
                         .append("\nBest-Selling Product: ").append(bestProductDetails[0])
                         .append(" (Best-Selling)")
                         .append("\nQuantity Sold: ").append(maxSales)
                         .append("\nPrice: ").append(bestProductDetails[1])
                         .append("\n---------------------------\n");

            // Display the rest of the products
            for (Map.Entry<String, Integer> productEntry : productSales.entrySet()) {
                if (!productEntry.getKey().equals(bestSellingProduct)) {
                    String[] otherProductDetails = productDetails.get(productEntry.getKey());
                    reportContent.append("Product: ").append(otherProductDetails[0])
                                 .append("\nQuantity Sold: ").append(productEntry.getValue())
                                 .append("\nPrice: ").append(otherProductDetails[1])
                                 .append("\n---------------------------\n");
                }
            }
        }

        bestSellingArea.setText(reportContent.toString());
        bestSellingFrame.add(new JScrollPane(bestSellingArea), BorderLayout.CENTER);
        bestSellingFrame.setVisible(true);
    }

    
    private void openRoleSpecificFrame(String role, String username) {

        if (role.equals("User")) {
            JFrame roleFrame = new JFrame(role + " Dashboard");
            roleFrame.setSize(850, 800);
            roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            roleFrame.setLayout(null);

            JLabel titleLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
            titleLabel.setBounds(0, 20, 600, 25);
            roleFrame.add(titleLabel);
            
            JButton settingsButton = new JButton("Settings");
            settingsButton.setBounds(50, 50, 100, 25);
            roleFrame.add(settingsButton);

            JButton postButton = new JButton("Post and Share");
            postButton.setBounds(160, 50, 150, 25);
            roleFrame.add(postButton);

            JButton buyFromStoresButton = new JButton("Buy from Stores");
            buyFromStoresButton.setBounds(320, 50, 150, 25);
            roleFrame.add(buyFromStoresButton);
            
            JButton browseButton = new JButton("Browse All Content");
            browseButton.setBounds(480, 50, 150, 25);
            roleFrame.add(browseButton);
            
            JButton feedbackButton = new JButton("Feedback");
            feedbackButton.setBounds(640, 50, 100, 25);
            roleFrame.add(feedbackButton);
            
            JButton communicateButton = new JButton("Communicate");
            communicateButton.setBounds(520, 100, 150, 25);
            roleFrame.add(communicateButton);

            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setBounds(50, 100, 400, 600);
            roleFrame.add(scrollPane);

            loadPosts(username, imagePanel); // Load posts for the user

            postButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openPostFrame(username, imagePanel, roleFrame);
                }
            });

            settingsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSettingsFrame(username);
                }
            });

            buyFromStoresButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openBuyFromStoresFrame(username);
                }
            });

            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openBrowseAllContentFrame(false);
                }
            });
            
            feedbackButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openFeedbackFrame(username);
                }
            });
            
            communicateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    communicateWithStoreOwner();
                }
            });

            roleFrame.setVisible(true);

        } else if (role.equals("Store Owner")) {
            JFrame roleFrame = new JFrame(role + " Dashboard");
            roleFrame.setSize(600, 800);
            roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            roleFrame.setLayout(null);

            JLabel titleLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
            titleLabel.setBounds(0, 20, 600, 25);
            roleFrame.add(titleLabel);

            JButton addProductButton = new JButton("Add Product");
            addProductButton.setBounds(50, 50, 150, 25);
            roleFrame.add(addProductButton);

            JButton viewMessagesButton = new JButton("View Messages");
            viewMessagesButton.setBounds(220, 50, 150, 25);
            roleFrame.add(viewMessagesButton);

            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(productPanel);
            scrollPane.setBounds(50, 100, 500, 600);
            roleFrame.add(scrollPane);

            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openAddProductFrame(username, productPanel);
                }
            });

            viewMessagesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openViewMessagesFrame(username);
                }
            });

            roleFrame.setVisible(true);

        } else if (role.equals("Admin")) {
            openAdminDashboard();
        }
    }
    
    //////////////////////////////////////////////////////////////
    //msg user and owner
    private void openViewMessagesFrame(String username) {
        JFrame messagesFrame = new JFrame("View Messages");
        messagesFrame.setSize(800, 600);
        messagesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        messagesFrame.setLayout(new BorderLayout());

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(messagePanel);
        messagesFrame.add(scrollPane, BorderLayout.CENTER);

        try {
            Map<String, String> messagesMap = getMessagesForStoreOwner(username);
            for (Map.Entry<String, String> entry : messagesMap.entrySet()) {
                String messageId = entry.getKey();
                String messageContent = entry.getValue();

                JPanel singleMessagePanel = new JPanel();
                singleMessagePanel.setLayout(new BorderLayout());

                JTextArea messageArea = new JTextArea(messageContent);
                messageArea.setEditable(false);
                messageArea.setLineWrap(true);
                messageArea.setWrapStyleWord(true);

                JButton replyButton = new JButton("Reply");
                replyButton.setFont(new Font("Arial", Font.BOLD, 14));
                replyButton.setBackground(new Color(70, 130, 180));
                replyButton.setForeground(Color.WHITE);
                replyButton.setFocusPainted(false);
                replyButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        openReplyMessageFrame(messageId, messageContent);
                    }
                });

                singleMessagePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
                singleMessagePanel.add(replyButton, BorderLayout.EAST);

                messagePanel.add(singleMessagePanel);
                messagePanel.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(messagesFrame, "Error loading messages!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        messagesFrame.setVisible(true);
    }

    // فتح نافذة الرد على رسالة محددة
    private void openReplyMessageFrame(String messageId, String messageContent) {
        JFrame replyFrame = new JFrame("Reply to Message");
        replyFrame.setSize(600, 400);
        replyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        replyFrame.setLayout(new BorderLayout());

        JTextArea replyArea = new JTextArea(10, 40);
        replyArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(replyArea);
        replyFrame.add(scrollPane, BorderLayout.CENTER);

        JLabel messageLabel = new JLabel("Replying to: " + messageContent);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        replyFrame.add(messageLabel, BorderLayout.NORTH);

        JButton sendReplyButton = new JButton("Send Reply");
        sendReplyButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendReplyButton.setBackground(new Color(34, 139, 34));
        sendReplyButton.setForeground(Color.WHITE);
        sendReplyButton.setFocusPainted(false);

        sendReplyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String reply = replyArea.getText().trim();
                if (!reply.isEmpty()) {
                    try {
                        saveResponseToFile(messageId, reply);
                        JOptionPane.showMessageDialog(replyFrame, "Reply sent successfully!");
                        replyFrame.dispose();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(replyFrame, "Error saving reply!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(replyFrame, "Please enter a reply message!", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendReplyButton);
        replyFrame.add(buttonPanel, BorderLayout.SOUTH);

        replyFrame.setVisible(true);
    }

   
    private Map<String, String> getMessagesForStoreOwner(String username) throws IOException {
        Map<String, String> messages = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("messages.txt"))) {
            String line;
            String currentMessageId = null;
            StringBuilder messageContent = new StringBuilder();
            boolean isOwnerMessage = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    currentMessageId = line.substring(4).trim();
                } else if (line.startsWith("Name: ") && line.substring(6).trim().equals(username)) {
                    isOwnerMessage = true;
                } else if (line.startsWith("Message: ")) {
                    if (isOwnerMessage && currentMessageId != null) {
                        messageContent.append(line.substring(9)).append("\n");
                    }
                } else if (line.equals("----") && isOwnerMessage) {
                    if (currentMessageId != null) {
                        messages.put(currentMessageId, messageContent.toString().trim());
                    }
                    currentMessageId = null;
                    messageContent.setLength(0);
                    isOwnerMessage = false;
                }
            }
        }
        return messages;
    }

    ////////////////////////
    public static void communicateWithStoreOwner() {
        JFrame frame = new JFrame("Communicate with Store Owners");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel selectionPanel = new JPanel();
        selectionPanel.setLayout(new BoxLayout(selectionPanel, BoxLayout.Y_AXIS));

        JLabel typeLabel = new JLabel("Type: StoreOwner");
        selectionPanel.add(typeLabel);

        JComboBox<String> nameComboBox = new JComboBox<>();
        nameComboBox.addItem("Select Name");
        selectionPanel.add(nameComboBox);

        frame.add(selectionPanel, BorderLayout.NORTH);

        JTextArea messageArea = new JTextArea(10, 40);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(messageArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send Message");
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        sendButton.setBackground(new Color(70, 130, 180));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedName = (String) nameComboBox.getSelectedItem();
                String message = messageArea.getText().trim();

                if ("Select Name".equals(selectedName) || message.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please select a name and enter a message!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    try {
                        saveMessageToFile("StoreOwner", selectedName, message);
                        JOptionPane.showMessageDialog(frame, "Message sent successfully! You can check for replies later.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(frame, "Error communicating with files!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JButton checkRepliesButton = new JButton("Check Replies");
        checkRepliesButton.setFont(new Font("Arial", Font.BOLD, 14));
        checkRepliesButton.setBackground(new Color(34, 139, 34));
        checkRepliesButton.setForeground(Color.WHITE);
        checkRepliesButton.setFocusPainted(false);

        checkRepliesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String replies = getResponsesForUserMessages();
                    JOptionPane.showMessageDialog(frame, "Replies:\n" + replies, "Replies", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error reading responses file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);
        buttonPanel.add(checkRepliesButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        updateNameComboBox("StoreOwner", nameComboBox);

        frame.setVisible(true);
    }

    private static void updateNameComboBox(String type, JComboBox<String> nameComboBox) {
        nameComboBox.removeAllItems();
        nameComboBox.addItem("Select Name");

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5 && parts[0].equalsIgnoreCase(type)) {
                    nameComboBox.addItem(parts[1]); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error reading users file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void saveMessageToFile(String type, String name, String message) throws IOException {
        String messageId = String.valueOf(System.currentTimeMillis());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("messages.txt", true))) {
            writer.write("ID: " + messageId);
            writer.newLine();
            writer.write("Type: " + type);
            writer.newLine();
            writer.write("Name: " + name);
            writer.newLine();
            writer.write("Message: " + message);
            writer.newLine();
            writer.write("----");
            writer.newLine();
        }
    }

    private static void saveResponseToFile(String messageId, String response) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("response.txt", true))) {
            writer.write("MessageID: " + messageId);
            writer.newLine();
            writer.write("Response: " + response);
            writer.newLine();
            writer.write("----");
            writer.newLine();
        }
    }

    private static String getResponsesForUserMessages() throws IOException {
        StringBuilder result = new StringBuilder();
        try (BufferedReader messageReader = new BufferedReader(new FileReader("messages.txt"));
             BufferedReader responseReader = new BufferedReader(new FileReader("response.txt"))) {

            Map<String, StringBuilder> messageResponses = new HashMap<>();
            String line;
            String currentMessageId = null;

            while ((line = messageReader.readLine()) != null) {
                if (line.startsWith("ID: ")) {
                    currentMessageId = line.substring(4).trim();
                    messageResponses.put(currentMessageId, new StringBuilder());
                } else if (line.startsWith("Message: ")) {
                    messageResponses.get(currentMessageId).append("Message: ").append(line.substring(9)).append("\n");
                }
            }

            while ((line = responseReader.readLine()) != null) {
                if (line.startsWith("MessageID: ")) {
                    String messageId = line.substring(11).trim();
                    StringBuilder responseBuilder = new StringBuilder();
                    while ((line = responseReader.readLine()) != null && !line.equals("----")) {
                        responseBuilder.append(line).append("\n");
                    }
                    if (messageResponses.containsKey(messageId)) {
                        messageResponses.get(messageId).append("Response: ").append(responseBuilder.toString()).append("\n");
                    }
                }
            }

            for (StringBuilder sb : messageResponses.values()) {
                result.append(sb.toString()).append("----\n");
            }
        }
        return result.toString().trim(); 
    }


    ///////////////////////////////////////////////////////////////////////
    //feedback user
    private void openFeedbackFrame(String username) {
        JFrame feedbackFrame = new JFrame("Feedback");
        feedbackFrame.setSize(600, 500);
        feedbackFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedbackFrame.setLayout(new BorderLayout(10, 10));

        JLabel headerLabel = new JLabel("Provide Feedback for Your Purchases", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        feedbackFrame.add(headerLabel, BorderLayout.NORTH);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        class Purchase {
            String productName;
            String storeOwner;

            Purchase(String productName, String storeOwner) {
                this.productName = productName;
                this.storeOwner = storeOwner;
            }
        }

        List<Purchase> purchases = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("purchase_report.txt"))) {
            boolean userSectionFound = false;
            String currentStoreOwner = "";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.startsWith("User:")) {
                    String currentUser = line.substring(5).trim();
                    if (currentUser.equals(username)) {
                        userSectionFound = true;
                    } else {
                        userSectionFound = false;
                    }
                } else if (userSectionFound) {
                    if (line.startsWith("Store Owner:")) {
                        currentStoreOwner = line.substring(12).trim();
                    } else if (line.startsWith("|")) {
                        String[] parts = line.split("\\|");
                        if (parts.length >= 2) {
                            String productName = parts[1].trim();
                            purchases.add(new Purchase(productName, currentStoreOwner));
                        }
                    } else if (line.startsWith("------------------------------------------------------------")) {
                        userSectionFound = false;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(feedbackFrame, "Error reading purchases file!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (purchases.isEmpty()) {
            JOptionPane.showMessageDialog(feedbackFrame, "No purchases found for user: " + username, "Info", JOptionPane.INFORMATION_MESSAGE);
            feedbackFrame.dispose();
            return;
        }

        for (Purchase purchase : purchases) {
            JPanel productPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

            JLabel productLabel = new JLabel(purchase.productName);
            productLabel.setPreferredSize(new Dimension(200, 25));
            productLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            JTextField feedbackField = new JTextField(25);

            productPanel.add(productLabel);
            productPanel.add(feedbackField);

            feedbackPanel.add(productPanel);
        }

        JButton saveButton = new JButton("Save Feedback");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(70, 130, 180));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        saveButton.setFocusPainted(false);

        JButton showButton = new JButton("Show Feedback");
        showButton.setFont(new Font("Arial", Font.BOLD, 14));
        showButton.setBackground(new Color(34, 139, 34));
        showButton.setForeground(Color.WHITE);
        showButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        showButton.setFocusPainted(false);

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFeedback(false);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
                    Component[] components = feedbackPanel.getComponents();
                    int index = 0;
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            JPanel productPanel = (JPanel) component;
                            Component[] innerComponents = productPanel.getComponents();
                            if (innerComponents.length >= 2) {
                                JLabel productLabel = (JLabel) innerComponents[0];
                                JTextField feedbackField = (JTextField) innerComponents[1];
                                String feedback = feedbackField.getText().trim();

                                if (!feedback.isEmpty()) {
                                    Purchase purchase = purchases.get(index);
                                    writer.write("User: " + username);
                                    writer.newLine();
                                    writer.write("Store Owner: " + purchase.storeOwner);
                                    writer.newLine();
                                    writer.write("Product: " + productLabel.getText());
                                    writer.newLine();
                                    writer.write("Feedback: " + feedback);
                                    writer.newLine();
                                    writer.write("------------------------------------------------------------");
                                    writer.newLine();
                                }
                                index++;
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(feedbackFrame, "Feedback saved successfully!");
                    feedbackFrame.dispose();

                    // Show feedback after saving
                    showFeedback(false);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(feedbackFrame, "Error saving feedback!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(showButton);

        feedbackFrame.add(new JScrollPane(feedbackPanel), BorderLayout.CENTER);
        feedbackFrame.add(buttonPanel, BorderLayout.SOUTH);

        feedbackFrame.setVisible(true);
    }

    private void showFeedback(boolean isAdmin) {
        JFrame feedbackDisplayFrame = new JFrame("Feedback Display");
        feedbackDisplayFrame.setSize(600, 400);
        feedbackDisplayFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        feedbackDisplayFrame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Feedback Entries", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        feedbackDisplayFrame.add(titleLabel, BorderLayout.NORTH);

        JPanel feedbackPanel = new JPanel();
        feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
        feedbackPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<String> feedbackEntries = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File("feedback.txt"))) {
            StringBuilder feedback = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("------------------------------------------------------------")) {
                    feedbackEntries.add(feedback.toString());
                    feedback = new StringBuilder();
                } else {
                    feedback.append(line).append("\n");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(feedbackDisplayFrame, "Error reading feedback file!", "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (String entry : feedbackEntries) {
            JPanel entryPanel = new JPanel(new BorderLayout());
            JTextArea feedbackTextArea = new JTextArea(entry);
            feedbackTextArea.setEditable(false);
            feedbackTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
            feedbackTextArea.setLineWrap(true);
            feedbackTextArea.setWrapStyleWord(true);
            feedbackTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            entryPanel.add(new JScrollPane(feedbackTextArea), BorderLayout.CENTER);

            if (isAdmin) {
                JButton deleteButton = new JButton("Delete");
                deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
                deleteButton.setBackground(new Color(220, 20, 60));
                deleteButton.setForeground(Color.WHITE);
                deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
                deleteButton.setFocusPainted(false);

                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        feedbackPanel.remove(entryPanel);
                        feedbackPanel.revalidate();
                        feedbackPanel.repaint();
                        feedbackEntries.remove(entry);
                        saveFeedbackToFile(feedbackEntries);
                    }
                });

                entryPanel.add(deleteButton, BorderLayout.EAST);
            }

            feedbackPanel.add(entryPanel);
        }

        JScrollPane scrollPane = new JScrollPane(feedbackPanel);
        feedbackDisplayFrame.add(scrollPane, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // OK Button
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(70, 130, 180));
        okButton.setForeground(Color.WHITE);
        okButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        okButton.setFocusPainted(false);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                JOptionPane.showMessageDialog(feedbackDisplayFrame, "Operation succeeded!", "Success", JOptionPane.INFORMATION_MESSAGE);
               
            }
        });
        buttonPanel.add(okButton);

        feedbackDisplayFrame.add(buttonPanel, BorderLayout.SOUTH);

        feedbackDisplayFrame.setVisible(true);
    }

    private void saveFeedbackToFile(List<String> feedbackEntries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt"))) {
            for (String entry : feedbackEntries) {
                writer.write(entry);
                writer.write("------------------------------------------------------------");
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error saving feedback!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    
    
////////////////////////////////////////////////////////////////////

    
    private void openBrowseAllContentFrame(boolean isAdmin) {
        JFrame browseFrame = new JFrame("Browse All Content");
        browseFrame.setSize(800, 600); 
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(null);

        JLabel titleLabel = new JLabel("All Available Content", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 800, 25);
        browseFrame.add(titleLabel);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(50, 60, 100, 25);
        browseFrame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(150, 60, 200, 25);
        browseFrame.add(searchField);

        JLabel filterLabel = new JLabel("Filter by Dietary Needs:");
        filterLabel.setBounds(50, 100, 150, 25);
        browseFrame.add(filterLabel);

        JComboBox<String> dietaryFilterComboBox = new JComboBox<>(new String[] {"All", "Gluten-Free", "Nut-Free", "Dairy-Free"});
        dietaryFilterComboBox.setBounds(200, 100, 250, 25);
        browseFrame.add(dietaryFilterComboBox);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(470, 60, 100, 25);
        browseFrame.add(searchButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(50, 140, 700, 400);
        browseFrame.add(scrollPane);

        // Load content initially
        loadAllContent(contentPanel, "", "All", isAdmin);

        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                String dietaryNeeds = (String) dietaryFilterComboBox.getSelectedItem();
                loadAllContent(contentPanel, searchQuery, dietaryNeeds, isAdmin);
            }
        });

        browseFrame.setVisible(true);
    }
    private void loadAllContent(JPanel panel, String searchQuery, String dietaryNeeds, boolean isAdmin) {
        panel.removeAll(); // Clear existing content

        try (BufferedReader reader = new BufferedReader(new FileReader("content.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) { // Ensure the line has the correct format
                    String username = parts[0];
                    String imagePath = parts[1];
                    String description = parts[2];

                    boolean matchesSearchQuery = description.toLowerCase().contains(searchQuery.toLowerCase());
                    boolean matchesDietaryNeeds = dietaryNeeds.equals("All") || description.toLowerCase().contains(dietaryNeeds.toLowerCase());

                    if (matchesSearchQuery && matchesDietaryNeeds) {
                        JPanel contentItemPanel = new JPanel();
                        contentItemPanel.setLayout(new BoxLayout(contentItemPanel, BoxLayout.Y_AXIS));
                        contentItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding around each item

                        JLabel usernameLabel = new JLabel("User: " + username);
                        contentItemPanel.add(usernameLabel);

                        JLabel sweetNameLabel = new JLabel("Sweet Name: " + description);
                        contentItemPanel.add(sweetNameLabel);

                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        contentItemPanel.add(imageLabel);

                        JLabel descriptionLabel = new JLabel("Description: " + description);
                        contentItemPanel.add(descriptionLabel);

                        if (isAdmin) {
                            JButton deleteButton = new JButton("Delete");
                            deleteButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Confirm delete
                                    int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this item?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                                    if (confirm == JOptionPane.YES_OPTION) {
                                    	deleteContent(username, imagePath, description);
                                        loadAllContent(panel, searchQuery, dietaryNeeds, isAdmin); // Reload content
                                    }
                                }
                            });
                            contentItemPanel.add(deleteButton);

                            JButton updateButton = new JButton("Update");
                            updateButton.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    // Implement update functionality here
                                    openUpdateFrame(username, imagePath, description);

                                }
                            });
                            contentItemPanel.add(updateButton);

                                             }

                        panel.add(contentItemPanel);
                        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between items
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }

        panel.revalidate(); // Refresh the panel to show new content
        panel.repaint();
    }
    private void openUpdateFrame(String oldUsername, String oldImagePath, String oldDescription) {
        JFrame updateFrame = new JFrame("Update Content");
        updateFrame.setSize(400, 300);
        updateFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        updateFrame.setLayout(null);

        JLabel usernameLabel = new JLabel("User:");
        usernameLabel.setBounds(20, 20, 80, 25);
        updateFrame.add(usernameLabel);

        JTextField usernameField = new JTextField(oldUsername);
        usernameField.setBounds(100, 20, 250, 25);
        updateFrame.add(usernameField);

        JLabel imagePathLabel = new JLabel("Image Path:");
        imagePathLabel.setBounds(20, 60, 80, 25);
        updateFrame.add(imagePathLabel);

        JTextField imagePathField = new JTextField(oldImagePath);
        imagePathField.setBounds(100, 60, 250, 25);
        updateFrame.add(imagePathField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 100, 80, 25);
        updateFrame.add(descriptionLabel);

        JTextField descriptionField = new JTextField(oldDescription);
        descriptionField.setBounds(100, 100, 250, 25);
        updateFrame.add(descriptionField);

        JButton saveButton = new JButton("Save");
        saveButton.setBounds(150, 150, 100, 25);
        updateFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newImagePath = imagePathField.getText();
                String newDescription = descriptionField.getText();

                updateContent(oldUsername, oldImagePath, oldDescription, newUsername, newImagePath, newDescription);
                updateFrame.dispose(); // Close the update frame
            }
        });

        updateFrame.setVisible(true);
    }
    private void updateContent(String oldUsername, String oldImagePath, String oldDescription, String newUsername, String newImagePath, String newDescription) {
        File inputFile = new File("content.txt");
        File tempFile = new File("tempContent.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String fileUsername = parts[0];
                    String fileImagePath = parts[1];
                    String fileDescription = parts[2];

                    // Update line if it matches the old content
                    if (fileUsername.equals(oldUsername) && fileImagePath.equals(oldImagePath) && fileDescription.equals(oldDescription)) {
                        writer.write(newUsername + "|" + newImagePath + "|" + newDescription);
                    } else {
                        writer.write(line);
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }

        // Replace the original file with the updated file
        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }
    }


    private void deleteContent(String username, String imagePath, String description) {
        File inputFile = new File("content.txt");
        File tempFile = new File("tempContent.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) {
                    String fileUsername = parts[0];
                    String fileImagePath = parts[1];
                    String fileDescription = parts[2];

                   
                    if (!(fileUsername.equals(username) && fileImagePath.equals(imagePath) && fileDescription.equals(description))) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions
        }

        // Replace the original file with the updated file
        if (!inputFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }
        if (!tempFile.renameTo(inputFile)) {
            System.out.println("Could not rename file");
        }
    }

//////////////////////////////////////////////////////////////////////////////////
    private void openAddProductFrame(String storeOwnerName, JPanel productPanel) {
        JFrame addProductFrame = new JFrame("Add Product");
        addProductFrame.setSize(400, 400); 
        addProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addProductFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        addProductFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 25);
        addProductFrame.add(nameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 70, 100, 25);
        addProductFrame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(150, 70, 200, 25);
        addProductFrame.add(priceField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(30, 110, 100, 25);
        addProductFrame.add(descriptionLabel);

        JTextField descriptionField = new JTextField();
        descriptionField.setBounds(150, 110, 200, 25);
        addProductFrame.add(descriptionField);

        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setBounds(30, 150, 100, 25);
        addProductFrame.add(imageLabel);

        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.setBounds(150, 150, 150, 25);
        addProductFrame.add(chooseImageButton);

        JLabel imagePreview = new JLabel();
        imagePreview.setBounds(150, 180, 200, 100); // مساحة عرض الصورة
        addProductFrame.add(imagePreview);

        JButton saveButton = new JButton("Save Product");
        saveButton.setBounds(150, 300, 150, 25);
        addProductFrame.add(saveButton);

        final String[] imagePath = {null};

        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath[0] = selectedFile.getAbsolutePath();
                    ImageIcon imageIcon = new ImageIcon(imagePath[0]);
                    Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                    imagePreview.setIcon(new ImageIcon(image));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = nameField.getText();
                String productPrice = priceField.getText();
                String productDescription = descriptionField.getText();

                if (!productName.isEmpty() && !productPrice.isEmpty() && !productDescription.isEmpty() && imagePath[0] != null) {
                    saveProduct(storeOwnerName, productName, productPrice, productDescription, imagePath[0]);

                    // Refresh the productPanel to display the new product
                    loadStoreOwnerProducts(storeOwnerName, productPanel);

                    addProductFrame.dispose(); // إغلاق النافذة بعد الحفظ
                } else {
                    JOptionPane.showMessageDialog(addProductFrame, "Please fill in all fields and choose an image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addProductFrame.setVisible(true);
    }

/////////////////////////////////////////////////////////////////////////////////
    private void saveProduct(String storeOwnerName, String productName, String productPrice, String productDescription, String imagePath) {
        try {
            FileWriter fw = new FileWriter("products.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(storeOwnerName + "|" + productName + "|" + productPrice + "|" + productDescription + "|" + imagePath);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////////////////////
    private void loadStoreOwnerProducts(String storeOwnerName, JPanel productPanel) {
        productPanel.removeAll();          

        try {
            FileReader fr = new FileReader("products.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                if (productDetails[0].equals(storeOwnerName)) {
                    JPanel productItemPanel = new JPanel();
                    productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.Y_AXIS));
                    productItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    // عرض الصورة
                    if (productDetails.length > 4) {
                        ImageIcon imageIcon = new ImageIcon(productDetails[4]);
                        Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        productItemPanel.add(imageLabel);
                    }

                    // عرض الاسم والسعر
                    JLabel nameLabel = new JLabel("Name: " + productDetails[1]);
                    productItemPanel.add(nameLabel);
                    
                    JLabel priceLabel = new JLabel("Price: " + productDetails[2]);
                    productItemPanel.add(priceLabel);

                    // عرض الوصف
                    JLabel descriptionLabel = new JLabel("Description: " + productDetails[3]);
                    productItemPanel.add(descriptionLabel);

                    // إضافة المنتج إلى اللوحة الرئيسية
                    productPanel.add(productItemPanel);
                }
            }
            br.close();
            productPanel.revalidate(); // تحديث اللوحة لعرض المنتجات الجديدة
            productPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////

    private void openBuyFromStoresFrame(String username) {
        JFrame buyFromStoresFrame = new JFrame("Buy from Stores");
        buyFromStoresFrame.setSize(800, 800);
        buyFromStoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buyFromStoresFrame.setLayout(null);

        JLabel selectStoreLabel = new JLabel("Select Store Owner:");
        selectStoreLabel.setBounds(50, 30, 150, 25);
        buyFromStoresFrame.add(selectStoreLabel);

        JComboBox<String> storeOwnerComboBox = new JComboBox<>();
        storeOwnerComboBox.setBounds(200, 30, 250, 25);
        buyFromStoresFrame.add(storeOwnerComboBox);

        JLabel searchLabel = new JLabel("Search Products:");
        searchLabel.setBounds(50, 70, 150, 25);
        buyFromStoresFrame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(200, 70, 250, 25);
        buyFromStoresFrame.add(searchField);

        JLabel filterLabel = new JLabel("Filter by Dietary Needs:");
        filterLabel.setBounds(50, 110, 150, 25);
        buyFromStoresFrame.add(filterLabel);

        JComboBox<String> dietaryFilterComboBox = new JComboBox<>(new String[]{"All", "Gluten-Free", "Nut-Free", "Dairy-Free"});
        dietaryFilterComboBox.setBounds(200, 110, 250, 25);
        buyFromStoresFrame.add(dietaryFilterComboBox);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setBounds(50, 150, 700, 500);
        buyFromStoresFrame.add(scrollPane);

        JButton loadProductsButton = new JButton("Load Products");
        loadProductsButton.setBounds(50, 660, 150, 25);
        buyFromStoresFrame.add(loadProductsButton);

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.setBounds(220, 660, 150, 25);
        buyFromStoresFrame.add(viewCartButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(390, 660, 150, 25);
        buyFromStoresFrame.add(checkoutButton);

        List<String[]> cart = new ArrayList<>();

        loadStoreOwners(storeOwnerComboBox);

        loadProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productPanel.removeAll();
                String selectedOwner = (String) storeOwnerComboBox.getSelectedItem();
                String searchQuery = searchField.getText().toLowerCase();
                String dietaryFilter = (String) dietaryFilterComboBox.getSelectedItem();

                if (selectedOwner != null) {
                    loadProductsForStoreOwner(selectedOwner, searchQuery, dietaryFilter, productPanel, cart);
                }
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder cartContents = new StringBuilder();
                double total = 0;
                for (String[] item : cart) {
                    cartContents.append(item[1]).append(" - $").append(item[2]).append("\n");
                    total += Double.parseDouble(item[2]);
                }
                JOptionPane.showMessageDialog(buyFromStoresFrame, "Cart contents:\n" + cartContents.toString() + "\nTotal: $" + total, "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = cart.stream().mapToDouble(item -> Double.parseDouble(item[2])).sum();

                String selectedOwner = (String) storeOwnerComboBox.getSelectedItem();
                StringBuilder report = new StringBuilder();
                report.append("Purchase Report\n");

                report.append("User: ").append(username).append("\n");
                report.append("Store Owner: ").append(selectedOwner).append("\n");
                report.append("Purchases:\n");

                for (String[] item : cart) {
                    report.append(" | ").append(item[1]).append(" | $").append(item[2]).append("\n");
                }
                report.append("Total Amount: $").append(total).append("\n");

                // Add a separator for different purchase reports
                report.append("\n------------------------------------------------------------\n");

                try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchase_report.txt", true))) {
                    writer.write(report.toString());
                    writer.newLine();
                } catch (IOException ioException) {
                    JOptionPane.showMessageDialog(buyFromStoresFrame,
                        "Error writing to file: " + ioException.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                    ioException.printStackTrace();
                }

                JOptionPane.showMessageDialog(buyFromStoresFrame, 
                    report.toString(), 
                    "Purchase Report", 
                    JOptionPane.INFORMATION_MESSAGE);

                cart.clear();
            }
        });
        buyFromStoresFrame.setVisible(true);
    }

    ///////////////////////////////////////////////////////////

    private void loadProductsForStoreOwner(String storeOwnerName, String searchQuery, String dietaryFilter, JPanel productPanel, List<String[]> cart) {
        try {
            FileReader fr = new FileReader("products.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                if (productDetails.length >= 5 && productDetails[0].equals(storeOwnerName)) {
                    String productName = productDetails[1].toLowerCase();
                    String productDescription = productDetails[3].toLowerCase();
                    String productDietaryInfo = productDetails.length > 5 ? productDetails[5].toLowerCase() : "";

                    boolean matchesQuery = searchQuery.isEmpty() || productName.contains(searchQuery) || productDescription.contains(searchQuery);
                    boolean matchesFilter = dietaryFilter.equals("All") || dietaryFilter.equals(productDietaryInfo);

                    if (matchesQuery && matchesFilter) {
                        JPanel productItemPanel = new JPanel();
                        productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.Y_AXIS));
                        productItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        
                        ImageIcon imageIcon = new ImageIcon(productDetails[4]);
                        Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        productItemPanel.add(imageLabel);
                        
                        JLabel nameLabel = new JLabel("Name: " + productDetails[1]);
                        productItemPanel.add(nameLabel);
                        
                        JLabel priceLabel = new JLabel("Price: " + productDetails[2]);
                        productItemPanel.add(priceLabel);
                        
                        JLabel descriptionLabel = new JLabel("Description: " + productDetails[3]);
                        productItemPanel.add(descriptionLabel);
                        
                        JButton addToCartButton = new JButton("Add to Cart");
                        addToCartButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cart.add(productDetails);
                                JOptionPane.showMessageDialog(productPanel, "Product added to cart.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        });
                        productItemPanel.add(addToCartButton);
                        
                        productPanel.add(productItemPanel);
                    }
                }
            }
            br.close();
            productPanel.revalidate();
            productPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////
    private void loadStoreOwners(JComboBox<String> storeOwnerComboBox) {
        try {
            FileReader fr = new FileReader("users.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length > 0 && userDetails[0].equals("StoreOwner")) {
                    storeOwnerComboBox.addItem(userDetails[1]); // Assuming the name is the second field
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
/////////////////////////////////////////////////////////

   
    
    
    
    
    

    private void openPostFrame(String username, JPanel imagePanel, JFrame roleFrame) {
        JFrame postFrame = new JFrame("Post and Share");
        postFrame.setSize(600, 500);
        postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Post your content", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 25);
        postFrame.add(titleLabel);

        JLabel imageTitleLabel = new JLabel("Image Title:");
        imageTitleLabel.setBounds(20, 60, 100, 25);
        postFrame.add(imageTitleLabel);

        JTextField imageTitleField = new JTextField();
        imageTitleField.setBounds(130, 60, 400, 25);
        postFrame.add(imageTitleField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 100, 100, 25);
        postFrame.add(descriptionLabel);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBounds(130, 100, 400, 100);
        postFrame.add(descriptionArea);

        JLabel imageLabel = new JLabel("Upload Image:");
        imageLabel.setBounds(20, 220, 100, 25);
        postFrame.add(imageLabel);

        JButton uploadButton = new JButton("Choose Image");
        uploadButton.setBounds(130, 220, 150, 25);
        postFrame.add(uploadButton);

        JButton postButton = new JButton("Post");
        postButton.setBounds(250, 420, 100, 25);
        postFrame.add(postButton);

        final String[] imagePath = {null};

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath[0] = selectedFile.getAbsolutePath();
                    JOptionPane.showMessageDialog(postFrame, "Image selected: " + imagePath[0]);
                }
            }
        });

        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagePath[0] != null) {
                    JLabel imageDisplay = new JLabel();
                    ImageIcon imageIcon = new ImageIcon(imagePath[0]);
                    Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                    imageDisplay.setIcon(new ImageIcon(image));

                    JLabel imageDescription = new JLabel(descriptionArea.getText());

                    JButton deleteButton = new JButton("Delete");
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            imagePanel.remove(imageDisplay);
                            imagePanel.remove(imageDescription);
                            imagePanel.remove(deleteButton);
                            imagePanel.revalidate();
                            imagePanel.repaint();
                            deletePost(username, imagePath[0], descriptionArea.getText());
                        }
                    });

                    imagePanel.add(imageDisplay);
                    imagePanel.add(imageDescription);
                    imagePanel.add(deleteButton);

                    savePost(username, imagePath[0], descriptionArea.getText());

                    roleFrame.setSize(roleFrame.getWidth(), roleFrame.getHeight() + 200);
                    roleFrame.revalidate();
                    roleFrame.repaint();

                    postFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(postFrame, "Please select an image to post.");
                }
            }
        });

        postFrame.setVisible(true);
    }



////////////////////////////////////////////////////


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

                User user = userList.stream().filter(u -> u.getName().equals(username)).findFirst().orElse(null);
                if (user != null) {
                    user.setName(newUsername);
                    user.setPassword(newPassword);
                    user.setEmail(newEmail);
                    user.setCountry(newCountry);
                    saveUserData();  // Save updated data to file
                    JOptionPane.showMessageDialog(settingsFrame, "User details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "User not found.");
                }

                settingsFrame.dispose();
            }
        });

        settingsFrame.setVisible(true);
    }
    //////////////////////////////////////////////////
    
    private void loadPosts(String username, JPanel imagePanel) {
        imagePanel.removeAll();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(username + "_posts.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String imagePath = parts[0];
                String description = parts[1];

                JLabel imageDisplay = new JLabel();
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imageDisplay.setIcon(new ImageIcon(image));

                JLabel imageDescription = new JLabel(description);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imagePanel.remove(imageDisplay);
                        imagePanel.remove(imageDescription);
                        imagePanel.remove(deleteButton);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                        deletePost(username, imagePath, description);
                    }
                });

                imagePanel.add(imageDisplay);
                imagePanel.add(imageDescription);
                imagePanel.add(deleteButton);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
 


    
   

    ///////////////////////////////////////////
    
    
    
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
    
    //////////////////////////////////

    private void saveUserData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : userList) {
                writer.println("User|" + user.getName() + "|" + user.getPassword() + "|" + user.getEmail() + "|" + user.getCountry());
            }
            for (StoreOwner storeOwner : storeOwnerList) {
                writer.println("StoreOwner|" + storeOwner.getUsername() + "|" + storeOwner.getPassword() + "|" + storeOwner.getEmail() + "|" + storeOwner.getCountry());
            }
            for (Admin admin : adminList) {
                writer.println("Admin|" + admin.getUsername() + "|" + admin.getPassword() + "|" + admin.getEmail() + "|" + admin.getCountry());
            }
            for (MaterialSupplier materialSupplier : materialSupplierList) {
                writer.println("MaterialSupplier|" + materialSupplier.getUsername() + "|" + materialSupplier.getPassword() + "|" + materialSupplier.getEmail() + "|" + materialSupplier.getCountry());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    ////////////////////////////
    private void savePost(String username, String imagePath, String description) {
        try {
            // حفظ في ملف باسم المستخدم
            BufferedWriter userFileWriter = new BufferedWriter(new FileWriter(username + "_posts.txt", true));
            userFileWriter.write(imagePath + "|" + description);
            userFileWriter.newLine();
            userFileWriter.close();
            
            // حفظ في ملف posts.txt
            BufferedWriter postsFileWriter = new BufferedWriter(new FileWriter("content.txt", true));
            postsFileWriter.write(username + "|" + imagePath + "|" + description);
            postsFileWriter.newLine();
            postsFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //////////////////////////////////
    private void deletePost(String username, String imagePath, String description) {
        try {
            File inputFile = new File(username + "_posts.txt");
            File tempFile = new File(username + "_posts_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(imagePath + "|" + description)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /////////////////////////
    
   

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
}
